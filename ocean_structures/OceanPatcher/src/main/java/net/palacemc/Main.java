/* This script takes the original shipwrecks and ruins structures, and generates new ones with included loot tables.
 * The original NBT files use structure data blocks to tell the generator how to create the loot chests, and to also
 * spawn mobs. Specifically, most of the underwater ruins use structure data blocks to spawn drowned mobs in specific
 * places.
 *
 * But since Minecraft won't touch existing data blocks, we have to generate all of the chests ourselves. Luckily the
 * game will still do the work of making the random loot, we just have to give the right tag on the chests.
 *
 * Here's how chests work normally:
 *   blocks:
 *     -{
 *       nbt:
 *         Items: []
 *         id: "minecraft:chest"
 *     -}
 *
 * Here's how loot chests work:
 *   blocks:
 *     -{
 *       nbt:
 *         id: "minecraft:chest"
 *         LootTable: "minecraft:chests/shipwreck_treasure"
 *         LootTableSeed: 7000719051697139786
 *     -}
 *
 * Minecraft uses Random.nextLong() to make the seeds, so we can just do the same.
 *
 * The plan is to make 10 seed variations of each ship and ruin, as well as to remove the air blocks and set everything
 * to be waterlogged. This will all be underwater, so this must be done.
 */

package net.palacemc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;
import net.minecraft.nbt.*;

public class Main {

    // Relative path of ruins and shipwrecks should be "VanillaTrees\ocean_structures" and this project should be in that path
    private static final Path structurePath = Paths.get("").resolve("../").toAbsolutePath().normalize();

    // Until WorldPainter supports multiple palettes, leave this as 'true'
    private static final boolean splitPalettes = true;

    /** Number of seeded random loot variants to create */
    private static final int LootGenerated = 0;

    /** Number of static random loot variants to create
     *
     * Note: this is only for those who do NOT have naturally generated Minecraft terrain with structures.
     *
     * These specific chests can cause massive lag/ crashes on such worlds:
     *  - Shipwreck map chest (appears in any backhalf/ upsidedown/ full/ with_mast variants only)
     *  - All ruins chest
     *
     * As such, you should only need to use static loot variants for these. But, this generates all static variants for
     * all structures anyway, in case that's what you really want.
     *
     * WARNING!
     * If you set this number too low, it's possible you may generate a world with very predictable loot. For example,
     * you generate only 2 shipwreck variants each, and only one of those contain a specifically enchanted book, then
     * players will discover this quickly and can seek out all enchanted books from those shipwrecks.
     */
    private static final int LootStatic = 16;

    public static void main (String[] args) {
        System.out.println("Working directory: " + structurePath.toString());

        System.out.println("Finding shipwrecks...");
        File dir = structurePath.resolve("shipwreck/default").toFile();
        File[] listing = dir.listFiles();
        assert listing != null;
        ArrayList<File> ships = new ArrayList<>(Arrays.asList(listing));

        int work = ships.size() * (LootStatic + LootGenerated);
        System.out.println("Found " + ships.size() + " shipwrecks, generating " + LootGenerated + "+" + LootStatic + " loot variants and each, totalling " + work + "...");
        int success = 0;

        for (File shipFile : ships) {
            try {
                CompoundTag ship = NbtIo.readCompressed(new FileInputStream(shipFile));

                // First step, fix the palettes by removing the structure blocks and making all stairs/ slabs waterlogged
                ListTag palettes = ship.getList("palettes", 9).copy(); // 9 = List (palettes is a List of Lists)
                int[] newIndex = new int[palettes.getList(0).size()];
                int currIndex = 0;

                // update palettes
                for (int i = 0; i < palettes.size(); i++) {
                    ListTag palette = palettes.getList(i).copy();
                    ListTag newPalette = new ListTag();

                    for (int k = 0; k < palette.size(); k++) {
                        CompoundTag item = palette.getCompound(k).copy();

                        // remove the structure block, set waterlogged
                        if (!item.getString("Name").equalsIgnoreCase("minecraft:structure_block")) {
                            CompoundTag props = item.getCompound("Properties").copy();
                            if (props.contains("waterlogged", TagTypes.STRING)) {
                                props.putString("waterlogged", "true");
                                item.put("Properties", props);
                            }
                            newPalette.add(item);

                            // update states on first run through
                            if (i == 0) {
                                newIndex[k] = currIndex;
                                currIndex++;
                            }
                        } else if (i == 0) {
                            newIndex[k] = -1; // marked for removal
                        }
                    }

                    // update palette
                    palettes.set(i, newPalette);
                }

                // remove structure blocks, store chest locations and type. There can be up to three chests in each ship.
                ArrayList<Integer> chestLocations = new ArrayList<>();
                BlockPos mapChest = null, supplyChest = null, treasureChest = null;

                ListTag blocks = ship.getList("blocks", 10).copy(); // 10 = CompoundTag

                for (int i = 0; i < blocks.size(); i++) {
                    CompoundTag block = blocks.getCompound(i).copy();
                    int index = newIndex[block.getInt("state")];
                    if (index != -1) {
                        block.putInt("state", index);
                        blocks.set(i, block);

                        CompoundTag nbt = block.getCompound("nbt").copy();
                        if (nbt.getString("id").equalsIgnoreCase("minecraft:chest")) {
                            chestLocations.add(i);
                        }

                        currIndex++;
                    } else {
                        // structure block, store location according to type
                        blocks.remove(i--);
                        CompoundTag nbt = block.getCompound("nbt").copy();
                        String type = nbt.getString("metadata");
                        ListTag position = block.getList("pos", 3).copy(); // 3 = IntTag

                        // These data structure blocks are placed on top of the chest they affect
                        BlockPos tempPos = new BlockPos(position.getInt(0), position.getInt(1) - 1, position.getInt(2));

                        if (type.equalsIgnoreCase("map_chest")) {
                            mapChest = tempPos;
                        } else if (type.equalsIgnoreCase("supply_chest")) {
                            supplyChest = tempPos;
                        } else if (type.equalsIgnoreCase("treasure_chest")) {
                            treasureChest = tempPos;
                        }
                    }
                }

                // Match up the chests (Type to ListIndex)
                Map<Integer, Integer> matches = Maps.newHashMap();
                for (int loc : chestLocations) {
                    CompoundTag block = blocks.getCompound(loc).copy();
                    ListTag pos = block.getList("pos", 3).copy(); // 3 = IntTag
                    BlockPos blockPos = new BlockPos(pos.getInt(0), pos.getInt(1), pos.getInt(2));
                    if (blockPos.equals(mapChest)) {
                        matches.put(0, loc);
                    } else if (blockPos.equals(supplyChest)) {
                        matches.put(1, loc);
                    } else if (blockPos.equals(treasureChest)) {
                        matches.put(2, loc);
                    }
                }

                // Annoyingly, none of the fences are connected and none of the stairs have the right shape. Cool.
                // We do all this work here so it's completely separate from the rest, mixing it into the above is sure
                // to be more difficult and cause issues.

                // Build the list of blocks by position, to make determining direction easier
                Map<BlockPos, CompoundTag> blockMap = Maps.newHashMap();
                for (int i = 0; i < blocks.size(); i++) {
                    CompoundTag block = blocks.getCompound(i).copy();
                    ListTag pos = block.getList("pos", 3).copy(); // 3 = IntTag
                    int state = block.getInt("state");
                    blockMap.put(
                            new BlockPos(pos.getInt(0), pos.getInt(1), pos.getInt(2), i, state),
                            palettes.getList(0).getCompound(state).copy()
                    );
                }

                StairFenceHelper helper = new StairFenceHelper(blockMap);
                ArrayList<ListTag> newPalettes = new ArrayList<>();

                // initiate palettes
                for (int i = 0; i < palettes.size(); i++)
                    newPalettes.add(new ListTag());

                blockMap.forEach((position, block) -> {
                    int paletteIndex = position.getState();
                    for (int i = 0; i < palettes.size(); i++) {
                        ListTag newPalette = newPalettes.get(i).copy();
                        CompoundTag newEntry = palettes.getList(i).getCompound(paletteIndex).copy();

                        // Fix stairs
                        String shape = helper.getStairShape(position);
                        if (!shape.isEmpty()) {
                            // Update entry
                            CompoundTag props = newEntry.getCompound("Properties").copy();
                            props.putString("shape", shape);
                            newEntry.put("Properties", props);
                        } else {
                            // Fix fences
                            boolean[] connections = helper.getFenceConnections(position);
                            if (connections.length == 4) {
                                // Update entry
                                CompoundTag props = newEntry.getCompound("Properties").copy();
                                props.putString("north", connections[0] ? "true" : "false");
                                props.putString("east", connections[1] ? "true" : "false");
                                props.putString("south", connections[2] ? "true" : "false");
                                props.putString("west", connections[3] ? "true" : "false");
                                newEntry.put("Properties", props);
                            } else if (newEntry.getString("Name").endsWith("_door")){
                                // Fix doors
                                CompoundTag props = newEntry.getCompound("Properties").copy();
                                props.putString("facing", "east"); // lower half is "south" ?!
                                props.putString("hinge", "left");  // lower half is "right" ?!
                                newEntry.put("Properties", props);
                                // I don't even know why it's like this... seriously wtf Minecraft?
                            }
                        }

                        // Add to palette and update the block
                        int loc = -1;
                        for (int k = 0; k < newPalette.size(); k++) {
                            CompoundTag item = newPalette.getCompound(k).copy();
                            if (newEntry.equals(item)) {
                                //System.out.println(item.getString("Name"));
                                //System.out.println(newEntry.getString("Name"));
                                loc = k;
                                break;
                            }
                        }

                        if (loc == -1) {
                            newPalette.add(newEntry);
                            loc = newPalette.size() - 1;
                        }

                        CompoundTag tBlock = new CompoundTag();
                        ListTag pos = new ListTag();
                        pos.add(IntTag.valueOf(position.x));
                        pos.add(IntTag.valueOf(position.y));
                        pos.add(IntTag.valueOf(position.z));

                        tBlock.put("pos", pos);
                        tBlock.putInt("state", loc);

                        blocks.set(position.getIndex(), tBlock);
                        newPalettes.set(i, newPalette);
                    }
                });

                /*for (int i = 0; i < palettes.size(); i++) {
                    System.out.println(i + ": " + palettes.getList(i).size());
                }*/

                for (int i = 0; i < newPalettes.size(); i++) {
                    palettes.set(i, newPalettes.get(i));
                }

                CompoundTag shipLoot = ship.copy();
                // WorldPainter currently doesn't support multiple palettes, so we just use the first palette
                if (splitPalettes) {
                    shipLoot.remove("palettes");
                    shipLoot.put("palette", palettes.getList(0));
                } else {
                    shipLoot.put("palettes", palettes);
                }

                // Now, we make 10 versions with random loot seeds
                ListTag blocksLoot = blocks.copy();
                String shipName = shipFile.getName();
                shipName = shipName.substring(0, shipName.length() - 4); // strip ".nbt"
                Path outPath = shipFile.toPath().resolve("../../"); // Back out of the "default" directory
                Random rng = new Random();
                for (int i = 0; i < LootGenerated; i++) {

                    // LootTable: "minecraft:chests/shipwreck_treasure"
                    // LootTableSeed: 7000719051697139786

                    if (matches.containsKey(0)) {
                        // map
                        CompoundTag chest = blocksLoot.getCompound(matches.get(0)).copy();
                        CompoundTag nbt = new CompoundTag();
                        nbt.putString("id", "minecraft:chest");
                        nbt.putString("LootTable", "minecraft:chests/shipwreck_map");
                        nbt.putLong("LootSeed", rng.nextLong());
                        chest.put("nbt", nbt);

                        blocksLoot.set(matches.get(0), chest);
                    }

                    if (matches.containsKey(1)) {
                        // supply
                        CompoundTag chest = blocksLoot.getCompound(matches.get(1)).copy();
                        CompoundTag nbt = new CompoundTag();
                        nbt.putString("id", "minecraft:chest");
                        nbt.putString("LootTable", "minecraft:chests/shipwreck_supply");
                        nbt.putLong("LootSeed", rng.nextLong());
                        chest.put("nbt", nbt);

                        blocksLoot.set(matches.get(1), chest);
                    }

                    if (matches.containsKey(2)) {
                        // treasure
                        CompoundTag chest = blocksLoot.getCompound(matches.get(2)).copy();
                        CompoundTag nbt = new CompoundTag();
                        nbt.putString("id", "minecraft:chest");
                        nbt.putString("LootTable", "minecraft:chests/shipwreck_treasure");
                        nbt.putLong("LootSeed", rng.nextLong());
                        chest.put("nbt", nbt);

                        blocksLoot.set(matches.get(2), chest);
                    }

                    shipLoot.put("blocks", blocksLoot);

                    Path temp = outPath.resolve("generated/" + shipName + "_" + i + ".nbt");
                    //noinspection ResultOfMethodCallIgnored
                    temp.resolve("../").toFile().mkdirs(); // Ensure directory exists
                    File shipOutTable = temp.toFile();

                    try {
                        NbtIo.writeCompressed(shipLoot, new FileOutputStream(shipOutTable));
                        success++;
                    } catch (IOException ex) {
                        System.err.println("Failed to save NBT file: " + shipOutTable.getPath());
                        ex.printStackTrace();
                    }
                }

                // Generate static loot for those who need it
                for (int i = 0; i < LootStatic; i++) {

                    if (matches.containsKey(0)) {
                        // map
                        CompoundTag chest = blocksLoot.getCompound(matches.get(0)).copy();
                        CompoundTag nbt = new CompoundTag();
                        nbt.putString("id", "minecraft:chest");
                        nbt.put("Items", LootGenerator.Loot.toNBT(LootGenerator.SHIPWRECK_MAP.generateLoot(rng), rng));
                        chest.put("nbt", nbt);

                        blocksLoot.set(matches.get(0), chest);
                    }

                    if (matches.containsKey(1)) {
                        // supply
                        CompoundTag chest = blocksLoot.getCompound(matches.get(1)).copy();
                        CompoundTag nbt = new CompoundTag();
                        nbt.putString("id", "minecraft:chest");
                        nbt.put("Items", LootGenerator.Loot.toNBT(LootGenerator.SHIPWRECK_SUPPLY.generateLoot(rng), rng));
                        chest.put("nbt", nbt);

                        blocksLoot.set(matches.get(1), chest);
                    }

                    if (matches.containsKey(2)) {
                        // treasure
                        CompoundTag chest = blocksLoot.getCompound(matches.get(2)).copy();
                        CompoundTag nbt = new CompoundTag();
                        nbt.putString("id", "minecraft:chest");
                        nbt.put("Items", LootGenerator.Loot.toNBT(LootGenerator.SHIPWRECK_TREASURE.generateLoot(rng), rng));
                        chest.put("nbt", nbt);

                        blocksLoot.set(matches.get(2), chest);
                    }

                    shipLoot.put("blocks", blocksLoot);

                    Path temp = outPath.resolve("static/" + shipName + "_" + i + ".nbt");
                    //noinspection ResultOfMethodCallIgnored
                    temp.resolve("../").toFile().mkdirs(); // Ensure directory exists
                    File shipOutTable = temp.toFile();

                    try {
                        NbtIo.writeCompressed(shipLoot, new FileOutputStream(shipOutTable));
                        success++;
                    } catch (IOException ex) {
                        System.err.println("Failed to save NBT file: " + shipOutTable.getPath());
                        ex.printStackTrace();
                    }
                }

            } catch (IOException ex) {
                System.err.println("Failed to read NBT file: " + shipFile.getPath());
                ex.printStackTrace();
            }
        }

        System.out.println("Successfully created " + success + " shipwrecks, with " + (work - success) + " failures.");

        System.out.println("\nNow building ruins");
        System.out.println("Finding ruins...");
        dir = structurePath.resolve("ruins/default").toFile();
        listing = dir.listFiles();
        assert listing != null;
        ArrayList<File> ruins = new ArrayList<>(Arrays.asList(listing));

        work = ruins.size() * (LootStatic + LootGenerated);
        System.out.println("Found " + ruins.size() + " ruins, generating " + LootGenerated + "+" + LootStatic + " loot variants each, totalling " + work + "...");
        success = 0;

        /* Not all ruins have chests, here are the ones lacking loot:
         *
         * mossy_1
         *
         * Lmfao, that's the only ruin which has no loot chest. Good job LadyAgnes. 47/48
         * Well, I went ahead and added the chest where it should have been if it hadn't been forgotten.
         */

        for (File ruinFile : ruins) {
            try {
                CompoundTag ruin = NbtIo.readCompressed(new FileInputStream(ruinFile));

                ListTag palette = ruin.getList("palette", 10).copy(); // 10 = CompoundTag

                // remap the palette for air and structure block removal
                int[] newIndex = new int[palette.size()];
                int currIndex = 0;
                ListTag newPalette = new ListTag();
                for (int i = 0; i < palette.size(); i++) {
                    CompoundTag item = palette.getCompound(i).copy();
                    String name = item.getString("Name");
                    if (!name.equalsIgnoreCase("minecraft:air") && !name.equalsIgnoreCase("minecraft:structure_block")) {
                        newIndex[i] = currIndex;
                        CompoundTag props = item.getCompound("Properties").copy();
                        if (props.contains("waterlogged", TagTypes.STRING)) {
                            props.putString("waterlogged", "true");
                            item.put("Properties", props);
                        }
                        newPalette.add(item);
                        currIndex++;
                    } else {
                        newIndex[i] = -1; // marked for removal
                    }
                }

                // Must add waterlogged chest to the palette, east seems to be the best direction, judging by the ruins
                CompoundTag chestTemplate = new CompoundTag();
                CompoundTag chestProperties = new CompoundTag();

                chestProperties.putString("facing", "east");
                chestProperties.putString("type", "single");
                chestProperties.putString("waterlogged", "true");

                chestTemplate.put("Properties", chestProperties);
                chestTemplate.putString("Name", "minecraft:chest");

                newPalette.add(chestTemplate);
                int chestIndex = newPalette.size() - 1; // for later

                palette = newPalette;
                CompoundTag ruinLoot = ruin.copy();
                ruinLoot.put("palette", palette);

                // Update blocks, replacing chest structure blocks with empty chests
                ListTag blocks = ruin.getList("blocks", 10).copy(); // 10 = CompoundTag
                ListTag newBlocks = new ListTag();

                // There's only ever 1 chest in all ruins
                int chestLocation = -1;
                for (int i = 0; i < blocks.size(); i++) {
                    CompoundTag block = blocks.getCompound(i).copy();

                    int index = newIndex[block.getInt("state")];
                    if (index != -1) {
                        block.putInt("state", index);
                        newBlocks.add(block);
                    } else if (chestLocation == -1){
                        // Check for chest structure block
                        if (block.getCompound("nbt").getString("metadata").equalsIgnoreCase("chest")) {
                            block.putInt("state", chestIndex);
                            newBlocks.add(block);
                            chestLocation = newBlocks.size() - 1;
                        }
                    }
                }

                blocks = newBlocks;

                // Now, we make 10 versions with random loot seeds
                String ruinName = ruinFile.getName();
                ruinName = ruinName.substring(0, ruinName.length() - 4); // strip ".nbt"
                Path outPath = ruinFile.toPath().resolve("../../"); // Back out of the "default" directory
                Random rng = new Random();
                boolean isBig = ruinFile.getName().startsWith("big");
                for (int i = 0; i < LootGenerated; i++) {

                    // LootTable: "minecraft:chests/underwater_ruin_small"
                    // LootTableSeed: 7000719051697139786
                    CompoundTag chest = blocks.getCompound(chestLocation).copy();
                    CompoundTag nbt = new CompoundTag();
                    nbt.putString("id", "minecraft:chest");
                    if (isBig)
                        nbt.putString("LootTable", "minecraft:chests/underwater_ruin_big");
                    else
                        nbt.putString("LootTable", "minecraft:chests/underwater_ruin_small");
                    nbt.putLong("LootSeed", rng.nextLong());
                    chest.put("nbt", nbt);
                    blocks.set(chestLocation, chest);
                    ruinLoot.put("blocks", blocks);

                    Path temp = outPath.resolve("generated/" + ruinName + "_" + i + ".nbt");
                    //noinspection ResultOfMethodCallIgnored
                    temp.resolve("../").toFile().mkdirs(); // Ensure directory exists
                    File ruinOut = temp.toFile();

                    try {
                        NbtIo.writeCompressed(ruinLoot, new FileOutputStream(ruinOut));
                        success++;
                    } catch (IOException ex) {
                        System.err.println("Failed to save NBT file: " + ruinOut.getPath());
                        ex.printStackTrace();
                    }
                }

                // Generate static loot for those who need it
                for (int i = 0; i < LootStatic; i++) {

                    CompoundTag chest = blocks.getCompound(chestLocation).copy();
                    CompoundTag nbt = new CompoundTag();
                    nbt.putString("id", "minecraft:chest");
                    if (isBig)
                        nbt.put("Items", LootGenerator.Loot.toNBT(LootGenerator.RUIN_BIG.generateLoot(rng), rng));
                    else
                        nbt.put("Items", LootGenerator.Loot.toNBT(LootGenerator.RUIN_SMALL.generateLoot(rng), rng));
                    chest.put("nbt", nbt);
                    blocks.set(chestLocation, chest);
                    ruinLoot.put("blocks", blocks);

                    Path temp = outPath.resolve("static/" + ruinName + "_" + i + ".nbt");
                    //noinspection ResultOfMethodCallIgnored
                    temp.resolve("../").toFile().mkdirs(); // Ensure directory exists
                    File ruinOut = temp.toFile();

                    try {
                        NbtIo.writeCompressed(ruinLoot, new FileOutputStream(ruinOut));
                        success++;
                    } catch (IOException ex) {
                        System.err.println("Failed to save NBT file: " + ruinOut.getPath());
                        ex.printStackTrace();
                    }
                }

            } catch (IOException ex) {
                System.err.println("Failed to read NBT file: " + ruinFile.getPath());
                ex.printStackTrace();
            }
        }

        System.out.println("Successfully created " + success + " ruins, with " + (work - success) + " failures.");

        System.out.println("Done!");

    }

}

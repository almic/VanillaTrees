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

import java.io.*;
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
    private static Path structurePath = Paths.get("").resolve("../").toAbsolutePath().normalize();

    // Until WorldPainter supports multiple palettes, leave this as 'true'
    private static boolean splitPalettes = true;

    public static void main (String[] args) {
        System.out.println("Working directory: " + structurePath.toString());

        System.out.println("Finding shipwrecks...");
        File dir = structurePath.resolve("shipwreck/default").toFile();
        File[] listing = dir.listFiles();
        assert listing != null;
        ArrayList<File> ships = new ArrayList<>(Arrays.asList(listing));

        System.out.println("Found " + ships.size() + " shipwrecks, generating 10 loot variants each, totalling " + ships.size() * 10 + "...");
        int success = 0;

        for (File shipFile : ships) {
            try {
                CompoundTag ship = NbtIo.readCompressed(new FileInputStream(shipFile));

                // First step, fix the palettes by removing the structure blocks and making all stairs/ slabs waterlogged
                ListTag palettes = ship.getList("palettes", 9); // 9 = List (palettes is a List of Lists)
                int[] newIndex = new int[palettes.getList(0).size()];
                int currIndex = 0;

                // update palettes
                for (int i = 0; i < palettes.size(); i++) {
                    ListTag palette = palettes.getList(i);
                    ListTag newPalette = new ListTag();

                    for (int k = 0; k < palette.size(); k++) {
                        CompoundTag item = palette.getCompound(k);

                        // remove the structure block, set waterlogged
                        if (!item.getString("Name").equalsIgnoreCase("minecraft:structure_block")) {
                            CompoundTag props = item.getCompound("Properties");
                            if (props.contains("waterlogged", 8)) { // 8 = StringTag
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

                ship.put("palettes", palettes);

                // remove structure blocks, store chest locations and type. There can be up to three chests in each ship.
                ArrayList<Integer> chestLocations = new ArrayList<>();
                BlockPos mapChest = null, supplyChest = null, treasureChest = null;

                ListTag blocks = ship.getList("blocks", 10); // 10 = CompoundTag
                ListTag newBlocks = new ListTag();

                for (int i = 0; i < blocks.size(); i++) {
                    CompoundTag block = blocks.getCompound(i);
                    int index = newIndex[block.getInt("state")];
                    if (index != -1) {
                        block.putInt("state", index);
                        newBlocks.add(block);

                        CompoundTag nbt = block.getCompound("nbt");
                        if (nbt.getString("id").equalsIgnoreCase("minecraft:chest")) {
                            chestLocations.add(newBlocks.size() - 1);
                        }
                    } else {
                        // structure block, store location according to type
                        CompoundTag nbt = block.getCompound("nbt");
                        String type = nbt.getString("metadata");
                        ListTag position = block.getList("pos", 3); // 3 = IntTag

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

                blocks = newBlocks;

                // Match up the chests (Type to ListIndex)
                Map<Integer, Integer> matches = Maps.newHashMap();
                for (int loc : chestLocations) {
                    CompoundTag block = blocks.getCompound(loc);
                    ListTag pos = block.getList("pos", 3); // 3 = IntTag
                    BlockPos blockPos = new BlockPos(pos.getInt(0), pos.getInt(1), pos.getInt(2));
                    if (blockPos.equals(mapChest)) {
                        matches.put(0, loc);
                    } else if (blockPos.equals(supplyChest)) {
                        matches.put(1, loc);
                    } else if (blockPos.equals(treasureChest)) {
                        matches.put(2, loc);
                    }
                }

                // Now, we make 10 versions with random loot seeds
                String shipName = shipFile.getName();
                shipName = shipName.substring(0, shipName.length() - 4); // strip ".nbt"
                Path outPath = shipFile.toPath().resolve("../../"); // Back out of the "default" directory
                Random rng = new Random();
                for (int i = 0; i < 10; i++) {

                    // LootTable: "minecraft:chests/shipwreck_treasure"
                    // LootTableSeed: 7000719051697139786

                    if (matches.containsKey(0)) {
                        // map
                        CompoundTag chest = blocks.getCompound(matches.get(0));
                        CompoundTag nbt = new CompoundTag();
                        nbt.putString("id", "minecraft:chest");
                        nbt.putString("LootTable", "minecraft:chests/shipwreck_map");
                        nbt.putLong("LootSeed", rng.nextLong());
                        chest.put("nbt", nbt);

                        blocks.set(matches.get(0), chest);
                    }

                    if (matches.containsKey(1)) {
                        // supply
                        CompoundTag chest = blocks.getCompound(matches.get(1));
                        CompoundTag nbt = new CompoundTag();
                        nbt.putString("id", "minecraft:chest");
                        nbt.putString("LootTable", "minecraft:chests/shipwreck_supply");
                        nbt.putLong("LootSeed", rng.nextLong());
                        chest.put("nbt", nbt);

                        blocks.set(matches.get(1), chest);
                    }

                    if (matches.containsKey(2)) {
                        // treasure
                        CompoundTag chest = blocks.getCompound(matches.get(2));
                        CompoundTag nbt = new CompoundTag();
                        nbt.putString("id", "minecraft:chest");
                        nbt.putString("LootTable", "minecraft:chests/shipwreck_treasure");
                        nbt.putLong("LootSeed", rng.nextLong());
                        chest.put("nbt", nbt);

                        blocks.set(matches.get(2), chest);
                    }

                    ship.put("blocks", blocks);

                    // WorldPainter currently doesn't support multiple palettes, so we just use the first palette
                    if (splitPalettes) {
                        ship.remove("palettes");
                        ship.put("palette", palettes.getList(0));
                    }

                    Path temp = outPath.resolve("generated/" + shipName + "_" + i + ".nbt");
                    //noinspection ResultOfMethodCallIgnored
                    temp.resolve("../").toFile().mkdirs(); // Ensure directory exists
                    File shipOut = temp.toFile();

                    try {
                        NbtIo.writeCompressed(ship, new FileOutputStream(shipOut));
                        success++;
                    } catch (IOException ex) {
                        System.err.println("Failed to save NBT file: " + shipOut.getPath());
                        ex.printStackTrace();
                    }
                }

            } catch (IOException ex) {
                System.err.println("Failed to read NBT file: " + shipFile.getPath());
                ex.printStackTrace();
            }
        }

        System.out.println("Successfully created " + success + " shipwrecks, with " + (ships.size() * 10 - success) + " failures.");

        System.out.println("\nNow building ruins");
        System.out.println("Finding ruins...");
        dir = structurePath.resolve("ruins/default").toFile();
        listing = dir.listFiles();
        assert listing != null;
        ArrayList<File> ruins = new ArrayList<>(Arrays.asList(listing));

        System.out.println("Found " + ruins.size() + " ruins, generating 10 loot variants each, totalling " + ruins.size() * 10 + "...");
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

                ListTag palette = ruin.getList("palette", 10); // 10 = CompoundTag

                // remap the palette for air and structure block removal
                int[] newIndex = new int[palette.size()];
                int currIndex = 0;
                ListTag newPalette = new ListTag();
                for (int i = 0; i < palette.size(); i++) {
                    CompoundTag item = palette.getCompound(i);
                    String name = item.getString("Name");
                    if (!name.equalsIgnoreCase("minecraft:air") && !name.equalsIgnoreCase("minecraft:structure_block")) {
                        newIndex[i] = currIndex;
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
                ruin.put("palette", palette);

                // Update blocks, replacing chest structure blocks with empty chests
                ListTag blocks = ruin.getList("blocks", 10); // 10 = CompoundTag
                ListTag newBlocks = new ListTag();

                // There's only ever 1 chest in all ruins
                int chestLocation = -1;
                for (int i = 0; i < blocks.size(); i++) {
                    CompoundTag block = blocks.getCompound(i);

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
                for (int i = 0; i < 10; i++) {

                    // LootTable: "minecraft:chests/underwater_ruin_small"
                    // LootTableSeed: 7000719051697139786
                    CompoundTag chest = blocks.getCompound(chestLocation);
                    CompoundTag nbt = new CompoundTag();
                    nbt.putString("id", "minecraft:chest");
                    if (isBig)
                        nbt.putString("LootTable", "minecraft:chests/underwater_ruin_big");
                    else
                        nbt.putString("LootTable", "minecraft:chests/underwater_ruin_small");
                    nbt.putLong("LootSeed", rng.nextLong());
                    chest.put("nbt", nbt);
                    blocks.set(chestLocation, chest);
                    ruin.put("blocks", blocks);

                    Path temp = outPath.resolve("generated/" + ruinName + "_" + i + ".nbt");
                    //noinspection ResultOfMethodCallIgnored
                    temp.resolve("../").toFile().mkdirs(); // Ensure directory exists
                    File ruinOut = temp.toFile();

                    try {
                        NbtIo.writeCompressed(ruin, new FileOutputStream(ruinOut));
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

        System.out.println("Successfully created " + success + " ruins, with " + (ruins.size() * 10 - success) + " failures.");

        System.out.println("Done!");

    }

}

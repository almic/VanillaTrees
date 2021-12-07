/* This script goes through all the saved coral NBT files and strips the extra water in them.
 * We can only do it this way because coral blocks naturally die quite quickly if they are not touching water, and
 * that means if I surrounded them in void blocks I would have only a second before they started dieing. I would have
 * to manually check the save file each time to see if I had saved it fast enough that no corals had died, and I was
 * not willing to spend the time doing that.
 *
 * So instead, I wrote this script which removes the water afterwards, effectively replacing it with void blocks, so I
 * wouldn't have to worry about checking if the coral died too fast.
 *
 * You might be asking "Why not set the random tick rate to 0 so the corals wouldn't die?"
 * I tried that, and the RNG tick has nothing to do with the coral death, they are on a slightly random timer that is
 * fully independent of the RNG ticks. Sad.
 */

package net.palacemc;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.nbt.*;

public class Main {

    // Relative path of corals should be "VanillaTrees\corals" and this project should be in that path
    private static Path coralPath = Paths.get("").resolve("../").toAbsolutePath().normalize();

    public static void main(String[] args) {
        System.out.println("Working directory: " + coralPath.toString());

        System.out.println("Finding corals...");
        ArrayList<File> corals = new ArrayList<>();
        // Remember, the corals are split into 6 shape-type directories for organizational purposes
        for (int i = 1; i <= 6; i++) {
            File dir = coralPath.resolve(String.valueOf(i)).toFile();
            File[] listing = dir.listFiles();
            assert listing != null;
            corals.addAll(Arrays.asList(listing));
        }

        System.out.println("Found " + corals.size() + " corals, processing...");
        int success = 0;

        for (File coralFile : corals) {
            try {

                CompoundTag coral = NbtIo.readCompressed(new FileInputStream(coralFile));
                //CompoundTag coral = NbtIo.read(coralFile);

                /*if (coral == null) {
                    System.err.println("Coral was null at: " + coralFile.getPath());
                    continue;
                }*/

                ListTag palette = coral.getList("palette", 10); // 10 = CompoundTag

                if (palette.size() == 0) {
                    System.err.println("Coral palette was empty at: " + coralFile.getPath());
                    continue;
                }

                // remap the palette for water removal
                int[] newIndex = new int[palette.size()];
                int currIndex = 0;
                ListTag newPalette = new ListTag();
                for (int i = 0; i < palette.size(); i++) {
                    CompoundTag item = palette.getCompound(i);
                    if (!item.getString("Name").equalsIgnoreCase("minecraft:water")) {
                        newIndex[i] = currIndex;
                        newPalette.add(item);
                        currIndex++;
                    } else {
                        newIndex[i] = -1; // marked for removal
                    }
                }

                ListTag blocks = coral.getList("blocks", 10); // 10 = CompoundTag

                if (blocks.size() == 0) {
                    System.err.println("Coral has no blocks at: " + coralFile.getPath());
                    continue;
                }

                // Go through blocks, removing ones that aren't mapped
                ListTag newBlocks = new ListTag();
                for (int i = 0; i < blocks.size(); i++) {
                    CompoundTag block = blocks.getCompound(i);

                    int index = newIndex[block.getInt("state")];
                    if (index != -1) {
                        block.putInt("state", index);
                        newBlocks.add(block);
                    }
                }

                // Attempt to save the new NBT file
                coral.put("palette", newPalette);
                coral.put("blocks", newBlocks);

                try {
                    NbtIo.writeCompressed(coral, new FileOutputStream(coralFile));
                    success++;
                } catch (IOException ex) {
                    System.err.println("Failed to save NBT file: " + coralFile.getPath());
                    ex.printStackTrace();
                }

            } catch (IOException ex) {
                System.err.println("Failed to read NBT file: " + coralFile.getPath());
                ex.printStackTrace();
            }
        }

        System.out.println("Successfully updated " + success + " corals, with " + (corals.size() - success) + " failures.");

    }

}

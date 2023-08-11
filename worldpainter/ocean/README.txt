First thing: only the normal ocean layer brushes were made to mimic vanilla terrain. All the "deep" versions were designed to add gameplay mechanics to the MinecraftEarthMap, of which this pack was primarily made for.
Switching the terrain used by the final layers should be simple, although I think you'll enjoy using the "deep" versions as they add fun things like packed ice, magma, obsidian, prismarine, and sea lanterns.

Second thing: ship/ ruin structures don't properly sit in the ground. by default they will balance precariously on a single block when you load them into WorldPainter. Have no fear, I made a script to fix this for you.
Just look through the project in the "parts" folder here, and type in your own shipwreck/ ruins structure layer files you want to fix. Please, only use it for layers which contain shipwrecks/ ruins and ONLY shipwrecks/ ruins.
You've been warned.

Third thing: by default, these layers use the "static" structures. This is only intended for maps which do NOT include naturally generated terrain with structures. If your map will include naturally generated structures,
I highly recommend changing the brushes to use the normal structure layers. At the very least, you should be safe to use the "mixed" structures even on maps with no Minecraft generated terrain. The reason for all this is
because some loot tables attempt to generate an exploration/ treasure map, resulting in massive lag and crashes if there are no buried treasure/ structures in the world.

These specific chests can cause massive lag/ crashes on such worlds:
  - Shipwreck map chest (appears in any backhalf/ upsidedown/ full/ with_mast variants only)
  - All ruins chest

If you absolutely require static structures and are making a massive map (like even bigger than MinecraftEarthMap), I recommend using the OceanPatcher and generating more static loot variants. The project files for OceanPatcher are only available in the full repository source download. If you downloaded the "Brushes" file, it won't be here. Check the project README.md on the homepage (https://github.com/almic/VanillaTrees/) for details.

-----

Since there's so many ocean biomes, and therefore so many ocean layer brushes, here's the list of terrain settings I used.

Terrain ground cover blob scale is 500% for all terrains

warm
 - sand (10/100), dirt (1/50), clay (1/50), gravel (1/25)
 - kelp (5%)
 - seagrass (25%)
 - sea pickles (1%)
 - corals (60% at 1 per 250 blocks at 50%)

lukewarm
 - sand (5/100), gravel (2/75), dirt (1/50), clay (1/50)
 - kelp (2%)
 - sea pickles (0.25%)
 - seagrass (20%)

ocean (neither hot or cold)
 - sand (5/100), gravel (5/100), dirt (1/50), clay (1/50)
 - seagrass (15%)

cold
 - gravel (7/100), sand (1/75), dirt (1/50), clay (1/50)
 - seagrass (10%)

frozen
 - gravel (4/100), dirt (1/50), clay (1/50)


All deep oceans use their own object layers, as specified in each section

deep cold
 - gravel (50/100), packed ice (10/50), clay (10/50), bone (5/5), prismarine (1/1)
 - seagrass (3%)
 - shipwrecks
 - small ruins (stonebrick and cracked stonebrick)
   - shipwreck:ruin = 1:5
   - 1 object per 16,0000
   - 40% factor cover in final

   - shipwrecks 1% (20%) [static 10%, mixed 16%] 
   - brick      5% (40%) [static 50%]
   - cracked    5% (40%) [static 50%]

deep frozen
 - gravel (50/100), packed ice (10/75), clay (5/30), bone (3/5), prismarine (3/1), sea lantern (1/1)
 - large ruins (stonebrick and cracked stonebrick)
   - small:big = 2:1
   - 1 object per 16,000
   - 50% factor cover in final

   - small brick   1% (33.33%)
   - small cracked 1% (33.33%)
   - big brick     1% (16.67%)
   - big cracked   1% (16.67%)

deep lukewarm
 - sand (1000/100), gravel (300/30), clay (300/30), bone (100/5), obsidian (1/1), magma (1/1)
 - kelp (1%)
 - seagrass (15%)
 - shipwrecks
 - small ruins (warm sandstone and mossy stonebrick)
   - shipwreck:ruin = 5:1
   - warm:mossy = 3:1
   - 1 object per 16,000
   - 20% factor cover in final

   - shipwrecks 32% (80%) [static 320%, mixed 512%]
   - warm       15% (15%) [static 150%]
   - mossy       5% ( 5%) [static  50%]

deep ocean (neither hot or cold)
 - sand (650/100), gravel (650/100), clay (300/30), bone (100/5), obsidian (1/1), magma (1/1)
 - seagrass (8%)
 - medium ruins (stonebrick and mossy only)
   - small:big = 5:1
   - 1 object per 16,000
   - 30% factor cover in final

   - big stone   1% (10%)
   - big mossy   1% (10%)
   - small stone 2% (40%)
   - small mossy 2% (40%)
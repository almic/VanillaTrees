# VanillaTrees (and Structures, too!)

A pack containing 700+ NBT objects of 1.15 vanilla Minecraft trees and structures. Primarily includes layer brushes for WorldPainter, so you can make worlds that look more like vanilla Minecraft. I built this pack to help in the creation of my custom [MinecraftEarthMap](https://github.com/almic/MinecraftEarthMap), forked from [MattiBorchers](https://github.com/MattiBorchers/MinecraftEarthMap). I copied the custom layered Mesa terrain from Matti, so credit for that bit goes to him.

## More than just trees

I've gone through every unique surface biome (from 1.15) and copied its look with layer brushes for WorldPainter, allowing you to make worlds which are nearly indistinguishable from vanilla Minecraft. Every naturally occuring block and plant has been copied, so your worlds will always include everything that normal Minecraft has.

- Bee hives
- Cocoa plants
- Pumpkins and melons
- Bamboo
- Corals (a lot)
- Lilypads
- Every flower
- Mossy rocks
- Layered mesa
- Giant mushrooms
- Shipwrecks
- Underwater ruins

For your WorldPainting pleasure, the layers **do not** add biomes. The variety of biomes is great and you're almost certainly going to want to mix things up. Remember to add your biomes after painting the terrain!

## Directory

1. [Directory](#directory)
2. [How to Use](#how-to-use)
3. [Advanced Use](#advanced-use)
    1. [Modifying Anything](#modifying-anything)
    2. [Modifying Structures/ Layers](#modifying-structures-layers)
    3. [Modifying Loot](#modifying-loot)
4. [TODO](#todo)
5. [Extra Information](#extra-information)
6. [What's not included/ unfixable issues](#whats-not-included-unfixable-issues)
7. [License/ Terms/ Credits](#license-terms-credits)

## How to Use

This section explains how to download and start using the brushes quickly. Luckily for you, a lot of preliminary work has been done to make it easy to just download the brushes and start painting with them. For advanced users who want to modify the layers or change things like the loot in shipwrecks/ underwater ruins, first go through this section, and then read the [Advanced Use](#advanced-use) section below.

I highly recommend following along closely and creating the folders in this section, it makes it easier to seperate your worlds from the custom brushes, and allows you to have modified brushes per-world without disrupting other WorldPainter projects you might have. You'll also have a good project structure and avoid confusion with all the brush files. If you follow this section correctly, you will have a file structure like below:

> ```
> WorldPainter Projects/
> ├─ Brushes/
> │  └─ # extracted files
> │
> └─ My Custom World/
>    ├─ my_custom_world.world
>    └─ custom brushes/
>       └─ # copied layers from "Brushes"
> ```

0. Create a folder to contain everything: your custom worlds and the brushes from this repository. Name it something like `WorldPainter Projects`
1. Go to the [Releases page](https://github.com/almic/VanillaTrees/releases) and download the version you want. Only download the `Brushes` file, do not download the source code!
2. Save this `Brushes.zip` file in your `WorldPainter Projects` folder, and extract it to a new `Brushes` folder. You'll want to access the folder easily if you decide to use it across multiple different projects!
3. Create a folder for your custom WorldPainter project, something like "My Custom World"
4. Create a folder inside the new folder named `custom brushes`, this is where you will copy all the brushes you want from the original `Brushes` folder that you extracted in step 2
5. Open WorldPainter, create a new world, and save it to your "My Custom World" folder. Name it something like "my_custom_world.world"
6. Open a new file explorer window and go to the extracted `Brushes` folder, pick out the brushes you want to use, and **copy** them into your project's `custom brushes` folder. On Windows, you can hold CTRL and click & drag the files to copy them over.

> You should now have a directory that looks something like this, with whatever brushes you selected:
> ```
> My Custom World/
> ├─ my_custom_world.world
> └─ custom brushes/
>    ├─ mega_spruce.layer
>    ├─ oak_bee_forest.layer
>    └─ sunflower_bee_plains.layer
> ```

7. Open WorldPainter, with your custom world file loaded, find the `Layers` pane. It should be at the bottom left of the application.
8. Click the `+` button at the bottom of the list
9. Select `Import custom layer(s) from file...`
10. Browse to your "custom brushes" folder and select the brushes you copied over
11. Click `Open`

> Now you should see something sort of like this in WorldPainter:  
> <img src="/images/layer_window.png" width="176"/>  
> If you imported a lot of layers, you probably won't be able to see them all. I recommend clicking and dragging the "final" tab out to its own window. If you still can't see everything, you'll have to create new palette tabs to hold them.

12. Select a layer from the "final" tab, and paint it where you want it.
13. **Don't forget to add biomes!** This pack does not automatically add biomes!
14. Export world
15. $$$

The other tabs contain the parts for the `final` layer brushes, you should ignore these extra parts. Each layer typically includes a terrain, terrain cover (ex: grass), and objects (ex: trees, boulders). If you really know what you are doing, then you can use these parts for extra control or to make custom layer brushes!

## Advanced Use

This repository contains all the original NBT structures and some mini-projects used to automate the creation of some layers/ objects. None of these are present in the "Brushes.zip" file, you will have to download the full repository source code to access them. **They are NOT necessary to use the layers provided in Brushes.zip!** Before going into detail on how to use these, I'll outline them first.

***NBT data***

[`NBT data`](https://github.com/almic/VanillaTrees/tree/master/NBT%20data). This is all the original (and meticulously acquired) structures, trees, boulders, huge mushrooms, etc. The files follow a naming convention like `name_X_Z_Y_variant`. Within each folder is a README.txt that explains what the names mean. It's been a long while since I created the files and have only just now written down what the naming convention is, so I might actually be wrong about the `X_Z_Y` part. This is the Minecraft XZY axis, where Y is the vertical component and Z is the depth component. All other 3D software uses Z as the vertical and Y as the depth!

***OceanPatcher***

Located in [`NBT Data/ocean_structures/OceanPatcher`](https://github.com/almic/VanillaTrees/tree/master/NBT%20data/ocean_structures/OceanPatcher), this is an automation project that takes the original ruins and shipwreck structures and produces fixed/ mixed loot. Additionally, it patches out the palettes that shipwrecks use because WorldPainter did not support palettes at the time this pack was originally created. And what's more, there are some issues with fences and stairs which Minecraft fixes on world generation, but WorldPainter does not perform those "fixes" when exporting a world, so this also does that.

***CoralFix***

Located in [`NBT Data/corals/CoralFix`](https://github.com/almic/VanillaTrees/tree/master/NBT%20data/corals/CoralFix), goes through the original coral NBT files and removes water from them. For a whole story about the mental damage suffered producing the corals, see the local [README.txt](https://github.com/almic/VanillaTrees/tree/master/NBT%20data/corals) and the comment at the top of the [`Main.java`](https://github.com/almic/VanillaTrees/blob/master/NBT%20data/corals/CoralFix/src/main/java/net/palacemc/Main.java) file.

***DeepOceanStructureFixer***

Located in [`worldpainter/ocean/parts/DeepOceanStructureFixer`](https://github.com/almic/VanillaTrees/tree/master/worldpainter/ocean/parts/DeepOceanStructureFixer), this is another automation project that takes the layer files I originally incorrectly created and fixes them. These original, incorrect, files are not available unless you download the source code, because you definitely don't actually want to use them in any of your projects. They only exist because I made a mistake and instead of fixing it manually, I wrote this mini-project to fix it for me, and left the files for historical purposes.

### Modifying Anything

When you save a `.world` file, all your brushes are saved directly to that file! This is great for creating portable world files, as you only need to worry about the `.world` file itself. Additionally, when you create a combined layer and export it to its own file, **everything** is saved inside that layer file. This is also great for portable layers, you just need the final layer and none of the layers it once referenced.

All these benefits come at a great price: you must be diligent when modifying layers and parts. Always re-export modifications back to the disk, if you want to reuse them in other projects. When you alter a part for a combined layer, you also have to re-export the combined layer to disk, not just the part. The only place where layers reference other layers directly are within the WorldPainter application itself. As soon as something is saved to the disk, it copies every referenced layer into itself so that it's a fully self-contained layer.

### Modifying Structures/ Layers

If you don't know how to create layers with WorldPainter, unfortunately this is not a tutorial on how to do that. This section explains how I used the source files to create the layers, and it assumes you already have some knowledge on how layers work in WorldPainter.

I created all the final layers using the combined layer type, and simply added the parts to that layer, setting their factor as appropriate to mimic vanilla Minecraft as closely as possible. I looked at the original terrain generation code for Minecraft itself to derive almost all of these values, as well as doing a lot of flying around and looking at natural generation with my own eyes to estimate some values that couldn't be easily concluded from the code. Each finalized combined layer uses a terrain, and a combination of ground cover and object layers. Ground cover layers use WorldPainter's ground cover layer type (as evident by the name) to randomly scatter plants and things that naturally spawn on the surface. The values in these ground cover layers were largely derived from Minecraft source code. Object layers contain the bigger stuff that WorldPainter doesn't have in ground cover layers, such as trees, boulders, and structures. These were also adjusted to spawn the trees/ structures according to the rates in Minecraft's source code. In places where the source code was unhelpful, I set the values by eye, and I like to think I have a very good eye. If you want to modify the object layers, may god help you. I created them two years ago and it was a lot of trial and error. Thankfully, I discovered you can select every object from the list and modify their values at the same time, but you have to be careful.

To match the spawn rates of trees, such as the default 5% of oak trees being "bubble" trees, I had to do some math. Details can be found within the README.txt files scattered everywhere in the project files. Those files are informational and were instrumental in accurately calculating the relative percentage values for object layers.

Some things, like the Jungle and Birch forest layers, offer alternatives such as without cocoa beans or beehives. To use these, you'll have to manually swap them out in the combined layers. To do this, you'll import that alternative tree/ object part layer into WorldPainter exactly the same way you did with the final brushes. Then, open the combined layer and swap out the layer with that alternative. It should then work exactly as advertised, but I'd recommend double checking the layers and values with the associated README.txt file in the folder.

### Modifying Loot

The core purpose of the OceanPatcher is to prepare the original shipwreck and ruin structures for use in WorldPainter. If you export a world with no natural terrain, then it's important to generate mixed/ static loot for shipwrecks and ruins. The problem is that Treasure Maps cause Minecraft to search for Burried Treasure in the world, and if there is no naturally occuring Burried Treasure, the server lags significantly as it searches very very very far in a single game tick before defaulting to an Empty map, and can crash in the process.

By default, the provided layers have mostly randomly generate LootSeeds. This means that the loot is generated *by Minecraft itself* at the time when a player first opens the container. From my memory, Minecraft uses the World Seed and the LootSeed together, so you should get truly original random Minecraft loot from shipwrecks and ruins. However, some chests which may contain Treasure Maps are pre-filled with random stuff, using the original method as Minecraft itself, so the loot is 1:1 with vanilla 1.15 Minecraft with the exception that all Treasure Maps will be replaced with a normal Map. If you want to use custom loot tables, or generate static loot with more variety, you'll want to use the OceanPatcher project. I have written a lot of comments in the code, and used a format identical to the loot table JSON files in resource packs, so advanced users should be able to easily modify the code/ loot to get what they want. The key files you'll modify located in `src/main/java/net/palacemc/` called `Main.java` and `LootGenerator.java`, the rest you shouldn't need to mess with.

I use a naming convention of "mixed" and "static". Mixed layers use seeded loot chests where possible, and pre-filled containers when a Treasure Map is a potential item. Static layers only use pre-filled containers. Despite being pre-filled, there should be enough variety that players shouldn't notice recurring loot. If you really wanna go crazy, you can alter the loot tables and generate a lot of static loot.

After generating the new loot, you'll need to manually create new object layers in WorldPainter and load them in. See the [Modifying Structures/ Layers](#modifying-structures-layers) section for more information. Good luck, by the way, as this step is tedious and you can easily make a mistake.

If you want to contribute a better/ easier system for doing this, please create and Issue and explain it, or add me on Discord. My username is listed at the end of this file.

## TODO:

Here's the things I haven't added yet but would like to in the future:

- [ ] Bamboo forest
- [ ] Ice spikes
- [ ] Glaciers
- [ ] Mushroom fields
- [ ] 1.18+ terrain and trees
- [ ] Shipwreck palettes *(see section below)*
- [X] ~~Underwater ruins~~
- [X] ~~Shipwrecks~~

## Extra Information

Every single folder contains a README which explains what's in that folder, how it is organized, and what you can do with it. I suggest you poke around and read some of these files, there's some good information in some of them. Some layers/ NBTs are more "special" than others, so if you notice something "special," and want to understand it better, you can likely find out in the README file associated with it.

## What's not included/ unfixable issues

**This pack does not and will not include complex/ joined structures like villages or strongholds.** At the moment, I have no plans to include these because of how complex they are, as they use hundreds of small pieces and connect them at various heights. If you want custom strongholds or villages, you're best doing it manually in your world by loading the structure pieces, and not trying to use WorldPainter.

**Shipwreck palettes** are not finished currently. Despite the name of this section, I actually do plan to fix this. Currently, WorldPainter does not support NBT structures with multiple palettes, something that all shipwrecks use heavily. Trying to import my shipwrecks just causes errors and does not work due to the difference between the normal single-palette structure and the shipwrecks' multi-palette structure. I've already made a [pull request that should fix this issue](https://github.com/Captain-Chaos/WorldPainter/pull/149), and I'm just waiting for it to make it to the release version.

Anyway, here's some problems with this pack that I will not be fixing, because they are simply too difficult or I don't see them as an actual problem worth fixing:

- **Trees generating impossibly close, occasionally making solid walls of logs.** This is a limitation of WorldPainter, as it does not allow "minimum distances" between objects on custom object layers. If WorldPainter introduces this feature, I will update this pack immediately.
- **Swamp trees generate weird, vines sometimes go through leaves of other trees.** This is caused by the same issue above. When WorldPainter allows me to fix this problem, I will do so.
- **Weird corals stuff.** Once again the same issue as above. There's nothing I can really do about it, but kelp will generate inside corals, sometimes resulting in impossible configurations where kelp is growing on top of seagrass or fans generated by the corals. I reordered the layer to remove this issue from generating kelp on seagrass or sea pickles normally added to the terrain, but sadly kelp completely ignores the custom objects no matter what.
- **Vines do not spawn on smaller jungle trees.** Normally they have vines, but I decided to not include the vines, because when growing these trees in normal Minecraft, vines do not generate like they do during world generation. However, the huge jungle trees all include vines.
- **Biomes on layer brushes.** I do not include this because there are too many variations of the biomes (hills, plateaus, etc.) and it's easier if you decide exactly what biome you want after painting the terrain.
- **Final layers without bee hives.** All the finalized (trees and ground cover) oak/ birch/ plains layer brushes I made include rare bee hives. If you don't want the bee hives, duplicate the combined layer and change the trees to the version without bee hives. Since most people are on 1.15+ now, there's no good reason for you to *not* be using the bee hives.
- **Flowers don't generate in "patches" like normal Minecraft.** There's literally nothing I can do about it. WorldPainter should honestly generate flowers in patches by default, but it unfortunately does not.

## License/ Terms/ Credits

All intellectual property of Minecraft belongs to Mojang AB. I do not take credit for any of this, except the work of making the collection, I suppose. When using these files, you must adhere to the terms and licenses of Minecraft. If you want to give me a shoutout when using these files, go ahead and do so, but you don't have to.

Just in case, my discord username is `almictm`. I check it often and accept friend requests, so you can add me and message me.

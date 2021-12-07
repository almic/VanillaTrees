# VanillaTrees

A pack containing 600+ NBT objects of 1.15+ vanilla Minecraft trees. Also includes layer brushes for WorldPainter, so you can make worlds that look more like Minecraft. I built this pack to help in the creation of my custom [MinecraftEarthMap](https://github.com/almic/MinecraftEarthMap), forked from [MattiBorchers](https://github.com/MattiBorchers/MinecraftEarthMap). I copied the custom layered Mesa terrain from Matti, so credit for that bit goes to him.

## More than just trees

I've gone through every unique biome and copied it's look by creating layer brushes for WorldPainter, allowing you to make worlds which are nearly indistinguishable from vanilla Minecraft. Every naturally occuring block and plant has been copied, so your worlds will always include everything that
normal Minecraft has.

- Bee hives
- Cocoa plants
- Pumpkins and melons
- Bamboo
- Corals
- Lilypads
- Every flower
- Mossy rocks
- Layered mesa
- Giant mushrooms
- Shipwrecks
- Underwater ruins

## How to use

Open the `vanilla_trees_test.world` in WorldPainter to get a quick look at the layer brushes included with this pack.

A lot of premade layer brushes are included, which are in the `worldpainter` folder of this repository. If you just want the premade stuff, you need ONLY the `*.layer` files. You do not need anything from the `parts` folders. Those are just there if you want to have more specific control over exactly what you are painting, like perhaps a desert terrain but with swamp trees (if you are crazy like that).

Anyway, here's a basic guide for using the layer brushes.

1. Create a new folder for your work files, like "My Custom World"
2. Create a new world on WorldPainter, save it to your "My Custom World" folder
3. Create a new folder in your world folder, name it something like "brushes"
4. Download the `.layer` files you want and save them to the "brushes" folder

> You should now have a directory kinda like this:
> ```
> My Custom World/
> ├─ my_world.world
> └─ brushes/
>    ├─ mega_spruce.layer
>    ├─ oak_bee_forest.layer
>    └─ sunflower_bee_plains.layer
> ```

5. Go back to WorldPainter, find the Layers pane. It should be at the bottom left of the application.
6. Click the `+` button at the bottom of the list
7. Select `Import custom layer(s) from file...`
8. Browse to your "brushes" folder and select everything you want to use
9. Click `Open`

> Now you should see something sort of like this:  
> <img src="/images/layer_window.png" width="176"/>

10. Select a layer from the "final" tab, and paint it where you want it.
11. **Don't forget to add biomes!** This pack does not automatically add biomes!
12. Export world
13. $$$

I suggest only using the layers in the "final" tab. Those extra tabs are just the "parts" of each layer. Each layer typically includes a terrain, terrain cover (ex: grass), and objects (ex: trees, boulders) You shouldn't use those individually unless you really know what you are doing!

A lot of the folders include additional README files to help you understand how to use the provided brushes, and some include details on how those brushes were created.

## TODO:

Here's the things I haven't added yet but would like to in the future:

- [ ] Bamboo forest
- [ ] Ice spikes
- [ ] Glaciers
- [ ] Mushroom fields
- [X] ~~Shipwrecks & buried treasure *(see section below)*~~
- [X] ~~Underwater ruins (would add prismarine, currently lacking in this pack)~~

## What's not included/ unfixable issues

**This pack does not and will not include complex structures like villages or strongholds.** At the moment, I have no plans to include these because of how complex they are, as they use hundreds of small pieces and connect them at various heights. If you want custom strongholds or villages, you're best doing it manually in your world, rather than loading custom structure pieces or trying to use WorldPainter.

**Shipwrecks & buried treasure** are not finished currently. Despite the name of this section, I plan to fix this. Currently, WorldPainter does not support NBT structures with multiple palettes, something that all shipwrecks use heavily. Trying to import my shipwrecks just causes errors and does not work due to the difference between the normal single-palette structure and the shipwrecks' multi-palette structure. I've already made a [pull request that should fix this issue](https://github.com/Captain-Chaos/WorldPainter/pull/149), and I'm just waiting for it to make it to the release version.

Anyway, here's some problems with this pack that I will not be fixing, because they are simply too difficult or I don't see them as an actual problem worth fixing:

- **Trees generating impossibly close, occasionally making solid walls of logs.** This is a problem with WorldPainter, as it does not allow "minimum distances" between objects on custom object layers. If WorldPainter introduces this feature, I will update this pack immediately.
- **Swamp trees generate weird, vines sometimes go through leaves of other trees.** This is caused by the same issue above. When WorldPainter allows me to fix this problem, I will do so.
- **Weird corals stuff.** Once again the same issue as above. There's nothing I can really do about it, but kelp will generate inside corals, sometimes resulting in impossible configurations where kelp is growing on top of seagrass or fans generated by the corals. I reordered the layer to remove this issue from generating kelp on seagrass or sea pickles normally added to the terrain, but sadly kelp completely ignores the custom objects no matter what.
- **Vines do not spawn on smaller jungle trees.** Normally they have vines, but I decided to not include the vines, because when growing these trees in normal Minecraft, vines do not generate like they do during world generation. However, the huge jungle trees all include vines.
- **Biomes on layer brushes.** I do not include this because there are too many variations of the biomes (hills, plateaus, etc.) and it's easier if you decide exactly what biome you want after painting the terrain.
- **Final layers without bee hives.** All the finalized (trees and ground cover) oak/ birch/ plains layer brushes I made include rare bee hives. If you don't want the bee hives, duplicate the combined layer and change the trees to the version without bee hives. Since most people are on 1.15+ now, there's no good reason for you to *not* be using the bee hives.
- **Flowers don't generate in "patches" like normal Minecraft.** There's literally nothing I can do about it. WorldPainter should honestly generate flowers in patches by default, but it unfortunately does not.

## License/ Terms/ Credits

All intellectual property of Minecraft belongs to Mojang AB. I do not take credit for any of this, except the work of making the collection, I guess. When using these files, you must adhere to the terms and licenses of Minecraft. If you want to give me a shoutout when using these files, go ahead, but you don't have to.

Just in case, my twitter is [@AlmicOrNothing](https://twitter.com/AlmicOrNothing).

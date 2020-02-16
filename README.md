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

### TODO:

Here's the things I haven't added yet but would like to in the future:

- [ ] Bamboo forest
- [ ] Ice spikes
- [ ] Glaciers
- [ ] Mushroom fields
- [ ] Shipwrecks & buried treasure
- [ ] Underwater ruins (would add prismarine, currently lacking in this pack)

### What's not included/ unfixable issues

**This pack does not and will not include complex structures like villages or strongholds.** At the moment, I have no plans to include these because of how complex they are, as they use hundreds of small pieces and connect them at various heights. If you want custom strongholds or villages, you're best doing it manually in your world, rather than loading custom structure pieces or trying to use WorldPainter.

**Shipwrecks & buried treasure** are not complex structures. Ships have about 4-6 different shapes (half ships, upsidedown, sideways, right side up). And buried treasure uses special NBT data which can be copied easily. Also, they contain a variety of potential loot and special treasure maps. Thankfully, these treasure maps can detect buried treasure thanks to the special NBT data on the chests. But, in order to properly include the variety of potential loot than can naturally generate, I will be forced to make a script which generates random loot. This will result in hundreds of different shipwreck and buried treasure NBT files, which I simply will not create manually. For the moment, these will probably be the last things I add to this pack.

Anyway, here's some problems with this pack that I will not be fixing, because they are simply too difficult or I don't see them as an actual problem worth fixing:

- **Trees generating impossibly close, occasionally making solid walls of logs.** This is a problem with WorldPainter, as it does not allow "minimum distances" between objects on custom object layers. If WorldPainter introduces this feature, I will update this pack immediately.
- **Swamp trees generate weird, vines sometimes go through leaves of other trees.** This is caused by the same issue above. When WorldPainter allows me to fix this problem, I will do so.
- **Vines do not spawn on smaller jungle trees.** Normally they have vines, but I decided to not include the vines, because when growing these trees in normal Minecraft, vines do not generate like they do during world generation. However, the huge jungle trees all include vines.
- **Biomes on layer brushes.** I do not include this because there are too many variations of the biomes (hills, plateaus, etc.) and it's easier if you decide exactly what biome you want after painting the terrain.
- **Final layers without bee hives.** All the finalized (trees and ground cover) oak/ birch/ plains layer brushes I made include rare bee hives. If you don't want the bee hives, duplicate the combined layer and change the trees to the version without bee hives. Since most people are on 1.15+ now, there's no good reason for you to *not* be using the bee hives.
- **Flowers don't generate in "patches" like normal Minecraft.** There's literally nothing I can do about it. WorldPainter should honestly generate flowers in patches by default, but it unfortunately does not.

## License/ Terms/ Credits

All intellectual property of Minecraft belongs to Mojang AB. I do not take credit for any of this, except the work of making the collection, I guess. When using these files, you must adhere to the terms and licenses of Minecraft. If you want to give me a shoutout when using these files, go ahead, but you don't have to.

Just in case, my twitter is [@AlmicOrNothing](https://twitter.com/AlmicOrNothing).

All the "XXX_trees.layer" files should be ignored unless you truly just want trees and no grass, flowers, or sugarcane.

I suggest using just "dark_oak_forest.layer", "birch_bee_forest.layer" and "oak_birch_bee_forest.layer"
These are the most vanilla types, which modify both the terrain (grass and flowers) and add the trees.

Since the only way to add these trees is through the WorldPainter object layer, which uses relative percentages,
I had to fiddle around to find the "most vanilla" percentages.

For oak forests, 90% of trees are normal, 5% are large, and 5% are the special "bubble" type.
For oak/birch forests, 88% of trees are normal, 5% are large, 5% are "bubble" and 2% are birch.
When including bee trees, everything is the same except 0.1% are birch trees with a beehive, and 2% are birch with no beehive.
If you plan on adding beehives, don't forget to add some flowers nearby.

To make these work with WorldPainter's "relative percentage," here's the values for each layer:

Oak only:
  oak1: 96%  (1728 out of 1920, 90%)
  oak2:  3%  (96 out of 1920, 5%)
  oak3: 32%  (96 out of 1920, 5%)
sparseness: 128 blocks (36 at 100%)

Oak and birch (no bees):
  oak1:   96% (1728 out of 1968, 87.805%)
  oak2:    3% (96 out of 1968, 4.878%)
  oak3:   32% (96 out of 1968, 4.878%)
  birch1:  4% (48 out of 1968, 2.439%)
sparseness: 128 blocks (36 at 100%)

Oak and birch, with bees:
  oak1:  192% (3456 out of 3930, 87.939%)
  oak2:    6% (192 out of 3930, 4.885%)
  oak3:   64% (192 out of 3930, 4.885%)
  birch1:  7% (84 out of 3930, 2.137%)
  birch2:  1% (6 out of 3930, 0.153%)
birch to bee, 14/15 birch, 1/15 with bees
sparseness: 128 blocks (36 at 100%)

Birch only forest with bees:
  birch1: 49% (588 out of 600, 98%)
  birch2:  2% (12 out of 600, 2%)
sparseness: 169 blocks (48 at 100%)

Dark oak, oak, birch, huge mushroom, even bigger mushroom:
  darkoak1:  165% (1980 out of 2406, 82.294%)
  oak1:       17% (306 out of 2406, 12.718%)
  birch:       5% (60 out of 2406, 2.494%)
  mush1 & 3:   8% (48 out of 2406, 1.995%)
  mush2 & 4:   2% (12 out of 2406, 0.499%)
sparseness: 64 blocks (18 at 100%)


Since the only way to add these trees is through the WorldPainter object layer, which uses relative percentages, I had to fiddle around to find the most vanilla percentages.

For oak forests, 90% of trees are normal, 5% are large, and 5% are the special "bubble" type.
For oak/birch forests, 88% of trees are normal, 5% are large, 5% are "bubble" and 2% are birch.
When including bee trees, everything is the same except 0.1% are birch trees with a beehive, and 2% are birch with no beehive.
If you plan on adding beehives, don't forget to add some flowers nearby.

For jungles with huge trees, 72% of trees are normal, 10% are huge, and 18% would be normal with cocoa
Without huge trees, 3/4 would be normal, and 1/4 would have cocoa

To make these work with WorldPainter's "relative percentage," here's the values for each layer:

Oak only:
  oak1: 96%  (1728 out of 1920, or 90%)
  oak2:  3%  (96 out of 1920, or 5%)
  oak3: 32%  (96 out of 1920, or 5%)

Oak and birch (no bees):
  oak1:   96% (1728 out of 1968, or 87.805%)
  oak2:    3% (96 out of 1968, or 4.878%)
  oak3:   32% (96 out of 1968, or 4.878%)
  birch1:  4% (48 out of 1968, or 2.439%)

Oak and birch, with bees:
  oak1:  192% (3456 out of 3930, or 87.939%)
  oak2:    6% (192 out of 3930, or 4.885%)
  oak3:   64% (192 out of 3930, or 4.885%)
  birch1:  7% (84 out of 3930, or 2.137%)
  birch2:  1% (6 out of 3930, or 0.153%)
birch to bee, 14/15 birch, 1/15 with bees

Birch only forest with bees:
  birch1: 49% (588 out of 600, or 98%)
  birch2:  2% (12 out of 600, or 2%)

Jungle with huge, no cocoa:
  jungle1: 8% (288 out of 320, or 90%)
  jungle2: 1% (32 out of 320, or 10%)

Jungle with huge, cocoa:
  jungle1: 32% (216 out of 302, or 72%)
  jungle2: 5% (32 out of 302, or 10%)
  jungle3: 16% (54 out of 302, or 18%)
normal to cocoa, 3/4 normal & 1/4 cocoa

Jungle, no huge, with cocoa:
  jungle1: 3 (108 out of 144, or 75%)
  jungle3: 2 (36 out of 144, or 25%)

Mega spruce:
  spruce1: 21% (504 out of 560, or 90%)
  spruce2:  4% (56 out of 560, or 10%)

Mega taiga: (more sparse than spruce)
  spruce1: 3% (36 out of 48, or 75%)
  spruce2: 2% (12 out of 48, or 25%)

Hope this clears stuff up.

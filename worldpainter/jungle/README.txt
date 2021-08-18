All the "XXX_trees.layer" and "XXX_cover.layer" files should be ignored.
I suggest using just the "jungle_edge_cocoa.layer" and "jungle_cocoa.layer"
These are the most vanilla types, which modify both the terrain ("cover," grass, flowers, bamboo, melons) and add the trees ("trees," of course).

Since the only way to add these trees is through the WorldPainter object layer, which uses relative percentages,
I had to fiddle around to find the "most vanilla" percentages.

For jungles with huge trees, 72% of trees are normal, 10% are huge, and 18% would be normal with cocoa
Without huge trees, 3/4 would be normal, and 1/4 would have cocoa

To make these work with WorldPainter's "relative percentage," here's the values for each layer:

Jungle, no cocoa:
  jungle1: 344% (12384 out of 14400, 86%)
  jungle2:  45% (1440 out of 14400, 10%)
  oak2:      9% (288 out of 14400, 2%)
  oak3:     96% (288 out of 14400, 2%)
sparseness: 84 blocks (24 at 100%)

Jungle with cocoa:
  jungle1: 280% (10080 out of 14400, 70%)
  jungle2:  45% (1440 out of 14400, 10%)
  jungle3: 128% (2304 out of 14400, 16%)
  oak2:      9% (288 out of 14400, 2%)
  oak3:     96% (288 out of 14400, 2%)
normal to cocoa, 35/43 normal & 8/43 cocoa, ~81% to ~19%, or ~4/5 to ~1/5
sparseness: 84 blocks (24 at 100%)

Jungle edge with cocoa:
  jungle1: 192% (6912 out of 9600, 72%)
  jungle3:  96% (1728 out of 9600, 18%)
  oak2:     15% (480 out of 9600, 5%)
  oak3:    160% (480 out of 9600, 5%)
sparseness: 526 blocks (150 at 100%)

Jungle edge, no cocoa:
  jungle1: 48% (1728 out of 1920, 90%)
  oak2:     3% (96 out of 1920, 5%)
  oak3:    32% (96 out of 1920, 5%)
sparseness: 526 blocks (150 at 100%)

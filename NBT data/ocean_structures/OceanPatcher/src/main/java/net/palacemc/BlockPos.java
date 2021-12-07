// Helper class for shipwreck chest positions
package net.palacemc;

class BlockPos {
    int x, y, z;
    private int index, state;

    BlockPos () {
        x = 0;
        y = 0;
        z = 0;
        index = 0;
    }

    BlockPos (int x, int y, int z) {
        this();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    BlockPos (int x, int y, int z, int index, int state) {
        this(x, y, z);
        this.index = index;
        this.state = state;
    }

    public int getIndex() {
        return index;
    }

    public int getState() {
        return state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj instanceof BlockPos other) {
            return (this.x == other.x) && (this.y == other.y) && (this.z == other.z);
        }

        return false;
    }

    public BlockPos relative(Direction direction) {
        return switch (direction) {
            case NORTH -> new BlockPos(x, y, z - 1);
            case EAST -> new BlockPos(x + 1, y, z);
            case SOUTH -> new BlockPos(x, y, z + 1);
            case WEST -> new BlockPos(x - 1, y, z);
        };
    }

    public BlockPos simple() {
        return new BlockPos(x, y, z);
    }
}

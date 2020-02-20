// Helper class for shipwreck chest positions
package net.palacemc;

class BlockPos {
    private int x, y, z;

    BlockPos () {
        x = 0;
        y = 0;
        z = 0;
    }

    BlockPos (int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj instanceof BlockPos) {
            BlockPos other = (BlockPos) obj;
            return (this.x == other.x) && (this.y == other.y) && (this.z == other.z);
        }

        return false;
    }
}

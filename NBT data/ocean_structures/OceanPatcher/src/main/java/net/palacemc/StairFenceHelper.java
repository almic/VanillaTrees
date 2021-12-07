// Helps determine the shape of stairs.

package net.palacemc;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;

import java.util.Map;

public record StairFenceHelper(Map<BlockPos, CompoundTag> blocks) {

    public StairFenceHelper(Map<BlockPos, CompoundTag> blocks) {
        // strip index and state, they only screw this up
        this.blocks = Maps.newHashMap();
        blocks.forEach((position, block) -> this.blocks.put(position.simple(), block));
    }

    public enum StairsShape {
        OUTER_LEFT,
        OUTER_RIGHT,
        INNER_LEFT,
        INNER_RIGHT,
        STRAIGHT;

        public String asString() {
            return switch (this) {
                case OUTER_LEFT -> "outer_left";
                case OUTER_RIGHT -> "outer_right";
                case INNER_LEFT -> "inner_left";
                case INNER_RIGHT -> "inner_right";
                default -> "straight";
            };
        }

        public static StairsShape from(String string) {
            return switch (string) {
                case "outer_left" -> OUTER_LEFT;
                case "outer_right" -> OUTER_RIGHT;
                case "inner_left" -> INNER_LEFT;
                case "inner_right" -> INNER_RIGHT;
                default -> STRAIGHT;
            };
        }
    }

    private enum Half {
        TOP,
        BOTTOM;

        public static Half from(String string) {
            return switch (string) {
                case "top" -> TOP;
                default -> BOTTOM;
            };
        }
    }

    private enum BlockType {
        FULL,
        SLAB,
        STAIR,
        FENCE
    }

    private static class Block {
        BlockPos position;
        CompoundTag self;
        BlockType type;

        private Block(BlockPos position, CompoundTag self) {
            this.position = position;
            this.self = self;
            type = BlockType.FULL;
        }
    }

    private class BlockHelper {
        Block get(BlockPos position) {
            for (BlockPos other : blocks.keySet()) {
                if (!other.equals(position)) continue;

                CompoundTag self = blocks.get(other);
                String name = self.getString("Name");
                if (name.endsWith("stairs")) {
                    return new StairBlock(position, self);
                } else if (name.endsWith("fence")) {
                    return new FenceBlock(position, self);
                } else if (name.endsWith("slab")) {
                    return new SlabBlock(position, self);
                }

                return new Block(position, self);
            }
            return null;
        }
    }

    private class StairBlock extends Block {
        Direction direction;
        Half half;
        StairsShape shape;

        private StairBlock(BlockPos position, CompoundTag self) {
            super(position, self);
            type = BlockType.STAIR;
            CompoundTag properties = self.getCompound("Properties");
            direction = Direction.from(properties.getString("facing"));
            half = Half.from(properties.getString("half"));
            shape = StairsShape.from(properties.getString("shape"));
        }
    }

    private class FenceBlock extends Block {
        boolean north = false,
                east = false,
                south = false,
                west = false;

        private FenceBlock(BlockPos position, CompoundTag self) {
            super(position, self);
            type = BlockType.FENCE;
        }

        boolean[] getConnections() {
            return new boolean[]{north, east, south, west};
        }

        void setConnection(Direction direction, boolean bool) {
            switch (direction) {
                case NORTH -> north = bool;
                case EAST -> east = bool;
                case SOUTH -> south = bool;
                case WEST -> west = bool;
            }
        }
    }

    private class SlabBlock extends Block {
        boolean top, bottom;

        private SlabBlock(BlockPos position, CompoundTag self) {
            super(position, self);
            type = BlockType.SLAB;
            CompoundTag properties = self.getCompound("Properties");
            String type = properties.getString("type");
            if (type.equalsIgnoreCase("top")) {
                top = true;
                bottom = false;
            } else if (type.equalsIgnoreCase("bottom")) {
                top = false;
                bottom = true;
            } else {
                top = true;
                bottom = true;
            }
        }

        boolean isFull() {
            return top && bottom;
        }
    }

    String getStairShape(BlockPos position) {
        BlockHelper helper = new BlockHelper();
        Block block = helper.get(position.simple());
        if (block == null || block.type != BlockType.STAIR) return "";

        StairBlock self = (StairBlock) block;

        block = helper.get(position.relative(self.direction));
        if (block != null && block.type == BlockType.STAIR) {
            StairBlock other = (StairBlock) block;
            if (other.half == self.half && !self.direction.onAxis(other.direction)) {
                if (other.direction == self.direction.getCounterClockWise()) {
                    return StairsShape.OUTER_LEFT.asString();
                }
                return StairsShape.OUTER_RIGHT.asString();
            }
        }

        block = helper.get(position.relative(self.direction.getOpposite()));
        if (block != null && block.type == BlockType.STAIR) {
            StairBlock other = (StairBlock) block;
            if (other.half == self.half && !self.direction.onAxis(other.direction)) {
                if (other.direction == self.direction.getCounterClockWise()) {
                    return StairsShape.INNER_LEFT.asString();
                }
                return StairsShape.INNER_RIGHT.asString();
            }
        }

        return StairsShape.STRAIGHT.asString();
    }

    // Since this is only for the shipwrecks, we can make some safe assumptions that we're only dealing with full blocks
    // that would definitely connect, slabs and stairs.
    boolean[] getFenceConnections(BlockPos position) {
        BlockHelper helper = new BlockHelper();
        Block block = helper.get(position.simple());
        if (block == null || block.type != BlockType.FENCE) return new boolean[]{};

        FenceBlock self = (FenceBlock) block;

        for (Direction direction : Direction.values()) {
            block = helper.get(position.relative(direction));
            if (block == null) {
                self.setConnection(direction, false);
                continue;
            }
            switch (block.type) {
                case FULL, FENCE -> self.setConnection(direction, true);
                case SLAB -> self.setConnection(direction, ((SlabBlock) block).isFull());
                case STAIR -> {
                    StairBlock stair = (StairBlock) block;
                    if (stair.direction == direction) {
                        self.setConnection(direction, false);
                    } else if (stair.direction == direction.getOpposite()) {
                        self.setConnection(direction, true);
                    } else if (stair.direction == direction.getCounterClockWise()) {
                        // It's possible we have not updated this particular stair block yet, so we have to be cautious.
                        self.setConnection(direction, StairsShape.from(getStairShape(stair.position)) == StairsShape.INNER_LEFT);
                    } else { // Direction must be clockwise
                        self.setConnection(direction, StairsShape.from(getStairShape(stair.position)) == StairsShape.INNER_RIGHT);
                    }
                }
            }
        }

        return self.getConnections();
    }

}

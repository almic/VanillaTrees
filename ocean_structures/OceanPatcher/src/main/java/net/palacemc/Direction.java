package net.palacemc;

public enum Direction {

    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Direction from(String string) {
        return switch (string) {
            case "north" -> NORTH;
            case "east" -> EAST;
            case "south" -> SOUTH;
            default -> WEST;
        };
    }

    public Direction getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            default -> EAST;
        };
    }

    public Direction getCounterClockWise() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            default -> SOUTH;
        };
    }

    public boolean onAxis(Direction other) {
        return switch (this) {
            case NORTH, SOUTH -> (other == NORTH) || (other == SOUTH);
            case EAST, WEST -> (other == EAST) || (other == WEST);
        };
    }

}

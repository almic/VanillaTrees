package net.palacemc;

public enum Direction {

    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Direction from(String string) {
        switch (string) {
            case "north": return NORTH;
            case "east": return EAST;
            case "south": return SOUTH;
            default: return WEST;
        }
    }

    public Direction getOpposite() {
        switch (this) {
            case NORTH: return SOUTH;
            case EAST: return WEST;
            case SOUTH: return NORTH;
            default: return EAST;
        }
    }

    public Direction getCounterClockWise() {
        switch (this) {
            case NORTH: return WEST;
            case EAST: return NORTH;
            case SOUTH:  return EAST;
            default: return SOUTH;
        }
    }

    public boolean onAxis(Direction other) {
        switch (this) {
            case NORTH:
            case SOUTH:
                return (other == NORTH) || (other == SOUTH);
            case EAST:
            case WEST:
                return (other == EAST) || (other == WEST);
            default:
                return false;
        }
    }

}

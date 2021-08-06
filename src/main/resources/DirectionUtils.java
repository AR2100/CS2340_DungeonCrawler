package resources;

public class DirectionUtils {
    public static Direction getOppositeDirection(Direction dir) {
        if (dir == Direction.NORTH) {
            return Direction.SOUTH;
        } else if (dir == Direction.SOUTH) {
            return Direction.NORTH;
        } else if (dir == Direction.EAST) {
            return Direction.WEST;
        } else {
            return Direction.EAST;
        }
    }
}

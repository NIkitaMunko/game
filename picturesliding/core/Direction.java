package picturesliding.core;

public enum Direction {
    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, 1),
    RIGHT(0, -1);

    private final int rowChange;
    private final int colChange;

    Direction(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    public int[] getMove() {
        return new int[]{rowChange, colChange};
    }

    public static Direction getDirection(String letter) {
        switch (letter) {
            case "w": return UP;
            case "s": return DOWN;
            case "a": return LEFT;
            case "d": return RIGHT;
            default: return null;
        }
    }
}

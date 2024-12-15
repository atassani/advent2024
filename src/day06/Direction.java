package day06;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    RIGHT(1, 0),
    LEFT(-1, 0);

    private final int dirX;
    private final int dirY;

    Direction(int dirX, int dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
    }

    public int getDirX() {
        return dirX;
    }

    public int getDirY() {
        return dirY;
    }
    public Direction turnRight() {
        switch (this) {
            case UP -> { return RIGHT; }
            case RIGHT -> { return DOWN; }
            case DOWN -> { return LEFT; }
            case LEFT -> { return UP; }
            default -> throw new IllegalStateException("Unknown direction: " + this);
        }
    }
}

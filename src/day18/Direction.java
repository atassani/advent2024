package day18;

public enum Direction {
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0),
    UP(0, -1);

    final int x;
    final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

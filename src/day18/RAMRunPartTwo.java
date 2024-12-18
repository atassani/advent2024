package day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RAMRunPartTwo {

    public static final String FILE_NAME = "resources/day18/input.txt";

    private static final int GRID_SIZE = 70;
    private static final int BYTES_TO_READ = 1024;

    public static void main(String[] args) {
        RAMRunPartTwo template = new RAMRunPartTwo();
        template.run();
    }

    private void run() {
        List<String> lines = readLines();
        Grid grid = new Grid(GRID_SIZE);
        updateGrid(grid, lines, BYTES_TO_READ);
        List<Point> path;
        int i = BYTES_TO_READ;
        Point point;
        do {
            i++;
            String[] parts = lines.get(i).split(",");
            point = new Point(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]));
            addPointToGrid(grid, point);
            path = findShortestPath(grid, 0, 0, GRID_SIZE, GRID_SIZE);
        } while (path != null);
        System.out.printf("Point (%d,%d) is the last point to be added to the grid.\n", point.x, point.y);
    }

    private void addPointToGrid(Grid grid, Point point) {
        grid.turnOn(point.x, point.y);
    }

    private List<Point> findShortestPath(Grid grid, int startX, int startY, int destX, int destY) {
        if (!grid.isInBoundaries(startX, startY) || !grid.isInBoundaries(destX, destY) || grid.isOn(startX, startY) || grid.isOn(destX, destY)) {
            return null;
        }

        boolean[][] visited = new boolean[GRID_SIZE+1][GRID_SIZE+1];
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> parentMap = new HashMap<>();

        queue.add(new Point(startX, startY));
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.x == destX && current.y == destY) {
                return constructPath(parentMap, current);
            }

            for (Direction dir : Direction.values()) {
                int newX = current.x + dir.x;
                int newY = current.y + dir.y;
                if (grid.isInBoundaries(newX, newY) && !grid.isOn(newX, newY) && !visited[newX][newY]) {
                    queue.add(new Point(newX, newY));
                    visited[newX][newY] = true;
                    parentMap.put(new Point(newX, newY), current);
                }
            }
        }
        return null;
    }

    private List<Point> constructPath(Map<Point, Point> parentMap, Point end) {
        List<Point> path = new ArrayList<>();
        for (Point at = end; at != null; at = parentMap.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    private void updateGrid(Grid grid, List<String> lines, int bytesToRead) {
        for (int i = 0; i < bytesToRead; i++) {
            String[] parts = lines.get(i).split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            grid.turnOn(x, y);
        }
    }

    private void printGrid(Grid grid) {
        for (int y = 0; y <= GRID_SIZE; y++) {
            for (int x = 0; x <= GRID_SIZE; x++) {
                System.out.print(grid.isOn(x, y) ? "#" : ".");
            }
            System.out.println();
        }
    }

    private void printGridAndPath(Grid grid, List<Point> path) {
        for (int y = 0; y <= GRID_SIZE; y++) {
            for (int x = 0; x <= GRID_SIZE; x++) {
                if (path.contains(new Point(x, y))) {
                    System.out.print("O");
                } else {
                    System.out.print(grid.isOn(x, y) ? "#" : ".");
                }
            }
            System.out.println();
        }
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
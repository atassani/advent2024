package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HoofItPartTwo {

    public static final String FILE_NAME = "resources/day10/input.txt";

    public static void main(String[] args) {
        HoofItPartTwo template = new HoofItPartTwo();
        template.run();
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void run() {
        List<String> lines = readLines();
        long sumScores = calculateSumScores(lines);
        System.out.printf("The sum of scores is %d\n", sumScores);
    }

    private long calculateSumScores(List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).length();
        int[][] map = new int[rows][cols];

        // Parse the input lines into a 2D array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = lines.get(i).charAt(j) - '0';
            }
        }

        long totalScore = 0;

        // Identify all trailheads (cells with value 0)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 0) {
                    List<List<Point>> pathsToNine = countPathsToNine(map, i, j, new boolean[rows][cols]);
//                    System.out.printf("Trailhead at (%d, %d) has %d paths to 9\n", i, j, pathsToNine.size());
//                    int num = 1;
//                    for (List<Point> path : pathsToNine) {
//                        System.out.print(num + " Path: ");
//                        num++;
//                        for (Point point : path) {
//                            System.out.printf("(%d,%d)%d ", point.row, point.col, map[point.row][point.col]);
//                        }
//                        System.out.println();
//                    }
                    long uniqueNines = pathsToNine.stream().map(path -> path.get(path.size()-1)).distinct().count();
                    // System.out.printf("Trailhead at (%d, %d) has %d unique nines\n", i, j, uniqueNines);
                    totalScore += pathsToNine.size();
                }
            }
        }

        return totalScore;
    }

    private List<List<Point>> countPathsToNine(int[][] map, int row, int col, boolean[][] visited) {
        int rows = map.length;
        int cols = map[0].length;

        List<List<Point>> paths = new ArrayList<>();

        // Base case: if the current cell is 9, return a list with a single path containing this cell
        if (map[row][col] == 9) {
            List<Point> path = new ArrayList<>();
            path.add(new Point(row, col));
            paths.add(path);
            return paths;
        }

        // Mark the current cell as visited
        visited[row][col] = true;

        int[] dRow = {-1, 1, 0, 0}; // Up, Down, Left, Right
        int[] dCol = {0, 0, -1, 1};

        // Explore all valid moves (up, down, left, right)
        for (int i = 0; i < 4; i++) {
            int newRow = row + dRow[i];
            int newCol = col + dCol[i];

            if (isValidMove(map, newRow, newCol, visited, map[row][col] + 1)) {
                List<List<Point>> newPaths = countPathsToNine(map, newRow, newCol, visited);
                for (List<Point> path : newPaths) {
                    path.add(0, new Point(row, col));
                    paths.add(path);
                }
            }
        }

        // Unmark the current cell as visited before returning
        visited[row][col] = false;

        return paths;
    }

    private boolean isValidMove(int[][] map, int row, int col, boolean[][] visited, int expectedValue) {
        int rows = map.length;
        int cols = map[0].length;

        return row >= 0 && row < rows && col >= 0 && col < cols && !visited[row][col] && map[row][col] == expectedValue;
    }
}
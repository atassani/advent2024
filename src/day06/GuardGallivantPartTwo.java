package day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static day06.Direction.DOWN;
import static day06.Direction.UP;

public class GuardGallivantPartTwo {

    public static final String FILE_NAME = "resources/day06/input.txt";

    public static void main(String[] args) {
        GuardGallivantPartTwo template = new GuardGallivantPartTwo();
        template.run();
    }

    private void run() {
        List<String> map = readLines();
        //map.stream().forEach(System.out::println);
        boolean wasPathVisited = false;
        int numFounds = 0;
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(0).length(); x++) {
                if (!validPositionForObstacle(map, x, y)) {
                    continue;
                }
                List<Position> visitedPositions = new ArrayList<>();
                Direction direction = UP;
                Position originalPosition =locateGuard(map);
                Position guardPosition =locateGuard(map);
                Position prevGuardPosition = null;
                List<String> updatedMap = new ArrayList<>(map);
                markNewObstacle(updatedMap, x, y);
                System.out.printf("Obstacle at (%d,%d)%n", x, y);
                do {
                    visitedPositions.add(guardPosition);
                    if (isGuardInObstacle(guardPosition, updatedMap)) {
                        guardPosition = moveBackwards(guardPosition, direction);
                        markGuardTurn(updatedMap, guardPosition);
                        direction = direction.turnRight();
                    } else {
                        if (!guardPosition.equals(originalPosition)) {
                            markGuardPosition(updatedMap, guardPosition, direction);
                        }
                    }
                    prevGuardPosition = guardPosition;
                    guardPosition = moveForward(guardPosition, direction);
                    wasPathVisited = isPathVisitied(visitedPositions, guardPosition, prevGuardPosition);
                } while (isGuardInMap(guardPosition, updatedMap) && !wasPathVisited);
                if (wasPathVisited) {
//                    System.out.printf("Obstacle at (%d,%d)%n", x, y);
//                    updatedMap.stream().forEach(System.out::println);
//                    System.out.println();
                    System.out.printf("FOUND at (%d,%d) --------------- %n", x, y);
                    numFounds++;
                }
                wasPathVisited = false;
            }
        }
        System.out.printf("Number of obstacles found: %d%n", numFounds);
    }

    private boolean validPositionForObstacle(List<String> map, int x, int y) {
        return map.get(y).charAt(x) == '.';
    }

    private boolean isPathVisitied(List<Position> visitedPositions, Position guardPosition, Position prevGuardPosition) {
        int index = visitedPositions.indexOf(guardPosition);
        if (index > 1 && visitedPositions.get(index -1).equals(prevGuardPosition)) {
            return true;
        }
        return false;
    }

    private void markNewObstacle(List<String> map, int x, int y) {
        String line = map.get(y);
        String updatedLine = line.substring(0, x) + 'O' + line.substring(x + 1);
        map.set(y, updatedLine);
    }

    private boolean isGuardInObstacle(Position guardPosition, List<String> map) {
        return map.get(guardPosition.getY()).charAt(guardPosition.getX()) == '#' ||
               map.get(guardPosition.getY()).charAt(guardPosition.getX()) == 'O';
    }

    private Position moveBackwards(Position guardPosition, Direction direction) {
        return new Position(guardPosition.getX() - direction.getDirX(), guardPosition.getY() - direction.getDirY());
    }

    private void markGuardPosition(List<String> map, Position guardPosition, Direction direction) {
        String line = map.get(guardPosition.getY());
        char mark = direction == UP || direction == DOWN ? '|' : '-';
        String updatedLine = line.substring(0, guardPosition.getX()) + mark + line.substring(guardPosition.getX() + 1);
        map.set(guardPosition.getY(), updatedLine);
    }

    private void markGuardTurn(List<String> map, Position guardPosition) {
        String line = map.get(guardPosition.getY());
        String updatedLine = line.substring(0, guardPosition.getX()) + '+' + line.substring(guardPosition.getX() + 1);
        map.set(guardPosition.getY(), updatedLine);
    }

    private Position moveForward(Position guardPosition, Direction direction) {
        return new Position(guardPosition.getX() + direction.getDirX(), guardPosition.getY() + direction.getDirY());
    }

    private boolean isGuardInMap(Position guardPosition, List<String> map) {
        return guardPosition.getY() >= 0 && guardPosition.getY() < map.size() &&
                guardPosition.getX() >= 0 && guardPosition.getX() < map.get(guardPosition.getY()).length();
    }

    private Position locateGuard(List<String> lines) {
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                if (lines.get(y).charAt(x) == '^') {
                    return new Position(x, y);
                }
            }
        }
        return null;
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

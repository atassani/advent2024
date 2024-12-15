package day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GuardGallivant {

    public static final String FILE_NAME = "resources/day06/input.txt";

    public static void main(String[] args) {
        GuardGallivant template = new GuardGallivant();
        template.run();
    }

    private void run() {
        List<String> map = readLines();
        List<String> updatedMap = new ArrayList<>(map);
        Position guardPosition =locateGuard(map);
        Direction direction = Direction.UP;
        map.stream().forEach(System.out::println);
        do {
            if (isGuardInObstacle(guardPosition, map)) {
                guardPosition = moveBackwards(guardPosition, direction);
                direction = direction.turnRight();
            } else {
                markGuardPosition(updatedMap, guardPosition);
            }
            guardPosition = moveForward(guardPosition, direction);
        } while (isGuardInMap(guardPosition, map));
        updatedMap.stream().forEach(System.out::println);
        long numPositions = updatedMap.stream().flatMapToInt(String::chars).filter(c -> c == 'X').count();
        System.out.printf("Number of positions visited: %d%n", numPositions);
    }

    private boolean isGuardInObstacle(Position guardPosition, List<String> map) {
        return map.get(guardPosition.getY()).charAt(guardPosition.getX()) == '#';
    }

    private Position moveBackwards(Position guardPosition, Direction direction) {
        return new Position(guardPosition.getX() - direction.getDirX(), guardPosition.getY() - direction.getDirY());
    }

    private void markGuardPosition(List<String> map, Position guardPosition) {
        String line = map.get(guardPosition.getY());
        String updatedLine = line.substring(0, guardPosition.getX()) + 'X' + line.substring(guardPosition.getX() + 1);
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

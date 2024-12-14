package day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CeresSearch {

    public static final String FILE_NAME = "resources/day04/input.txt";
    public static final String XMAS = "XMAS";

    public static void main(String[] args) {
        CeresSearch template = new CeresSearch();
        template.run();
    }

    private void run() {
        List<String> lines = readLines();
        int xmasFound = 0;
        lines.stream().forEach(System.out::println);
        // y vertical, x horizontal
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                xmasFound += findXmasNorth(lines, x, y);
                xmasFound += findXmasNorthEast(lines, x, y);
                xmasFound += findXmasEast(lines, x, y);
                xmasFound += findXmasSouthEast(lines, x, y);
                xmasFound += findXmasSouth(lines, x, y);
                xmasFound += findXmasSouthWest(lines, x, y);
                xmasFound += findXmasWest(lines, x, y);
                xmasFound += findXmasNorthWest(lines, x, y);
            }
        }
        System.out.println("XMAS found: " + xmasFound);
    }

    private int findXmasNorth(List<String> lines, int x, int y) {
        return findXmasVerHor(lines, x, y, 0, -1);
    }

    private int findXmasNorthEast(List<String> lines, int x, int y) {
        return findXmasVerHor(lines, x, y, 1,  -1);
    }

    private int findXmasEast(List<String> lines, int x, int y) {
        return findXmasVerHor(lines, x, y, 1, 0);
    }

    private int findXmasSouthEast(List<String> lines, int x, int y) {
        return findXmasVerHor(lines, x, y, 1, 1);
    }

    private int findXmasSouth(List<String> lines, int x, int y) {
        return findXmasVerHor(lines, x, y, 0, 1);
    }

    private int findXmasSouthWest(List<String> lines, int x, int y) {
        return findXmasVerHor(lines, x, y, -1, 1);
    }

    private int findXmasWest(List<String> lines, int x, int y) {
        return findXmasVerHor(lines, x, y, -1, 0);
    }

    private int findXmasNorthWest(List<String> lines, int x, int y) {
        return findXmasVerHor(lines, x, y, -1, -1);
    }

    private int findXmasVerHor(List<String> lines, int x, int y, int dirHorizontal, int dirVertical) {
        if (x < 0 || x >= lines.get(0).length() || y < 0 || y >= lines.size() ||
            x + (XMAS.length() - 1) * dirHorizontal < 0 ||
            x + (XMAS.length() - 1) * dirHorizontal >= lines.get(0).length() ||
            y + (XMAS.length() - 1) * dirVertical < 0 ||
            y + (XMAS.length() - 1) * dirVertical >= lines.size())
        {
            return 0;
        }
        for (int i = 0; i < XMAS.length(); i++) {
            if (lines.get(y + i*dirVertical).charAt(x + i*dirHorizontal) != XMAS.charAt(i)) {
                return 0;
            }
        }
        System.out.printf("Found XMAS at (%d, %d) with direction (%d, %d)%n", x, y, dirVertical, dirHorizontal);
        return 1;
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CeresSearchPartTwo {

    public static final String FILE_NAME = "resources/day04/input.txt";

    public static void main(String[] args) {
        CeresSearchPartTwo template = new CeresSearchPartTwo();
        template.run();
    }

    private void run() {
        List<String> lines = readLines();
        int xmasFound = 0;
        lines.stream().forEach(System.out::println);
        // y vertical, x horizontal
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                xmasFound += findXmas(lines, x, y);
            }
        }
        System.out.println("XMAS found: " + xmasFound);
    }

    private int findXmas(List<String> lines, int x, int y) {
        if (x < 0 || x >= lines.get(0).length() || y < 0 || y >= lines.size() ||
            x + (3 - 1) >= lines.get(0).length() ||
            y + (3 - 1) >= lines.size())
        {
            return 0;
        }
        boolean firstFound = false;
        if (((lines.get(y + 0).charAt(x + 0) == 'M') &&
             (lines.get(y + 1).charAt(x + 1) == 'A') &&
             (lines.get(y + 2).charAt(x + 2) == 'S')) ||
            ((lines.get(y + 0).charAt(x + 0) == 'S') &&
             (lines.get(y + 1).charAt(x + 1) == 'A') &&
             (lines.get(y + 2).charAt(x + 2) == 'M'))) {
                firstFound = true;
        }
        if (!firstFound) {
            return 0;
        }
        if (((lines.get(y + 0).charAt(x + 2) == 'M') &&
             (lines.get(y + 2).charAt(x + 0) == 'S')) ||
            ((lines.get(y + 0).charAt(x + 2) == 'S') &&
             (lines.get(y + 2).charAt(x + 0) == 'M'))) {
            System.out.printf("Found XMAS at (%d, %d)%n", x, y);
            return 1;
        }
        return 0;
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

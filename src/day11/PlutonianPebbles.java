package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PlutonianPebbles {

    public static final String FILE_NAME = "resources/day11/input.txt";

    public static void main(String[] args) {
        PlutonianPebbles template = new PlutonianPebbles();
        template.run();
    }

    private void run() {
        List<String> lines = readLines();
        List<Long> stones = parseStones(lines);
        int blinks = 25;
        for (int i = 0; i < blinks; i++) {
            stones = blink(stones);
        }
        System.out.printf("After %d blinks, there are %d stones.\n", blinks, stones.size());
    }

    private List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Long> parseStones(List<String> lines) {
        List<Long> stones = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            for (String part : parts) {
                try {
                    stones.add(Long.parseLong(part));
                } catch (NumberFormatException e) {
                    System.err.printf("Skipping invalid entry: %s\n", part);
                }
            }
        }
        return stones;
    }

    private List<Long> blink(List<Long> stones) {
        List<Long> newStones = new ArrayList<>();
        for (long stone : stones) {
            if (stone == 0) {
                newStones.add(1L);
            } else if (Long.toString(stone).length() % 2 == 0) {
                String stoneStr = Long.toString(stone);
                int mid = stoneStr.length() / 2;
                newStones.add(Long.parseLong(stoneStr.substring(0, mid)));
                newStones.add(Long.parseLong(stoneStr.substring(mid)));
            } else {
                newStones.add(stone * 2024);
            }
        }
        return newStones;
    }
}
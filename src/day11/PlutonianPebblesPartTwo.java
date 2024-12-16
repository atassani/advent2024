package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlutonianPebblesPartTwo {

    public static final String FILE_NAME = "resources/day11/input.txt";

    public static void main(String[] args) {
        PlutonianPebblesPartTwo template = new PlutonianPebblesPartTwo();
        template.run();
    }

    private void run() {
        List<String> lines = readLines();
        Map<Long, Long> stones = parseStones(lines);
        int blinks = 75;
        for (int i = 0; i < blinks; i++) {
            System.out.println("Blinking " + i + " --------------------- ");
            stones = blink(stones);
        }
        long totalStones = stones.values().stream().mapToLong(Long::longValue).sum();
        System.out.printf("After %d blinks, there are %d stones.\n", blinks, totalStones);
    }

    private List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Long, Long> parseStones(List<String> lines) {
        Map<Long, Long> stones = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            for (String part : parts) {
                try {
                    long stone = Long.parseLong(part);
                    stones.put(stone, stones.getOrDefault(stone, 0L) + 1);
                } catch (NumberFormatException e) {
                    System.err.printf("Skipping invalid entry: %s\n", part);
                }
            }
        }
        return stones;
    }

    private Map<Long, Long> blink(Map<Long, Long> stones) {
        Map<Long, Long> newStones = new HashMap<>();
        for (Map.Entry<Long, Long> entry : stones.entrySet()) {
            long stone = entry.getKey();
            long count = entry.getValue();
            if (stone == 0) {
                newStones.put(1L, newStones.getOrDefault(1L, 0L) + count);
            } else if (Long.toString(stone).length() % 2 == 0) {
                String stoneStr = Long.toString(stone);
                int mid = stoneStr.length() / 2;
                long left = Long.parseLong(stoneStr.substring(0, mid));
                long right = Long.parseLong(stoneStr.substring(mid));
                newStones.put(left, newStones.getOrDefault(left, 0L) + count);
                newStones.put(right, newStones.getOrDefault(right, 0L) + count);
            } else {
                long newStone = stone * 2024;
                newStones.put(newStone, newStones.getOrDefault(newStone, 0L) + count);
            }
        }
        return newStones;
    }
}
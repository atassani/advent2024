package day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HistorianHisteriaPart2 {
    public static final String FILE_NAME = "resources/day01/input.txt";

    public static void main(String[] args) {
        HistorianHisteriaPart2 historianHisteria = new HistorianHisteriaPart2();
        historianHisteria.run();
    }

    private void run() {
        List<Integer> firstNumbers = new ArrayList<>();
        List<Integer> secondNumbers = new ArrayList<>();

        // Read lines from the input file and populate the first and second lists
        List<String> lines = readLines();
        for (String line : lines) {
            String[] parts = line.trim().split("\\s+");
            firstNumbers.add(Integer.parseInt(parts[0]));
            secondNumbers.add(Integer.parseInt(parts[1]));
        }

        // Calculate the sum of number * occurrences
        int result = firstNumbers.stream()
                .mapToInt(number -> number * (int) secondNumbers.stream().filter(n -> n.equals(number)).count())
                .sum();

        // Print the result
        System.out.println("Result: " + result);
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

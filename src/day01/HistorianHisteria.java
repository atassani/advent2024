package day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HistorianHisteria {

    public static final String FILE_NAME = "resources/day01/input.txt";

    public static void main(String[] args) {
        HistorianHisteria historianHisteria = new HistorianHisteria();
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

        // Sort the lists in ascending order
        Collections.sort(firstNumbers);
        Collections.sort(secondNumbers);

        // Create a new list with the difference between elements of the first and second lists
        List<Integer> differences = IntStream.range(0, Math.min(firstNumbers.size(), secondNumbers.size()))
                .mapToObj(i ->  Math.abs(secondNumbers.get(i) - firstNumbers.get(i)))
                .collect(Collectors.toList());

        // Sum the contents of the differences list
        int sumOfDifferences = differences.stream().mapToInt(Integer::intValue).sum();

        // Print the lists to verify the result
        for (int i = 0; i < firstNumbers.size(); i++) {
            System.out.printf("%d - %d = %d%n", secondNumbers.get(i), firstNumbers.get(i), differences.get(i));
        }
        //System.out.println("First Numbers: " + firstNumbers);
        //System.out.println("Second Numbers: " + secondNumbers);
        //System.out.println("Differences: " + differences);
        System.out.println("Sum of Differences: " + sumOfDifferences);
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

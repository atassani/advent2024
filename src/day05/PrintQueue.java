package day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrintQueue {

    public static final String FILE_NAME = "resources/day05/input.txt";

    public static void main(String[] args) {
        PrintQueue template = new PrintQueue();
        template.run();
    }

    private void run() {
        List<String> lines = readLines();
        List<OrderRule> rules = new ArrayList<>();
        List<List<Integer>> updates = new ArrayList<>();
        boolean readingRules = true;
        for (String line : lines) {
            if (line.isEmpty()) {
                readingRules = false;
                continue;
            }
            if (readingRules) {
                String[] parts = line.split("\\|");
                rules.add(new OrderRule(parts[0], parts[1]));
            } else {
                updates.add(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
            }
        }
        int middleNumbersAdded = 0;
        for (List<Integer> update : updates) {
            if (isRuleValidated(update, rules)) {
                int middleValue = update.get(update.size() / 2);
                middleNumbersAdded += middleValue;
            }
        }
        System.out.printf("Middle numbers added: %d%n", middleNumbersAdded);
    }

    public boolean isRuleValidated(List<Integer> update, List<OrderRule> rules) {
        for (OrderRule rule : rules) {
            int firstIndex = update.indexOf(rule.getFirst());
            int secondIndex = update.indexOf(rule.getSecond());
            if (firstIndex != -1 && secondIndex != -1 && firstIndex >= secondIndex) {
                return false;
            }
        }
        return true;
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

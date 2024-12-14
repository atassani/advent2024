package day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrintQueuePartTwo {

    public static final String FILE_NAME = "resources/day05/input.txt";

    public static void main(String[] args) {
        PrintQueuePartTwo template = new PrintQueuePartTwo();
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
            if (!isRuleValidated(update, rules)) {
                List<Integer> fixedUpdate = fixUpdate(update, rules);
                //System.out.printf("%s becomes %s%n", update, fixedUpdate);
                int middleValue = fixedUpdate.get(fixedUpdate.size() / 2);
                middleNumbersAdded += middleValue;
            }
        }
        System.out.printf("Middle numbers added: %d%n", middleNumbersAdded);
    }

    private List<Integer> fixUpdate(List<Integer> update, List<OrderRule> rules) {
        List<Integer> fixedUpdate = new ArrayList<>(update);
        boolean changed;
        do {
            changed = false;
            for (OrderRule rule : rules) {
                int firstIndex = fixedUpdate.indexOf(rule.getFirst());
                int secondIndex = fixedUpdate.indexOf(rule.getSecond());
                if (firstIndex != -1 && secondIndex != -1 && firstIndex > secondIndex) {
                    // Swap the elements to satisfy the rule
                    fixedUpdate.set(firstIndex, rule.getSecond());
                    fixedUpdate.set(secondIndex, rule.getFirst());
                    changed = true;
                }
            }
        } while (changed);
        return fixedUpdate;
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

package day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOverPartTwo {

    public static final String FILE_NAME = "resources/day03/input.txt";
    private static final String REGEX = "(mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\))";

    public static void main(String[] args) {
        MullItOverPartTwo template = new MullItOverPartTwo();
        template.run();
    }

    private void run() {
        List<String> lines = readLines();
        long matches = findMatchesAndMultiply(lines);
        System.out.println("Result: " + matches);
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long findMatchesAndMultiply(List<String> lines) {
        Pattern pattern = Pattern.compile(REGEX);
        long result = 0;
        boolean isEnabled = true;
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String group = matcher.group();
                if (group.equals("do()")) {
                    isEnabled = true;
                } else if (group.equals("don't()")) {
                    isEnabled = false;
                } else if (isEnabled && group.startsWith("mul")) {
                    int firstNumber = Integer.parseInt(matcher.group(2));
                    int secondNumber = Integer.parseInt(matcher.group(3));
                    result += (long)firstNumber * (long)secondNumber;
                }
            }
        }
        return result;
    }
}

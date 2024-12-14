package day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOver {

    public static final String FILE_NAME = "resources/day03/input.txt";
    private static final String REGEX = "mul\\((\\d{1,3}),(\\d{1,3})\\)";

    public static void main(String[] args) {
        MullItOver template = new MullItOver();
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
        return lines.stream()
                .flatMap(line -> {
                    Matcher matcher = pattern.matcher(line);
                    return matcher.results()
                            .map(result -> {
                                int firstNumber = Integer.parseInt(result.group(1));
                                int secondNumber = Integer.parseInt(result.group(2));
                                return firstNumber * secondNumber;
                            });
                })
                .mapToLong(Integer::longValue).sum();
    }
}

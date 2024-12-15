package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RedNosedReportsPart2 {

    public static final String FILE_NAME = "resources/day02/input.txt";

    public static void main(String[] args) {
        RedNosedReportsPart2 redNosedReports = new RedNosedReportsPart2();
        redNosedReports.run();
    }

    private void run() {
        List<String> lines = readLines();
        // Transform lines into a list of reports
        List<List<Integer>> reports = lines.stream()
                .map(line -> Stream.of(line.split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .toList();

        // Count the number of safe reports
        long safeReportsCount = reports.stream()
                .filter(this::isSafeReportWithProblemDampener)
                .count();

        // Print the reports to verify the result
        //reports.forEach(report -> System.out.printf("%s %b %n%n", report, isSafeReport(report)));

        // Print the number of safe reports
        System.out.println("Number of safe reports: " + safeReportsCount);
    }

    private boolean isSafeReportWithProblemDampener(List<Integer> report) {
        if (isSafeReport(report)) {
            return true;
        }
        for (int i = 0; i < report.size(); i++) {
            List<Integer> reportCopy = new ArrayList<>(report);
            reportCopy.remove(i);
            if (isSafeReport(reportCopy)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSafeReport(List<Integer> report) {
        // Gets the differences between the elements of the report
        List<Integer> differences = IntStream.range(1, report.size()).mapToObj(i -> report.get(i) - report.get(i - 1)).toList();

        // If any difference is less than 1 or greater than 3, the report is not safe
        boolean isSafeDifference = differences.stream().allMatch(diff -> Math.abs(diff) >= 1 && Math.abs(diff) <= 3);

        // All differences are increasing or decreasing
        boolean isAllIncreasing = differences.stream().allMatch(diff -> diff >= 0);
        boolean isAllDecreasing = differences.stream().allMatch(diff -> diff <= 0);

        return isSafeDifference && (isAllIncreasing || isAllDecreasing);
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

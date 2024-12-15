package day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BridgeRepair {

    public static final String FILE_NAME = "resources/day07/input.txt";

    public static void main(String[] args) {
        BridgeRepair template = new BridgeRepair();
        template.run();
    }

    private void run() {
        List<String> lines = readLines();
        long result = 0;
        for (String line : lines) {
            CalibrationEquation equation = new CalibrationEquation(line);
            //System.out.printf("Equation: %s is %s%n", line, equation.isValid());
            if (equation.isValid()) {
                result += equation.getTestValue();
            }
        }
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
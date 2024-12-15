package day07;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalibrationEquation {
    private final long testValue;
    private final List<Long> numbers;


    public CalibrationEquation(String line) {
        Pattern pattern = Pattern.compile("(\\d+):\\s*(.*)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            this.testValue = Long.parseLong(matcher.group(1));
            this.numbers = new ArrayList<>();
            for (String num : matcher.group(2).split("\\s+")) {
                this.numbers.add(Long.parseLong(num));
            }
        } else {
            throw new IllegalArgumentException("Line format is incorrect: " + line);
        }
    }

    public boolean isValid() {
        return isValidHelper(numbers.get(0), 1);
    }

    private boolean isValidHelper(long currentResult, int index) {
        if (index == numbers.size()) {
            return currentResult == testValue;
        }

        long nextNumber = numbers.get(index);
        return isValidHelper(currentResult + nextNumber, index + 1) ||
               isValidHelper(currentResult * nextNumber, index + 1);
    }

    public long getTestValue() {
        return testValue;
    }
}

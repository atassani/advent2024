package day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClawContraption {

    public static final String FILE_NAME = "resources/day13/input.txt";

    public static void main(String[] args) {
        ClawContraption template = new ClawContraption();
        template.run();
    }

    private void run() {
        List<String> lines = readLines();
        List<Machine> machines = parseMachines(lines);
        int totalTokens = calculateMinimumTokens(machines);
        System.out.println("Minimum tokens required to win all possible prizes: " + totalTokens);
    }

    private List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Machine> parseMachines(List<String> lines) {
        List<Machine> machines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 4) {
            String[] buttonA = lines.get(i).split("[+=,]");
            String[] buttonB = lines.get(i + 1).split("[+=,]");
            String[] prize = lines.get(i + 2).split("[=,]");
            int aX = Integer.parseInt(buttonA[1].trim());
            int aY = Integer.parseInt(buttonA[3].trim());
            int bX = Integer.parseInt(buttonB[1].trim());
            int bY = Integer.parseInt(buttonB[3].trim());
            int prizeX = Integer.parseInt(prize[1].trim());
            int prizeY = Integer.parseInt(prize[3].trim());
            machines.add(new Machine(aX, aY, bX, bY, prizeX, prizeY));
        }
        return machines;
    }

    private int calculateMinimumTokens(List<Machine> machines) {
        int totalTokens = 0;
        for (Machine machine : machines) {
            int minTokens = findMinimumTokens(machine);
            if (minTokens != Integer.MAX_VALUE) {
                totalTokens += minTokens;
            }
        }
        return totalTokens;
    }

    private int findMinimumTokens(Machine machine) {
        int minTokens = Integer.MAX_VALUE;
        for (int a = 0; a <= 100; a++) {
            for (int b = 0; b <= 100; b++) {
                int x = a * machine.aX + b * machine.bX;
                int y = a * machine.aY + b * machine.bY;
                if (x == machine.prizeX && y == machine.prizeY) {
                    int tokens = a * 3 + b;
                    minTokens = Math.min(minTokens, tokens);
                }
            }
        }
        return minTokens;
    }

    private static class Machine {
        int aX, aY, bX, bY, prizeX, prizeY;

        Machine(int aX, int aY, int bX, int bY, int prizeX, int prizeY) {
            this.aX = aX;
            this.aY = aY;
            this.bX = bX;
            this.bY = bY;
            this.prizeX = prizeX;
            this.prizeY = prizeY;
        }
    }
}
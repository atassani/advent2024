package day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ResonantCollinearityPartTwo {

    public static final String FILE_NAME = "resources/day08/input.txt";

    public static void main(String[] args) {
        ResonantCollinearityPartTwo template = new ResonantCollinearityPartTwo();
        template.run();
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void run() {
        List<String> lines = readLines();
        int maxX = lines.get(0).length();
        int maxY = lines.size();
        Map<Character, List<Node>> frequencyMap = buildFrequencyMap(lines);
        Map<Character, List<Node>> resonancesMap = getResonances(frequencyMap, maxX, maxY);

        Set<Node> uniqueResonances = new HashSet<>();
        for (List<Node> resonances : resonancesMap.values()) {
            uniqueResonances.addAll(resonances);
        }

        List<Node> sortedResonances = new ArrayList<>(uniqueResonances);
        sortedResonances.sort((n1, n2) -> {
            if (n1.y != n2.y) {
                return Integer.compare(n1.y, n2.y);
            } else {
                return Integer.compare(n1.x, n2.x);
            }
        });

        for (Node resonance : sortedResonances) {
            System.out.println(resonance);
        }

        System.out.println("Total unique resonances: " + uniqueResonances.size());
    }

    private Map<Character, List<Node>> getResonances(Map<Character, List<Node>> frequencyMap, int maxX, int maxY) {
        Map<Character, List<Node>> resonancesMap = new HashMap<>();

        for (Map.Entry<Character, List<Node>> entry : frequencyMap.entrySet()) {
            char frequency = entry.getKey();
            List<Node> nodes = entry.getValue();
            List<Node> resonances = new ArrayList<>(nodes);

            for (int i = 0; i < nodes.size(); i++) {
                for (int j = i + 1; j < nodes.size(); j++) {
                    Node node1 = nodes.get(i);
                    Node node2 = nodes.get(j);

                    int dx = node2.x - node1.x;
                    int dy = node2.y - node1.y;

                    // Add nodes in the positive direction
                    Node current = new Node(node2.x + dx, node2.y + dy);
                    while (isWithinBounds(current, maxX, maxY)) {
                        resonances.add(current);
                        current = new Node(current.x + dx, current.y + dy);
                    }

                    // Add nodes in the negative direction
                    current = new Node(node1.x - dx, node1.y - dy);
                    while (isWithinBounds(current, maxX, maxY)) {
                        resonances.add(current);
                        current = new Node(current.x - dx, current.y - dy);
                    }
                }
            }

            resonancesMap.put(frequency, resonances);
        }

        return resonancesMap;
    }

    private boolean isWithinBounds(Node node, int maxX, int maxY) {
        return node.x >= 0 && node.x < maxX && node.y >= 0 && node.y < maxY;
    }

    private Map<Character, List<Node>> buildFrequencyMap(List<String> lines) {
        Map<Character, List<Node>> frequencyMap = new HashMap<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                char c = lines.get(y).charAt(x);
                if (c != '.') {
                    frequencyMap.computeIfAbsent(c, k -> new ArrayList<>()).add(new Node(x, y));
                }
            }
        }
        return frequencyMap;
    }
}
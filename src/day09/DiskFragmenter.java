package day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DiskFragmenter {

    public static final String FILE_NAME = "resources/day09/input.txt";

    public static void main(String[] args) {
        DiskFragmenter template = new DiskFragmenter();
        template.run();
    }

    private void run() {
        List<String> lines = readLines();
        String[] disk = expandDisk(lines.get(0));
        String[] compressedDisk = compressDisk(disk);
        long checksum = calculateChecksum(compressedDisk);
        //System.out.println(lines.get(0));
        //Stream.of(disk).forEach(System.out::print);
        //System.out.println();
        //Stream.of(compressedDisk).forEach(System.out::print);
        //System.out.println();
        System.out.println(checksum);
    }

    private long calculateChecksum(String[] compressedDisk) {
        long checksum = 0;
        for (int i = 0; i < compressedDisk.length; i++) {
            String s = compressedDisk[i];
            if (!".".equals(s)) {
                checksum += Integer.valueOf(s)*i;
            }
        }
        return checksum;
    }

    private String[] compressDisk(String[] disk) {
        String[] diskCopy = Arrays.copyOf(disk, disk.length);
        int n = disk.length;

        for (int i = n - 1; i >= 0; i--) {
            if (!".".equals(diskCopy[i])) {
                boolean dotFound = false;
                for (int j = 0; j < i; j++) {
                    if (".".equals(diskCopy[j])) {
                        diskCopy[j] = diskCopy[i];
                        diskCopy[i] = ".";
                        dotFound = true;
                        break;
                    }
                }
                if (!dotFound) {
                    break;
                }
            }
            //Stream.of(diskCopy).forEach(System.out::print);
            //System.out.println();
        }

        return diskCopy;
    }

    private String[] expandDisk(String line) {
        List<String> expandedDisk = new ArrayList<>();
        int idNumber = 0;
        for (int i = 0; i < line.length(); i++) {
            int repeatCount = Character.getNumericValue(line.charAt(i));
            String repeatedValue;
            if (i % 2 == 0) {
                repeatedValue = String.valueOf(idNumber);
                idNumber++;
            } else {
                repeatedValue = ".";
            }
            for (int j = 0; j < repeatCount; j++) {
                expandedDisk.add(repeatedValue);
            }
        }
        return expandedDisk.toArray(new String[0]);
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

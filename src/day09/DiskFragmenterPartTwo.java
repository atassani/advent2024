package day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DiskFragmenterPartTwo {

    public static final String FILE_NAME = "resources/day09/input.txt";

    public static void main(String[] args) {
        DiskFragmenterPartTwo template = new DiskFragmenterPartTwo();
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
                checksum += Integer.valueOf(s) * i;
            }
        }
        return checksum;
    }

    private String[] compressDisk(String[] disk) {
        String[] diskCopy = Arrays.copyOf(disk, disk.length);
        int n = disk.length;

        for (int i = n - 1; i >= 0; i--) {
            if (!".".equals(diskCopy[i])) {
                // Identify the file
                int fileId = Integer.parseInt(diskCopy[i]);
                int fileStart = i;
                while (fileStart >= 0 && !".".equals(diskCopy[fileStart]) && Integer.parseInt(diskCopy[fileStart]) == fileId) {
                    fileStart--;
                }
                fileStart++;
                int fileLength = i - fileStart + 1;

                // Find the leftmost space where the file can fit
                int spaceStart = -1;
                int spaceLength = 0;
                for (int j = 0; j < fileStart; j++) {
                    if (".".equals(diskCopy[j])) {
                        if (spaceStart == -1) {
                            spaceStart = j;
                        }
                        spaceLength++;
                        if (spaceLength == fileLength) {
                            break;
                        }
                    } else {
                        spaceStart = -1;
                        spaceLength = 0;
                    }
                }

                // Move the file if there is enough space
                if (spaceLength == fileLength) {
                    for (int j = 0; j < fileLength; j++) {
                        diskCopy[spaceStart + j] = String.valueOf(fileId);
                        diskCopy[fileStart + j] = ".";
                    }
                }

                // Move the index to the start of the file
                i = fileStart;
            }
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
}

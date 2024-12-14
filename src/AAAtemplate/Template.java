package AAAtemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Template {

    public static final String FILE_NAME = "resources/template/input.txt";

    public static void main(String[] args) {
        Template template = new Template();
        template.run();
    }

    private void run() {
        List<String> lines = readLines();
    }

    private static List<String> readLines() {
        try {
            return Files.readAllLines(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

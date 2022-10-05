import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Solution {
    private static final String FILE_NAME = "testDataForW1D1.txt"; //"input123.txt";
    private static final Mapper mapper = new Mapper();

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(new File(FILE_NAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                processLine(line);
            }
            mapper.printWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void processLine(String line) {
        List<String> list = List.of(line.split("[ -]"));
        for(String text : list) {
            mapper.addToList(text);
        }
    }
}

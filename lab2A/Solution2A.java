import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Solution2A {

    private static final String FILE_NAME = "lab1/testDataForW1D1.txt";

    private static final Mapper mapper = new Mapper();
    private static Reducer reducer = new Reducer();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                processLine(line);
            }
            mapper.sortWords();
            System.out.println("Mapper Output");
            mapper.printWords();

            System.out.println("\nReducer Input");
            mapper.getListWord()
                    .forEach(reducer::addPairToGroupByPairs);
            reducer.printInputGroupByPairs();

            System.out.println("\nReducer Output");
            reducer.printOutputGroupByPairs();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void processLine(String line) {
        List<String> list = List.of(line.split("[ -\"]"));
        for(String text : list) {
            mapper.addToList(text);
        }
    }
}

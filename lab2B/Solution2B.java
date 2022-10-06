import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Solution2B {
    private static final String inputSplit = "lab2B/inputSplit{%s}.txt";

    private static WordCount wordCount;

    public static void main(String[] args) {
        wordCount = new WordCount(3, 4);
        for(int i = 0; i < 3; i++) {
            Mapper mapper = wordCount.getMapperByIndex(i);
            mapperProcess(mapper, inputSplit.replace("{%s}", "" + i + ""));

            System.out.println("Mapper " + i + " Output");
            mapper.printWords();
            mapper.sortWords();
        }
        wordCount.mapperProduceWords();
        for(int i = 0; i < 4; i++) {
            Reducer reducer = wordCount.getReducerByIndex(i);
            System.out.println("Reducer " + i + " input");
            reducer.printInputGroupByPairs();

            System.out.println("Reducer " + i + " output");
            reducer.printOutputGroupByPairs();
        }
    }

    private static void mapperProcess(Mapper mapper, String filename) {
        try(Scanner scanner = new Scanner(new File(filename))) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                processRecord(line, mapper);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    private static void processRecord(String line, Mapper mapper) {
        List<String> list = List.of(line.split("[ \\-\"\']"));
        for(String text : list) {
            mapper.addToList(text);
        }
    }
}

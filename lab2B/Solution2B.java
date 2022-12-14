package lab2B;

public class Solution2B {
    private static final String inputSplit = "lab2B/inputSplit{%s}.txt";

    private static WordCount wordCount;

    public static void main(String[] args) {
        wordCount = new WordCount(3, 4);
        for(int i = 0; i < 3; i++) {
            Mapper mapper = wordCount.getMapperByIndex(i);
            mapper.Map(inputSplit.replace("{%s}", "" + i + ""));

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

}

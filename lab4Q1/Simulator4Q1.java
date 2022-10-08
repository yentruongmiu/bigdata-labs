package lab4Q1;

public class Simulator4Q1 {
    private static final String inputSplit = "lab4Q1/inputSplit{%s}.txt";
    private static InMapperWordCount wordCount;
    private final static int mapperNumber = 3;
    private final static int reducerNumber = 4;

    public static void main(String[] args) {
        wordCount = new InMapperWordCount(mapperNumber, reducerNumber);
        for(int i = 0; i < mapperNumber; i++) {
            Mapper mapper = wordCount.getMapperByIndex(i);
            mapper.Map(inputSplit.replace("{%s}", "" + i + ""));

            System.out.println("Mapper " + i + " Output");
            mapper.printWords();
        }
        wordCount.mapperProduceWords();
        for(int i = 0; i < 4; i++) {
            Reducer reducer = wordCount.getReducerByIndex(i);
            reducer.sortGroupByPairs();
            System.out.println("Reducer " + i + " input");
            reducer.printInputGroupByPairs();

            System.out.println("Reducer " + i + " output");
            reducer.printOutputGroupByPairs();
        }
    }

}

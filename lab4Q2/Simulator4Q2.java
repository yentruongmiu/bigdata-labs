package lab4Q2;


public class Simulator4Q2 {
    private final static String filename = "lab4Q2/file{%s}.txt";
    private final static int mapperNumber = 4;
    private final static int reducerNumber = 3;
    private static InMapperWordAverage wordAverage;

    public static void main(String[] args) {
        wordAverage = new InMapperWordAverage(mapperNumber, reducerNumber);
        for(int i = 1; i <= mapperNumber; i++) {
            Mapper mapper = wordAverage.getMapperByIndex(i - 1);
            mapper.Map(filename.replace("{%s}", "" + i + ""));

            System.out.println("Mapper " + (i-1) + " output");
            mapper.printMapperOutput();
        }
        wordAverage.calculateReducerForEmitting();
        for(int i = 0; i < reducerNumber; i++) {
            Reducer reducer = wordAverage.getReducerByIndex(i);
            reducer.sortGroupByPairs();

            System.out.println("Reducer " + i + " input");
            reducer.printInputGroupByPairs();

            System.out.println("Reducer " + i + " output");
            reducer.printOutputGroupByPairs();
        }
    }
}

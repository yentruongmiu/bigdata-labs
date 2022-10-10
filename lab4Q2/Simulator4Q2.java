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
        }
        for(int i = 0; i < mapperNumber; i++) {
            Mapper mapper = wordAverage.getMapperByIndex(i);
            System.out.println("Mapper " + i + " output");
            mapper.close();
        }
        wordAverage.calculateReducerForEmitting();
        for(int i = 0; i < reducerNumber; i++) {
            Reducer reducer = wordAverage.getReducerByIndex(i);

            Reducer.reducer(reducer, i);
        }
    }
}

package lab2A;

import lab2A.Mapper;
import lab2A.Pair;
import lab2A.Reducer;
public class Solution2A {

    private static final String FILE_NAME = "lab1/testDataForW1D1.txt";

    private static final Mapper mapper = new Mapper();
    private static Reducer reducer = new Reducer();

    public static void main(String[] args) {
        mapper.Map(FILE_NAME);
        mapper.sortWords();
        System.out.println("Mapper Output");
        mapper.printWords();

        System.out.println("\nReducer Input");
        mapper.getListWord()
                    .forEach(reducer::addPairToGroupByPairs);
        reducer.printInputGroupByPairs();

        System.out.println("\nReducer Output");
        reducer.printOutputGroupByPairs();
    }

}

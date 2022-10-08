package lab1;

import lab1.Mapper;
public class Solution {
    private static final String filename = "lab1/testDataForW1D1.txt"; //"input123.txt";
    private static final Mapper mapper = new Mapper();

    public static void main(String[] args) {
        mapper.Map(filename);
        mapper.printWords();
    }

}

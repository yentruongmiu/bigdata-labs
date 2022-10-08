package lab2B;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Mapper {
    private List<Pair<String, Integer>> words = new ArrayList<>();

    public Mapper() {}

    public void Map(String filename) {
        try(Scanner scanner = new Scanner(new File(filename))) {
            while(scanner.hasNextLine()) {
                String record = scanner.nextLine();
                processRecord(record);
            }
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Pair<String, Integer>> getListWord() {
        return this.words;
    }

    public void addToList(String text) {
        String word = getWord(text);
        if(word != "") {
            //input word to listWord if it is word
            Pair<String, Integer> pair = new Pair(word, 1);
            words.add(pair);
        }
    }

    public void addOrCombineToList(String text) {
        String word = getWord(text);
        if(word != "") {
            words.stream().filter(w -> word.equals(w.getKey()))
                    .findFirst()
                    .ifPresentOrElse((w) -> {
                        w.setValue(w.getValue() + 1);
                    }, () -> {
                        Pair<String, Integer> pair = new Pair(word, 1);
                        words.add(pair);
                    });
        }
    }

    public void printWords() {
        //System.out.println("number words: " + words.size());
        words.forEach(System.out::println);
    }

    private String getWord(String text) {
        // abc123, abc.txt, abc_def => is not word
        // abc. , abc, abc?, abc! => is word
        // abc-def => abc and def => is 2 words
        Pattern pattern = Pattern.compile("^[A-Za-z]+[!,?.]*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if(matcher.find()) {
            String word = matcher.group().toLowerCase();
            String endingChar = String.valueOf(word.charAt(word.length() - 1));

            if(endingChar.equals(".") || endingChar.equals("!") || endingChar.equals(",") || endingChar.equals("?")) {
                word = word.substring(0, word.length() - 1);
            }
            return word;
        } else return "";
    }

    public void sortWords() {
        this.words = words.stream().sorted(Comparator.comparing(Pair::getKey))
                .collect(Collectors.toList());
    }

    public void close(Reducer reducer, Pair<String, Integer> pair) {
        //Emit data to reducer
        reducer.addPairToGroupByPairs(pair);
    }

    private void processRecord(String record) {
        List<String> list = List.of(record.split("[ \\-\"\']"));
        for(String text : list) {
            //addOrCombineToList(text);
            addToList(text);
        }
    }
}

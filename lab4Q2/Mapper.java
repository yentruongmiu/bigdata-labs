package lab4Q2;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Mapper {
    private List<Pair<String, List<Integer>>> outPutWords = new ArrayList<>();

    private List<Pair<String, List<Integer>>> words = new ArrayList<>();

    public Mapper() {}

    public void Map(String filename) {
        try(Scanner scanner = new Scanner(new File(filename))) {
            while(scanner.hasNextLine()) {
                String record = scanner.nextLine();
                processRecord(record);
            }
            wordsMap();
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Pair<String, List<Integer>>> getListWord() {
        return this.words;
    }

    public List<Pair<String, List<Integer>>> getOutPutWords() {
        return this.outPutWords;
    }

    private void wordsMap() {
        sortWords();
        words.forEach((w) -> {
            if(outPutWords.size() == 0) {
                outPutWords.add(new Pair<String, List<Integer>>(w.getKey(), w.getValue()));
            } else {
                int lastIdx = outPutWords.size() - 1;
                Pair<String, List<Integer>> tmp = outPutWords.get(lastIdx);
                if(tmp.getKey().equals(w.getKey())) {
                    List<Integer> oldValues = tmp.getValue();
                    List<Integer> newValues = new ArrayList<>(Arrays.asList(oldValues.get(0) + w.getValue().get(0), oldValues.get(1) + 1));
                    outPutWords.get(lastIdx).setValue(newValues);
                } else {
                    outPutWords.add(new Pair<>(w.getKey(), w.getValue()));
                }
            }
        });
    }

    public void addOrCombineToList(String text) {
        String word = getWord(text);
        if(word != "") {
            words.add(new Pair(String.valueOf(word.charAt(0)), Arrays.asList(word.length(), 1)));
        }
    }

    public void printWords() {
        words.forEach(System.out::println);
    }
    public void printMapperOutput() {
        outPutWords.forEach(System.out::println);
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

    public void close(){
        //Emit data to console
        printMapperOutput();
    }

    private void processRecord(String record) {
        List<String> list = List.of(record.split("[ \\-\"\']"));
        for(String text : list) {
            addOrCombineToList(text);
        }
    }
}

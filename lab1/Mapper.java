import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Mapper {
    private List<Pair<String, Integer>> words = new ArrayList<>();

    public Mapper() {}

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
}

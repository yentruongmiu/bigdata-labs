package lab4Q2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Reducer {
    private List<GroupByPair<String, List<Integer>>> groupByPairs = new ArrayList<>();
    private List<Pair<String, List<Integer>>> inputData = new ArrayList<>();

    public Reducer() {}

    public List<GroupByPair<String, List<Integer>>> getGroupByPairs() {
        return groupByPairs;
    }

    public void addPairToGroupByPairs(Pair<String, List<Integer>> pair) {
        //compare key is the same or not => add pair value
        //or create an item then add it
        if (groupByPairs.size() < 1) {
            GroupByPair<String, List<Integer>> newGroup = new GroupByPair<>(pair.getKey(), new ArrayList<>(Arrays.asList(pair.getValue())));
            groupByPairs.add(newGroup);
        } else {
            groupByPairs.stream().filter(group -> group.getKey().equals(pair.getKey()))
                    .findFirst()
                    .ifPresentOrElse(group -> group.addToGroup(pair), () -> {
                        GroupByPair<String, List<Integer>> newGroup = new GroupByPair<>(pair.getKey(), new ArrayList<>(Arrays.asList(pair.getValue())));
                        groupByPairs.add(newGroup);
                    });
        }
    }

    public void sortGroupByPairs() {
        groupByPairs = groupByPairs.stream().sorted(Comparator.comparing(GroupByPair::getKey))
                .collect(Collectors.toList());
    }

    public void printInputGroupByPairs() {
        groupByPairs.forEach(System.out::println);
    }

    public void printOutputGroupByPairs() {
        groupByPairs.forEach(this::printOutputGroupByPair);
    }

    private void printOutputGroupByPair(GroupByPair<String, List<Integer>> groupByPair) {
        DecimalFormat df = new DecimalFormat("#.################");

        List<Integer> accum = new ArrayList<>(Arrays.asList(0, 0));
        List<Integer> sumAve = groupByPair.getValues().stream().reduce(accum, (prevValue, current) -> {
            return new ArrayList<>(Arrays.asList(prevValue.get(0) + current.get(0), prevValue.get(1) + current.get(1)));
        });
        float average = Float.valueOf(df.format((float)sumAve.get(0)/sumAve.get(1)));
        String str = "< " +
                groupByPair.getKey() + " , " + average +
                " >";
        System.out.println(str);
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Reducer {
    private List<GroupByPair<String, Integer>> groupByPairs = new ArrayList<>();

    public Reducer() {
    }

    ;

    public List<GroupByPair<String, Integer>> getGroupByPairs() {
        return groupByPairs;
    }

    public void addPairToGroupByPairs(Pair<String, Integer> pair) {
        //compare key is the same or not => add pair value
        //or create an item then add it
        if (groupByPairs.size() < 1) {
            GroupByPair<String, Integer> newGroup = new GroupByPair<>(pair.getKey(), pair.getValue());
            groupByPairs.add(newGroup);
        } else {
            groupByPairs.stream().filter(group -> group.getKey().equals(pair.getKey()))
                    .findFirst()
                    .ifPresentOrElse(group -> group.addToGroup(pair), () -> {
                        GroupByPair<String, Integer> newGroup = new GroupByPair<>(pair.getKey(), pair.getValue());
                        groupByPairs.add(newGroup);
                    });
        }
    }

    private void sortGroupByPairs() {
        groupByPairs = groupByPairs.stream().sorted(Comparator.comparing(GroupByPair::getKey))
                .collect(Collectors.toList());
    }

    public void printInputGroupByPairs() {
        groupByPairs.forEach(System.out::println);
    }

    public void printOutputGroupByPairs() {
        groupByPairs.forEach(this::printOutputGroupByPair);
    }

    private void printOutputGroupByPair(GroupByPair<String, Integer> groupByPair) {
        Integer total = groupByPair.getValues().stream().reduce(0, (sum, val) -> sum + val);
        String str = "< " +
                groupByPair.getKey() + " , " + total +
                " >";
        System.out.println(str);
    }
}

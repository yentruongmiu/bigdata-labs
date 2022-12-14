package lab2A;

import java.util.ArrayList;
import java.util.List;

public class GroupByPair<K, V> {
    private K key;
    private List<V> values = new ArrayList<>();

    public GroupByPair(K key, V value) {
        this.key = key;
        this.values.add(value);
    }

    public GroupByPair(K key) {
        this.key = key;
    }

    public void setKey( K key) {
        this.key = key;
    }
    public K getKey () {
        return key;
    }

    public List<V> getValues() {
        return values;
    }

    public void addToGroup(Pair<K,V> pair) {
        if(pair.getKey().equals(this.key)) {
            this.values.add(pair.getValue());
        }
    }

    @Override
    public String toString() {
        return " < " +
                key +
                " , " + values.toString() +
                " >";
    }
}

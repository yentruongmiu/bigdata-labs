package lab4Q2;

import java.util.ArrayList;
import java.util.List;

public class GroupByPair<K, V> {
    private K key;
    private List<V> values = new ArrayList<>();

    public GroupByPair(K key, V value) {
        this.key = key;
        this.values.add(value);
    }
    public GroupByPair(K key, List<V> values) {
        this.key = key;
        values.forEach(value -> this.values.add(value));
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

    public void setValues(List<V> values) {
        this.values = values;
    }
    public void addValues(V value) {
        this.values.add(value);
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

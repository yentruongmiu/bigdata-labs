package lab4Q1;

import java.util.*;

public class InMapperWordCount {
    private int mapperNumber;
    private List<Mapper> mappers = new ArrayList<>();
    private int reducerNumber;
    private List<Reducer> reducers = new ArrayList<>();

    public InMapperWordCount(int numMapper, int numReducer) {
        this.mapperNumber = numMapper;
        this.reducerNumber = numReducer;
        for(int i = 0; i < numMapper; i++) {
            mappers.add(new Mapper());
        }
        for(int j = 0; j < numReducer; j++) {
            reducers.add(new Reducer());
        }
    }

    public int getMapperNumber() {
        return mapperNumber;
    }
    public int getReducerNumber() {
        return reducerNumber;
    }
    public List<Mapper> getMappers() {
        return mappers;
    }
    public Mapper getMapperByIndex(int index) {
        return mappers.get(index);
    }
    public List<Reducer> getReducers() {
        return reducers;
    }
    public Reducer getReducerByIndex(int index) {
        return reducers.get(index);
    }

    public int getPartition(String key) {
        return (int) key.hashCode() % reducerNumber;
    }

    public void mapperProduceWords() {
        Map<String, List<Pair<String, Integer>>> producePairs = new HashMap();

        for(int i = 0; i < mappers.size(); i++) {
            Mapper mapper = mappers.get(i);
            List<Pair<String, Integer>> words = mapper.getListWord();

            for(int j = 0; j < words.size(); j ++) {
                Pair<String, Integer> pair = words.get(j);
                int idxReducer = getPartition(pair.getKey());
                String key = "Mapper " + i + " Reducer " + idxReducer;
                if(producePairs.containsKey(key)){
                    List<Pair<String, Integer>> tmp = producePairs.get(key);
                    tmp.add(pair);
                    producePairs.replace(key, tmp);
                } else {
                    List<Pair<String, Integer>> tmp = new ArrayList<>(Arrays.asList(pair));
                    producePairs.put(key, tmp);
                }
                Reducer reducer = reducers.get(idxReducer);
                reducer.addPairToGroupByPairs(pair);
            }
        }
        producePairs.forEach( (key, items) -> {
            System.out.println("Pairs send from " + key);
            items.forEach(System.out::println);
        });
    }
}

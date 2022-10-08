package lab4Q2;

import java.util.*;

public class InMapperWordAverage {
    private int mapperNumber;
    private List<Mapper> mappers = new ArrayList<>();
    private int reducerNumber;
    private List<Reducer> reducers = new ArrayList<>();

    public InMapperWordAverage(int mapperNumber, int reducerNumber) {
        this.mapperNumber = mapperNumber;
        this.reducerNumber = reducerNumber;
        for(int i = 0; i < mapperNumber; i++) {
            mappers.add(new Mapper());
        }
        for(int i = 0; i < reducerNumber; i++) {
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

    public void calculateReducerForEmitting() {
        Map<String, List<Pair<String, List<Integer>>>> emitPairs = new HashMap();

        for(int i = 0; i < mapperNumber; i++) {
            Mapper mapper = mappers.get(i);
            List<Pair<String, List<Integer>>> groupWords = mapper.getOutPutWords();

            for(int j = 0; j < groupWords.size(); j ++) {
                Pair<String, List<Integer>> groupPair = groupWords.get(j);
                int idxReducer = getPartition(groupPair.getKey());

                String key = "Mapper " + i + " Reducer " + idxReducer;
                if(emitPairs.containsKey(key)) {
                    List<Pair<String, List<Integer>>> tmp = emitPairs.get(key);
                    tmp.add(groupPair);
                    emitPairs.replace(key, tmp);
                } else {
                    List<Pair<String, List<Integer>>> tmp = new ArrayList<>(Arrays.asList(groupPair));
                    emitPairs.put(key, tmp);
                }
                Reducer reducer = reducers.get(idxReducer);
                mapper.close(reducer, groupPair);
            }
        }
        emitPairs.forEach( (key, items) -> {
            System.out.println("Pairs send from " + key);
            items.forEach(System.out::println);
        });
    }
}

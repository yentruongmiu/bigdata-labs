import java.util.*;

public class WordCount {
    //number of input-splits == mapper number
    private int mapperNumber;
    private List<Mapper> mappers = new ArrayList<>();
    //number of reducers
    private int reducerNumber;
    private List<Reducer> reducers = new ArrayList<>();
    //method getPartition -> determine the reducer

    public WordCount(int mapperNumber, int reducerNumber) {
        this.mapperNumber = mapperNumber;
        this.reducerNumber = reducerNumber;
        for(int i = 0; i < mapperNumber; i++) {
            mappers.add(new Mapper());
        }
        for(int j = 0; j < reducerNumber; j++) {
            reducers.add(new Reducer());
        }
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

    //determine the reducer that a key-value Pair should go
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

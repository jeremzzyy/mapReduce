import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapReduce {
    public static final int MAPPER_NUM = 6;
    int num =0;
    public static List<Tuple> result;

    public class Tuple<String, Integer>{
        public final String key;
        public final Integer value;

        public Tuple(String key, Integer value){
            this.key = key;
            this.value = value;
        }
        public void printTuple(){
            System.out.println(key + ": " + value);
        }
    }



    public String readFile(StringBuffer buffer,String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine();
        while (line != null) {
            buffer.append(line);
            buffer.append(" ");
            line = reader.readLine();
        }
        reader.close();
        is.close();
        return buffer.toString().toLowerCase().replaceAll("[\\pP\\p{Punct}]"," ");
    }
    public String splitString(String filepath, int left, int right) throws IOException {
        String[] split_s = readFile(filepath).split("\n");
        String[] split_ss = new String[right-left];
        System.arraycopy(split_s,left,split_ss,0,right-left);
        return split_ss;
    }

    public String[] splitting(String input){
        String[] wordList = input.split(" ");
        return wordList;
    }

    public List<Tuple> mapping(String[] input){
        List<Tuple> list = new ArrayList<>();
        for (int i=0; i<input.length; i++){
            Tuple tmp = new Tuple(input[i],1);
            list.add(tmp);
        }
        return list;
    }
    public HashMap<String, List<Integer>> shuffling(List<Tuple> list){
        HashMap<String, List<Integer>> map = new HashMap<>();
        for (int i=0; i<list.size(); i++){
            add(map,(String)list.get(i).key);
        }
        return map;
    }

    public void add(HashMap<String, List<Integer>> map, String s){
        if (map.containsKey(s)){
            map.get(s).add(1);
        }
        else{
            List<Integer> tmp = new ArrayList<>();
            tmp.add(1);
            map.put(s,tmp);
        }
    }

    public List<Tuple> reducing(HashMap<String, List<Integer>> map){
        List<Tuple> list = new ArrayList<>();
        for (String s:map.keySet()){
            int count = map.get(s).size();
            Tuple tmp = new Tuple(s,count);
            list.add(tmp);
        }
        return list;
    }
    public List<Tuple> mapReduce(String filepath, int left, int right) throws IOException {
        return reducing(shuffling(mapping(splitting(splitString(filepath, left, right)))));
    }


    public static void main(String[] args) throws IOException {

        String filename =" " ;
        long startTime = System.currentTimeMillis();

        long endTime = System.currentTimeMillis();
        System.out.println("总共需要: " + (endTime - startTime) +  "ms");

        MapReduce mapReduce = new MapReduce();
        List<Tuple> result = mapReduce.mapReduce(filename,0,2);

        for (int i=0; i<result.size(); i++){
            result.get(i).printTuple();
        }
        long endtime = System.currentTimeMillis();
       System.out.println(endtime-startTime);
    }
}

}


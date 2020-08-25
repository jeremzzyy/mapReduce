import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyThread implements Runnable {
    private String name;
    private int left;
    private int right;
    private String filename;
    private List<MapReduce.Tuple> result;


    public MyThread(String name,String filename, int left, int right, List<MapReduce.Tuple> result){
        this.name = name;
        this.left = left;
        this.right = right;
        this.filename = filename;
        this.result = result;
    }

    public List<MapReduce.Tuple> mapReduceThread(String filename, int left, int right) throws IOException {
        MapReduce  mapReduce = new MapReduce();

        return mapReduce.mapReduce(filename,left,right);
    }

    public void print(){
        for (MapReduce.Tuple tuple: result){
            tuple.printTuple();
        }
    }

    @Override
    public void run() {
        try {
            List<MapReduce.Tuple> tmp_result = mapReduceThread(filename, left, right);
            for (MapReduce.Tuple tuple : tmp_result) {
                if (result.contains(tuple.key)){
                    result.get(result.indexOf(tuple.key)).value += tuple.value;
                }
                else{
                    result.add(tuple);
                }

            }
            for (int i=0; i<result.size(); i++){
                result.get(i).printTuple();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

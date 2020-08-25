import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String[] args){

        List<MapReduce.Tuple> result = new ArrayList<>();
        String filename = "/Users/jocelyn/Exercise/Offer/src/HTSC/hamlet.txt";
        MyThread mt1 = new MyThread("线程1",filename, 0,1000,result);
        MyThread mt2 = new MyThread("线程2",filename, 1001,2000,result);
        MyThread mt3 = new MyThread("线程3",filename, 2001,3000,result);
        MyThread mt4 = new MyThread("线程4",filename, 3001,4000,result);
        MyThread mt5 = new MyThread("线程1",filename, 4001,5500,result);

        Thread t1 = new Thread(mt1);
        Thread t2 = new Thread(mt2);
        Thread t3 = new Thread(mt3);
        Thread t4 = new Thread(mt4);
        Thread t5 = new Thread(mt5);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        for (MapReduce.Tuple tuple: result) {
            tuple.printTuple();
        }

    }
}

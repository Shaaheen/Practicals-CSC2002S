import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * Created by Shaaheen on 7/27/2015.
 */
public class Main {

    //Test array of what input text files would produce
    public static int[] testAry = {5,12,80,91,5,4,60,500,800,8,7,45,65,2,12,54,6,8,98,97,64,6,12,35,43,79,46,116,1};

    public static void main(String[] args) throws InterruptedException{
        //TestThread ts = new TestThread(5,6,8,1);
        String time = "nfvjkf";
        int t = 06;
        String yess = "Hello " + "World";
        //int yu = Integer.parseInt(yess.substring(0,1) + yess.substring(1,2));

        //System.out.println(ts.ans);
        for (int i = 0; i <  testAry.length -2 ; i++){
            (new TestThread(testAry[i] , testAry[i+1] , testAry[i+2] ,i)).start();
        }

    }
}

//To test threading
class TestThread extends Thread {
    //Instance var to store object properties
    public int x;
    public int y;
    public int threadNum;
    public int z;
    public int[] splitAry;

    //Constructor method to set object variables
    TestThread(int x,int y,int z,int i){

        this.x = x;
        this.y = y;
        this.z = z;
        int[] spltArray = {x,z,y};
        this.splitAry = spltArray;
        this.threadNum = i;
    }

    //Thread method that will run when thread initialised
    public void run(){
        Arrays.sort(splitAry);
        System.out.println("Thread " + threadNum + " Median is: " + splitAry[splitAry.length/2]);
    }

}

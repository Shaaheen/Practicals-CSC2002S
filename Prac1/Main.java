import java.util.concurrent.*;

/**
 * Created by Shaaheen on 7/27/2015.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException{
        TestThread ts = new TestThread(5,6);
        ts.start();
        ts.join();
        System.out.println(ts.ans);

    }
}

//To test threading
class TestThread extends Thread {
    //Instance var to store object properties
    public int x;
    public int y;
    public int ans;

    //Constructor method to set object variables
    TestThread(int x,int y){
        this.x = x;
        this.y = y;
    }

    //Thread method that will run when thread initialised
    public void run(){
        //System.out.println("Thread 2");
        ans=x+y;
    }

}

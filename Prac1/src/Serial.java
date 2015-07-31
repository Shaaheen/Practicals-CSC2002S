package src;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Shaaheen on 7/28/2015.
 */
public class Serial {

    //Array to test program on as input from text files hasn't been implemented yet
    public static double[] testAry = {5,12,80,91,5,4,60,500,800,8,7,45,65,2,12,54,6,8,98,97,64,6,12,35,43,79,46,116,1};
    public static int filterTest = 3;

    public double[] noise;
    public int filter;
    public int ends;

    Serial(double[] noiseArray){
        this.noise = noiseArray;
    }

    public static void main(String[] args) throws IOException {
        String workingDir = System.getProperty("user.dir");
        double[] txt1 = FileUtil.getNoise(workingDir + "\\src\\TxtFiles\\inp1.txt");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        long time3 = System.currentTimeMillis();
        System.out.println("Start : " + dateFormat.format(date));
        Serial srTx1 = new Serial(txt1);
        System.out.println( "Created Object : " + dateFormat.format(date));
        srTx1.filterNoise(filterTest);
        System.out.println("Done : " + dateFormat.format(date));
        System.out.println("inp1.txt :");
        long time4 = System.currentTimeMillis();
        System.out.println("In Milli seconds : " + (time4 - time3));
        //srTx1.printNoise();
        System.out.println();
        //System.out.println();

        long time1 = System.currentTimeMillis();
        double[] txt4 = FileUtil.getNoise(workingDir + "\\src\\TxtFiles\\inp4.txt");
        System.out.println("Milli seconds :" + time1);
        System.out.println("Start : " + dateFormat.format(date));
        Serial srTx4 = new Serial(txt4);
        System.out.println( "Created Object : " + dateFormat.format(date));
        srTx4.filterNoise(filterTest);
        System.out.println("Done : " + dateFormat.format(date));
        long time2 = System.currentTimeMillis();
        System.out.println("In Milliseconds : " + (time2 - time1));
        System.out.println("inp1.txt :");
        //srTx1.printNoise();
        System.out.println();

        Serial sr1 = new Serial(testAry);
        sr1.filterNoise(filterTest);
        sr1.printNoise();
        int[] testing = {5,6,8,2};
        System.out.println(Arrays.toString(Arrays.copyOfRange(testing,1,testing.length-1)));
    }

    /*
        Method to print array for comparison
     */
    public void printNoise(){
        for (int j = 0; j <noise.length; j++){
            System.out.print(noise[j] + " ");
        }
    }

    /*
    Method to set values in an array to the median of the
    */
    public double[] filterNoise(int filter){
        this.filter = filter;
        this.ends = filter - ((filter/2) + 1); //Gets the border lengths i.e length from centre to end of array of filter size
        //eg filter size of 5 will give a segment {1,2,3,4,5} the ends = 2 as length between centre and end is 2

        for (int i = ends; i < noise.length - ends; i++){
            noise[i] = getMiddle(i);
        }
        return noise;
    }

    /*
        Method to get the median of a segment of an array at the index i
     */
    public double getMiddle(int i){

        double[] tempArray = Arrays.copyOfRange(noise , i - ends , i + ends + 1 );
        Arrays.sort(tempArray);
        return tempArray[tempArray.length/2];
    }
}

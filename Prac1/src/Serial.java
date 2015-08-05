package src;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Shaaheen on 7/28/2015.
 */
public class Serial {

    //Array to test program on as input from text files hasn't been implemented yet
    public static double[] testAry = {5,12,80,91,5,4,60,500,800,8,7,45,65,2,12,54,6,8,98,97,64,6,12,35,43,79,46,116,1};
    public static int filterTest = 3;

    //Stored variables for Serial Object
    private double[] noise;
    private double[] theFilteredNoise;
    private int filter;
    private int ends;

    //Constructor to set array in object
    public Serial(double[] noiseArray){
        this.noise = noiseArray;
        this.theFilteredNoise = new double[noiseArray.length];
    }

    public Serial(String fileName,int f) throws IOException {
        this.noise = FileUtil.getNoise(fileName);
        this.filter = f;
        this.theFilteredNoise = new double[noise.length];
    }

    public double serialNoiseFilter(){
        long time1 = System.nanoTime();
        double[] filtered1 = filterNoise(filter);
        long time2 = System.nanoTime();
        return (time2-time1 + 0.0)/1000000000;
    }

    public String markDownTimes(int repeat){
        String exportString = "Times for Filter :," + filter + ",Array:," + "" + "\r\n" + "\r\n";
        exportString = exportString + "Trials , Times " + "\r\n";
        long time1,time2;
        for (int i = 0; i < repeat ; i++){
            time1 = System.nanoTime();
            double[] filtered1 = filterNoise(filter);
            time2 = System.nanoTime();
            exportString = exportString + i + "," + ( (time2-time1 + 0.0) / 1000000000) + "\r\n";
        }
        return exportString;
    }

    /*
        Method to print array for comparison
     */
    private void printNoise(){
        for (int j = 0; j <noise.length; j++){
            System.out.print(noise[j] + " ");
        }
    }

    /*
    Method to set values in an array to the median of the
    */
    public double[] filterNoise(int filter){
        if (filter > noise.length){
            return noise;
        }
        this.filter = filter;
        this.ends = filter - ((filter/2) + 1); //Gets the border lengths i.e length from centre to end of array of filter size
        //eg filter size of 5 will give a segment {1,2,3,4,5} the ends = 2 as length between centre and end is 2

        for (int i = 0; i < noise.length; i++) {

            if (i <ends || (i>(noise.length - ends - 1)) ){
                theFilteredNoise[i] = noise[i];
            }
            else{
                theFilteredNoise[i] = getMiddle(i);
            }

        }
        return theFilteredNoise;
    }

    /*
        Method to get the median of a segment of an array at the index i
     */
    private double getMiddle(int i){
        double[] tempArray = Arrays.copyOfRange(noise , i - ends , i + ends + 1 );
        Arrays.sort(tempArray);
        return tempArray[tempArray.length/2];
    }
}

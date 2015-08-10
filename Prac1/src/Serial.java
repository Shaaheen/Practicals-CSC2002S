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
 * Class to filter Noise sequentially with a specified filter
 */
public class Serial {

    //Stored variables for Serial Object
    private double[] noise;
    private double[] theFilteredNoise;
    private int filter;
    private int ends;

    //Constructor for Parallel use
    public Serial(double[] noiseArray){
        this.noise = noiseArray;
        this.theFilteredNoise = new double[noiseArray.length];
    }

    //Constructor for Sequential use
    public Serial(double[] noiseAry,int f) throws IOException {
        this.noise = noiseAry;
        this.filter = f;
        this.theFilteredNoise = new double[noise.length];
    }

    //Will process methods need to filter array and return time taken
    public double serialNoiseFilter(boolean median){
        long time1 = System.nanoTime();
        double[] filtered1 = filterNoise(filter,median);
        long time2 = System.nanoTime();
        return (time2-time1 + 0.0)/1000000000;
    }

    public String markDownTimes(int repeat){
        String exportString = "Times for Filter :," + filter + ",Array:," + "" + "\r\n" + "\r\n";
        exportString = exportString + "Trials , Times " + "\r\n";
        long time1,time2;
        for (int i = 0; i < repeat ; i++){
            time1 = System.nanoTime();
            double[] filtered1 = filterNoise(filter,true);
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
    Method to set values in an array to the median of the filter segment
    */
    public double[] filterNoise(int filter,boolean median){
        //If filter is bigger than the whole array then just return the array
        if (filter > noise.length){
            return noise;
        }
        //set filter
        this.filter = filter;
        //Gets the border lengths i.e length from centre to end of array of filter size
        this.ends = filter - ((filter/2) + 1);
        //eg filter size of 5 will give a segment {1,2,3,4,5} the ends = 2 as length between centre and end is 2

        //Iterate through the noise
        for (int i = 0; i < noise.length; i++) {
            //If on the border then don't process
            if (i <ends || (i>(noise.length - ends - 1)) ){
                theFilteredNoise[i] = noise[i];
            }
            else{
                if (median){ //If using median filtering
                    theFilteredNoise[i] = getMiddle(i);
                }
                else{ //if using mean filtering
                    theFilteredNoise[i] = getMean(i);
                }
            }

        }
        return theFilteredNoise;
    }

    /*
        Method to get the median of a segment of an array at the index i
     */
    private double getMiddle(int i){
        //Gets an array of the appropriate filter size
        double[] tempArray = Arrays.copyOfRange(noise , i - ends , i + ends + 1 );
        //Sorts filter size array
        Arrays.sort(tempArray);
        //Returns middle value of sorted array
        return tempArray[tempArray.length/2];
    }

    public double getMean(int i){
        double[] tempArray = Arrays.copyOfRange(noise,i - ends, i + ends + 1);
        double mean = 0.0;
        for (double j:tempArray){
            mean += j;
        }
        return mean/tempArray.length;
    }
}

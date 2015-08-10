package src;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import javax.print.attribute.IntegerSyntax;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Shaaheen on 7/30/2015.
 * Class to parallelize median filtering
 */
public class RecursiveThread {

    //Instance variables
    public double[] noiseAry;
    public int filter;

    static final ForkJoinPool fjPool = new ForkJoinPool();

    //Main filter array method that calls the threaded forkjoin class
    public static double[] filterArray(double[] array,int filter,boolean median) {
        int ends = filter - ((filter/2) + 1); //Gets the border lengths i.e length from centre to end of array of filter size
        if (filter > array.length){
            return array;
        }
        return fjPool.invoke(new FilterNoise(array,0,array.length,filter,ends,median));
    }

    //Constructor to set array and filter
    public RecursiveThread(double[] noise,int f) throws IOException {
        this.noiseAry = noise;
        this.filter = f;
    }

    //returns the time taken to filter the noise given
    public double parallelNoiseFilter(boolean median){
        long time1 = System.nanoTime();
        double[] filtered1 = filterArray(noiseAry,filter,median);
        long time2 = System.nanoTime();
        return (time2-time1 + 0.0)/1000000000;
    }


    //To mark down the times in a excel file so as to graph and find avg
    public String markDownTimes(int repeat) throws FileNotFoundException {
        PrintStream stream = new PrintStream("ParallelTimes.csv");
        String exportString = "Times for Filter :," + filter + ",Array:," + "" + "\r\n" + "\r\n";
        exportString = exportString + "Trials , Times " + "\r\n";
        long time1,time2;
        for (int i = 0; i < repeat ; i++){
            time1 = System.nanoTime();
            filterArray(noiseAry, filter,true);
            time2 = System.nanoTime();
            exportString = exportString + i + "," + ( (time2-time1 + 0.0) / 1000000000) + "\r\n";
        }
        stream.print(exportString);
        return exportString;
    }

    public static void changeSequentialCutOff(int seq){
        FilterNoise.SEQUENTIAL_THRESHOLD = seq;
    }
}

//Threaded class
class FilterNoise extends RecursiveTask< double[] > {
    //Instance variables of a thread
    static int SEQUENTIAL_THRESHOLD = 10000;
    int lo;
    int hi;
    double[] arr;
    int ends ;
    int filter;
    boolean median;

    //Constructor method of a thread
    FilterNoise(double[] a, int l, int h,int fil,int end,boolean median) {
        lo=l;
        hi=h;
        arr=a;
        this.filter = fil;
        this.ends = end;
        this.median = median;
    }

    //Method run when thread is created
    public double[] compute() {

        //If array is of the size of the sequential cutoff
        if(hi - lo <= SEQUENTIAL_THRESHOLD) {
            //If whole noise is smaller than cut off then just process sequentially
            if ( lo == 0 && hi == arr.length){
                Serial filtered = new Serial(arr);
                return filtered.filterNoise(filter,median);
            }
            //if beginning of noise then join  from the beginning of array and leave borders
            else if (lo == 0){
                Serial filtered = new Serial(Arrays.copyOfRange(arr , lo, hi + ends));
                double[] noiseFiltered = filtered.filterNoise(filter,median);
                return Arrays.copyOfRange(noiseFiltered,0,noiseFiltered.length - ends);
            }
            //if end of array then leave don't process the end of the array
            else if (hi==arr.length){
                Serial filtered = new Serial(Arrays.copyOfRange(arr , lo - ends, hi));
                double[] noiseFiltered = filtered.filterNoise(filter,median);
                return Arrays.copyOfRange(noiseFiltered,ends,noiseFiltered.length);
            }
            //else a middle segment of array then take extra length on both sides to process edges
            else{
                Serial filtered = new Serial(Arrays.copyOfRange(arr , lo - ends, hi + ends));
                double[] noiseFiltered = filtered.filterNoise(filter,median);
                return Arrays.copyOfRange(noiseFiltered,ends,noiseFiltered.length - ends);
            }

        }
        else {
            //If need to reduce array size for a thread even more
            //Create a thread with the first half of the array to process
            FilterNoise left = new FilterNoise(arr,lo,(hi+lo)/2,filter,ends,median);
            //Compute the other half of the array in the current thread
            FilterNoise right = new FilterNoise(arr,(hi+lo)/2,hi,filter,ends,median);
            //Wait for the first thread to finish
            left.fork();
            //Get the array from the current thread computation
            double[] rightAns = right.compute();
            //get array from the other thread
            double[] leftAns = left.join();
            //Join the results of both threads
            return joinFilteredNoise(leftAns,rightAns);
        }
    }

    /*
        Method to join two integer arrays together
     */
    public double[] joinFilteredNoise(double[] a1,double[] a2){

        double[] joined = new double[a1.length + a2.length];

        for (int i = 0 ; i < (a1.length + a2.length); i++){
            if (i >= a1.length ){
                joined[i] = a2[ i - a1.length];
            }
            else{
                joined[i] = a1[i];
            }
        }

        return joined;

    }
}


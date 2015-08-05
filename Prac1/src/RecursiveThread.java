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
 */
public class RecursiveThread {

    //Instance variables
    public double[] noiseAry;
    public int filter;

    static final ForkJoinPool fjPool = new ForkJoinPool();

    //Main filter array method that calls the threaded forkjoin class
    public static double[] filterArray(double[] array,int filter) {
        int ends = filter - ((filter/2) + 1); //Gets the border lengths i.e length from centre to end of array of filter size
        if (filter > array.length){
            return array;
        }
        return fjPool.invoke(new FilterNoise(array,0,array.length,filter,ends));
    }

    //Constructor to set array and filter
    public RecursiveThread(String filename,int f) throws IOException {
        this.noiseAry = FileUtil.getNoise(filename);
        this.filter = f;
    }

    //returns the time taken to filter the noise given
    public double parallelNoiseFilter(){
        long time1 = System.nanoTime();
        double[] filtered1 = filterArray(noiseAry,filter);
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
            filterArray(noiseAry, filter);
            time2 = System.nanoTime();
            exportString = exportString + i + "," + ( (time2-time1 + 0.0) / 1000000000) + "\r\n";
        }
        stream.print(exportString);
        return exportString;
    }
}

//Threaded class
class FilterNoise extends RecursiveTask<double[]> {
    //Instance variables of a thread
    static int SEQUENTIAL_THRESHOLD = 10000;
    int lo;
    int hi;
    double[] arr;
    int ends ;
    int filter;

    //Constructor method of a thread
    FilterNoise(double[] a, int l, int h,int fil,int end) {
        lo=l;
        hi=h;
        arr=a;
        this.filter = fil;
        this.ends = end;
    }

    //Method run when thread is created
    public double[] compute() {

        //If array is of the size of the sequential cutoff
        if(hi - lo <= SEQUENTIAL_THRESHOLD) {
            //If whole noise is smaller than cut off then just process sequentially
            if ( lo == 0 && hi == arr.length){
                Serial filtered = new Serial(arr);
                return filtered.filterNoise(filter);
            }
            //if beginning of noise then join  from the beginning of array and leave borders
            else if (lo == 0){
                Serial filtered = new Serial(Arrays.copyOfRange(arr , lo, hi + ends));
                double[] noiseFiltered = filtered.filterNoise(filter);
                return Arrays.copyOfRange(noiseFiltered,0,noiseFiltered.length - ends);
            }
            //if end of array then leave don't process the end of the array
            else if (hi==arr.length){
                Serial filtered = new Serial(Arrays.copyOfRange(arr , lo - ends, hi));
                double[] noiseFiltered = filtered.filterNoise(filter);
                return Arrays.copyOfRange(noiseFiltered,ends,noiseFiltered.length);
            }
            //else a middle segment of array then take extra length on both sides to process edges
            else{
                Serial filtered = new Serial(Arrays.copyOfRange(arr , lo - ends, hi + ends));
                double[] noiseFiltered = filtered.filterNoise(filter);
                return Arrays.copyOfRange(noiseFiltered,ends,noiseFiltered.length - ends);
            }

        }
        else {
            FilterNoise left = new FilterNoise(arr,lo,(hi+lo)/2,filter,ends);
            FilterNoise right = new FilterNoise(arr,(hi+lo)/2,hi,filter,ends);
            left.fork();
            double[] rightAns = right.compute();
            double[] leftAns = left.join();
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


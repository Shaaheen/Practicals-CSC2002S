package src;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import javax.print.attribute.IntegerSyntax;
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

    public static double[] testAry = {5,12,80,91,5,4,60,500,800,8,7,45,65,2,12,54,6,8,98,97,64,6,12,35,43,79,46,116,1};

    static final ForkJoinPool fjPool = new ForkJoinPool();
    public static double[] filterArray(double[] array,int filter) {
        int ends = filter - ((filter/2) + 1); //Gets the border lengths i.e length from centre to end of array of filter size
        if (filter > array.length){
            return array;
        }
        return fjPool.invoke(new FilterNoise(array,0,array.length,filter,ends));
    }

    public static void main(String[] args) throws IOException {
        PrintStream stream = new PrintStream("times.csv");
        String workingDir = System.getProperty("user.dir");

        double[] txt4 = FileUtil.getNoise(workingDir + "\\src\\TxtFiles\\inp4.txt");
        long time1 = System.nanoTime();
        System.out.println();
        System.out.println("Inp4.txt - Start:");
        double[] filtered1 = filterArray(txt4,21);
        long time2 = System.nanoTime();
        System.out.println("Done: " + ((time2-time1 + 0.0)/1000000000) + " seconds");
        System.out.println();

        String csvTest = markDownTimes(txt4,21,40);
        stream.println(csvTest);

    }

    public static String markDownTimes(double[] array,int filter,int repeat){
        String exportString = "Times for Filter :," + filter + ",Array:," + "" + "\r\n" + "\r\n";
        exportString = exportString + "Trials , Times " + "\r\n";
        long time1,time2;
        for (int i = 0; i < repeat ; i++){
            time1 = System.nanoTime();
            filterArray(array, filter);
            time2 = System.nanoTime();
            exportString = exportString + i + "," + ( (time2-time1 + 0.0) / 1000000000) + "\r\n";
        }
        return exportString;
    }
}

class FilterNoise extends RecursiveTask<double[]> {
    static int SEQUENTIAL_THRESHOLD = 10000;
    int lo;
    int hi;
    double[] arr;
    int ends ;
    int filter;

    FilterNoise(double[] a, int l, int h,int fil,int end) {
        lo=l;
        hi=h;
        arr=a;
        this.filter = fil;
        this.ends = end;
    }

    public double[] compute() {
        if(hi - lo <= SEQUENTIAL_THRESHOLD) {
            if ( lo == 0 && hi == arr.length){
                Serial filtered = new Serial(arr);
                return filtered.filterNoise(filter);
            }
            else if (lo == 0){
                Serial filtered = new Serial(Arrays.copyOfRange(arr , lo, hi + ends));
                double[] noiseFiltered = filtered.filterNoise(filter);
                return Arrays.copyOfRange(noiseFiltered,0,noiseFiltered.length - ends);
            }
            else if (hi==arr.length){
                Serial filtered = new Serial(Arrays.copyOfRange(arr , lo - ends, hi));
                double[] noiseFiltered = filtered.filterNoise(filter);
                return Arrays.copyOfRange(noiseFiltered,ends,noiseFiltered.length);
            }
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


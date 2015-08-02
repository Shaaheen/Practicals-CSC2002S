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

    //Stored variables for Serial Object
    public double[] noise;
    public double[] theFilteredNoise;
    public int filter;
    public int ends;

    //Constructor to set array in object
    public Serial(double[] noiseArray){
        this.noise = noiseArray;
        this.theFilteredNoise = new double[noiseArray.length];
    }

    public static void main(String[] args) throws IOException {
        //Gets absolute path of source directory - for text files
        String workingDir = System.getProperty("user.dir");
        //Set file name path of the first text files
        double[] txt1 = FileUtil.getNoise(workingDir + "\\src\\TxtFiles\\inp1.txt");


        /*
        long time3 = System.currentTimeMillis();
        System.out.println("Inp1.txt -Start : " + dateFormat.format(date));
        Serial srTx1 = new Serial(txt1);
        System.out.println( "Created Object : " + dateFormat.format(date));
        srTx1.filterNoise(filterTest);
        System.out.println("Done : " + dateFormat.format(date));
        long time4 = System.currentTimeMillis();
        System.out.println("In Milli seconds : " + (time4 - time3));
        //srTx1.printNoise();
        System.out.println();
        //System.out.println();
        */


        double[] txt5 = FileUtil.getNoise(workingDir + "\\src\\TxtFiles\\inp4.txt");
        long time1 = System.nanoTime();
        System.out.println();
        System.out.println("Inp4.txt - Start:");
        Serial sr5 = new Serial(txt5);
        double[] filtered1 = sr5.filterNoise(21);
        long time2 = System.nanoTime();
        System.out.println("Done: " + ((time2-time1 + 0.0)/1000000000) + " seconds");
        System.out.println();

        //System.out.println(Arrays.toString(filtered1));



        int[] testing = {5,6,8,2};
        //System.out.println(Arrays.toString(Arrays.copyOfRange(testing,1,testing.length-1)));

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
    public double getMiddle(int i){
        double[] tempArray = Arrays.copyOfRange(noise , i - ends , i + ends + 1 );
        Arrays.sort(tempArray);
        return tempArray[tempArray.length/2];
    }
}

package src;

import java.util.Arrays;

/**
 * Created by Shaaheen on 7/28/2015.
 */
public class Serial {

    //Array to test program on as input from text files hasn't been implemented yet
    public static int[] testAry = {5,12,80,91,5,4,60,500,800,8,7,45,65,2,12,54,6,8,98,97,64,6,12,35,43,79,46,116,1};
    public static int filterTest = 3;

    public int[] noise;
    public int filter;
    public int ends;

    Serial(int[] noiseArray){
        this.noise = noiseArray;
    }

    public static void main(String[] args) {
        Serial sr1 = new Serial(testAry);
        sr1.filterNoise(filterTest);
        for (int j = 0; j <sr1.noise.length; j++){
            System.out.print(sr1.noise[j] + " ");
        }
    }

    /*
    Method to set values in an array to the median of the
    */
    public void filterNoise(int filter){
        this.filter = filter;
        this.ends = filter - ((filter/2) + 1); //Gets the border lengths i.e length from centre to end of array of filter size
        //eg filter size of 5 will give a segment {1,2,3,4,5} the ends = 2 as length between centre and end is 2

        for (int i = ends; i < noise.length - ends; i++){
            noise[i] = getMiddle(i);
        }
    }



    /*
        Method to get the median of a segment of an array at the index i
     */
    public int getMiddle(int i){

        int[] tempArray = Arrays.copyOfRange(noise , i - ends , i + ends + 1 );
        Arrays.sort(tempArray);
        return tempArray[tempArray.length/2];
    }
}

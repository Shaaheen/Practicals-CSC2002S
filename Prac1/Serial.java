import java.util.Arrays;

/**
 * Created by Shaaheen on 7/28/2015.
 */
public class Serial {

    //Array to test program on as input from text files hasn't been implemented yet
    public static int[] testAry = {5,12,80,91,5,4,60,500,800,8,7,45,65,2,12,54,6,8,98,97,64,6,12,35,43,79,46,116,1};
    public static int filter = 3;

    public static void main(String[] args) {
        int start = filter - ((filter/2) + 1);
        System.out.println(start);
        System.out.println(testAry[3]);
        int[] tempAry;
        int[] tempAry1 = Arrays.copyOfRange(testAry , 1 - start , 1 + start + 1 );
        System.out.println(tempAry1[0] + " " + tempAry1[1] + " " + tempAry1[2]);

        for (int i = start; i <testAry.length - start; i++){
            testAry[i] = getMiddle(testAry,i,start);
        }
    }

    /*
        Method to get the median of a segment of an array at the index i
     */
    public static int getMiddle(int[] arry, int i,int start){
        int[] tempArray = Arrays.copyOfRange(testAry , i - start , i + start + 1 );
        Arrays.sort(tempArray);

        return tempArray[tempArray.length/2];
    }
}

package src.test;

import junit.framework.TestCase;
import org.junit.Test;
import src.*;

public class MedianTest extends TestCase {

    //*************CREATE MORE TEST CASES****************
    //^^^^^^^^^^^^CREATE RANDOM TEST CASES FOR SEQUENTIAL VS PARALLEL^^^^^^^^^^^^^^^^^^^

    /*
        Method to test if the Serial class median filtering works
     */
    @Test
    public void testSerial() throws Exception {
        double[] testArray = {5,12,80,91,5,4,60,500,800,8,7,45};

        //Test 1 - filter 3
        double[] expectedArray = {5.0,12.0,80.0,80.0,5.0,5.0,60.0,500.0,500.0,8.0,8.0,45};
        testSerialFiltering(testArray,expectedArray,3);

        //Test 2 - filter 5
        double[] expectedArray2 = {5.0,12.0,12.0,12.0,60.0,60.0,60.0,60.0,60.0,45.0,7.0,45.0};
        testSerialFiltering(testArray,expectedArray2,5);

        //Test 3 - filter 7
        double[] expectedArray3 = {5,12,80,12,60,80,60,8,45,8,7,45};
        testSerialFiltering(testArray,expectedArray3,7);

        //Test - filter 9
        double[] expectedArray4 = {5,12,80,91,60,60,60,45,800,8,7,45};
        testSerialFiltering(testArray,expectedArray4,9);

    }

    /*
        Method to go through each element in array and check if its value is what is expected
        at a specific filter. i.e Expected filtered noise = Actual filtered noise?
     */
    public void testSerialFiltering(double[] testArray,double[] expectedArray,int filter) throws Exception{
        Serial test1 = new Serial(testArray);
        double[] actualArray = test1.filterNoise(filter,true);
        for (int i = 0; i < actualArray.length; i++){
            assertEquals("Failed at index " + i + " For Filter " + filter,expectedArray[i],actualArray[i]);
        }
    }

    /*
        UNDER ASSUMPTION SERIAL WORKS FROM PREVIOUS TESTING
        Method to test if Parallel class median filtering works by
        comparing to Serial class results
     */
    @Test
    public void testParallelVsSerial(){
        double[] testArray = {5,12,80,91,5,4,60,500,800,8,7,45};

        //Test 1 - filter 3
        compareSerialVSParallel(testArray,3,1);

        //Test 2 - filter 5
        compareSerialVSParallel(testArray,5,2);

        //Test 3 - filter 7
        compareSerialVSParallel(testArray,7,3);

        //Test 4 - filter 9
        compareSerialVSParallel(testArray,9,4);

        //Test 5 - filter 11
        compareSerialVSParallel(testArray,11,5);

        //Test 6 - filter 21
        compareSerialVSParallel(testArray,21,6);

        double[] testArray2 = {5,12,80,91,5,4,60,500,800,8,7,45,65,2,12,54,6,8,98,97,64,6,12,35,43,79,46,116,1,79,34,52,76,49,17,28,33,48,15,3,156,87,5,32,75,24,31,74,97,99,2,654,234};

        System.out.println(testArray2.length  + "  " +  testArray2[48]);
        //Test 7 - filter 3
        compareSerialVSParallel(testArray2,3,7);

        //Test 8 - filter 5
        compareSerialVSParallel(testArray2,5,8);

        //Test 9 - filter 7
        compareSerialVSParallel(testArray2,7,9);

        //Test 10 - filter 9
        compareSerialVSParallel(testArray2,9,10);

        //Test 11 - filter 11
        compareSerialVSParallel(testArray2,11,11);

        //Test 12 - filter 21
        compareSerialVSParallel(testArray2,21,12);
    }

    /*
        Method to get median filtered arrays from Serial class and Parallel class
        and Check if they are the same
        The Serial class must test correct above for this test to be able to check if the
        Parallel class works properly
     */
    public void compareSerialVSParallel(double[] testArray,int filter, int testNo){
        Serial sr = new Serial(testArray);
        double[] serialResult = sr.filterNoise(filter,true);
        double[] parallelResult = RecursiveThread.filterArray(testArray, filter,true);
        for (int i = 0; i < serialResult.length; i++){
            assertEquals("Failed at index " + i + " For Filter "  + filter + " Test : " + testNo ,serialResult[i], parallelResult[i]);
        }
    }
}
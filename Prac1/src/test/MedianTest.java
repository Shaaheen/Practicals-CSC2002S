package src.test;

import junit.framework.TestCase;
import org.junit.Test;
import src.*;

public class SerialTest extends TestCase {

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

    public void testSerialFiltering(double[] testArray,double[] expectedArray,int filter) throws Exception{
        Serial test1 = new Serial(testArray);
        double[] actualArray = test1.filterNoise(filter);
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
        compareSerialVSParallel(testArray,3);
    }
    public void compareSerialVSParallel(double[] testArray,int filter){
        Serial sr = new Serial(testArray);
        double[] serialResult = sr.filterNoise(filter);
        double[] parallelResult = RecursiveThread.filterArray(testArray, filter);
        for (int i = 0; i < serialResult.length; i++){
            assertEquals(serialResult[i] , parallelResult[i]);
        }
    }
}
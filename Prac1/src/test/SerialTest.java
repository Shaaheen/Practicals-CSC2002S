package src.test;

import junit.framework.TestCase;
import org.junit.Test;
import src.*;

public class SerialTest extends TestCase {

    @Test
    public void testGetMiddle() throws Exception {
        /*
        //test 1
        int[] test1 = {80,6,90};
        assertEquals(80, Serial.getMiddle(test1, 1, 1));

        //test 2
        int[] test2 = {5,12,80,91,5,4,60,500,800,8,7,45,65,2,12,54,6,8,98,97,64,6,12,35,43,79,46,116,1};
        assertEquals(12,Serial.getMiddle(test2,12,2));
        5 12 80 80 5 5 60 500 500 8 8 45 45 12 12 12 8 8 97 97 64 12 12 35 43 46 46 46 1
        5 12 80 80 5 5 60 500 500 8 8 45 45 12 12 12 8 8 97 97 64 12 12 35 43 46 46 46 1
        */
    }

    @Test
    public void testSerialFiltering() throws Exception{
        //test 1 - Filter 3
        double[] testArray = {5,12,80,91,5,4,60,500,800,8,7,45};
        Serial test1 = new Serial(testArray);
        double[] actualArray = test1.filterNoise(3);
        double[] expectedArray = {5.0,12.0,80.0,80.0,5.0,5.0,60.0,500.0,500.0,8.0,8.0,45};

        for (int i = 0; i < actualArray.length; i++){
            assertEquals("Failed at index ",expectedArray[i],actualArray[i]);
        }


        //test 2 - Filter 5
        double[] testArray5 = {5,12,80,91,6,4,60,500,800,8,7,45};
        Serial test2 = new Serial(testArray5);
        double[] actualArray2 = test2.filterNoise(5);
        double[] expectedArray2 = {5.0,12.0,12.0,12.0,60.0,60.0,60.0,60.0,60.0,45.0,7.0,45.0};

        for (int i = 0; i < actualArray2.length; i++){
            assertEquals("Failed at index " + i,expectedArray2[i],actualArray2[i]);
        }

    }
}
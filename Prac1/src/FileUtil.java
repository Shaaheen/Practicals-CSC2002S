package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Shaaheen on 7/30/2015.
 * Class to provide a way to get array from files
 */
public class FileUtil {

    public static double[] getNoise(String fileName) throws IOException {
        BufferedReader bufReader = new BufferedReader( new FileReader(fileName));
        String line = null;
        line = bufReader.readLine();
        double[] noiseArray = new double[Integer.parseInt(line)];
        int i =0;
        StringBuilder  inputText = new StringBuilder();
        String ls = System.getProperty("line.separator");

        while( ( line = bufReader.readLine() ) != null ) {
            String[] split = line.split(" ");
            noiseArray[i] = Double.parseDouble(split[1]);
            i++;
         }
        return noiseArray;
    }
}

import src.RecursiveThread;
import src.Serial;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by Shaaheen on 7/27/2015.
 */
public class Main {

    //Gets current path to text files
    public static String workingDir = System.getProperty("user.dir") + "\\src\\TxtFiles\\";
    //Sets filter for when loading in files
    public static int filter = 21;
    public static boolean median = true;
    public static String med = "Median";

    public static void main(String[] args) throws InterruptedException, IOException {

        if (workingDir.substring(0,1).equals("/")){
            //Then Linux system
            workingDir = System.getProperty("user.dir") + "/src/TxtFiles/";
        }
        //Scanner Object
        Scanner user_input = new Scanner(System.in);
        String option;

        //UI loop
        while (true){
            //Options for user
            System.out.println("Choose Input File" +
                    "\r\n1)inp1.txt" +
                    "\r\n2)inp2.txt" +
                    "\r\n3)inp3.txt" +
                    "\r\n4)inp4.tx" +
                    "\r\n5)Change Filter (Filter is " + filter + ")" +
                    "\r\n6)Change type of Filtering (Type is " + med + ")" +
                    "\r\n7)Quit");

            option = user_input.nextLine();
            //Create Parallel and Serial class to compare
            if (option.equals("1")){
                //Create and set Parallel class with name of file of array and filter
                RecursiveThread RT1 = new RecursiveThread(workingDir + "inp1.txt",filter);

                double parallel = RT1.parallelNoiseFilter(median);
                System.out.println("Parallel Time: " + parallel);

                Serial sr = new Serial(workingDir  + "inp1.txt",filter);
                double serial = sr.serialNoiseFilter(median);
                System.out.println("Sequential Time: " +  serial);

                System.out.println("Parallel is " + serial/parallel + " faster");
            }
            else if (option.equals("2")){
                //Create and set Parallel class with name of file of array and filter
                RecursiveThread RT1 = new RecursiveThread(workingDir + "inp2.txt",filter);

                double parallel = RT1.parallelNoiseFilter(median);
                System.out.println("Parallel Time: " + parallel);

                Serial sr = new Serial(workingDir  + "inp2.txt",filter);
                double serial = sr.serialNoiseFilter(median);
                System.out.println("Sequential Time: " +  serial);

                System.out.println("Parallel is " + serial/parallel + " faster");
            }
            else if (option.equals("3")){
                //Create and set Parallel class with name of file of array and filter
                RecursiveThread RT1 = new RecursiveThread(workingDir + "inp3.txt",filter);

                double parallel = RT1.parallelNoiseFilter(median);
                System.out.println("Parallel Time: " + parallel);

                Serial sr = new Serial(workingDir  + "inp3.txt",filter);
                double serial = sr.serialNoiseFilter(median);
                System.out.println("Sequential Time: " +  serial);

                System.out.println("Parallel is " + serial/parallel + " faster");
            }
            else if (option.equals("4")){
                //Create and set Parallel class with name of file of array and filter
                RecursiveThread RT1 = new RecursiveThread(workingDir + "inp4.txt",filter);

                double parallel = RT1.parallelNoiseFilter(median);
                System.out.println("Parallel Time: " + parallel);

                Serial sr = new Serial(workingDir  + "inp4.txt",filter);
                double serial = sr.serialNoiseFilter(median);
                System.out.println("Sequential Time: " +  serial);

                System.out.println("Parallel is " + serial/parallel + " faster");
            }
            //Changes filter for arrays
            else if (option.equals("5")){
                System.out.println("Choose new Filter :");
                filter = user_input.nextInt();
                user_input.nextLine();
            }
            //Changes type of filtering for noise
            else if (option.equals("6")){
                System.out.println("Choose Type 1)Median or 2)Mean) :");
                if (user_input.nextInt() == 1){
                    med = "Median";
                    median=true;
                }
                else{
                    med = "Mean";
                    median = false;
                }
                user_input.nextLine();
            }
            else{
                System.exit(0);
            }


        }

    }
}
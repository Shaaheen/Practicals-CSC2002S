package src;

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

    public static void main(String[] args) throws InterruptedException, IOException {

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
                    "\r\n6)Quit");

            option = user_input.nextLine();
            //Create Parallel and Serial class to compare
            if (option.equals("1")){
                //Create and set Parallel class with name of file of array and filter
                RecursiveThread RT1 = new RecursiveThread(workingDir + "inp1.txt",filter);
                System.out.println("Parallel : " + RT1.parallelNoiseFilter());
                Serial sr = new Serial(workingDir  + "inp1.txt",filter);
                System.out.println("Sequential : " + sr.serialNoiseFilter());
            }
            else if (option.equals("2")){
                RecursiveThread RT2 = new RecursiveThread(workingDir + "inp2.txt",filter);
                System.out.println("Parallel : " + RT2.parallelNoiseFilter());
                Serial sr = new Serial(workingDir  + "inp2.txt",filter);
                System.out.println("Sequential : " + sr.serialNoiseFilter());
            }
            else if (option.equals("3")){
                RecursiveThread RT3 = new RecursiveThread(workingDir + "inp3.txt",filter);
                System.out.println("Parallel : " + RT3.parallelNoiseFilter());
                Serial sr = new Serial(workingDir  + "inp3.txt",filter);
                System.out.println("Sequential : " + sr.serialNoiseFilter());
            }
            else if (option.equals("4")){
                RecursiveThread RT4 = new RecursiveThread(workingDir + "inp4.txt",filter);
                System.out.println("Parallel : " + RT4.parallelNoiseFilter());
                Serial sr = new Serial(workingDir  + "inp4.txt",filter);
                System.out.println("Sequential : " + sr.serialNoiseFilter());
            }
            //Changes filter for arrays
            else if (option.equals("5")){
                System.out.println("Choose new Filter :");
                filter = user_input.nextInt();
                user_input.nextLine();
            }
            else{
                System.exit(0);
            }


        }

    }
}
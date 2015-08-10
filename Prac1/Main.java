import src.FileUtil;
import src.RecursiveThread;
import src.Serial;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by Shaaheen on 7/27/2015.
 * Class to act as UI for the Parallel and Serial calculations
 */
public class Main {

    //Gets current path to text files
    public static String workingDir = System.getProperty("user.dir") + "\\src\\TxtFiles\\";
    //Sets filter for when loading in files
    public static int filter = 21;
    public static boolean median = true;
    public static String med = "Median";

    //Noise from files
    private static double[] inp1;
    private static double[] inp2;
    private static double[] inp3;
    private static double[] inp4;

    public static void main(String[] args) throws InterruptedException, IOException {

        if (workingDir.substring(0,1).equals("/")){
            //Then Linux system
            workingDir = System.getProperty("user.dir") + "/src/TxtFiles/";
        }

        //Creates Arrays from all the files so program doesn't have to
        //load file every time performing a median filter - Can just use preloaded array
        System.out.println("Loading Files....");
        inp1 = FileUtil.getNoise(workingDir + "inp1.txt");
        inp2 = FileUtil.getNoise(workingDir + "inp2.txt");
        inp3 = FileUtil.getNoise(workingDir + "inp3.txt");
        inp4 = FileUtil.getNoise(workingDir + "inp4.txt");

        //Scanner Object
        Scanner user_input = new Scanner(System.in);
        int option;

        //UI loop
        while (true){
            //Options for user
            System.out.println();
            System.out.println("Choose Input File" +
                    "\r\n1)inp1.txt" +
                    "\r\n2)inp2.txt" +
                    "\r\n3)inp3.txt" +
                    "\r\n4)inp4.tx" +
                    "\r\n5)Change Filter (Filter is " + filter + ")" +
                    "\r\n6)Change type of Filtering (Type is " + med + ")" +
                    "\r\n7)Mark Down times and Speed Up in a CSV file" +
                    "\r\n8)Test speedup at different filters (CSV file)" +
                    "\r\n9)Test speedup at different Sequential Cut-Offs (CSV file)" +
                    "\r\n10)Quit");

            option = user_input.nextInt();
            //Create Parallel and Serial class to compare
            if (option == 1 || option ==2 || option ==3 || option ==4){
                //Create and set Parallel class with name of file of array and filter
                RecursiveThread RT1 = new RecursiveThread(getNoiseFromOption(option),filter);

                double parallel = RT1.parallelNoiseFilter(median);
                System.out.println("Parallel Time: " + parallel);

                Serial sr = new Serial(getNoiseFromOption(option),filter);
                double serial = sr.serialNoiseFilter(median);
                System.out.println("Sequential Time: " +  serial);

                System.out.println("Parallel is " + serial / parallel + " faster");
            }
            //Changes filter for arrays
            else if (option == 5){
                System.out.println("Choose new Filter :");
                filter = user_input.nextInt();
                user_input.nextLine();
            }
            //Changes type of filtering for noise
            else if (option == 6){
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
            else if (option == 7){
                System.out.println("Enter file to process 1)inp1.txt 2)inp2.txt 3)inp3.txt 4)inp4.txt");
                int file = user_input.nextInt();
                System.out.println("Enter number of times to repeat filterings");
                markDownSpeedUps(user_input.nextInt(),getNoiseFromOption(file));
                user_input.nextLine();
            }
            else if (option == 8){
                System.out.println("Filtering with different filters...");
                PrintStream strm2 = new PrintStream("Filters.csv");
                strm2.print("Filters,Speed-Up Inp2.txt" + "\r\n");
                for (int i = 3; i <= 21; i+=2) {
                    strm2.print(i + ",");
                    double avgSpeedUp = 0;
                    for (int j = 0; j < 1000; j++) {
                        //Create and set Parallel class with name of file of array and filter
                        RecursiveThread RT1 = new RecursiveThread(inp2,i);

                        double parallel = RT1.parallelNoiseFilter(median);

                        Serial sr = new Serial(inp2,i);
                        double serial = sr.serialNoiseFilter(median);

                        double speedUp = serial/parallel;

                        avgSpeedUp += speedUp;
                    }
                    strm2.print((avgSpeedUp / 1000.0) + "\r\n");
                }
            }
            else if (option == 9){
                System.out.println("Tesing different sequential cut-offs...");
                PrintStream strm2 = new PrintStream("SequentialCutOffs.csv");
                strm2.print("SequentialCutOff,Speed-Up Inp2.txt" + "\r\n");
                for (int i = 100; i <= 150000; i*=2) {
                    strm2.print(i + ",");
                    double avgSpeedUp = 0;
                    for (int j = 0; j < 1000; j++) {
                        //Create and set Parallel class with name of file of array and filter
                        RecursiveThread RT1 = new RecursiveThread(inp2,19);
                        RT1.changeSequentialCutOff(i);

                        double parallel = RT1.parallelNoiseFilter(median);

                        Serial sr = new Serial(inp2,19);
                        double serial = sr.serialNoiseFilter(median);

                        double speedUp = serial/parallel;

                        avgSpeedUp += speedUp;
                    }
                    strm2.print((avgSpeedUp / 1000.0) + "\r\n");
                }
            }
            else{
                System.exit(0);
            }


        }

    }

    /*
        Method to return the array chosen based on the option chosen
     */
    private static double[] getNoiseFromOption(int optionChosen){
        if (optionChosen == 1){
            return inp1;
        }
        else if (optionChosen == 2){
            return inp2;
        }
        else if (optionChosen == 3){
            return inp3;
        }
        else {
            return inp4;
        }
    }

    private static void markDownSpeedUps(int repeat,double[] inputNoise) throws IOException {
        System.out.println("Marking down speed ups...");
        PrintStream stream = new PrintStream("SpeedUps.csv");
        stream.print("Speed Ups" + "\r\n" +
                "Parallel Time,Sequential Time,SpeedUp" + "\r\n");

        for (int i = 0 ; i < repeat ; i++){
            //Create and set Parallel class with name of file of array and filter
            RecursiveThread RT1 = new RecursiveThread(inputNoise,filter);

            double parallel = RT1.parallelNoiseFilter(median);
            stream.print(parallel + ",");

            Serial sr = new Serial(inputNoise,filter);
            double serial = sr.serialNoiseFilter(median);
            stream.print(serial + ",");

            stream.print(serial / parallel + "\r\n");
        }


    }
}
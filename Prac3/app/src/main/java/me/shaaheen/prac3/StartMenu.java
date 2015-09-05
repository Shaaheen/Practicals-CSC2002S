package me.shaaheen.prac3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Shaaheen on 9/2/2015.
 * Class that manages the start screen and its functionality
 */
public class StartMenu extends Activity{

    //Instance variables
    Button welcomeButton; //Button that launches app into view pager mode
    ImageView imageView; //For background image
    //Array of all the drawables
    static int[] images = new int[]{R.drawable.smallesteco,R.drawable.smallct,R.drawable.smalcsc,R.drawable.smallarts,
                                    R.drawable.finalslide,R.drawable.intro,R.drawable.smallfitz,R.drawable.smallgreenuct,
                                    R.drawable.smalljammie,R.drawable.smallsnape,R.drawable.smallsnapeagain,R.drawable.smalltable,
                                    R.drawable.smalltuggies};
    static String[] imageFileNames = new String[]{"Eco.jpeg","CT.jpeg","CSC.jpeg","Arts.jpeg","FinalSlide.jpeg","Intro.jpeg",
            "CloseUp.jpeg","GreenUCT.jpeg","Jammie.jpeg","Snape.jpeg","Snape2.jpeg","TableMnt.jpeg","Lower.jpeg"};

    /*
        Method to set layout and give button functionality
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Sets default create method
        super.onCreate(savedInstanceState);
        //Sets xml as the layout
        setContentView(R.layout.start_menu);

        //Get button object and image view object
        welcomeButton = (Button) findViewById(R.id.welcome_button);
        imageView = (ImageView) findViewById(R.id.mainImage);

        //Set the introduction start page image
        imageView.setImageResource(R.drawable.intro);

        try {
            saveImagesIntoMemory();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Give functionality to the welcome button to launch into the view pager
        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a new intent with the intention of switching classes/views
                Intent intent = new Intent(StartMenu.this,SlideShow.class);
                startActivity(intent); //Switch to SlideShow class
                finish(); //End current class appriopiately
            }
        });


    }

    /*
        Method to control saving images into storage
     */
    public void saveImagesIntoMemory() throws IOException {
        if (checkForMemoryCard()){
            System.out.println("SD card found again");
            saveFiles();
            System.out.println("Saved images!");
        }
    }

    /*
        Method to save all images into internal storage
     */
    public void saveFiles() throws IOException {
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() +  "/DiscoverUCT";
        System.out.println("Target Path is: " + targetPath);

        OutputStream fOut = null;
        //To check if directory "DiscoverUCT/" already exists
        File folder = new File(targetPath);

        //If doesn't
        if (!folder.exists()) {
            folder.mkdir(); //Create directory
            System.out.println("Folder not found - now created");
        }
        else{
            System.out.println("Folder found");
        }

        //Go through each image and add to storage if already not there
        for (int i = 0; i < images.length; i++) {
            //File to check if already exists or not
            File currImage = new File(targetPath,imageFileNames[i]);
            //If image exists then do nothing
            if(currImage.exists()){
                System.out.println( imageFileNames[i] + " exists! - Do nothing");
            }
            else{
                //Used to print to file - create image into file
                fOut = new FileOutputStream(currImage);
                //Sets bitmap to file
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), images[i]);

                if (!bmp.compress(Bitmap.CompressFormat.JPEG, 90, fOut)) {
                    Log.e("Log", "error while saving bitmap " + targetPath );
                }
                //Stop printing to file
                fOut.close();
                System.out.println("File did not exist - Was created");
            }
        }

    }

    /*
        Method to check if the user's device has a memory card
     */
    private boolean checkForMemoryCard() {
        // Check for SD Card or internal storage
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            System.out.println("No SD card");
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            System.out.println("SD card found");
            return true;
        }
    }



    }

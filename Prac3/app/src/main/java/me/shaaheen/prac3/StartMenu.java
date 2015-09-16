package me.shaaheen.prac3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Shaaheen on 9/2/2015.
 * Class that manages the start screen and its functionality
 */
public class StartMenu extends Activity{

    //Instance variables
    Button welcomeButton; //Button that launches app into view pager mode
    ImageView imageView; //For background image
    String targetPath;
    static ArrayList<SlideShowImage> slideShowImages;

    /*
        Method to set layout and give button functionality
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Sets default create method
        super.onCreate(savedInstanceState);
        //Sets xml as the layout
        setContentView(R.layout.start_menu);

        //Get all images and description of images into program
        targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() +  "/DiscoverUCT";
        slideShowImages = new ArrayList<SlideShowImage>();
        slideShowImages = loadImages(slideShowImages);

        final SaveImages svImg = new SaveImages();
        svImg.execute();
        System.out.println("Stuff here");

        //Get button object and image view object
        welcomeButton = (Button) findViewById(R.id.welcome_button);
        imageView = (ImageView) findViewById(R.id.mainImage);

        //Set the introduction start page image
        imageView.setImageResource(R.drawable.intro);

        //Give functionality to the welcome button to launch into the view pager
        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    welcomeButton.setText("Loading Images...");
                    System.out.println("Clicked");
                    svImg.get(200000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                //Create a new intent with the intention of switching classes/views
                Intent intent = new Intent(StartMenu.this, SlideShow.class);
                startActivity(intent); //Switch to SlideShow class
                finish(); //End current class appriopiately
            }
        });

    }

    /*
        Method to create all image objects which contain the name and description of image
        This is stored in an array for the use of the program
     */
    public ArrayList<SlideShowImage> loadImages(ArrayList<SlideShowImage> slideImages){
        //Education images
        slideImages.add(new SlideShowImage("Education.jpeg",R.drawable.smalleducation,"",true));
        slideImages.add(new SlideShowImage("Class1.jpeg",R.drawable.smalllearn,"Classic UCT Lecture Theatre",true));
        slideImages.add(new SlideShowImage("Class2.jpeg",R.drawable.smallclass,"Another Awesome Classroom!",true));
        slideImages.add(new SlideShowImage("CSC.jpeg",R.drawable.smalcsc,"Computer Science Senior Labs!",true));

        //Faculty Images
        slideImages.add(new SlideShowImage("Faculty.jpeg",R.drawable.smallfaculty,"",false));
        slideImages.add(new SlideShowImage("Humanities.jpeg",R.drawable.smallactualhuman,"Humanities Building",true));
        slideImages.add(new SlideShowImage("MedSchool.jpeg",R.drawable.smallhuman,"UCT Medical School",true));
        slideImages.add(new SlideShowImage("Leslie.jpeg",R.drawable.smallleslie,"Commerce Building!",true));
        slideImages.add(new SlideShowImage("Snape.jpeg",R.drawable.smallsnape,"The new High Tech Engineering building",true));
        slideImages.add(new SlideShowImage("Snape2.jpeg", R.drawable.smallsnapeagain, "Another view from inside the engineeing building", true));

        //UCT Images
        slideImages.add(new SlideShowImage("UCTImages.jpeg",R.drawable.smallenjoy,"",false));
        slideImages.add(new SlideShowImage("Eco.jpeg",R.drawable.smallesteco,"Passage way past the Economics building..",true));
        slideImages.add(new SlideShowImage("TableMnt.jpeg", R.drawable.smalltable, "View of table mountain from Lower Campus", false));
        slideImages.add(new SlideShowImage("Jammie.jpeg", R.drawable.smalljammie, "Jameson Hall - The centre of the university", false));
        slideImages.add(new SlideShowImage("CloseUp.jpeg",R.drawable.smallfitz,"Close-up of a UCT building",true));
        slideImages.add(new SlideShowImage("CT.jpeg",R.drawable.smallct,"The city you would be living in :)",true));
        slideImages.add(new SlideShowImage("Lower.jpeg", R.drawable.smalltuggies, "Night view of lower campus", true));
        slideImages.add(new SlideShowImage("GreenUCT.jpeg", R.drawable.smallgreenuct, "Upper campus when its blooming", true));
        slideImages.add(new SlideShowImage("Arts.jpeg",R.drawable.smallarts,"The UCT Arts building ",true));
        slideImages.add(new SlideShowImage("PinkBuilding.jpeg",R.drawable.smallbuilding,"Beauty of the Botany Building",true));
        slideImages.add(new SlideShowImage("Pathway.jpeg",R.drawable.smalloutagain,"Pathways through UCT",true));
        slideImages.add(new SlideShowImage("Smuts.jpeg",R.drawable.smalloutside,"View of Smuts",true));
        slideImages.add(new SlideShowImage("Fountain.jpeg",R.drawable.smallfountain,"Centre Fountain of UCT",true));
        slideImages.add(new SlideShowImage("LowerCampus.jpeg",R.drawable.smallreslower,"Lower campus residence",true));
        slideImages.add(new SlideShowImage("FinalSlide.jpeg", R.drawable.finalslide, "", true));
        return slideImages;

    }

    /*
        Method to get the list of images
     */
    public static ArrayList<SlideShowImage> getSlideShowImages(){
        return slideShowImages;
    }



    private class SaveImages extends AsyncTask<Void,Void,Void> {

        /*
            Overides main Inherited method that is run as soon as AsynchTask is executed
            Method will check for the device storage/sd card path and save image into default directory
         */
        @Override
        protected Void doInBackground(Void... params) {
            System.out.println("Run in the background");
            //Checks if device storage/sd card can be found
            if (checkForMemoryCard()){
                System.out.println("SD card found again");
                try {
                    //Saves Images into default directory
                    saveFiles(slideShowImages);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Saved images!");
            }
            return null;
        }


        /*
            Overides inherited method
            Launched when told to by the doInBackground method
            This method displays a Toast message and changes text of button
            to stat that images are still being stored
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            System.out.println("on progress update");
            welcomeButton.setText("Saving Images..."); //change text of button
            //Display toast message
            Toast.makeText(getApplicationContext(), "Saving Images to SD card in Parallel",
                    Toast.LENGTH_LONG).show();
        }

        /*
            Overides inherited onPostExecute method which is
            automatically launched after asynchtask is done
         */
        @Override
        protected void onPostExecute(Void x){
            System.out.println("Done running");
            welcomeButton.setText("Start Exploring!"); //Change button text back to start exploring
        }

        /*
    Method to save all images into internal storage
 */
        public void saveFiles(ArrayList<SlideShowImage> slideShowImages) throws IOException {
            String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() +  "/DiscoverUCT";
            System.out.println("Target Path is: " + targetPath);

            OutputStream fOut = null;
            //To check if directory "DiscoverUCT/" already exists
            File folder = new File(targetPath);

            //If doesn't
            if (!folder.exists()) {
                folder.mkdir(); //Create directory
                publishProgress(); //Display to user that images are being saved
                publishProgress(null);
                System.out.println("Folder not found - now created");
            }
            else{
                System.out.println("Folder found");
            }

            //Go through each image and add to storage if already not there
            for (int i = 0; i < slideShowImages.size(); i++) {
                //File to check if already exists or not
                File currImage = new File(targetPath,(slideShowImages.get(i)).getFileName());
                //If image exists then do nothing
                if(currImage.exists()){
                    System.out.println( slideShowImages.get(i) + " exists! - Do nothing");
                }
                else{
                    //Used to print to file - create image into file
                    fOut = new FileOutputStream(currImage);
                    //Sets bitmap to file
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), slideShowImages.get(i).getDrawablePath());

                    //Saves bitmap image as JPEG into output path specified in the outputStream
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
                return false;
            }
            else {
                System.out.println("SD card found");
                return true;
            }
        }
    }
}

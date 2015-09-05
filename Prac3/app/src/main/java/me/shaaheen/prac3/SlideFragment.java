package me.shaaheen.prac3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Shaaheen on 9/2/2015.
 * Class that represents the actual Slide object containing the actual image with text attached
 */
public class SlideFragment extends android.support.v4.app.Fragment{

    static String appDirectory ;

    /*
        Method to overide parent class  - launched when new slide is chosen
     */
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        //Get view and set layout from xml
        View v = inflater.inflate(R.layout.slide_fragment,container,false);
        //Get the relative layout and set to black  - background color = black
        RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.mainLayout);
        relativeLayout.setBackgroundColor(Color.BLACK);

        //Get text displaying object
        TextView textView = (TextView) v.findViewById(R.id.imageDescription);

        //Grab the main image view of the slide
        ImageView imageView = (ImageView) v.findViewById(R.id.currentSlide);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY); //So slide fills whole screen
        //Prevents too many picture stored on memory
        imageView.destroyDrawingCache();
        imageView.refreshDrawableState();

        appDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() +  "/DiscoverUCT";

        //Set the image and text depending on position given
        if (getArguments().getString("msg").equals("1")){
            //Create bitmap from the image saved in the internal storage
            Bitmap bmp = BitmapFactory.decodeFile(appDirectory + "/TableMnt.jpeg");
            imageView.setImageBitmap(bmp);
            //imageView.setImageResource(R.drawable.smalltable);
            textView.setText("View of table mountain from Lower Campus");
        }
        else if (getArguments().getString("msg").equals("2")){
            //Create bitmap from the image saved in the internal storage
            Bitmap bmp = BitmapFactory.decodeFile(appDirectory + "/Jammie.jpeg");
            imageView.setImageBitmap(bmp);
            textView.setText("Jameson Hall - The centre of the university");
        }
        else if (getArguments().getString("msg").equals("3")){
            //Create bitmap from the image saved in the internal storage
            Bitmap bmp = BitmapFactory.decodeFile(appDirectory + "/CloseUp.jpeg");
            imageView.setImageBitmap(bmp);
            textView.setText("Close-up of a UCT building");
            textView.setTextColor(Color.WHITE);
        }
        else if (getArguments().getString("msg").equals("4")){
            //Create bitmap from the image saved in the internal storage
            Bitmap bmp = BitmapFactory.decodeFile(appDirectory + "/CSC.jpeg");
            imageView.setImageBitmap(bmp);
            textView.setText("Computer Science Senior Labs!");
        }
        else if (getArguments().getString("msg").equals("5")){
            //Create bitmap from the image saved in the internal storage
            Bitmap bmp = BitmapFactory.decodeFile(appDirectory + "/CT.jpeg");
            imageView.setImageBitmap(bmp);
            textView.setText("The city you would be living in :)");
            textView.setTextColor(Color.WHITE);

        }
        else if (getArguments().getString("msg").equals("6")){
            //Create bitmap from the image saved in the internal storage
            Bitmap bmp = BitmapFactory.decodeFile(appDirectory + "/Lower.jpeg");
            imageView.setImageBitmap(bmp);
            textView.setText("Night view of lower campus");
            textView.setTextColor(Color.WHITE);
        }
        else if (getArguments().getString("msg").equals("7")){
            //Create bitmap from the image saved in the internal storage
            Bitmap bmp = BitmapFactory.decodeFile(appDirectory + "/GreenUCT.jpeg");
            imageView.setImageBitmap(bmp);
            textView.setText("Upper campus when its blooming");
            textView.setTextColor(Color.WHITE);
        }
        else if (getArguments().getString("msg").equals("8")){
            //Create bitmap from the image saved in the internal storage
            Bitmap bmp = BitmapFactory.decodeFile(appDirectory + "/Arts.jpeg");
            imageView.setImageBitmap(bmp);
            textView.setText("The UCT Arts building ");
            textView.setTextColor(Color.WHITE);
        }
        else if (getArguments().getString("msg").equals("9")){
            //Create bitmap from the image saved in the internal storage
            Bitmap bmp = BitmapFactory.decodeFile(appDirectory + "/Snape.jpeg");
            imageView.setImageBitmap(bmp);
            textView.setText("The new High Tech Engineering building");
            textView.setTextColor(Color.WHITE);
        }
        else if (getArguments().getString("msg").equals("10")){
            //Create bitmap from the image saved in the internal storage
            Bitmap bmp = BitmapFactory.decodeFile(appDirectory + "/Snape2.jpeg");
            imageView.setImageBitmap(bmp);
            textView.setText("Another view from inside the engineeing building");
            textView.setTextColor(Color.WHITE);
        }
        else if (getArguments().getString("msg").equals("11")){
            //Create bitmap from the image saved in the internal storage
            Bitmap bmp = BitmapFactory.decodeFile(appDirectory + "/FinalSlide.jpeg");
            imageView.setImageBitmap(bmp);
            //textView.setText("Applications open!");
        }
        else{
            Bitmap bmp = BitmapFactory.decodeFile(appDirectory + "/Eco.jpeg");
            imageView.setImageBitmap(bmp);
            textView.setText("Passage way past the Economics building..");
            textView.setTextColor(Color.WHITE);
        }
        return v;
    }

    /*
        Method to create a new slide and set image and text of the slide
     */
    public static SlideFragment newInstance(int position) {

        SlideFragment f = new SlideFragment();

        //For bundle that will be passed to slide
        Bundle b = new Bundle();
        b.putString("msg", position + ""); //Store position variable in bundle so to access to know which image to use

        f.setArguments(b); //Sets image and text

        //Return slide with all information attached
        return f;
    }
}

package me.shaaheen.prac3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
        //Prevents too many picture stored on RAM memory
        imageView.destroyDrawingCache();
        imageView.refreshDrawableState();

        //Gets directory of where images should have been stored
        appDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() +  "/DiscoverUCT";

        //Get list of images
        ArrayList<SlideShowImage> slideShowImages = StartMenu.getSlideShowImages();

        //Gets index of chosen picture and gets that image from the list of images
        int currPicIndx = Integer.parseInt(getArguments().getString("msg"));
        SlideShowImage currSlideImage = slideShowImages.get(currPicIndx);

        Bitmap bmp = null;
        System.out.println("Here - Trying to load from internal memory...");

        //Create bitmap from the image saved in the internal storage
        bmp = BitmapFactory.decodeFile(appDirectory + "/" + currSlideImage.getFileName());

        //If fails to load image from internal storage then load from drawables
        if (bmp == null){
            System.out.println("Failed --- Loading image from drawable ");
            //Gets bitmap from drawables
            bmp = BitmapFactory.decodeResource(getResources(),currSlideImage.getDrawablePath());
        }
        //Change the imageView to display new picture
        imageView.setImageBitmap(bmp);
        //Sets the text on screen to the description of the image
        textView.setText(currSlideImage.getDescription());
        //Change font size of text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        //Changes color of text depending on what image requires
        if (currSlideImage.white()){
            textView.setTextColor(Color.WHITE);
        }
        return v;
    }

    /*
        Method to create a new slide and set image and text of the slide
     */
    public static SlideFragment newInstance(int position) {

        //New Slide
        SlideFragment f = new SlideFragment();

        //For bundle that will be passed to slide
        Bundle b = new Bundle();
        b.putString("msg", position + ""); //Store position variable in bundle so to access to know which image to use

        f.setArguments(b); //Sets image and text

        //Return slide with all information attached
        return f;
    }
}

package me.shaaheen.prac3;

import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Shaaheen on 9/2/2015.
 * Class to act as the viewPager that will know which slide (fragment) to launch
 * and will handle the slideshow functionality - waiting 3 seconds then next slide etc
 * also houses all buttons to control slideshow - back,start,stop, next , previous
 */
public class SlideShow extends FragmentActivity{

    //Local variables
    ViewPager viewPager; //Main view pager that needs to also be accessed by inner class hence making it a instance variable
    int slideshowPosition; //So class knows which position in slideshow it is in so can move to next position after 3 second delay
    boolean slideshowOn; //So class knows that slideshow is currently active
    Button slideshowButton; //This is an instance variable so the text can be changed to stop when start is pressed

    /*
        Methods to overide parent class add extra things for the class to do when created
        - Sets all button functionality
        - Sets up view pager with an adapter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState); //Call to parent class
        setContentView(R.layout.slideshow); //Create and set layout of current screen

        //Gets all these objects from the XML to add functionality
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Button nextButton = (Button) findViewById(R.id.nextButton);
        Button prevButton = (Button) findViewById(R.id.previousButton);
        Button backButton = (Button) findViewById(R.id.backButton);
        slideshowButton = (Button) findViewById(R.id.start);

        //Give the view pager and adapter so it knows when to change to next slide - button, slideshow or swipe
        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(0); //To save on memory

        //Sets a task to be done when button clicked
        //Set an event listener for when the button is clicked
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Make view pager change to next slide
                viewPager.setCurrentItem(getItem(+1), true);
            }
        });

        //Similar to above
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Make view pager change to the previous slide
                viewPager.setCurrentItem(getItem(-1), true);
            }
        });

        //Will change back to home screen
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set an intent to change from this class to the StartMenu class
                Intent intent = new Intent(SlideShow.this, StartMenu.class);
                //Do the intent
                startActivity(intent);
                //This will close down current class
                finish();
            }
        });

        //Sets instance variable to default false as slide show is not playing on default
        slideshowOn = false;

        //Set click event listener
        slideshowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //If slide show is not on then run slide show
                if (!slideshowOn) {
                    //This will launch the runnable which launches the slideshow - changes slide, waits, changes slide
                    runnable.run();
                    slideshowOn = true; //slideshow is now active
                    slideshowButton.setText("Stop Slideshow"); //change text on button so user knows how to stop slideshow
                }
                //If user stopped slideshow then end the running slideshow and take away its listeners
                // - Stop slideshow
                else {
                    //If there was ever a slideshow created
                    if (handler != null) {
                        //Remove anything that was waiting to go onto next slide
                        handler.removeCallbacks(runnable);
                        handler.removeCallbacksAndMessages(null);
                        slideshowOn = false; //slideshow inactive
                        slideshowButton.setText("Start Slideshow"); //change text back
                    }
                }
            }
        });
    }

    /*
        Instance variables for running the slideshow
     */
    //handler is used to delay the runnable for the appropriate amount of time then moves to next slide
    private Handler handler = new Handler();

    //Runnable variable is used to keep changing position of slideshow
    private Runnable runnable = new Runnable() {
        public void run() {
            //Get the current position of the slideshow
            slideshowPosition = getItem(1);
            //Increment the position by 1 - i.e Move to next position
            if( slideshowPosition >= 12){
                slideshowPosition = 0;
            }
            //Make view pager go onto next slide
            viewPager.setCurrentItem(slideshowPosition, true);
            //Delay runnable for 3 seconds to imitate a slideshow
            handler.postDelayed(runnable, 3000);
        }


    };

    /*
        used to get the next or previous position of the slideshow
     */
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    /*
        Method to make sure when app is paused, the slideshow does not continue
     */
    @Override
    public void onPause() {
        super.onPause();
        if (handler!= null) {
            handler.removeCallbacks(runnable);
        }
    }

    /*
        Method to make sure slideshow continues when app resumed
     */
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        if (slideshowOn){
            handler.postDelayed(runnable, 3000);
        }
    }



    /*
        Custom adapter for view pager to change to next slide (fragment)
     */
    private class CustomPagerAdapter extends FragmentStatePagerAdapter{

        //Constructor - calls super
        public CustomPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        /*
            Method to get the next slide
         */
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return (SlideFragment.newInstance(position));
        }

        /*
            Method to get how many slides there are in total
         */
        @Override
        public int getCount() {
            return 12;
        }
    }
}



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
 */
public class SlideShow extends FragmentActivity{


    ViewPager viewPager;
    int slideshowPosition;
    boolean slideshowOn;
    Button slideshowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideshow);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Button nextButton = (Button) findViewById(R.id.nextButton);
        Button prevButton = (Button) findViewById(R.id.previousButton);
        Button backButton = (Button) findViewById(R.id.backButton);
        slideshowButton = (Button) findViewById(R.id.start);

        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(0);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(getItem(+1), false);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(getItem(-1), false);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SlideShow.this, StartMenu.class);
                startActivity(intent);
                finish();
            }
        });

        slideshowOn = false;

        slideshowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Clicked again");
                if (!slideshowOn){
                    runnable.run();
                    slideshowOn = true;
                    slideshowButton.setText("Stop Slideshow");
                }
                else{
                    if (handler!= null) {
                        handler.removeCallbacks(runnable);
                        handler.removeCallbacksAndMessages(null);
                        slideshowOn = false;
                        slideshowButton.setText("Start Slideshow");
                    }
                }
            }
        });


        System.out.println("On created");
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            slideshowPosition = viewPager.getCurrentItem();
            if( slideshowPosition >= 11){
                slideshowPosition = 0;
            }else{
                slideshowPosition ++;
            }
            viewPager.setCurrentItem(slideshowPosition, true);
            handler.postDelayed(runnable, 5000);
        }


    };

    @Override
    public void onPause() {
        super.onPause();
        if (handler!= null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        if (slideshowOn){
            handler.postDelayed(runnable, 3000);
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    private class CustomPagerAdapter extends FragmentStatePagerAdapter{

        public CustomPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public void destroyItem(View collection, int position, Object o) {
            Log.d("DESTROY", "destroying view at position " + position);
            View view = (View) o;
            ((ViewPager) collection).removeView(view);
            view = null;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            slideshowPosition = position;
            System.out.println("SHould get here sometime - getItem( " + position + " )" );
            return (SlideFragment.newInstance(position));
        }


        @Override
        public int getCount() {
            return 12;
        }
    }
}



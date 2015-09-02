package me.shaaheen.prac3;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

    private ViewFlipper viewFlipper;
    private float lastX;
    private View  lastView;
    private View nextView;

    private int keepCount;

    private int[] images = {R.drawable.economics,R.drawable.fitzpatrick,R.drawable.snape,
            R.drawable.editedct,R.drawable.betterfromfield,R.drawable.editeduct10};



    public MainActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper1);

        ImageView testImg = new ImageView(this);
        testImg.setImageResource(images[keepCount]);
        testImg.willNotCacheDrawing();
        viewFlipper.addView(testImg);
        viewFlipper.willNotCacheDrawing();

    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        System.out.println("Touched");
        switch (touchEvent.getAction()){

            case MotionEvent.ACTION_DOWN:
                lastX = touchEvent.getX();
                System.out.println("Init touch");
                break;


            case MotionEvent.ACTION_UP:
                System.out.println("Swipe");
                float currentX = touchEvent.getX();

                //Left to Right Swipe
                if (lastX < currentX){

                    //If at edge of screen then can't swipe right
                    if (viewFlipper.getDisplayedChild() == 0){
                        //break;
                    }

                    keepCount--;

                    System.out.println("Here atleast keepCount " + keepCount + "images length " + images.length);

                    if (keepCount < 0){
                        keepCount = images.length - 1;
                    }

                    System.out.println("here keepCount" + keepCount + "  views" + viewFlipper.toString());
                    viewFlipper.removeAllViews();
                    viewFlipper.removeAllViewsInLayout();
                    viewFlipper.refreshDrawableState();

                    ImageView newImg = new ImageView(this);
                    newImg.setImageResource(images[keepCount]);
                    newImg.setScaleType(ImageView.ScaleType.FIT_XY);
                    newImg.willNotCacheDrawing();

                    viewFlipper.addView(newImg);
                    //viewFlipper.setInAnimation(this,R.anim.slide_in_from_left);
                    //viewFlipper.setInAnimation(this,R.anim.slide_out_from_right);
                    viewFlipper.showNext();
                    onTrimMemory(60);



                }
                if (lastX > currentX){
                    if (viewFlipper.getDisplayedChild()  == 1){
                        //break;
                    }

                    keepCount++;
                    System.out.println("Here at least, keepCount " + keepCount);

                    if (keepCount >= images.length ){
                        keepCount = 0;
                    }

                    viewFlipper.removeAllViews();
                    viewFlipper.removeAllViewsInLayout();
                    viewFlipper.refreshDrawableState();
                    onTrimMemory(5);
                    ImageView newImg = new ImageView(this);
                    newImg.setImageResource(images[keepCount]);
                    newImg.setScaleType(ImageView.ScaleType.FIT_XY);
                    newImg.willNotCacheDrawing();
                    viewFlipper.addView(newImg);
                    //viewFlipper.setInAnimation(this,R.anim.slide_in_from_left);
                    //viewFlipper.setInAnimation(this,R.anim.slide_out_from_right);
                    viewFlipper.showNext();
                    onTrimMemory(60);

                }
                break;


        }
        return false;
    }
}

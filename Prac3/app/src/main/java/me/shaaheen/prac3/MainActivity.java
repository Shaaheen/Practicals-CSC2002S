package me.shaaheen.prac3;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

    private ViewFlipper viewFlipper;
    private float lastX;
    private View  lastView;
    private View nextView;

    private int keepCount;

    private int[] images = {R.drawable.economics,R.drawable.fitzpatrick,R.drawable.snape};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper1);

        ImageView testImg = new ImageView(this);
        testImg.setImageResource(images[keepCount]);
        viewFlipper.addView(testImg);

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

                    if (keepCount > 0){
                        System.out.println("here keepCount" + keepCount + "  views" + viewFlipper.toString());
                        viewFlipper.removeAllViews();
                        ImageView newImg = new ImageView(this);
                        newImg.setImageResource(images[keepCount]);
                        viewFlipper.addView(newImg);
                        //viewFlipper.setInAnimation(this,R.anim.slide_in_from_left);
                        //viewFlipper.setInAnimation(this,R.anim.slide_out_from_right);
                        viewFlipper.showNext();
                    }
                    else{
                        keepCount = images.length - 1;
                        viewFlipper.removeAllViews();
                        ImageView newImg = new ImageView(this);
                        newImg.setImageResource(images[keepCount]);
                        viewFlipper.addView(newImg);
                        //viewFlipper.setInAnimation(this,R.anim.slide_in_from_left);
                        //viewFlipper.setInAnimation(this,R.anim.slide_out_from_right);
                        viewFlipper.showNext();
                    }


                }
                if (lastX > currentX){
                    if (viewFlipper.getDisplayedChild()  == 1){
                        //break;
                    }

                    keepCount++;
                    System.out.println("Here atlease keepCount " + keepCount);

                    if (keepCount < images.length ){
                        viewFlipper.removeAllViews();
                        ImageView newImg = new ImageView(this);
                        newImg.setImageResource(images[keepCount]);
                        viewFlipper.addView(newImg);
                        //viewFlipper.setInAnimation(this,R.anim.slide_in_from_left);
                        //viewFlipper.setInAnimation(this,R.anim.slide_out_from_right);
                        viewFlipper.showNext();
                    }
                    else {
                        keepCount = 0;
                        viewFlipper.removeAllViews();
                        ImageView newImg = new ImageView(this);
                        newImg.setImageResource(images[keepCount]);
                        viewFlipper.addView(newImg);
                        //viewFlipper.setInAnimation(this,R.anim.slide_in_from_left);
                        //viewFlipper.setInAnimation(this,R.anim.slide_out_from_right);
                        viewFlipper.showNext();
                    }
                }
                break;


        }
        return false;
    }
}

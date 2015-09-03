package me.shaaheen.prac3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Shaaheen on 9/2/2015.
 */
public class SlideFragment extends android.support.v4.app.Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.slide_fragment,container,false);
        RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.mainLayout);

        ImageView imageView = (ImageView) v.findViewById(R.id.currentSlide);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.destroyDrawingCache();
        imageView.refreshDrawableState();

        relativeLayout.setBackgroundColor(Color.BLACK);

        System.out.println("Message is " + getArguments().getString("msg"));

        if (getArguments().getString("msg").equals("1")){
            imageView.setImageResource(R.drawable.smalltable);
        }
        else if (getArguments().getString("msg").equals("2")){
            imageView.setImageResource(R.drawable.smalljammie);
        }
        else if (getArguments().getString("msg").equals("3")){
            imageView.setImageResource(R.drawable.smallfitz);
        }
        else if (getArguments().getString("msg").equals("4")){
            imageView.setImageResource(R.drawable.smalcsc);
        }
        else if (getArguments().getString("msg").equals("5")){
            imageView.setImageResource(R.drawable.smallct);
        }
        else if (getArguments().getString("msg").equals("6")){
            imageView.setImageResource(R.drawable.smalltuggies);
        }
        else if (getArguments().getString("msg").equals("7")){
            imageView.setImageResource(R.drawable.smallgreenuct);
        }
        else if (getArguments().getString("msg").equals("8")){
            imageView.setImageResource(R.drawable.smallarts);
        }
        else if (getArguments().getString("msg").equals("9")){
            imageView.setImageResource(R.drawable.smallsnape);
        }
        else if (getArguments().getString("msg").equals("10")){
            imageView.setImageResource(R.drawable.smallsnapeagain);
        }
        else if (getArguments().getString("msg").equals("11")){
            imageView.setImageResource(R.drawable.finalslide);
        }
        else{
            imageView.setImageResource(R.drawable.smallesteco);
        }

        /*
        if (getArguments().getString("msg").equals("1")){
            imageView.setImageResource(R.drawable.snape);
        }
        else if (getArguments().getString("msg").equals("2")){
            imageView.setImageResource(R.drawable.editeductsmall);
        }
        else if (getArguments().getString("msg").equals("3")){
            imageView.setImageResource(R.drawable.snapeagain);
        }
        else if (getArguments().getString("msg").equals("4")){
            imageView.setImageResource(R.drawable.fitzpatrick);
        }
        else if (getArguments().getString("msg").equals("5")){
            imageView.setImageResource(R.drawable.upper);
        }
        else if (getArguments().getString("msg").equals("6")){
            imageView.setImageResource(R.drawable.tuggies);
        }
        else{
            imageView.setImageResource(R.drawable.small);
        }
        */

        return v;
    }

    public static SlideFragment newInstance(int position) {

        SlideFragment f = new SlideFragment();
        System.out.println("new Instance yay!");

        Bundle b = new Bundle();
        b.putString("msg", position + "");

        f.setArguments(b);

        System.out.println("Done with Instance");

        return f;
    }
}

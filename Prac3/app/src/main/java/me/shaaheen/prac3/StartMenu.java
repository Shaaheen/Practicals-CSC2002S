package me.shaaheen.prac3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Shaaheen on 9/2/2015.
 */
public class StartMenu extends Activity{

    Button welcomeButton;
    ImageView imageView;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);

        welcomeButton = (Button) findViewById(R.id.welcome_button);
        imageView = (ImageView) findViewById(R.id.mainImage);

        imageView.setImageResource(R.drawable.intro);
        counter = 0;

        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartMenu.this,SlideShow.class);
                startActivity(intent);
                finish();
            }
        });


    }



}

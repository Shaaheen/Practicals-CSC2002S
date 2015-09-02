package me.shaaheen.prac3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

/**
 * Created by Shaaheen on 9/2/2015.
 */
public class StartMenu extends Activity{

    Button welcomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);

        welcomeButton = (Button) findViewById(R.id.welcome_button);
        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartMenu.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



}

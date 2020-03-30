package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Delay the launch of the app to see the logo
        new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    //Start the LoginMenuActivity
                    Intent homeIntent = new Intent(MainActivity.this, LoginMenuActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
        },SPLASH_TIME_OUT);
    }
}

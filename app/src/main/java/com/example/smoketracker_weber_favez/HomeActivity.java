package com.example.smoketracker_weber_favez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNextButton = (Button) findViewById(R.id.button_next_homeActivity);
        mNextButton.setEnabled(false);
        //mNextButton.setOnClickListener();

    }

}

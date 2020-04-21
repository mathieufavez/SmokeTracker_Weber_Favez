package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smoketracker_weber_favez.R;

public class ModifyTablesActivity extends AppCompatActivity {

    private Button userButton;
    private Button dayButton;
    private Button hourButton;

   //Here the user can choose with table he wants to see from the database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_tables);

        userButton = (Button) findViewById(R.id.button_user);


        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent userIntent = new Intent(ModifyTablesActivity.this, ModifyUserActivity.class);
                startActivity(userIntent);
                // }

            }
        });

        dayButton = (Button) findViewById(R.id.button_day);


        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent dayIntent = new Intent(ModifyTablesActivity.this, ModifyDayActivity.class);
                startActivity(dayIntent);
                // }

            }
        });

        hourButton = (Button) findViewById(R.id.button_hour);
        hourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent dayIntent = new Intent(ModifyTablesActivity.this, ModifyHourActivity.class);
                startActivity(dayIntent);
                // }

            }
        });
    }
}


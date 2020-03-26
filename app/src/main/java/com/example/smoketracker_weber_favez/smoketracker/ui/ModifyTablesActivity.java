package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;

public class ModifyTablesActivity extends AppCompatActivity {

    private Button userButton;
    private Button dayButton;

    AppDatabase db;
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
    }
}


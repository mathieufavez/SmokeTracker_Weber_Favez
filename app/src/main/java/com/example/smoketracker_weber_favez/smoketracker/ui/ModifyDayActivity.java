package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;

public class ModifyDayActivity extends AppCompatActivity {

    private Button showDayButton;
    private Button addDayButton;
    private Button deleteDayButton;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        showDayButton = (Button) findViewById(R.id.button_day_show);


        showDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent showDayIntent = new Intent(ModifyDayActivity.this, ShowDayActivity.class);
                startActivity(showDayIntent);
                // }

            }
        });

        addDayButton = (Button) findViewById(R.id.button_day_add);


        addDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent addDayIntent = new Intent(ModifyDayActivity.this, AddDayActivity.class);
                startActivity(addDayIntent);
                // }

            }
        });
        deleteDayButton = (Button) findViewById(R.id.button_day_delete);


        deleteDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent deleteDayIntent = new Intent(ModifyDayActivity.this, DeleteDayActivity.class);
                startActivity(deleteDayIntent);
                // }

            }
        });
    }
}


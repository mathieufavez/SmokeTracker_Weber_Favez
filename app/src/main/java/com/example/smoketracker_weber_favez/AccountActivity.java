package com.example.smoketracker_weber_favez;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

    private Button modifyButton;
    private Button startOverButton;
    private Button modifyTablesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        modifyButton = (Button) findViewById(R.id.button_modify);


        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent homeActivityIntent = new Intent(AccountActivity.this, HomeActivity.class);
                startActivity(homeActivityIntent);
                // }

            }
        });
        modifyTablesButton = (Button) findViewById(R.id.button_modify_tables);


        modifyTablesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent modifyTablesIntent = new Intent(AccountActivity.this, ModifyTablesActivity.class);
                startActivity(modifyTablesIntent);
                // }

            }
        });
    }
}

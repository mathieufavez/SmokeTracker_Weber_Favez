package com.example.smoketracker_weber_favez;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyUserActivity extends AppCompatActivity {

    private Button showUserButton;
    private Button addUserButton;
    private Button deleteUserButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        showUserButton = (Button) findViewById(R.id.button_user_show);


        showUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent showUserIntent = new Intent(ModifyUserActivity.this, ShowUserActivity.class);
                startActivity(showUserIntent);
                // }

            }
        });

        addUserButton = (Button) findViewById(R.id.button_user_add);


        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent addUserIntent = new Intent(ModifyUserActivity.this, AddUserActivity.class);
                startActivity(addUserIntent);
                // }

            }
        });
        deleteUserButton = (Button) findViewById(R.id.button_user_delete);


        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent deleteUserIntent = new Intent(ModifyUserActivity.this, DeleteUserActivity.class);
                startActivity(deleteUserIntent);
                // }

            }
        });
    }
}


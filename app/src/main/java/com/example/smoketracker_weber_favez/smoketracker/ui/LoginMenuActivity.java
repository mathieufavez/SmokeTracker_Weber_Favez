package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smoketracker_weber_favez.R;

public class LoginMenuActivity extends AppCompatActivity {

    private Button mConnectButton ;
    private Button mCreateAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);

        mConnectButton = (Button) findViewById(R.id.button_login);
        mCreateAccountButton = (Button) findViewById(R.id.button_create_account);

        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMenuActivity.this, Login_activity.class);
                startActivity(intent);
            }
        });


        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trackingActivityIntent = new Intent(LoginMenuActivity.this, HomeActivity.class);
                startActivity(trackingActivityIntent);
            }
        });
    }

}

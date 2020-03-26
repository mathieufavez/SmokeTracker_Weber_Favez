package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.CreateUser;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.User;

public class Login_activity extends AppCompatActivity {

    private Button buttonConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        buttonConnect = (Button) findViewById(R.id.button_connect);
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_activity.this, TrackingActivity.class);
                startActivity(intent);
            }
        });
    }
}

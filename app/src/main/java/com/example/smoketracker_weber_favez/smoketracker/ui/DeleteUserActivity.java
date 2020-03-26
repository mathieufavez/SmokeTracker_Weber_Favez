package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;

public class DeleteUserActivity extends AppCompatActivity {

    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
    }
}


package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TrackingActivity extends AppCompatActivity {
    AppDatabase db ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        //Add the listener for the bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.activity_tracking_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        //Launch directly the Tracking Fragment when we arrive on Tracking Activity
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_tracking_frame_layout, new TrackingFragment()).commit();
    }

    //To set the clicks on the bottom navigation bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId()){
                        case R.id.nav_tracking:
                            selectedFragment = new TrackingFragment();
                            break;
                        case R.id.nav_history:
                            selectedFragment = new HistoryFragment();
                            break;
                        case R.id.nav_stats:
                            selectedFragment = new StatsFragment();
                            break;
                        case R.id.nav_account:
                            selectedFragment = new AccountFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_tracking_frame_layout,
                            selectedFragment).commit();

                    return true;
                }
            };
}

package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.adapter.DayRecyclerAdapter;
import com.example.smoketracker_weber_favez.smoketracker.adapter.HourRecyclerAdapter;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.HourEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.RecyclerViewItemClickListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayListViewModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Hour.HourListViewModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Hour.ListHourViewModel;

import java.util.ArrayList;
import java.util.List;

public class ModifyHourActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    private List<HourEntity> hours;
    private HourRecyclerAdapter hourRecyclerAdapter;
    private HourListViewModel viewModel;


    //Here the users see a list of all the Hours in the Database
    //He can click on one too have the details, and then delete or update an Hour
    //He can also click on the + to add a new Hour directly from here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        setTitle("Hour Details");

        //Is ok too for Hours
        RecyclerView recyclerView = findViewById(R.id.usersRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        hours = new ArrayList<>();
        hourRecyclerAdapter = new HourRecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + hours.get(position).toString());

                Intent intent = new Intent(ModifyHourActivity.this, ShowHourActivity.class);

                intent.putExtra("hourId", hours.get(position).getId());
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );

                startActivity(intent);
            }

        });

        HourListViewModel.Factory factory = new HourListViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(HourListViewModel.class);
        viewModel.getAllHours().observe(this, hourEntities -> {
            if (hourEntities != null) {
                hours = hourEntities;
                hourRecyclerAdapter.setData(hours);
            }
        });

        recyclerView.setAdapter(hourRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0,0, Menu.NONE, "Create Hour")
                .setIcon(R.drawable.ic_add_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (item.getItemId() == 0) {
            Intent intent = new Intent(ModifyHourActivity.this, CreateHourActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

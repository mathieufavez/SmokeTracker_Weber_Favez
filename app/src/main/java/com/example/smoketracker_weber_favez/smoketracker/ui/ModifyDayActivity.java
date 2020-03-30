package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.adapter.DayRecyclerAdapter;
import com.example.smoketracker_weber_favez.smoketracker.adapter.RecyclerAdapter;
import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.RecyclerViewItemClickListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayListViewModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.User.UserListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ModifyDayActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    private List<DayEntity> days;
    private DayRecyclerAdapter dayRecyclerAdapter;
    private DayListViewModel viewModel;


    //Here the users see a list of all the Days in the Database
    //He can click on one too have the details, and then delete or update a day
    //He can also click on the + to add a new day directly from here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        setTitle("Days Details");

        //Is ok too for Days
        RecyclerView recyclerView = findViewById(R.id.usersRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        days = new ArrayList<>();
        dayRecyclerAdapter = new DayRecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + days.get(position).toString());

                Intent intent = new Intent(ModifyDayActivity.this, ShowDayActivity.class);

                intent.putExtra("dayId", days.get(position).getId());
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );

                startActivity(intent);
            }

        });

        DayListViewModel.Factory factory = new DayListViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(DayListViewModel.class);
        viewModel.getAllDays().observe(this, dayEntities -> {
            if (dayEntities != null) {
                days = dayEntities;
                dayRecyclerAdapter.setData(days);
            }
        });

        recyclerView.setAdapter(dayRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0,0, Menu.NONE, "Create Day")
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
            Intent intent = new Intent(ModifyDayActivity.this, CreateDayActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

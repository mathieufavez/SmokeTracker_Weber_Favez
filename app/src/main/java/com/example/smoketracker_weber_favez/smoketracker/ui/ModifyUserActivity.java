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
import com.example.smoketracker_weber_favez.smoketracker.adapter.RecyclerAdapter;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.RecyclerViewItemClickListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.User.UserListViewModel;

import java.util.ArrayList;
import java.util.List;


public class ModifyUserActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    private List<UserEntity> users;
    private RecyclerAdapter recyclerAdapter;
    private UserListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        setTitle("User Details");

        RecyclerView recyclerView = findViewById(R.id.usersRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        users = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + users.get(position).toString());

                Intent intent = new Intent(ModifyUserActivity.this, ShowUserActivity.class);
                intent.putExtra("userEmail", users.get(position).getUser_email());
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );

                startActivity(intent);
            }

        });

        UserListViewModel.Factory factory = new UserListViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(UserListViewModel.class);
        viewModel.getUsers().observe(this, userEntities -> {
            if (userEntities != null) {
                users = userEntities;
                recyclerAdapter.setData(users);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0,0, Menu.NONE, "Create User")
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
            Intent intent = new Intent(ModifyUserActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

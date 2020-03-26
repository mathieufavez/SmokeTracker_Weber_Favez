package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.adapter.RecyclerAdapter;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.User;
import com.example.smoketracker_weber_favez.smoketracker.util.RecyclerViewItemClickListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.UserListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShowUserActivity extends AppCompatActivity {
    private static final String TAG = "ShowUserActivity";

    private List<User> users;
    private UserListViewModel viewModel;
    private RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

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

                Intent intent = new Intent(ShowUserActivity.this, HomeActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("clientEmail", users.get(position).getUser_email());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + users.get(position).toString());
            }
        });

        UserListViewModel.Factory factory = new UserListViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(UserListViewModel.class);
        viewModel.getAllUsers().observe(this, userEntity -> {
            if (userEntity != null) {
                users = userEntity;
                recyclerAdapter.setData(users);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);
       /*List<String> array = new ArrayList<>();
        List<User> users = db.userDao().getAllUsers();

        for (User user : users){
            array.add(user.getUser_first_name());

        }

        ListView listView = (ListView) findViewById(R.id.listviewuser);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, array);
        listView.setFastScrollEnabled(true);
        listView.setAdapter(adapter);*/

    }
}


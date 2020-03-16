package com.example.smoketracker_weber_favez;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smoketracker_weber_favez.db.AppDatabase;
import com.example.smoketracker_weber_favez.db.User;
import java.util.ArrayList;
import java.util.List;

public class ShowUserActivity extends AppCompatActivity {
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        db = AppDatabase.getAppDatabase(this);

        List<User> users = db.userDao().getAllUsers();
        List<String> array = new ArrayList<>();
        for (User user : users){
            array.add(user.getUser_first_name());
        }

        ListView listView = (ListView) findViewById(R.id.listviewuser);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, array);
        listView.setFastScrollEnabled(true);
        listView.setAdapter(adapter);

    }
}


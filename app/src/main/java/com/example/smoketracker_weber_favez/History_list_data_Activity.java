package com.example.smoketracker_weber_favez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class History_list_data_Activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list_data);

        ListView listView = (ListView) findViewById(R.id.smoked_craved_listview);


        final HashMap<String, String> details_hours = new HashMap<>();
        details_hours.put("Smoked", "10:30");
        details_hours.put("Craved", "12:12");
        details_hours.put("Smoked", "21:30");

        //set the adapter
        final List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this,
                listItems,
                R.layout.activity_history_list_data_item,
                new String[]{"First line","Second line"},
                new int[]{R.id.text1_list_item_history, R.id.text2_list_item_history});


        //Put the values for the listview
        Iterator it = details_hours.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultsMap.put("First line", pair.getKey().toString());
            resultsMap.put("Second line", pair.getValue().toString());
            listItems.add(resultsMap);
        }


        listView.setFastScrollEnabled(true);
        listView.setAdapter(adapter);


    }
}

package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.content.Intent;
import android.os.Bundle;
import java.lang.*;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HistoryFragment extends Fragment {
    AppDatabase db;
    private int day1 = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        ListView listView = (ListView) view.findViewById(R.id.date_listview);

        final HashMap<String, String> dayDates = new HashMap<>();

        //Dates Array
        List<String> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i=0; i<31; i++) {
            calendar.add(Calendar.DATE, 1);
            SimpleDateFormat curFormater = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
            dateList.add(curFormater.format(calendar.getTime()));
        }


        //Days Array
        final List<String> days = new ArrayList<>();
        //Add the days in the arraylist begining with day 1 & add the values in the hashmap
        for (int i = 0; i<dateList.size();i++){
            days.add("Day "+day1);
            dayDates.put(days.get(i),dateList.get(i));
            day1++;
        }

        //set the com.example.smoketracker_weber_favez.smoketracker.adapter
        final List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                listItems,
                R.layout.fragment_history_list_item,
                new String[]{"First line","Second line"},
                new int[]{R.id.text1_list_item, R.id.text2_list_item});

        //Put the values for the listview
        Iterator it = dayDates.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultsMap.put("First line", pair.getKey().toString());
            resultsMap.put("Second line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        listView.setFastScrollEnabled(true);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),History_list_data_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
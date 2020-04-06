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
import androidx.lifecycle.ViewModelProviders;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayListOneUserViewEmailModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayListOneUserViewModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.User.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HistoryFragment extends Fragment {

    private UserViewModel userViewModel;
    private DayListOneUserViewEmailModel dayViewModel;

    private List<DayEntity> days ;

    private UserEntity user;
    private DayEntity day;

    private ListView listView;

    private SimpleAdapter adapter;

    private List<String> daysList;
    private List<String> dateList;

    private Date dateParse;
    private String stringParse;

    //page where we can see all the days with the date in a ListView
    //WHen we click on a listView, it goes on the respective day and we have statistiques about this unique day
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        listView = (ListView) view.findViewById(R.id.date_listview);

        String userEmailLogged = getActivity().getIntent().getStringExtra("loggedUserEmail");

        days = new ArrayList<>();

        UserViewModel.Factory factory = new UserViewModel.Factory(getActivity().getApplication(),userEmailLogged);
        userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        userViewModel.getUser().observe(this, userEntity -> {
            if (userEntity!=null) {
                user = userEntity;
                declareDayFactory();
            }
        });


        return view;
    }

    private void declareDayFactory(){
        DayListOneUserViewEmailModel.Factory factoryDay = new DayListOneUserViewEmailModel.Factory(getActivity().getApplication(),user.getUser_email());
        dayViewModel = ViewModelProviders.of(this, factoryDay).get(DayListOneUserViewEmailModel.class);
        dayViewModel.getAllDaysForOneUser().observe(this, dayEntities -> {
            if (dayEntities!=null) {
                days = dayEntities;

                final HashMap<String, String> dayDates = new HashMap<>();

                //Dates Array
                dateList = new ArrayList<>();
                daysList = new ArrayList<>();
                for (int i=0; i<days.size(); i++) {
                    if (days.get(i).getDate()!=null) {
                        daysList.add("Day " + Integer.toString(days.get(i).getDay_number()));
                        dateParse =  days.get(i).getDate();
                        stringParse = new SimpleDateFormat("dd-MM-yyyy").format(dateParse);
                        dateList.add(stringParse);
                    }
                }

                for (int i =0 ; i<daysList.size() ;i++) {
                    dayDates.put(daysList.get(i), dateList.get(i));
                }

                //set the com.example.smoketracker_weber_favez.smoketracker.adapter
                final List<HashMap<String, String>> listItems = new ArrayList<>();
                adapter = new SimpleAdapter(getActivity(),
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
                        intent.putExtra("DayID", days.get(position).getId());
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
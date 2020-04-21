package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.HourEntity;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayViewModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Hour.ListHourViewModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class History_list_data_Activity extends AppCompatActivity {

    private String dayId;
    private DayViewModel viewModel;
    private ListHourViewModel listHourViewModel;

    private DayEntity day;
    private ListView listView;
    private List<HourEntity> hours;

    private TextView date_history_day, smoked_value_history, craved_value_history, chf_spent_value_history;

    private Date dateParse;
    private String stringParse;

    private Date hourParse;
    private String hourParseString;

    private List<String> descriptionlist;
    private List<String> hourList;

    private SimpleAdapter adapter;

    private static DecimalFormat df2 = new DecimalFormat("#.##");


    //Page with the statistique of a day and we can also see the hour when the user smoked or craved
    //Also with a ListView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list_data);

        initateView();

        dayId = getIntent().getStringExtra("DayID");

        DayViewModel.Factory factoryDay = new DayViewModel.Factory(getApplication(), dayId);
        viewModel = ViewModelProviders.of(this, factoryDay).get(DayViewModel.class);
        viewModel.getDay().observe(this, dayEntity -> {
            if (dayEntity != null) {
                day = dayEntity;
                dateParse = day.getDate();
                stringParse = new SimpleDateFormat("dd-MM-yyyy").format(dateParse);
                date_history_day.setText(stringParse);
                smoked_value_history.setText(Integer.toString(day.getCigarettes_smoked_per_day()));
                craved_value_history.setText(Integer.toString(day.getCigarettes_craved_per_day()));
                chf_spent_value_history.setText(df2.format(day.getMoney_saved_per_day()));


                //Pour les heures de Smoked et Craved
                //Il faut retenir le temps au moment ou on clique sur le bouton et quel bouton c'est pour ensuite récupérer ces infos et les mettre ici dans la hashmap

                ListHourViewModel.Factory factoryHour = new ListHourViewModel.Factory(getApplication(), dayId);
                listHourViewModel = ViewModelProviders.of(this, factoryHour).get(ListHourViewModel.class);
                listHourViewModel.getAllHoursForOneDay().observe(this, hourEntities -> {

                    if (hourEntities != null) {
                        hours = hourEntities;

                        final HashMap<String, String> hourDescription = new HashMap<>();

                        //Dates Array
                        descriptionlist = new ArrayList<>();
                        hourList = new ArrayList<>();
                        for (int i = 0; i < hours.size(); i++) {
                            if (hours.get(i).getHour() != null) {
                                descriptionlist.add(hours.get(i).getDescription());
                                hourParse = hours.get(i).getHour();
                                hourParseString = new SimpleDateFormat("HH:mm").format(hourParse);
                                hourList.add(hourParseString);
                            }
                        }

                        for (int i = 0; i < descriptionlist.size(); i++) {
                            hourDescription.put(hourList.get(i),descriptionlist.get(i));
                        }

                        //set the com.example.smoketracker_weber_favez.smoketracker.adapter
                        final List<HashMap<String, String>> listItems = new ArrayList<>();
                        adapter = new SimpleAdapter(this,
                                listItems,
                                R.layout.activity_history_list_data_item,
                                new String[]{"First line", "Second line"},
                                new int[]{R.id.text1_list_item_history, R.id.text2_list_item_history});

                        //Put the values for the listview
                        Iterator it = hourDescription.entrySet().iterator();
                        while (it.hasNext()) {
                            HashMap<String, String> resultsMap = new HashMap<>();
                            Map.Entry pair = (Map.Entry) it.next();
                            resultsMap.put("First line", pair.getValue().toString());
                            resultsMap.put("Second line",  pair.getKey().toString());

                            listItems.add(resultsMap);
                        }
                        listView.setFastScrollEnabled(true);
                        listView.setAdapter(adapter);
                    }
                });
            }
        });

        //Try to sort the dates but it displays the whole date with an old date...
                   /*if (hourEntities != null) {
                        hours = hourEntities;

                        final TreeMap<Date, String> hourDescription = new TreeMap<>();

                        //Dates Array
                        descriptionlist = new ArrayList<>();
                        hourList = new ArrayList<>();
                        for (int i = 0; i < hours.size(); i++) {
                            if (hours.get(i).getHour() != null) {
                                descriptionlist.add(hours.get(i).getDescription());
                                hourList.add(hours.get(i).getHour());
                            }
                        }

                        for (int i = 0; i < descriptionlist.size(); i++) {
                            hourDescription.put(hourList.get(i),descriptionlist.get(i));
                        }


                        //set the com.example.smoketracker_weber_favez.smoketracker.adapter
                        final List<HashMap<String, String>> listItems = new ArrayList<>();
                        adapter = new SimpleAdapter(this,
                                listItems,
                                R.layout.activity_history_list_data_item,
                                new String[]{"First line", "Second line"},
                                new int[]{R.id.text1_list_item_history, R.id.text2_list_item_history});

                        //Put the values for the listview
                        Iterator it = hourDescription.entrySet().iterator();
                        while (it.hasNext()) {
                            HashMap<String, String> resultsMap = new HashMap<>();
                            Map.Entry pair = (Map.Entry) it.next();
                            resultsMap.put("First line", pair.getValue().toString());
                            resultsMap.put("Second line",  pair.getKey().toString());
                            listItems.add(resultsMap);
                        }
                        listView.setFastScrollEnabled(true);
                        listView.setAdapter(adapter);
                    }
                });
            }
        });
    }

                     */
    }

    public void initateView() {
        date_history_day = (TextView) findViewById(R.id.date_history_day);
        smoked_value_history = (TextView) findViewById(R.id.smoked_value_history);
        craved_value_history = (TextView) findViewById(R.id.craved_value_history);
        chf_spent_value_history = (TextView) findViewById(R.id.chf_spent_value_history);
        listView = (ListView) findViewById(R.id.smoked_craved_listview);
    }


}

package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayViewModel;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class History_list_data_Activity extends AppCompatActivity {

    private int dayId;
    private DayViewModel viewModel;

    private DayEntity day;
    private ListView listView;

    private TextView date_history_day, smoked_value_history,craved_value_history,chf_spent_value_history;

    private Date dateParse;
    private String stringParse;

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list_data);

        initateView();

        dayId = getIntent().getIntExtra("DayID",-1);

        DayViewModel.Factory factoryDay = new DayViewModel.Factory(getApplication(), dayId);
        viewModel = ViewModelProviders.of(this, factoryDay).get(DayViewModel.class);
        viewModel.getDay().observe(this, dayEntity -> {
            if (dayEntity != null) {
                day = dayEntity;
                dateParse =  day.getDate();
                stringParse = new SimpleDateFormat("dd-MM-yyyy").format(dateParse);
                date_history_day.setText(stringParse);
                smoked_value_history.setText(Integer.toString(day.getCigarettes_smoked_per_day()));
                craved_value_history.setText(Integer.toString(day.getCigarettes_craved_per_day()));
                chf_spent_value_history.setText(df2.format(day.getMoney_saved_per_day()));
            }
        });

        //Pour les heures de Smoked et Craved
        //Il faut retenir le temps au moment ou on clique sur le bouton et quel bouton c'est pour ensuite récupérer ces infos et les mettre ici dans la hashmap
        final HashMap<String, String> details_hours = new HashMap<>();
        details_hours.put("Smoked", "10:30");
        details_hours.put("Craved", "12:12");
        details_hours.put("Smoked", "21:30");

        //set the com.example.smoketracker_weber_favez.smoketracker.adapter
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

    public void initateView(){
        date_history_day = (TextView) findViewById(R.id.date_history_day);
        smoked_value_history = (TextView) findViewById(R.id.smoked_value_history);
        craved_value_history = (TextView) findViewById(R.id.craved_value_history);
        chf_spent_value_history = (TextView) findViewById(R.id.chf_spent_value_history);
        listView = (ListView) findViewById(R.id.smoked_craved_listview);
    }
}

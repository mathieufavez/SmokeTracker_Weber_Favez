package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayListOneUserViewEmailModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayListOneUserViewModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.User.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatsFragment extends Fragment {

    private UserViewModel userViewModel;
    private DayListOneUserViewEmailModel dayViewModel;

    private UserEntity user;

    private TextView sinceday_textView,totalsmoked_value, totalcraved_value, totalspent_value, avgsmoked_value, avgcraved_value;

    private List<DayEntity> days ;

    private int cigarettesSmokedTotal;
    private int cigarettesCravedTotal;
    private double moneySpentTotal;
    private double avgCigarettesSmoked;
    private double avgCigarettesCraved;
    private String userEmailLogged;
    private Date dateFirstDay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        userEmailLogged = getActivity().getIntent().getStringExtra("loggedUserEmail");
        days = new ArrayList<>();

        initiateView(view);

        UserViewModel.Factory factory = new UserViewModel.Factory(getActivity().getApplication(),userEmailLogged);
        userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        userViewModel.getUser().observe(this, userEntity -> {
            if (userEntity!=null) {
                user = userEntity;
                declareDayFactory(userEntity.getId());
            }
        });

        return view;
    }

    private void initiateView(View view){
        sinceday_textView = view.findViewById(R.id.sinceday_textView);
        totalsmoked_value = view.findViewById(R.id.totalsmoked_value);
        totalcraved_value = view.findViewById(R.id.totalcraved_value);
        totalspent_value = view.findViewById(R.id.totalspent_value);
        avgsmoked_value = view.findViewById(R.id.avgsmoked_value);
        avgcraved_value = view.findViewById(R.id.avgcraved_value);
    }

    private void declareDayFactory(int userId){
        DayListOneUserViewEmailModel.Factory factoryDay = new DayListOneUserViewEmailModel.Factory(getActivity().getApplication(),userEmailLogged);
        dayViewModel = ViewModelProviders.of(this, factoryDay).get(DayListOneUserViewEmailModel.class);
        dayViewModel.getAllDaysForOneUser().observe(this, dayEntities -> {
            if (dayEntities!=null) {
                days = dayEntities;
                dateFirstDay =  days.get(0).getDate();
                for (int i =0;i<days.size();i++) {
                    cigarettesSmokedTotal+= days.get(i).getCigarettes_smoked_per_day();
                    cigarettesCravedTotal+= days.get(i).getCigarettes_craved_per_day();
                    moneySpentTotal += days.get(i).getMoney_saved_per_day();
                }

                avgCigarettesSmoked= cigarettesSmokedTotal/days.size();
                avgCigarettesCraved=cigarettesCravedTotal/days.size();

                String formatedDate = new SimpleDateFormat("dd-MM-yyyy").format(dateFirstDay);

                sinceday_textView.setText("Since " + formatedDate);
                totalsmoked_value.setText(Integer.toString(cigarettesSmokedTotal));
                totalcraved_value.setText(Integer.toString(cigarettesCravedTotal));
                totalspent_value.setText(Double.toString(moneySpentTotal));
                avgsmoked_value.setText(Double.toString(avgCigarettesSmoked));
                avgcraved_value.setText(Double.toString(avgCigarettesCraved));


            }
        });
    }
}

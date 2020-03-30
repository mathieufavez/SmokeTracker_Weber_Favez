package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.HourEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayListOneUserViewEmailModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayViewEmailModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Hour.HourViewModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Hour.ListHourViewModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.User.UserViewModel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TrackingFragment extends Fragment {

    private String TAG_AddCraved = "AddCraved";
    private String TAG_AddSmoked = "AddSmoked";
    private String TAG_AddHourCraved = "AddHourCraved";
    private String TAG_AddHourSmoked = "AddHourSmoked";
    private TextView day_textView;
    private TextView x_cigarettes_left_textView;
    private TextView smoked_value;
    private TextView craved_value;
    private TextView money_spent_value;

    private Button button_i_smoked;
    private Button button_i_craved;

    private UserViewModel userViewModel;
    private DayListOneUserViewEmailModel dayViewModel;
    private DayViewEmailModel viewModel;
    private HourViewModel hourViewModel;

    private UserEntity user;
    private DayEntity dayCraved;
    private DayEntity daySmoke;
    private HourEntity hour;


    private List<DayEntity> days;

    private double depenseSmokeOneCigarette;
    private double totalDepenseSmoke;

    private int cigaretteLimit;

    private Date currentTimeSmoked;
    private Date currentTimeCraved;
    private Date formatedOk;
    private static DecimalFormat df2 = new DecimalFormat("#.##");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracking, container, false);

        initiateView(view);

        days = new ArrayList<>();

        //We take the email of the user logged
        String userEmailLogged = getActivity().getIntent().getStringExtra("loggedUserEmail");

        UserViewModel.Factory factory = new UserViewModel.Factory(getActivity().getApplication(), userEmailLogged);
        userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        userViewModel.getUser().observe(this, userEntity -> {
            if (userEntity != null) {
                user = userEntity;
                declareDayFactory();
                cigaretteLimit = user.getUser_smoke_per_day_limit();

            }
        });
        //When the user clicks on I Craved it creates an hour and update the day and user table
        button_i_craved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCraved();
            }
        });

        //Same as up
        button_i_smoked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSmoked();
            }
        });

        return view;
    }

    //Create the hour
    private void addCraved() {
        HourViewModel.Factory factory = new HourViewModel.Factory(getActivity().getApplication(), days.get(days.size()-1).getId());
        hourViewModel = ViewModelProviders.of(this, factory).get(HourViewModel.class);
        hourViewModel.getHour().observe(this, hourEntity -> {
                hour = new HourEntity();
                hour.setIdDay(days.get(days.size()-1).getId());
                currentTimeCraved =  Calendar.getInstance().getTime();
                String formatedDate = new SimpleDateFormat("HH:mm").format(currentTimeCraved);
                try {
                    formatedOk = new SimpleDateFormat("HH:mm").parse(formatedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                hour.setHour(formatedOk);
                hour.setDescription("Craved");

                hourViewModel.createHour(hour, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG_AddHourCraved, "addHourCraved: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG_AddHourCraved, "addHourCraved: failure");
                    }
                });
                hourViewModel.getHour().removeObservers(this);
        });



        DayListOneUserViewEmailModel.Factory factoryDay = new DayListOneUserViewEmailModel.Factory(getActivity().getApplication(), user.getUser_email());
        dayViewModel = ViewModelProviders.of(this, factoryDay).get(DayListOneUserViewEmailModel.class);
        dayViewModel.getAllDaysForOneUser().observe(this, dayEntities -> {
            if (dayEntities != null) {
            }
        });

        DayViewEmailModel.Factory factory2 = new DayViewEmailModel.Factory(getActivity().getApplication(), user.getUser_email());
        viewModel = ViewModelProviders.of(this, factory2).get(DayViewEmailModel.class);
        viewModel.getDay().observe(this, dayEntity -> {
            if (dayEntity != null) {
                dayCraved = dayEntity;
                dayCraved.setCigarettes_craved_per_day(days.get(days.size() - 1).getCigarettes_craved_per_day() + 1);

                viewModel.updateDay(dayCraved, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG_AddCraved, "addCraved: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG_AddCraved, "addCraved: failure");
                    }
                });
                viewModel.getDay().removeObservers(this);
            }
        });

    }

    //Create the hour
    private void addSmoked() {

        HourViewModel.Factory factory = new HourViewModel.Factory(getActivity().getApplication(), days.get(days.size()-1).getId());
        hourViewModel = ViewModelProviders.of(this, factory).get(HourViewModel.class);
        hourViewModel.getHour().observe(this, hourEntity -> {
            hour = new HourEntity();
            hour.setIdDay(days.get(days.size()-1).getId());
            currentTimeCraved =  Calendar.getInstance().getTime();
            String formatedDate = new SimpleDateFormat("HH:mm").format(currentTimeCraved);
            try {
                formatedOk = new SimpleDateFormat("HH:mm").parse(formatedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            hour.setHour(formatedOk);
            hour.setDescription("Smoked");

            hourViewModel.createHour(hour, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG_AddHourSmoked, "addHourSmoked: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG_AddHourSmoked, "addHourSmoked: failure");
                }
            });
            hourViewModel.getHour().removeObservers(this);
        });

        cigaretteLimit -= 1;
        DayListOneUserViewEmailModel.Factory factoryDay = new DayListOneUserViewEmailModel.Factory(getActivity().getApplication(), user.getUser_email());
        dayViewModel = ViewModelProviders.of(this, factoryDay).get(DayListOneUserViewEmailModel.class);
        dayViewModel.getAllDaysForOneUser().observe(this, dayEntities -> {
            if (dayEntities != null) {
            }
        });

        DayViewEmailModel.Factory factory2 = new DayViewEmailModel.Factory(getActivity().getApplication(), user.getUser_email());
        viewModel = ViewModelProviders.of(this, factory2).get(DayViewEmailModel.class);
        viewModel.getDay().observe(this, dayEntity -> {
            if (dayEntity != null) {
                daySmoke = dayEntity;

                daySmoke.setCigarettes_smoked_per_day(days.get(days.size() - 1).getCigarettes_smoked_per_day() + 1);

                depenseSmokeOneCigarette = user.getUser_packet_price() / user.getUser_quantity_per_packet();
                totalDepenseSmoke += depenseSmokeOneCigarette;
                daySmoke.setMoney_saved_per_day(totalDepenseSmoke);

                viewModel.updateDay(daySmoke, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG_AddSmoked, "addSmokedDay: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG_AddSmoked, "addSmokedDay: failure");
                    }
                });
                viewModel.getDay().removeObservers(this);
            }
        });
    }

    private void declareDayFactory() {
        DayListOneUserViewEmailModel.Factory factoryDay = new DayListOneUserViewEmailModel.Factory(getActivity().getApplication(), user.getUser_email());
        dayViewModel = ViewModelProviders.of(this, factoryDay).get(DayListOneUserViewEmailModel.class);
        dayViewModel.getAllDaysForOneUser().observe(this, dayEntities -> {
            if (dayEntities != null) {
                days = dayEntities;
                day_textView.setText("Day " + Integer.toString(days.get(days.size() - 1).getDay_number()));
                x_cigarettes_left_textView.setText(Integer.toString(cigaretteLimit));
                smoked_value.setText(Integer.toString(days.get(days.size() - 1).getCigarettes_smoked_per_day()));
                craved_value.setText(Integer.toString(days.get(days.size() - 1).getCigarettes_craved_per_day()));
                money_spent_value.setText(df2.format(days.get(days.size() - 1).getMoney_saved_per_day()));
            }
        });
    }

    private void initiateView(View view) {
        button_i_smoked = (Button) view.findViewById(R.id.button_i_smoked);
        button_i_craved = (Button) view.findViewById(R.id.button_i_craved);

        day_textView = (TextView) view.findViewById(R.id.day_textView);
        x_cigarettes_left_textView = (TextView) view.findViewById(R.id.x_cigarettes_left_textView);
        smoked_value = (TextView) view.findViewById(R.id.smoked_value);
        craved_value = (TextView) view.findViewById(R.id.craved_value);
        money_spent_value = (TextView) view.findViewById(R.id.chf_spent_value);
    }

}

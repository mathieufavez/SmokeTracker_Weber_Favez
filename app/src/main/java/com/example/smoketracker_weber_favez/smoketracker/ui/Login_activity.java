package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayListOneUserViewEmailModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayViewEmailModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.User.UserViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Login_activity extends AppCompatActivity {
    private static final String TAG = "Login_Activity";


    private Button buttonConnect;

    private EditText emailLogin;
    private EditText passwordLogin;

    private UserViewModel viewModel;
    private DayViewEmailModel viewEmailModel;
    private DayListOneUserViewEmailModel dayViewModel;

    private UserEntity user;
    private DayEntity day;
    private int dayNumber;

    private Date currentTime = Calendar.getInstance().getTime();
    private Date dateFormatedCurrent;
    private Date previousDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        String formatedDateCurrent = new SimpleDateFormat("dd-MM-yyyy").format(currentTime);

        try {
            dateFormatedCurrent = new SimpleDateFormat("dd-MM-yyyy").parse(formatedDateCurrent);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        emailLogin = (EditText) findViewById(R.id.edit_text_email_connection);
        passwordLogin = (EditText) findViewById(R.id.edit_text_password_connection);


        buttonConnect = (Button) findViewById(R.id.button_connect);
        buttonConnect.setOnClickListener(view -> attemptLogin());

    }

    private void attemptLogin() {

        // Reset errors.
        emailLogin.setError(null);
        passwordLogin.setError(null);

        // Store values at the time of the login attempt.
        String email = emailLogin.getText().toString();
        String password = passwordLogin.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailLogin.setError("This field is required");
            focusView = emailLogin;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailLogin.setError("Invalid email address");
            focusView = emailLogin;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            DayListOneUserViewEmailModel.Factory factoryDay = new DayListOneUserViewEmailModel.Factory(getApplication(), email);
            dayViewModel = ViewModelProviders.of(this, factoryDay).get(DayListOneUserViewEmailModel.class);
            dayViewModel.getAllDaysForOneUser().observe(this, dayEntities -> {
                        if (dayEntities != null) {
                            if (dayEntities.size()<=0) {
                                dayNumber = 1;
                            }
                            else {
                                dayNumber = dayEntities.get(dayEntities.size() - 1).getDay_number() + 1;
                                previousDate = dayEntities.get((dayEntities.size() - 1)).getDate();
                            }
                        }
                    }
            );

            DayViewEmailModel.Factory dayFactory = new DayViewEmailModel.Factory(getApplication(), email);
            viewEmailModel = ViewModelProviders.of(this, dayFactory).get(DayViewEmailModel.class);
            viewEmailModel.getDay().observe(this, dayEntity -> {
                if (dayEntity != null) {
                    day = dayEntity;
                }
            });

            UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), email);
            viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
            viewModel.getUser().observe(this, userEntity -> {
                if (userEntity != null) {
                    if (userEntity.getUser_password().equals(password)) {
                        SharedPreferences.Editor editor = getSharedPreferences("SharedPrefs", 0).edit();
                        editor.putString("LoggedIn", userEntity.getUser_email());
                        editor.apply();
                        if (previousDate.compareTo( dateFormatedCurrent)<0) {
                            try {
                                createDay(currentTime,dayNumber,0,0,0,userEntity.getUser_email());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        else {

                            Intent intent = new Intent(Login_activity.this, TrackingActivity.class);
                            intent.putExtra("loggedUserEmail", userEntity.getUser_email());
                            startActivity(intent);
                            emailLogin.setText("");
                            passwordLogin.setText("");
                        }

                    } else {
                        passwordLogin.setError("This password is incorrect");
                        passwordLogin.requestFocus();
                        passwordLogin.setText("");
                    }
                } else {
                    emailLogin.setError("Invalid e-mail address");
                    emailLogin.requestFocus();
                    emailLogin.setText("");
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void createDay(Date date, int dayNumber, int cigarettesSmoked, int cigarettesCraved, double moneySaved, String userEmail) throws ParseException {

        String formatedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);

        Date dateFromated = new SimpleDateFormat("dd-MM-yyyy").parse(formatedDate);

        day = new DayEntity();
        day.setDate(dateFromated);
        day.setDay_number(dayNumber);
        day.setCigarettes_smoked_per_day(cigarettesSmoked);
        day.setCigarettes_craved_per_day(cigarettesCraved);
        day.setMoney_saved_per_day(moneySaved);
        day.setUserEmail(userEmail);

        viewEmailModel.createDay(day, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createDay: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "createDay: failure", e);
            }
        });
    }

}
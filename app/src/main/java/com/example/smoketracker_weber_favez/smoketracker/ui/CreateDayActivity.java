package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayViewEmailModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateDayActivity extends AppCompatActivity {

    private static final String TAG = "CreateDayActivity";

    private EditText date, dayNumber, cigarettesSmokedPerDay, cigarettesCravedPerDay, moneySpentPerDay, userId, edit_userEmail_showday;
    private Button button_confirm_dayAdd;

    private DayViewEmailModel viewModel;

    private DayEntity day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_day);

        initiateView();

        button_confirm_dayAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datee =  date.getText().toString();
                try {
                    Date formatedDate = new SimpleDateFormat("dd-MM-yyyy").parse(datee);
                createDay(
                        formatedDate,
                        Integer.parseInt(dayNumber.getText().toString()),
                        Integer.parseInt(cigarettesSmokedPerDay.getText().toString()),
                        Integer.parseInt(cigarettesCravedPerDay.getText().toString()),
                        Double.parseDouble(moneySpentPerDay.getText().toString()),
                        edit_userEmail_showday.getText().toString()

                );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //Créer en boucle
    private void createDay(Date date, int dayNbr, int cigarettesSmoked, int cigarettesCraved, double moneySpent, String emailUser) {
        DayViewEmailModel.Factory factory = new DayViewEmailModel.Factory(getApplication(), emailUser);
        viewModel = ViewModelProviders.of(this, factory).get(DayViewEmailModel.class);
        viewModel.getDay().observe(this, dayEntity -> {
            if (dayEntity!=null) {
                day = dayEntity;
                try {
                    day = new DayEntity();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                day.setDate(date);
                day.setDay_number(dayNbr);
                day.setCigarettes_smoked_per_day(cigarettesSmoked);
                day.setCigarettes_craved_per_day(cigarettesCraved);
                day.setMoney_saved_per_day(moneySpent);
                day.setUserEmail(emailUser);

                viewModel.createDay(day, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "createDay: success");
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "createDay: failure", e);
                    }
                });
            }
        });
    }

    private void initiateView(){
        date= (EditText) findViewById(R.id.edit_date_input);
        dayNumber = (EditText) findViewById(R.id.edit_day_number_input);
        cigarettesSmokedPerDay = (EditText) findViewById(R.id.edit_cigarettes_smoked_per_day);
        cigarettesCravedPerDay = (EditText) findViewById(R.id.edit_cigarettes_craved_per_day);
        moneySpentPerDay = (EditText) findViewById(R.id.edit_money_saved_per_day);
        userId = (EditText) findViewById(R.id.edit_userId);
        edit_userEmail_showday = (EditText) findViewById(R.id.edit_userEmail_showday) ;

        button_confirm_dayAdd = (Button) findViewById(R.id.button_confirm_dayAdd) ;
    }
}


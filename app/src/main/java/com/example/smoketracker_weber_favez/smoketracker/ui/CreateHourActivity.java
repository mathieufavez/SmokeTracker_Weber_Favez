package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.HourEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Hour.HourViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Page where the user creates the Hour directly in the database
public class CreateHourActivity extends AppCompatActivity {

    private static final String TAG = "CreateHourActivity";

    private EditText edit_hour_input, edit_description_showhour, edit_dayId_showhour;
    private Button button_confirm_hourAdd;

    private HourViewModel viewModel;

    private HourEntity hour;

    //Page where the user creates a day directly in the database manually
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hour);

        initiateView();

        button_confirm_hourAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heure =  edit_hour_input.getText().toString();
                try {
                    Date formatedHour = new SimpleDateFormat("HH:mm").parse(heure);
                    createDay(
                            formatedHour,
                            edit_description_showhour.getText().toString(),
                            edit_dayId_showhour.getText().toString(),
                            "test"

                    );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createDay(Date heure, String description, String dayId, String userId) {
        HourViewModel.Factory factory = new HourViewModel.Factory(getApplication(), dayId);
        viewModel = ViewModelProviders.of(this, factory).get(HourViewModel.class);

        hour = new HourEntity();
        hour.setHour(heure);
        hour.setDescription(description);
        hour.setIdDay(dayId);


        viewModel.createHour(hour,userId,dayId ,new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createHour: success");
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "createHour: failure", e);
            }
        });
    }

    private void initiateView(){
        edit_hour_input= (EditText) findViewById(R.id.edit_hour_input);
        edit_description_showhour = (EditText) findViewById(R.id.edit_description_showhour);
        edit_dayId_showhour = (EditText) findViewById(R.id.edit_dayId_showhour);

        button_confirm_hourAdd = (Button) findViewById(R.id.button_confirm_hourAdd) ;
    }
}



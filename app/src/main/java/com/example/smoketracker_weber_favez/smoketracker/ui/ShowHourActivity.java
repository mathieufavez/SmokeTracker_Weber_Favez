package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.HourEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayViewModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Hour.HourViewModel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowHourActivity extends AppCompatActivity {

    private static final String TAG = "HourMoreDetails";

    private static final int EDIT_HOUR = 1;
    private static final int DELETE_HOUR = 2;

    private Toast statusToast;

    private boolean isEditable;

    private EditText edit_hour_input, edit_description_showhour, edit_dayId_showhour;
    private Button button_confirm_hourAdd;

    private HourViewModel hourViewModel;

    private HourEntity hour;

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    //Page where the user can see the details of an Hour, delete it or update it
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hour);
        setTitle("Hour Details");

        initiateView();

        int idHour = getIntent().getIntExtra("hourId",-1);

        HourViewModel.Factory factory = new HourViewModel.Factory(getApplication(),idHour);
        hourViewModel = ViewModelProviders.of(this, factory).get(HourViewModel.class);
        hourViewModel.getHour().observe(this, hourEntity -> {
            if (hourEntity!=null) {
                hour = hourEntity;
                updateContent();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_HOUR) {
            if (isEditable) {
                item.setIcon(R.drawable.ic_mode_edit_white_24dp);
                try {
                    switchEditableMode();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                item.setIcon(R.drawable.ic_done_white_24dp);
                try {
                    switchEditableMode();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        if (item.getItemId() == DELETE_HOUR) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Delete");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Do you really want to delete this Day ?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete", (dialog, which) -> {
                hourViewModel.deleteHour(hour, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "deleteDay: success");
                        onBackPressed();
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "deleteDay: failure", e);
                        onBackPressed();
                    }
                });
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (hour != null) {
            menu.add(0, EDIT_HOUR, Menu.NONE, "Edit")
                    .setIcon(R.drawable.ic_mode_edit_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0, DELETE_HOUR, Menu.NONE, "Delete")
                    .setIcon(R.drawable.ic_delete_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    private void switchEditableMode() throws ParseException {
        if (!isEditable) {

            edit_hour_input.setFocusable(true);
            edit_hour_input.setEnabled(true);
            edit_hour_input.setFocusableInTouchMode(true);

            edit_description_showhour.setFocusable(true);
            edit_description_showhour.setEnabled(true);
            edit_description_showhour.setFocusableInTouchMode(true);

            edit_dayId_showhour.setFocusable(true);
            edit_dayId_showhour.setEnabled(true);
            edit_dayId_showhour.setFocusableInTouchMode(true);

            edit_hour_input.requestFocus();
        } else {

            String datee =  edit_hour_input.getText().toString();
            Date formatedDate = new SimpleDateFormat("HH:mm").parse(datee);

            saveChanges(
                    formatedDate,
                    edit_description_showhour.getText().toString(),
                    Integer.parseInt(edit_dayId_showhour.getText().toString())
            );

            edit_hour_input.setFocusable(false);
            edit_hour_input.setEnabled(false);

            edit_description_showhour.setFocusable(false);
            edit_description_showhour.setEnabled(false);

            edit_dayId_showhour.setFocusable(false);
            edit_dayId_showhour.setEnabled(false);

        }
        isEditable = !isEditable;
    }

    private void saveChanges(Date heure, String description, int dayId) {

        hour.setHour(heure);
        hour.setDescription(description);
        hour.setIdDay(dayId);


        hourViewModel.updateHour(hour, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateHour: success");
                setResponse(true);
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "updateHour: failure", e);
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(this, "Hour edited", Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG);
            statusToast.show();
        }
    }

    private void updateContent() {
        Date datee =  hour.getHour();
        String formatedDate = new SimpleDateFormat("HH:mm").format(datee);

        if (hour!=null) {
            edit_hour_input.setText(formatedDate);
            edit_description_showhour.setText(hour.getDescription());
            edit_dayId_showhour.setText(Integer.toString(hour.getIdDay()));
        }
    }

    private void initiateView() {
        isEditable = false;

        edit_hour_input= (EditText) findViewById(R.id.edit_hour_input);
        edit_description_showhour = (EditText) findViewById(R.id.edit_description_showhour);
        edit_dayId_showhour = (EditText) findViewById(R.id.edit_dayId_showhour);
        button_confirm_hourAdd = (Button) findViewById(R.id.button_confirm_hourAdd) ;

        button_confirm_hourAdd.setEnabled(false);
        button_confirm_hourAdd.setVisibility(View.GONE);

        edit_hour_input.setFocusable(false);
        edit_hour_input.setEnabled(false);

        edit_description_showhour.setFocusable(false);
        edit_description_showhour.setEnabled(false);

        edit_dayId_showhour.setFocusable(false);
        edit_dayId_showhour.setEnabled(false);

    }

}

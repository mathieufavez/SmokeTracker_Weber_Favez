package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayViewModel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ShowDayActivity extends AppCompatActivity {

    private static final String TAG = "UserMoreDetails";

    private static final int EDIT_DAY = 1;
    private static final int DELETE_DAY = 2;

    private Toast statusToast;

    private boolean isEditable;

    private EditText date, dayNumber, cigarettesSmokedPerDay, cigarettesCravedPerDay, moneySpentPerDay, userId, edit_userEmail_showday;
    private Button button_confirm_dayAdd;

    private DayViewModel viewModel;

    private DayEntity day;

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    //Page where the user can see the details of a Day, delete it or update it
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_day);
        setTitle("Day Details");

        initiateView();

        String idDay = getIntent().getStringExtra("dayId");

        DayViewModel.Factory factory = new DayViewModel.Factory(getApplication(),idDay);
        viewModel = ViewModelProviders.of(this, factory).get(DayViewModel.class);
        viewModel.getDay().observe(this, dayEntity -> {
            if (dayEntity!=null) {
                day = dayEntity;
                updateContent();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_DAY) {
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
        if (item.getItemId() == DELETE_DAY) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Delete");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Do you really want to delete this Day ?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete", (dialog, which) -> {
                viewModel.deleteDay(day, new OnAsyncEventListener() {
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
        if (day != null) {
            menu.add(0, EDIT_DAY, Menu.NONE, "Edit")
                    .setIcon(R.drawable.ic_mode_edit_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0, DELETE_DAY, Menu.NONE, "Delete")
                    .setIcon(R.drawable.ic_delete_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    private void switchEditableMode() throws ParseException {
        if (!isEditable) {


            date.setFocusable(true);
            date.setEnabled(true);
            date.setFocusableInTouchMode(true);

            dayNumber.setFocusable(true);
            dayNumber.setEnabled(true);
            dayNumber.setFocusableInTouchMode(true);

            cigarettesSmokedPerDay.setFocusable(true);
            cigarettesSmokedPerDay.setEnabled(true);
            cigarettesSmokedPerDay.setFocusableInTouchMode(true);

            cigarettesCravedPerDay.setFocusable(true);
            cigarettesCravedPerDay.setEnabled(true);
            cigarettesCravedPerDay.setFocusableInTouchMode(true);

            moneySpentPerDay.setFocusable(true);
            moneySpentPerDay.setEnabled(true);
            moneySpentPerDay.setFocusableInTouchMode(true);

            userId.setFocusable(true);
            userId.setEnabled(true);
            userId.setFocusableInTouchMode(true);

            edit_userEmail_showday.setFocusable(true);
            edit_userEmail_showday.setEnabled(true);
            edit_userEmail_showday.setFocusableInTouchMode(true);

            date.requestFocus();
        } else {

            String datee =  date.getText().toString();
            Date formatedDate = new SimpleDateFormat("dd-MM-yyyy").parse(datee);

            saveChanges(
                    formatedDate,
                    Integer.parseInt(dayNumber.getText().toString()),
                    Integer.parseInt(cigarettesSmokedPerDay.getText().toString()),
                    Integer.parseInt(cigarettesCravedPerDay.getText().toString()),
                    Double.parseDouble(moneySpentPerDay.getText().toString()),
                    userId.getText().toString(),
                    edit_userEmail_showday.getText().toString()

            );

            date.setFocusable(false);
            date.setEnabled(false);

            dayNumber.setFocusable(false);
            dayNumber.setEnabled(false);

            cigarettesSmokedPerDay.setFocusable(false);
            cigarettesSmokedPerDay.setEnabled(false);

            cigarettesCravedPerDay.setFocusable(false);
            cigarettesCravedPerDay.setEnabled(false);

            moneySpentPerDay.setFocusable(false);
            moneySpentPerDay.setEnabled(false);

            userId.setFocusable(false);
            userId.setEnabled(false);

            edit_userEmail_showday.setFocusable(false);
            edit_userEmail_showday.setEnabled(false);

        }
        isEditable = !isEditable;
    }

    private void initiateView() {
        isEditable = false;

        date= (EditText) findViewById(R.id.edit_date_input);
        dayNumber = (EditText) findViewById(R.id.edit_day_number_input);
        cigarettesSmokedPerDay = (EditText) findViewById(R.id.edit_cigarettes_smoked_per_day);
        cigarettesCravedPerDay = (EditText) findViewById(R.id.edit_cigarettes_craved_per_day);
        moneySpentPerDay = (EditText) findViewById(R.id.edit_money_saved_per_day);
        userId = (EditText) findViewById(R.id.edit_userId);
        edit_userEmail_showday = (EditText) findViewById(R.id.edit_userEmail_showday) ;

        button_confirm_dayAdd = (Button) findViewById(R.id.button_confirm_dayAdd) ;
        button_confirm_dayAdd.setEnabled(false);
        button_confirm_dayAdd.setVisibility(View.GONE);

        date.setFocusable(false);
        date.setEnabled(false);

        dayNumber.setFocusable(false);
        dayNumber.setEnabled(false);

        cigarettesSmokedPerDay.setFocusable(false);
        cigarettesSmokedPerDay.setEnabled(false);

        cigarettesCravedPerDay.setFocusable(false);
        cigarettesCravedPerDay.setEnabled(false);

        moneySpentPerDay.setFocusable(false);
        moneySpentPerDay.setEnabled(false);

        userId.setFocusable(false);
        userId.setEnabled(false);

        edit_userEmail_showday.setFocusable(false);
        edit_userEmail_showday.setEnabled(false);

    }

    private void saveChanges(Date date, int dayNumber, int cigarettesSmokedPerDay, int cigarettesCravedPerDay, double moneySpentPerDay, String userId, String userEmail) {

        day.setDate(date);
        day.setDay_number(dayNumber);
        day.setCigarettes_smoked_per_day(cigarettesSmokedPerDay);
        day.setCigarettes_craved_per_day(cigarettesCravedPerDay);
        day.setMoney_saved_per_day(moneySpentPerDay);
        day.setUserId(userId);
        day.setUserEmail(userEmail);

        viewModel.updateDay(day, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateDay: success");
                setResponse(true);
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "updateDay: failure", e);
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(this, "User edited", Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG);
            statusToast.show();
        }
    }

    private void updateContent() {
        Date datee =  day.getDate();
        String formatedDate = new SimpleDateFormat("dd-MM-yyyy").format(datee);

        if (day!=null) {
            date.setText(formatedDate);
            dayNumber.setText(Integer.toString(day.getDay_number()));
            cigarettesSmokedPerDay.setText(Integer.toString(day.getCigarettes_smoked_per_day()));
            cigarettesCravedPerDay.setText(Integer.toString(day.getCigarettes_craved_per_day()));
            moneySpentPerDay.setText(df2.format(day.getMoney_saved_per_day()));
            userId.setText(day.getUserId());
            edit_userEmail_showday.setText(day.getUserEmail());
        }
    }
}

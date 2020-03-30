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
import android.widget.TextView;
import android.widget.Toast;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.User.UserViewModel;

public class ShowUserActivity extends AppCompatActivity {

    private static final String TAG = "UserMoreDetails";

    private static final int EDIT_USER = 1;
    private static final int DELETE_USER = 2;

    private Toast statusToast;

    private boolean isEditable;

    private EditText lastname, firstname, email, password, brand, packetPrice, quantityPerPacket, cigarettesSmokedPerDay;
    private Button button_next_homeActivity;
    private TextView fillup;

    private UserViewModel viewModel;

    private UserEntity user;

    //Page where the user can see the details of an User, delete it or update it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("User Details");

        String userEmail = getIntent().getStringExtra("userEmail");

        initiateView();

        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(),userEmail);
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        viewModel.getUser().observe(this, userEntity -> {
            if (userEntity!=null) {
                user = userEntity;
                updateContent();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_USER) {
            if (isEditable) {
                item.setIcon(R.drawable.ic_mode_edit_white_24dp);
                switchEditableMode();
            } else {
                item.setIcon(R.drawable.ic_done_white_24dp);
                switchEditableMode();
            }
        }
        if (item.getItemId() == DELETE_USER) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Delete");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Do you really want to delete this User ?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete", (dialog, which) -> {
                viewModel.deleteUser(user, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "deleteClient: success");
                        onBackPressed();
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "deleteClient: failure", e);
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
        if (user != null) {
            menu.add(0, EDIT_USER, Menu.NONE, "Edit")
                    .setIcon(R.drawable.ic_mode_edit_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0, DELETE_USER, Menu.NONE, "Delete")
                    .setIcon(R.drawable.ic_delete_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    private void switchEditableMode() {
        if (!isEditable) {
            lastname.setFocusable(true);
            lastname.setEnabled(true);
            lastname.setFocusableInTouchMode(true);

            firstname.setFocusable(true);
            firstname.setEnabled(true);
            firstname.setFocusableInTouchMode(true);

            email.setFocusable(true);
            email.setEnabled(true);
            email.setFocusableInTouchMode(true);

            password.setFocusable(true);
            password.setEnabled(true);
            password.setFocusableInTouchMode(true);

            brand.setFocusable(true);
            brand.setEnabled(true);
            brand.setFocusableInTouchMode(true);

            packetPrice.setFocusable(true);
            packetPrice.setEnabled(true);
            packetPrice.setFocusableInTouchMode(true);

            quantityPerPacket.setFocusable(true);
            quantityPerPacket.setEnabled(true);
            quantityPerPacket.setFocusableInTouchMode(true);

            cigarettesSmokedPerDay.setFocusable(true);
            cigarettesSmokedPerDay.setEnabled(true);
            cigarettesSmokedPerDay.setFocusableInTouchMode(true);

            lastname.requestFocus();
        } else {
            saveChanges(
                    lastname.getText().toString(),
                    firstname.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString(),
                    brand.getText().toString(),
                    Double.parseDouble(packetPrice.getText().toString()),
                    Integer.parseInt(quantityPerPacket.getText().toString()),
                    Integer.parseInt(cigarettesSmokedPerDay.getText().toString())
            );
            lastname.setFocusable(false);
            lastname.setEnabled(false);

            firstname.setFocusable(false);
            firstname.setEnabled(false);

            email.setFocusable(false);
            email.setEnabled(false);

            password.setFocusable(false);
            password.setEnabled(false);

            brand.setFocusable(false);
            brand.setEnabled(false);

            packetPrice.setFocusable(false);
            packetPrice.setEnabled(false);

            quantityPerPacket.setFocusable(false);
            quantityPerPacket.setEnabled(false);

            cigarettesSmokedPerDay.setFocusable(false);
            cigarettesSmokedPerDay.setEnabled(false);
        }
        isEditable = !isEditable;
    }

    private void initiateView() {
        isEditable = false;

        button_next_homeActivity = (Button) findViewById(R.id.button_next_homeActivity) ;
        button_next_homeActivity.setEnabled(false);
        button_next_homeActivity.setVisibility(View.GONE);

        fillup = (TextView) findViewById(R.id.fill_up_textview);
        lastname = (EditText) findViewById(R.id.edit_lastname_input);
        firstname = (EditText) findViewById(R.id.edit_firstname_input);
        email = (EditText) findViewById(R.id.edit_email_input);
        password = (EditText) findViewById(R.id.edit_password_input);
        brand = (EditText) findViewById(R.id.edit_brand_input);
        packetPrice = (EditText) findViewById(R.id.edit_packetprice_input);
        quantityPerPacket = (EditText) findViewById(R.id.edit_quantityperpacket_input);
        cigarettesSmokedPerDay = (EditText) findViewById(R.id.edit_smokeperday_input);

        fillup.setText("Your information");

        lastname.setFocusable(false);
        lastname.setEnabled(false);

        firstname.setFocusable(false);
        firstname.setEnabled(false);

        email.setFocusable(false);
        email.setEnabled(false);

        password.setFocusable(false);
        password.setEnabled(false);

        brand.setFocusable(false);
        brand.setEnabled(false);

        packetPrice.setFocusable(false);
        packetPrice.setEnabled(false);

        quantityPerPacket.setFocusable(false);
        quantityPerPacket.setEnabled(false);

        cigarettesSmokedPerDay.setFocusable(false);
        cigarettesSmokedPerDay.setEnabled(false);
    }

    private void saveChanges(String lastName, String firstName, String email, String password, String brand, double packetPrice, int quantityPerPacket, int cigarettesSmokedPerDay) {

        user.setUser_last_name(lastName);
        user.setUser_first_name(firstName);
        user.setUser_email(email);
        user.setUser_password(password);
        user.setUser_brand(brand);
        user.setUser_packet_price(packetPrice);
        user.setUser_quantity_per_packet(quantityPerPacket);
        user.setUser_smoke_per_day_limit(cigarettesSmokedPerDay);

        viewModel.updateUser(user, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateClient: success");
                setResponse(true);
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateClient: failure", e);
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
        if (user!=null) {
            firstname.setText(user.getUser_first_name());
            lastname.setText(user.getUser_last_name());
            email.setText(user.getUser_email());
            password.setText(user.getUser_password());
            brand.setText(user.getUser_brand());
            packetPrice.setText(Double.toString( user.getUser_packet_price()));
            quantityPerPacket.setText(Integer.toString(user.getUser_quantity_per_packet()));
            cigarettesSmokedPerDay.setText(Integer.toString(user.getUser_smoke_per_day_limit()));
        }
    }
}

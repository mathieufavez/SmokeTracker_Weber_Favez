package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.User.UserViewModel;

public class ModifyInfoActivity extends AppCompatActivity {

    private static final String TAG = "ModifyInfoActivity";

    private EditText modify_lastName;
    private EditText modify_firstname;
    private EditText modify_email;
    private EditText modify_password;
    private EditText modify_brand;
    private EditText modify_packetprice;
    private EditText modify_quantityperpacket;
    private EditText modify_smokeperday;

    private Button button_confirm_modification;

    private UserViewModel viewModel;

    private UserEntity user;

    private Toast statusToast;

    private boolean isEditable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);

        String userEmailLogged = getIntent().getStringExtra("userLoggedEmail");

        initiateView();

        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(),userEmailLogged);
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        viewModel.getUser().observe(this, userEntity -> {
            if (userEntity!=null) {
                user = userEntity;
                updateContent();
            }
        });


        button_confirm_modification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges(
                        modify_lastName.getText().toString(),
                        modify_firstname.getText().toString(),
                        modify_email.getText().toString(),
                        modify_password.getText().toString(),
                        modify_brand.getText().toString(),
                        Double.parseDouble(modify_packetprice.getText().toString()),
                        Integer.parseInt(modify_quantityperpacket.getText().toString()),
                        Integer.parseInt(modify_smokeperday.getText().toString()));
            }
        });
    }

    public void initiateView(){
        modify_lastName = findViewById(R.id.modify_lastname_input);
        modify_firstname = findViewById(R.id.modify_firstname_input);
        modify_email = findViewById(R.id.modify_email_input);
        modify_password = findViewById(R.id.modify_password_input);
        modify_brand = findViewById(R.id.modify_brand_input);
        modify_packetprice = findViewById(R.id.modify_packetprice_input);
        modify_quantityperpacket = findViewById(R.id.modify_quantityperpacket_input);
        modify_smokeperday = findViewById(R.id.modify_smokeperday_input);

        button_confirm_modification = findViewById(R.id.button_confirm_modification);

        modify_lastName.setFocusable(true);
        modify_lastName.setEnabled(true);
        modify_lastName.setFocusableInTouchMode(true);

        modify_firstname.setFocusable(true);
        modify_firstname.setEnabled(true);
        modify_firstname.setFocusableInTouchMode(true);

        modify_email.setFocusable(true);
        modify_email.setEnabled(true);
        modify_email.setFocusableInTouchMode(true);

        modify_password.setFocusable(true);
        modify_password.setEnabled(true);
        modify_password.setFocusableInTouchMode(true);

        modify_brand.setFocusable(true);
        modify_brand.setEnabled(true);
        modify_brand.setFocusableInTouchMode(true);

        modify_packetprice.setFocusable(true);
        modify_packetprice.setEnabled(true);
        modify_packetprice.setFocusableInTouchMode(true);

        modify_quantityperpacket.setFocusable(true);
        modify_quantityperpacket.setEnabled(true);
        modify_quantityperpacket.setFocusableInTouchMode(true);

        modify_smokeperday.setFocusable(true);
        modify_smokeperday.setEnabled(true);
        modify_smokeperday.setFocusableInTouchMode(true);

        modify_lastName.requestFocus();

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
                Log.d(TAG, "modifyClient: success");
                setResponse(true);
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "modifyClient: failure", e);
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(this, "Edited", Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG);
            statusToast.show();
        }
    }

    private void updateContent() {
        if (user!=null) {
            modify_lastName.setText(user.getUser_last_name());
            modify_firstname.setText(user.getUser_first_name());
            modify_email.setText(user.getUser_email());
            modify_password.setText(user.getUser_password());
            modify_brand.setText(user.getUser_brand());
            modify_packetprice.setText(Double.toString( user.getUser_packet_price()));
            modify_quantityperpacket.setText(Integer.toString(user.getUser_quantity_per_packet()));
            modify_smokeperday.setText(Integer.toString(user.getUser_smoke_per_day_limit()));
        }
    }
}

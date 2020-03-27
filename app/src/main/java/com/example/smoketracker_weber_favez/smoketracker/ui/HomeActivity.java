package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.UserViewModel;


public class HomeActivity extends AppCompatActivity {
    private Button mNextButton;

    private static final String TAG = "HomeActivity";

    private UserEntity user;

    private EditText lastname, firstname, email, password, brand, packetPrice, quantityPerPacket, cigarettesSmokedPerDay;

    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        updateContent();

        mNextButton = (Button) findViewById(R.id.button_next_homeActivity);

        lastname = (EditText) findViewById(R.id.edit_lastname_input);
        firstname = (EditText) findViewById(R.id.edit_firstname_input);
        email = (EditText) findViewById(R.id.edit_email_input);
        password = (EditText) findViewById(R.id.edit_password_input);
        brand = (EditText) findViewById(R.id.edit_brand_input);
        packetPrice = (EditText) findViewById(R.id.edit_packetprice_input);
        quantityPerPacket = (EditText) findViewById(R.id.edit_quantityperpacket_input);
        cigarettesSmokedPerDay = (EditText) findViewById(R.id.edit_smokeperday_input);

        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), email.getText().toString());
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        viewModel.getUser().observe(this, userEntity -> {
            if (userEntity != null) {
                user = userEntity;
                updateContent();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCreateUser();
            }
        });
    }

    private void attemptCreateUser() {
        // Reset errors.
        email.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordS) && !isPasswordValid(passwordS)) {
            password.setError("This password is too short (<5 characters");
            password.setText("");
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailS)) {
            email.setError("This field is required");
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(emailS)) {
            email.setError("Invalid email address");
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            createUser(
                    lastname.getText().toString(),
                    firstname.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString(),
                    brand.getText().toString(),
                    Double.parseDouble(packetPrice.getText().toString()),
                    Integer.parseInt(quantityPerPacket.getText().toString()),
                    Integer.parseInt(cigarettesSmokedPerDay.getText().toString()));
            Intent intent = new Intent(HomeActivity.this, TrackingActivity.class);
            startActivity(intent);
        }
    }


    private void createUser(String lastName, String firstName, String email, String password, String brand, double packetPrice, int quantityPerPacket, int cigarettesSmokedPerDay) {

        user = new UserEntity();
        user.setUser_last_name(lastName);
        user.setUser_first_name(firstName);
        user.setUser_email(email);
        user.setUser_password(password);
        user.setUser_brand(brand);
        user.setUser_packet_price(packetPrice);
        user.setUser_quantity_per_packet(quantityPerPacket);
        user.setUser_smoke_per_day_limit(cigarettesSmokedPerDay);

        viewModel.createUser(user, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createClient: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createClient: failure", e);
            }
        });
    }
    private void updateContent() {
        if (user != null) {
            firstname.setText(user.getUser_first_name());
            lastname.setText(user.getUser_last_name());
            email.setText(user.getUser_email());
            password.setText(user.getUser_password());
            brand.setText(user.getUser_brand());
            packetPrice.setText((int) user.getUser_packet_price());
            quantityPerPacket.setText(user.getUser_quantity_per_packet());
            cigarettesSmokedPerDay.setText(user.getUser_smoke_per_day_limit());
        }
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }
}

package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.UserViewModel;

public class Login_activity extends AppCompatActivity {

    private Button buttonConnect;

    private EditText emailLogin;
    private EditText passwordLogin;

    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        emailLogin = (EditText) findViewById(R.id.edit_text_email_connection) ;
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

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordLogin.setError("This password is too short (<5 characters");
            passwordLogin.setText("");
            focusView = passwordLogin;
            cancel = true;
        }

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
            UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), email);
            viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
            viewModel.getUser().observe(this, userEntity -> {
                if (userEntity != null){
                    if (userEntity.getUser_password().equals(password)) {
                        SharedPreferences.Editor editor = getSharedPreferences("SharedPrefs", 0).edit();
                        editor.putString("LoggedIn", userEntity.getUser_email());
                        editor.apply();

                        Intent intent = new Intent(Login_activity.this, TrackingActivity.class);
                        startActivity(intent);
                        emailLogin.setText("");
                        passwordLogin.setText("");

                    } else {
                        passwordLogin.setError("This password is incorrect");
                        passwordLogin.requestFocus();
                        passwordLogin.setText("");
                    }
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

}
package com.example.smoketracker_weber_favez.smoketracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.CreateUser;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.User;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.UserListViewModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.UserViewModel;

import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private UserViewModel viewModel;
    private User user;

    private Button mNextButton;

    private EditText lastname, firstname, email, brand, packetPrice, quantityPerPacket, cigarettesSmokedPerDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lastname = (EditText) findViewById(R.id.edit_lastname_input);
        firstname = (EditText) findViewById(R.id.edit_firstname_input);
        email = (EditText) findViewById(R.id.edit_email_input);
        brand = (EditText) findViewById(R.id.edit_brand_input);
        packetPrice = (EditText) findViewById(R.id.edit_packetprice_input);
        quantityPerPacket = (EditText) findViewById(R.id.edit_quantityperpacket_input);
        cigarettesSmokedPerDay = (EditText) findViewById(R.id.edit_smokeperday_input);

        mNextButton = (Button) findViewById(R.id.button_next_homeActivity);

        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), email.toString());
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        viewModel.getClient().observe(this, userEntity -> {
            if (userEntity != null) {
                user = userEntity;
                updateContent();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if the fields are empty or not
                if (isEmpty(lastname) || isEmpty(firstname) || isEmpty(email)|| isEmpty(brand) || isEmpty(packetPrice) || isEmpty(quantityPerPacket) || isEmpty(cigarettesSmokedPerDay)) {
                   Toast.makeText(HomeActivity.this, "Please fill up all the fields", Toast.LENGTH_LONG).show();
                }
                else{
                //Create the new User and launch the next activity
                    User newUser = new User (lastname.getText().toString(),firstname.getText().toString(),email.getText().toString(),brand.getText().toString(),Double.parseDouble(packetPrice.toString()),Integer.parseInt(quantityPerPacket.toString()),Integer.parseInt(cigarettesSmokedPerDay.toString()));
                    new CreateUser(getApplication(), new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "createUserWithEmail: success");
                            Intent intent = new Intent(HomeActivity.this, TrackingActivity.class);
                            startActivity(intent);
                        }
                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "createUserWithEmail: failure", e);
                        }
                    }).execute(newUser);

                }
            }
        });
    }

    //Method to see if a editText is empty. Return true if it is empty
    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    private void updateContent() {
        if (user != null) {
            firstname.setText(user.getUser_first_name());
            lastname.setText(user.getUser_last_name());
            email.setText(user.getUser_email());
        }
    }


}

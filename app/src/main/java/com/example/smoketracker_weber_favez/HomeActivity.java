package com.example.smoketracker_weber_favez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    private Button mNextButton;

    private EditText lastname, firstname, brand, packetPrice, quantityPerPacket, cigarettesSmokedPerDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lastname = (EditText) findViewById(R.id.edit_lastname_input);
        firstname = (EditText) findViewById(R.id.edit_firstname_input);
        brand = (EditText) findViewById(R.id.edit_brand_input);
        packetPrice = (EditText) findViewById(R.id.edit_packetprice_input);
        quantityPerPacket = (EditText) findViewById(R.id.edit_quantityperpacket_input);
        cigarettesSmokedPerDay = (EditText) findViewById(R.id.edit_smokeperday_input);

        mNextButton = (Button) findViewById(R.id.button_next_homeActivity);


        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (isEmpty(lastname) || isEmpty(firstname) || isEmpty(brand) || isEmpty(packetPrice) || isEmpty(quantityPerPacket) || isEmpty(cigarettesSmokedPerDay)) {
                   Toast.makeText(HomeActivity.this, "Please fill up all the fields", Toast.LENGTH_LONG).show();
                //}
               // else{
                    Intent trackingActivityIntent = new Intent(HomeActivity.this, TrackingActivity.class);
                    startActivity(trackingActivityIntent);
               // }

            }
        });
    }

    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }
}

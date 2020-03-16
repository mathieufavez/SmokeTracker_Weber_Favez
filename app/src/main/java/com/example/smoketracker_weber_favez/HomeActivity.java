package com.example.smoketracker_weber_favez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smoketracker_weber_favez.db.AppDatabase;
import com.example.smoketracker_weber_favez.db.User;


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
                //Check if the fields are empty or not
                if (isEmpty(lastname) || isEmpty(firstname) || isEmpty(brand) || isEmpty(packetPrice) || isEmpty(quantityPerPacket) || isEmpty(cigarettesSmokedPerDay)) {
                   Toast.makeText(HomeActivity.this, "Please fill up all the fields", Toast.LENGTH_LONG).show();

                    readSQL(firstname,lastname,brand,packetPrice,quantityPerPacket,cigarettesSmokedPerDay);




                }
                else{
                //Launch the next activity
                    Intent trackingActivityIntent = new Intent(HomeActivity.this, TrackingActivity.class);
                    startActivity(trackingActivityIntent);
                }
            }
        });
    }

    //Method to see if a editText is empty. Return true if it is empty
    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    private void readSQL(EditText firstname, EditText lastname, EditText brand, EditText packetPrice, EditText quantityPerPacket, EditText cigarettesSmokedPerDay){
        AppDatabase db = AppDatabase.getAppDatabase(this);
        User user = new User(firstname.getText().toString(),lastname.getText().toString(),brand.getText().toString(),Float.parseFloat(packetPrice.getText().toString()),Integer.parseInt(quantityPerPacket.getText().toString()),Integer.parseInt(cigarettesSmokedPerDay.getText().toString()));

        //Test
                   /*user.setUser_first_name(firstname.getText().toString());
                   user.setUser_last_name(lastname.getText().toString());
                   user.setUser_brand(brand.getText().toString());
                   user.setUser_packet_price(Float.parseFloat(packetPrice.getText().toString()));
                   user.setUser_quantity_per_packet(Integer.parseInt(quantityPerPacket.getText().toString()));
                   */

        db.userDao().addUser(new User("Mathieu","Favez","Winston bleu",8.2,10,10));
    }
}

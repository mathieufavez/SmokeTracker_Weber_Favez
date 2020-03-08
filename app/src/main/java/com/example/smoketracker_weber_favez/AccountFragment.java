package com.example.smoketracker_weber_favez;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    private Button modifyButton;
    private Button startOverButton;
    private Button modifyTablesButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        modifyButton = (Button) view.findViewById(R.id.button_modify);


        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent homeActivityIntent = new Intent(getContext(), HomeActivity.class);
                startActivity(homeActivityIntent);
                // }

            }
        });
        return view;
    }
}



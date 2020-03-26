package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;

public class AccountFragment extends Fragment {
    AppDatabase db;
    private Button modifyButton;
    private Button startOverButton;
    private Button modifyTablesButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        modifyButton = (Button) view.findViewById(R.id.button_modify);
        startOverButton = (Button) view.findViewById(R.id.button_start_over);
        modifyTablesButton = (Button) view.findViewById(R.id.button_modify_tables);

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent homeActivityIntent = new Intent(getContext(), HomeActivity.class);
                startActivity(homeActivityIntent);
            }
        });

        startOverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent startOverIntent = new Intent(getContext(), AccountFragment.class);
                startActivity(startOverIntent);
            }
        });

        modifyTablesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent modifyTablesIntent = new Intent(getContext(), ModifyTablesActivity.class);
                startActivity(modifyTablesIntent);
            }
        });
        return view;

    }
}



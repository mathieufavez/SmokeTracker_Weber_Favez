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

public class AccountFragment extends Fragment {
    private Button modifyButton;
    private Button modifyTablesButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        modifyButton = (Button) view.findViewById(R.id.button_modify);
        modifyTablesButton = (Button) view.findViewById(R.id.button_modify_tables);

        String userEmailLogged = getActivity().getIntent().getStringExtra("loggedUserEmail");


        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the next activity
                Intent homeActivityIntent = new Intent(getContext(), ModifyInfoActivity.class);
                homeActivityIntent.putExtra("userLoggedEmail",userEmailLogged);
                startActivity(homeActivityIntent);
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



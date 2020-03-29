package com.example.smoketracker_weber_favez.smoketracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayListOneUserViewEmailModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayListOneUserViewModel;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.User.UserViewModel;

import java.util.ArrayList;
import java.util.List;


public class TrackingFragment extends Fragment {
    private TextView day_textView;
    private TextView x_cigarettes_left_textView;
    private TextView smoked_value;
    private TextView craved_value;
    private TextView money_spent_value;

    private Button button_i_smoked;
    private Button button_i_craved;

    private UserViewModel userViewModel;
    private DayListOneUserViewEmailModel dayViewModel;

    private UserEntity user;
    private DayEntity day;

    private List<DayEntity> days ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracking, container, false);

        initiateView(view);

        days = new ArrayList<>();

        String userEmailLogged = getActivity().getIntent().getStringExtra("loggedUserEmail");

        UserViewModel.Factory factory = new UserViewModel.Factory(getActivity().getApplication(),userEmailLogged);
        userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        userViewModel.getUser().observe(this, userEntity -> {
            if (userEntity!=null) {
                user = userEntity;
                declareDayFactory(userEntity.getId());
            }
        });

        return view;
    }

    private void declareDayFactory(int userId){
        DayListOneUserViewEmailModel.Factory factoryDay = new DayListOneUserViewEmailModel.Factory(getActivity().getApplication(),user.getUser_email());
        dayViewModel = ViewModelProviders.of(this, factoryDay).get(DayListOneUserViewEmailModel.class);
        dayViewModel.getAllDaysForOneUser().observe(this, dayEntities -> {
            if (dayEntities!=null) {
                days = dayEntities;

                days.get(days.size()-1).getDay_number();
                day_textView.setText("Day "+  Integer.toString(days.get(days.size()-1).getDay_number()));
                x_cigarettes_left_textView.setText(Integer.toString(user.getUser_smoke_per_day_limit()));
                smoked_value.setText(Integer.toString(days.get(days.size()-1).getCigarettes_smoked_per_day()));
                craved_value.setText(Integer.toString(days.get(days.size()-1).getCigarettes_craved_per_day()));
                money_spent_value.setText(Double.toString(days.get(days.size()-1).getMoney_saved_per_day()));
            }
        });
    }

    private void initiateView(View view) {
        button_i_smoked = (Button) view.findViewById(R.id.button_i_smoked) ;
        button_i_craved = (Button) view.findViewById(R.id.button_i_craved) ;

        day_textView = (TextView)  view.findViewById(R.id.day_textView) ;
        x_cigarettes_left_textView = (TextView)  view.findViewById(R.id.x_cigarettes_left_textView) ;
        smoked_value = (TextView)  view.findViewById(R.id.smoked_value) ;
        craved_value = (TextView)  view.findViewById(R.id.craved_value) ;
        money_spent_value = (TextView)  view.findViewById(R.id.chf_spent_value) ;
    }
}

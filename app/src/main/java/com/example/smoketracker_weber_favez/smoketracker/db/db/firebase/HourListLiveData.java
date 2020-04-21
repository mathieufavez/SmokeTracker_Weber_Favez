package com.example.smoketracker_weber_favez.smoketracker.db.db.firebase;

import android.util.Log;

import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.HourEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class HourListLiveData extends LiveData<List<HourEntity>> {

    private static final String TAG = "HourListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public HourListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toHourList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<HourEntity> toHourList(DataSnapshot snapshot) {
        List<HourEntity> hours = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            HourEntity entity = childSnapshot.getValue(HourEntity.class);
            entity.setId(childSnapshot.getKey());
            hours.add(entity);
        }
        return hours;
    }
}


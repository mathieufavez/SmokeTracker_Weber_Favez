package com.example.smoketracker_weber_favez.smoketracker.db.db.firebase;

import android.util.Log;

import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;


public class DayLiveData extends LiveData<DayEntity> {

    private static final String TAG = "DayLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public DayLiveData(DatabaseReference ref) {
        this.reference = ref;
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
            if (dataSnapshot.exists()) {
                DayEntity entity = dataSnapshot.getValue(DayEntity.class);
                entity.setId(dataSnapshot.getKey());
                setValue(entity);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}

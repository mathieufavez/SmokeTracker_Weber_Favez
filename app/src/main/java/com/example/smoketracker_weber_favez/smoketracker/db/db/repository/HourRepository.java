package com.example.smoketracker_weber_favez.smoketracker.db.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.HourEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.firebase.HourListLiveData;
import com.example.smoketracker_weber_favez.smoketracker.db.db.firebase.HourLiveData;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HourRepository {
    private static HourRepository instance;

    private HourRepository() {}

    public static HourRepository getInstance() {
        if (instance == null) {
            synchronized (HourRepository.class) {
                if (instance == null) {
                    instance = new HourRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<HourEntity>> getAllHoursForOneDay(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("hours");
        return new HourListLiveData(reference);
    }

    public LiveData<List<HourEntity>> getAllHours() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("hours");
        return new HourListLiveData(reference);
    }


    public LiveData<HourEntity> getOneHour(final String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("hours")
                .child(id);
        return new HourLiveData(reference);
    }

    /*public void insert(final HourEntity hour, OnAsyncEventListener callback, Context context) {
        new CreateHour(context,callback).execute(hour);
    }*/

    public void insert(final HourEntity hour, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("hours").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("hours")
                .child(id)
                .setValue(hour, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


   /* public void update(final HourEntity hour, OnAsyncEventListener callback, Context context) {
        new UpdateHour(context, callback).execute(hour);
    }*/

    public void update(final HourEntity hour, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("hours")
                .child(hour.getId())
                .updateChildren(hour.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


    /*public void delete(final HourEntity hour, OnAsyncEventListener callback, Context context) {
        new DeleteHour(context, callback).execute(hour);
    }*/

    public void delete(final HourEntity hour, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("hours")
                .child(hour.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


}

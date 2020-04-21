package com.example.smoketracker_weber_favez.smoketracker.db.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.firebase.DayListLiveData;
import com.example.smoketracker_weber_favez.smoketracker.db.db.firebase.DayLiveData;
import com.example.smoketracker_weber_favez.smoketracker.db.db.firebase.UserListLiveData;
import com.example.smoketracker_weber_favez.smoketracker.db.db.firebase.UserLiveData;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

public class DayRepository {
    private static DayRepository instance;

        private DayRepository() {}

        public static DayRepository getInstance() {
            if (instance == null) {
                synchronized (DayRepository.class) {
                    if (instance == null) {
                        instance = new DayRepository();
                    }
                }
            }
            return instance;
        }

        public LiveData<DayEntity> getOneDay(final String idUser, final String idDay){
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(idUser)
                    .child("days")
                    .child(idDay);
            return new DayLiveData(reference);
        }

        public LiveData<DayEntity> getOneDayWithEmail(final String email){
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference("days")
                    .child(email);
            return new DayLiveData(reference);
        }

        public LiveData<DayEntity> getOneDaySpecific(String idUser, String dayId){
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(idUser)
                    .child("days")
                    .child(dayId);
            return new DayLiveData(reference);
        }

   public LiveData<List<DayEntity>> getAllDays() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("days");
        return new DayListLiveData(reference);
    }

    public LiveData<List<DayEntity>> getAllDaysForOneUser(final String id) {
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .child("days");
            return new DayListLiveData(reference);
        }

      /* public LiveData<List<DayEntity>> getAllDaysForOneUser(final String email) {
            return AppDatabase.getInstance(context).dayDao().getAllDaysForOneUser(email);
        }*/

       /* public void insert(final DayEntity day, OnAsyncEventListener callback) {
            new CreateDay(context,callback).execute(day);
        }*/

        public void insert(final DayEntity day, String userId, final OnAsyncEventListener callback) {
            String id = FirebaseDatabase.getInstance().getReference("days").push().getKey();
            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(userId)
                    .child("days")
                    .child(id)
                    .setValue(day, (databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            callback.onFailure(databaseError.toException());
                        } else {
                            callback.onSuccess();
                        }
                    });
        }


        /*public void update(final DayEntity day, OnAsyncEventListener callback, Context context) {
            new UpdateDay(context, callback).execute(day);
        }*/

        public void update(final DayEntity day, String userId, String dayId, final OnAsyncEventListener callback) {
            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(userId)
                    .child("days")
                    .child(dayId)
                    .updateChildren(day.toMap(), (databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            callback.onFailure(databaseError.toException());
                        } else {
                            callback.onSuccess();
                        }
                    });
        }


       /* public void delete(final DayEntity day, OnAsyncEventListener callback, Context context) {
            new DeleteDay(context, callback).execute(day);
        }
        */

        public void delete(final DayEntity day, OnAsyncEventListener callback) {
            FirebaseDatabase.getInstance()
                    .getReference("days")
                    .child(day.getId())
                    .removeValue((databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            callback.onFailure(databaseError.toException());
                        } else {
                            callback.onSuccess();
                        }
                    });
        }
}

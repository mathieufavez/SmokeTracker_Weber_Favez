package com.example.smoketracker_weber_favez.smoketracker.db.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.Day.CreateDay;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.Day.DeleteDay;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.Day.UpdateDay;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;

import java.util.List;

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

        public LiveData<DayEntity> getOneDay(final int id, Context context){
            return AppDatabase.getInstance(context).dayDao().getOneDay(id);
        }

        public LiveData<DayEntity> getOneDayWithEmail(final String email, Context context){
            return AppDatabase.getInstance(context).dayDao().getOneDay(email);
        }

        public LiveData<DayEntity> getOneDaySpecific(String userEmail, int dayId, Context context){
                return AppDatabase.getInstance(context).dayDao().getOneDaySpecific(userEmail, dayId);
        }

        public LiveData<List<DayEntity>> getAllDays(Context context) {
            return AppDatabase.getInstance(context).dayDao().getAllDays();
        }
        public LiveData<List<DayEntity>> getAllDaysForOneUser(final int id, Context context) {
            return AppDatabase.getInstance(context).dayDao().getAllDaysForOneUser(id);
        }

        public LiveData<List<DayEntity>> getAllDaysForOneUser(final String email, Context context) {
            return AppDatabase.getInstance(context).dayDao().getAllDaysForOneUser(email);
        }

        public void insert(final DayEntity day, OnAsyncEventListener callback, Context context) {
            new CreateDay(context,callback).execute(day);
        }

        public void update(final DayEntity day, OnAsyncEventListener callback, Context context) {
            new UpdateDay(context, callback).execute(day);
        }

        public void delete(final DayEntity day, OnAsyncEventListener callback, Context context) {
            new DeleteDay(context, callback).execute(day);
        }
}

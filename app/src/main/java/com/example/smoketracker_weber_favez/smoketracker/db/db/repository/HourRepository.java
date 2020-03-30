package com.example.smoketracker_weber_favez.smoketracker.db.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.Hour.CreateHour;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.Hour.DeleteHour;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.Hour.UpdateHour;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.HourEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;

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

    public LiveData<List<HourEntity>> getAllHoursForOneDay(final int id, Context context) {
        return AppDatabase.getInstance(context).hourDao().getAllHoursForADay(id);
    }

    public LiveData<List<HourEntity>> getAllHours(Context context) {
        return AppDatabase.getInstance(context).hourDao().getAllHours();
    }


    public LiveData<HourEntity> getOneHour(final int id, Context context){
        return AppDatabase.getInstance(context).hourDao().getOneHour(id);
    }

    public void insert(final HourEntity hour, OnAsyncEventListener callback, Context context) {
        new CreateHour(context,callback).execute(hour);
    }

    public void update(final HourEntity hour, OnAsyncEventListener callback, Context context) {
        new UpdateHour(context, callback).execute(hour);
    }

    public void delete(final HourEntity hour, OnAsyncEventListener callback, Context context) {
        new DeleteHour(context, callback).execute(hour);
    }


}

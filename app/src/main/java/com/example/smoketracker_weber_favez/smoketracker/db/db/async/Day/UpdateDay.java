package com.example.smoketracker_weber_favez.smoketracker.db.db.async.Day;

import android.content.Context;
import android.os.AsyncTask;

import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;

public class UpdateDay extends AsyncTask<DayEntity, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateDay(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(DayEntity... params) {
        try {
            for (DayEntity day : params)
                database.dayDao().update(day);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}

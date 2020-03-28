package com.example.smoketracker_weber_favez.smoketracker.db.db.async.User;
import android.content.Context;
import android.os.AsyncTask;

import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;

public class DeleteUser extends AsyncTask<UserEntity, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteUser(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(UserEntity... params) {
        try {
            for (UserEntity user : params)
                database.userDao().delete(user);
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

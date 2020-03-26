package com.example.smoketracker_weber_favez.smoketracker.db.db.async;
import android.content.Context;
import android.os.AsyncTask;

import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.User;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;

public class CreateUser extends AsyncTask<User, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateUser(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(User... params) {
        try {
            for (User client : params)
                database.userDao().insert(client);
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

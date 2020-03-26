package com.example.smoketracker_weber_favez.smoketracker.db.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.CreateUser;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.DeleteUser;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.UpdateUser;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.User;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;

import java.util.List;

public class UserRepository {

    private static UserRepository instance;

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }


    public LiveData<User> getUser(final String email, Context context){
        return AppDatabase.getInstance(context).userDao().getByEmail(email);
    }
    public LiveData<List<User>> getAllUsers(Context context) {
        return AppDatabase.getInstance(context).userDao().getAll();
    }

    public void insert(final User user, OnAsyncEventListener callback,Context context) {
        new CreateUser(context,callback).execute(user);
    }

    public void update(final User client, OnAsyncEventListener callback, Context context) {
        new UpdateUser(context, callback).execute(client);
    }

    public void delete(final User client, OnAsyncEventListener callback, Context context) {
        new DeleteUser(context, callback).execute(client);
    }}
package com.example.smoketracker_weber_favez.smoketracker.db.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.User.CreateUser;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.User.DeleteUser;
import com.example.smoketracker_weber_favez.smoketracker.db.db.async.User.UpdateUser;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;

import java.util.List;

public class UserRepository{
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


    public LiveData<UserEntity> getUser(final String email, Context context){
        return AppDatabase.getInstance(context).userDao().getByEmail(email);
    }
    public LiveData<List<UserEntity>> getAllUsers(Context context) {
        return AppDatabase.getInstance(context).userDao().getAll();
    }

    public void insert(final UserEntity user, OnAsyncEventListener callback, Context context) {
        new CreateUser(context,callback).execute(user);
    }

    public void update(final UserEntity user, OnAsyncEventListener callback, Context context) {
        new UpdateUser(context, callback).execute(user);
    }

    public void delete(final UserEntity user, OnAsyncEventListener callback, Context context) {
        new DeleteUser(context, callback).execute(user);
    }
}

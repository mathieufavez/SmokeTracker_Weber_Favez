package com.example.smoketracker_weber_favez.smoketracker.db.db.dao;


import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;

import java.util.List;


@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email")
    LiveData<UserEntity> getByEmail(String email);

    @Query("SELECT * FROM users")
    LiveData<List<UserEntity>> getAll();

    @Insert
    void insert(UserEntity user) throws SQLiteConstraintException;

    @Update
    void update(UserEntity client);

    @Delete
    void delete(UserEntity client);

    @Query("DELETE FROM users")
    void deleteAll();
}
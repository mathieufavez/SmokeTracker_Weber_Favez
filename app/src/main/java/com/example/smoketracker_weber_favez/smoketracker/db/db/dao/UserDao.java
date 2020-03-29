package com.example.smoketracker_weber_favez.smoketracker.db.db.dao;


import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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

    @Query("SELECT id FROM users WHERE email= :email")
    int getIdUserByEmail(String email);

    @Insert
    long insert(UserEntity user) throws SQLiteConstraintException;

    @Update
    void update(UserEntity user);

    @Delete
    void delete(UserEntity user);

    @Query("DELETE FROM users")
    void deleteAll();
}
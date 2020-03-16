package com.example.smoketracker_weber_favez.db;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DayDao {

    @Insert
    void insertAll(Day... days) throws SQLiteConstraintException;
}

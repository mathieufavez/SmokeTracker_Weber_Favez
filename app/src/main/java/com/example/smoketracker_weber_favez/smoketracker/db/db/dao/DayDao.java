package com.example.smoketracker_weber_favez.smoketracker.db.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.Day;

@Dao
public interface DayDao {

    @Insert
    void insertAll(Day... days) throws SQLiteConstraintException;
}

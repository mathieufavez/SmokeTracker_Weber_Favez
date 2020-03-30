package com.example.smoketracker_weber_favez.smoketracker.db.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.HourEntity;

import java.util.List;

@Dao
public interface HourDao {

    @Insert
    void insert(HourEntity hour)throws SQLiteConstraintException;

    @Query("SELECT * FROM hours WHERE id = :id")
    LiveData<HourEntity> getOneHour(int id);

    @Query("SELECT * FROM hours WHERE idDay=:idDay")
    LiveData<List<HourEntity>> getAllHoursForADay(int idDay);

    @Query("SELECT * FROM hours")
    LiveData<List<HourEntity>> getAllHours();

    @Update
    void update(HourEntity hour);

    @Delete
    void delete(HourEntity hour);
}



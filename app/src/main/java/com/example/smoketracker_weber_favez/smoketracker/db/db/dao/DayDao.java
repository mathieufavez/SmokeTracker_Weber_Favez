package com.example.smoketracker_weber_favez.smoketracker.db.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;

import java.util.List;

@Dao
public interface DayDao {
    @Query("SELECT * FROM days WHERE id = :id")
    LiveData<DayEntity> getOneDay(int id);

    @Query("SELECT * FROM days")
    LiveData<List<DayEntity>> getAllDays();

    @Query("SELECT * FROM days WHERE id=:userId")
    LiveData<List<DayEntity>> getAllDaysForOneUser(int userId);

    @Insert
    void insert(DayEntity day)throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DayEntity> days);

    @Update
    void update(DayEntity day);

    @Delete
    void delete(DayEntity day);

    @Query("DELETE FROM days")
    void deleteAll();
}

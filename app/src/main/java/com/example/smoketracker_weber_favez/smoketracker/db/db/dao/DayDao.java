package com.example.smoketracker_weber_favez.smoketracker.db.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;

import java.util.List;

@Dao
public interface DayDao {
    @Query("SELECT * FROM days WHERE id = :id")
    LiveData<DayEntity> getOneDay(int id);

    @Query("SELECT * FROM days WHERE userEmail = :userEmail")
    LiveData<DayEntity> getOneDay(String userEmail);

    @Query("SELECT * FROM days WHERE userEmail = :userEmail AND id= :dayId")
    LiveData<DayEntity> getOneDaySpecific(String userEmail, int dayId);

    @Query("SELECT * FROM days")
    LiveData<List<DayEntity>> getAllDays();

    @Query("SELECT * FROM days WHERE userId=:userId")
    LiveData<List<DayEntity>> getAllDaysForOneUser(int userId);

    @Query("SELECT * FROM days WHERE userEmail=:userEmail")
    LiveData<List<DayEntity>> getAllDaysForOneUser(String userEmail);

    @Query("SELECT cigarettes_smoked_per_day FROM days WHERE userId= :idUser AND id = :idDay")
    int getCigarettesSmokedForADay (int idUser, int idDay);

    @Update
    void updateCigarettesSmoked(DayEntity day);

    @Insert
    void insert(DayEntity day)throws SQLiteConstraintException;

    @Update
    void update(DayEntity day);

    @Delete
    void delete(DayEntity day);

    @Query("DELETE FROM days")
    void deleteAll();
}

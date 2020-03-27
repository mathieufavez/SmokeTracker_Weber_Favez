package com.example.smoketracker_weber_favez.smoketracker.db.db.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "days",
        foreignKeys = @ForeignKey(
                entity = UserEntity.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete =  ForeignKey.CASCADE
        ),
        indices = {
        @Index(
                value = {"userId"})
        })
public class Day {



    @PrimaryKey(autoGenerate = true)
    private int id;

    //Date a la place de String
    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "cigarettes_smoked_per_day")
    private int cigarettes_smoked_per_day;

    @ColumnInfo(name = "cigarettes_craved_per_day")
    private int cigarettes_craved_per_day;

    @ColumnInfo(name = "money_saved_per_day")
    private double money_saved_per_day;

    @ColumnInfo(name = "userId")
    private int userId;


    public Day(Date date, int cigarettes_smoked_per_day, int cigarettes_craved_per_day, double money_saved_per_day, int userId) {
        this.date = date;
        this.cigarettes_smoked_per_day = cigarettes_smoked_per_day;
        this.cigarettes_craved_per_day = cigarettes_craved_per_day;
        this.money_saved_per_day = money_saved_per_day;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCigarettes_smoked_per_day() {
        return cigarettes_smoked_per_day;
    }

    public void setCigarettes_smoked_per_day(int cigarettes_smoked_per_day) {
        this.cigarettes_smoked_per_day = cigarettes_smoked_per_day;
    }

    public int getCigarettes_craved_per_day() {
        return cigarettes_craved_per_day;
    }

    public void setCigarettes_craved_per_day(int cigarettes_craved_per_day) {
        this.cigarettes_craved_per_day = cigarettes_craved_per_day;
    }

    public double getMoney_saved_per_day() {
        return money_saved_per_day;
    }

    public void setMoney_saved_per_day(double money_saved_per_day) {
        this.money_saved_per_day = money_saved_per_day;
    }

}

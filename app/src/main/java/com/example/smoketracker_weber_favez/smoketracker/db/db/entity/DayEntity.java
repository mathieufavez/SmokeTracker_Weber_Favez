package com.example.smoketracker_weber_favez.smoketracker.db.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.smoketracker_weber_favez.smoketracker.db.db.DateConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "days",
        foreignKeys =
        @ForeignKey(
        entity = UserEntity.class,
        parentColumns = "email",
        childColumns = "userEmail",
        onDelete = ForeignKey.CASCADE
),
        indices = {
                @Index(
                        value = {"userId"}
                ),
                @Index(
                value = {"userEmail"}
        )}
)

public class DayEntity implements Comparable{

    @PrimaryKey(autoGenerate = true)
    private int id;

    //Date a la place de String
    @ColumnInfo(name = "date")
    @TypeConverters({DateConverter.class})
    private Date date;

    @ColumnInfo(name = "day_number")
    private int day_number;

    @ColumnInfo(name = "cigarettes_smoked_per_day")
    private int cigarettes_smoked_per_day;

    @ColumnInfo(name = "cigarettes_craved_per_day")
    private int cigarettes_craved_per_day;

    @ColumnInfo(name = "money_saved_per_day")
    private double money_saved_per_day;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "userEmail")
    private String userEmail;

    public DayEntity(Date date, int day_number, int cigarettes_smoked_per_day, int cigarettes_craved_per_day, double money_saved_per_day, int userId, String userEmail) throws ParseException {
        this.date = date;
        this.day_number = day_number;
        this.cigarettes_smoked_per_day = cigarettes_smoked_per_day;
        this.cigarettes_craved_per_day = cigarettes_craved_per_day;
        this.money_saved_per_day = money_saved_per_day;
        this.userId = userId;
        this.userEmail = userEmail;
    }

    @Ignore
    public DayEntity() throws ParseException {
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getDay_number() {
        return day_number;
    }

    public int getCigarettes_smoked_per_day() {
        return cigarettes_smoked_per_day;
    }

    public int getCigarettes_craved_per_day() {
        return cigarettes_craved_per_day;
    }

    public double getMoney_saved_per_day() {
        return money_saved_per_day;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDay_number(int day_number) {
        this.day_number = day_number;
    }

    public void setCigarettes_smoked_per_day(int cigarettes_smoked_per_day) {
        this.cigarettes_smoked_per_day = cigarettes_smoked_per_day;
    }

    public void setCigarettes_craved_per_day(int cigarettes_craved_per_day) {
        this.cigarettes_craved_per_day = cigarettes_craved_per_day;
    }

    public void setMoney_saved_per_day(double money_saved_per_day) {
        this.money_saved_per_day = money_saved_per_day;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return id+ " " + date;
    }


    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

}

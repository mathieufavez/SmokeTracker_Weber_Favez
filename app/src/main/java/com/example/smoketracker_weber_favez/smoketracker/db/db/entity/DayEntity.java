package com.example.smoketracker_weber_favez.smoketracker.db.db.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DayEntity implements Comparable{

    private String id;
    private Date date;
    private int day_number;
    private int cigarettes_smoked_per_day;
    private int cigarettes_craved_per_day;
    private double money_saved_per_day;
    private String userId;
    private String userEmail;

    public DayEntity() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDay_number() {
        return day_number;
    }

    public void setDay_number(int day_number) {
        this.day_number = day_number;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("day_number", day_number);
        result.put("cigarettes_smoked_per_day", cigarettes_smoked_per_day);
        result.put("cigarettes_craved_per_day", cigarettes_craved_per_day);
        result.put("money_saved_per_day", money_saved_per_day);
        result.put("userId", userId);
        result.put("userEmail", userEmail);
        return result;
    }

}

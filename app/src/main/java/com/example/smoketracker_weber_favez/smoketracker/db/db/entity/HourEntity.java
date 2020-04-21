package com.example.smoketracker_weber_favez.smoketracker.db.db.entity;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HourEntity {

    private String id;
    private Date hour;
    private String description;
    private String idDay;

    public HourEntity() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Exclude
    public String getIdDay() {
        return idDay;
    }

    public void setIdDay(String idDay) {
        this.idDay = idDay;
    }

    @Override
    public String toString() {
        return id+ " : " + description +" : "+ hour;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("hour", hour);
        result.put("description", description);
        return result;
    }
}

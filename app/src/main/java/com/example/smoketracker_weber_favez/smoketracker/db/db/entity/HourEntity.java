package com.example.smoketracker_weber_favez.smoketracker.db.db.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.smoketracker_weber_favez.smoketracker.db.db.DateConverter;

import java.util.Date;

@Entity(tableName = "hours",
        foreignKeys =
        @ForeignKey(
                entity = DayEntity.class,
                parentColumns = "id",
                childColumns = "idDay",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(
                        value = {"idDay"}
                )}
)

public class HourEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    //Date a la place de String
    @ColumnInfo(name = "date")
    @TypeConverters({DateConverter.class})
    private Date hour;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "idDay")
    private int idDay;

    public HourEntity(int id, Date hour, String description, int idDay) {
        this.id = id;
        this.hour = hour;
        this.description = description;
        this.idDay = idDay;
    }

    @Ignore
    public HourEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getIdDay() {
        return idDay;
    }

    public void setIdDay(int idDay) {
        this.idDay = idDay;
    }

    @Override
    public String toString() {
        return id+ " : " + description +" : "+ hour;
    }
}

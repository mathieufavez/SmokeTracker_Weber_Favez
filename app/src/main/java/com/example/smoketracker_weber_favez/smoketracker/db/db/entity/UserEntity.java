package com.example.smoketracker_weber_favez.smoketracker.db.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "users", indices = {@Index(value = {"email"}, unique = true)})
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "first_name")
    private String user_first_name;

    @ColumnInfo(name = "last_name")
    private String user_last_name;

    @ColumnInfo(name = "email")
    private String user_email;

    @ColumnInfo(name = "password")
    private String user_password;

    @ColumnInfo(name = "brand")
    private String user_brand;

    @ColumnInfo(name = "packet_price")
    private double user_packet_price;

    @ColumnInfo(name = "quantity_per_packet")
    private int user_quantity_per_packet;

    @ColumnInfo(name = "smoke_per_day_limit")
    private int user_smoke_per_day_limit;


    public UserEntity( String user_first_name, String user_last_name, String user_email,String user_password, String user_brand, double user_packet_price, int user_quantity_per_packet, int user_smoke_per_day_limit) {
        this.user_first_name = user_first_name;
        this.user_last_name = user_last_name;
        this.user_email = user_email;
        this.user_password=user_password;
        this.user_brand = user_brand;
        this.user_packet_price = user_packet_price;
        this.user_quantity_per_packet = user_quantity_per_packet;
        this.user_smoke_per_day_limit = user_smoke_per_day_limit;
    }

    @Ignore
    public UserEntity() {
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_brand() {
        return user_brand;
    }

    public void setUser_brand(String user_brand) {
        this.user_brand = user_brand;
    }

    public double getUser_packet_price() {
        return user_packet_price;
    }

    public void setUser_packet_price(double user_packet_price) {
        this.user_packet_price = user_packet_price;
    }

    public int getUser_quantity_per_packet() {
        return user_quantity_per_packet;
    }

    public void setUser_quantity_per_packet(int user_quantity_per_packet) {
        this.user_quantity_per_packet = user_quantity_per_packet;
    }

    public int getUser_smoke_per_day_limit() {
        return user_smoke_per_day_limit;
    }

    public void setUser_smoke_per_day_limit(int user_smoke_per_day_limit) {
        this.user_smoke_per_day_limit = user_smoke_per_day_limit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof UserEntity)) return false;
        UserEntity o = (UserEntity) obj;
        return o.getUser_email().equals(this.getUser_email());
    }

    @Override
    public String toString() {
        return id+ " " + user_last_name + " " + user_first_name;
    }
}


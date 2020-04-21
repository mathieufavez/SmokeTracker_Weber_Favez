package com.example.smoketracker_weber_favez.smoketracker.db.db.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserEntity implements Comparable {

    private String id;
    private String user_first_name;
    private String user_last_name;
    private String user_email;
    private String user_password;
    private String user_brand;
    private double user_packet_price;
    private int user_quantity_per_packet;
    private int user_smoke_per_day_limit;
    private Date user_start_date;

    public UserEntity(String user_first_name, String user_last_name, String user_email, String user_password, String user_brand, double user_packet_price, int user_quantity_per_packet, int user_smoke_per_day_limit, Date user_start_date) {
        this.user_first_name = user_first_name;
        this.user_last_name = user_last_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_brand = user_brand;
        this.user_packet_price = user_packet_price;
        this.user_quantity_per_packet = user_quantity_per_packet;
        this.user_smoke_per_day_limit = user_smoke_per_day_limit;
        this.user_start_date = user_start_date;
    }

    public UserEntity(){

    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Date getUser_start_date() {
        return user_start_date;
    }

    public void setUser_start_date(Date user_start_date) {
        this.user_start_date = user_start_date;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("user_first_name", user_first_name);
        result.put("user_last_name", user_last_name);
        result.put("user_email", user_email);
        result.put("user_password", user_password);
        result.put("user_brand", user_brand);
        result.put("user_packet_price", user_packet_price);
        result.put("user_quantity_per_packet", user_quantity_per_packet);
        result.put("user_smoke_per_day_limit", user_smoke_per_day_limit);
        result.put("user_start_date", user_start_date);
        return result;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }


}


package com.example.appbowling;

import java.util.Date;

public class ReservationItem {
    private String id;
    private String name;
    private String email;
    private String date;
    private String time;
    private String number;

    public ReservationItem(String id, String name, String email, String date, String time, String number) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.date = date;
        this.time = time;
        this.number = number;
    }

    private String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getNumber() {
        return number;
    }
}


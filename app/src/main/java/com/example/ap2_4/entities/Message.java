package com.example.ap2_4.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Message {
    @SerializedName("id")
    public int id;
    @SerializedName("created")
    public Date date;
    @SerializedName("sender")
    public User user;
    @SerializedName("content")
    public String message;

    public Message(int id, Date date, User user, String message) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.message = message;
    }
}

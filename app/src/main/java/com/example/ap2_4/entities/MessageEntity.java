package com.example.ap2_4.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class MessageEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public boolean fromMe;
    public String message;
    public Date date;

    public MessageEntity(boolean fromMe, String message, Date date) {
        this.fromMe = fromMe;
        this.message = message;
        this.date = date;
    }
}

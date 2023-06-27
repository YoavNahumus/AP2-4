package com.example.ap2_4.entities;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class FireToken {
    @SerializedName("firetoken")
    public String firetoken;

    public FireToken(String firetoken) {
        this.firetoken = firetoken;
    }
}
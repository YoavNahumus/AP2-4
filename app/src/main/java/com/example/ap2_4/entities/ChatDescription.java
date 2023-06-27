package com.example.ap2_4.entities;

import com.google.gson.annotations.SerializedName;

public class ChatDescription {
    @SerializedName("id")
    public String id;
    @SerializedName("user")
    public User user;

    public ChatDescription(String id, User user) {
        this.id = id;
        this.user = user;
    }
}

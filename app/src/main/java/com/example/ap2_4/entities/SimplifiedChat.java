package com.example.ap2_4.entities;

import com.google.gson.annotations.SerializedName;

public class SimplifiedChat {
    @SerializedName("id")
    public String id;
    @SerializedName("user")
    public User user;
    @SerializedName("lastMessage")
    public Message lastMessage;

    public SimplifiedChat(String id, User user, Message lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }
}

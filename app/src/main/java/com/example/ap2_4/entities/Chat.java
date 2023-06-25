package com.example.ap2_4.entities;

import com.google.gson.annotations.SerializedName;

public class Chat {
    @SerializedName("id")
    public String id;
    @SerializedName("users")
    public User[] users;
    @SerializedName("messages")
    public Message[] messages;

    public Chat(String id, User[] users, Message[] messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }
}

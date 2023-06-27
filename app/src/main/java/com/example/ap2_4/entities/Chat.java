package com.example.ap2_4.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Chat {
    @SerializedName("id")
    public String id;
    @SerializedName("users")
    public List<User> users;
    @SerializedName("messages")
    public List<Message> messages;

    public Chat(String id, List<User> users, List<Message> messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }
}

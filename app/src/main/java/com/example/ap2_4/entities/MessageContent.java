package com.example.ap2_4.entities;

import com.google.gson.annotations.SerializedName;

public class MessageContent {
    @SerializedName("msg")
    public String msg;

    public MessageContent(String msg) {
        this.msg = msg;
    }
}

package com.example.ap2_4.entities;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("username")
    public String username;
    @SerializedName("displayName")
    public String displayName;
    @SerializedName("profilePic")
    public String image;
    @SerializedName("password")
    public String password;

    public User(String username, String displayName, String image, String password) {
        this.username = username;
        this.displayName = displayName;
        this.image = image;
        this.password = password;
    }
}

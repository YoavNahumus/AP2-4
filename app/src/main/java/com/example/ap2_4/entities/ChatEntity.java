package com.example.ap2_4.entities;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import org.jetbrains.annotations.NotNull;

@Entity
public class ChatEntity {

    @PrimaryKey(autoGenerate = false)
    @NotNull
    public String id;
    public String username;
    public String displayName;
    public Bitmap image;
    public String lastMessage;

    public ChatEntity(@NotNull String id, String username, String displayName, Bitmap image, String lastMessage) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.image = image;
        this.lastMessage = lastMessage;
    }
}

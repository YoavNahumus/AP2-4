package com.example.ap2_4;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ap2_4.entities.ChatEntity;
import com.example.ap2_4.entities.MessageEntity;

@Database(entities = {ChatEntity.class, MessageEntity.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDB extends RoomDatabase {
    public abstract ChatDao chatDao();
    public abstract MessageDao messageDao();

    private static AppDB instance;

    public static void setInstance(AppDB instance) {
        AppDB.instance = instance;
    }
    public static AppDB getInstance() {
        return instance;
    }
}

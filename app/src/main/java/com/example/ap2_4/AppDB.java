package com.example.ap2_4;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ap2_4.entities.ChatEntity;

@Database(entities = {ChatEntity.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDB extends RoomDatabase {
    public abstract ChatDao chatDao();

    private static AppDB instance;

    public static void setInstance(AppDB instance) {
        AppDB.instance = instance;
    }
    public static AppDB getInstance() {
        return instance;
    }
}

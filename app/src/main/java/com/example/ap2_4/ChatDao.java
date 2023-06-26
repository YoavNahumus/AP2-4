package com.example.ap2_4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap2_4.entities.ChatEntity;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM chatentity")
    List<ChatEntity> index();

    @Query("SELECT * FROM chatentity WHERE id = :id")
    ChatEntity get(String id);

    @Insert
    void insert(ChatEntity... chatEntity);

    @Delete
    void delete(ChatEntity... chatEntity);

    @Update
    void update(ChatEntity... chatEntity);

    @Query("DELETE FROM chatentity")
    void deleteAll();
}

package com.example.ap2_4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap2_4.entities.MessageEntity;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messageentity")
    List<MessageEntity> index();

    @Query("SELECT * FROM messageentity WHERE id = :id")
    MessageEntity get(String id);

    @Insert
    void insert(MessageEntity... chatEntity);

    @Delete
    void delete(MessageEntity... chatEntity);

    @Update
    void update(MessageEntity... chatEntity);

    @Query("DELETE FROM messageentity")
    void deleteAll();
}

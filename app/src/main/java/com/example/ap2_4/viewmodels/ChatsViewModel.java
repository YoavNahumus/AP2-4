package com.example.ap2_4.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap2_4.entities.ChatEntity;
import com.example.ap2_4.repositories.ChatsRepository;

import java.util.List;

public class ChatsViewModel extends ViewModel {

    private ChatsRepository repository;
    private LiveData<List<ChatEntity>> chats;

    public ChatsViewModel() {

    }

    public void insertToken(String token) {
        repository = new ChatsRepository(token);
        chats = repository.getChats();
    }

    public LiveData<List<ChatEntity>> getChats() {
        return chats;
    }

    public void add(ChatEntity chat) {
        repository.addChat(chat);
    }

    public void reload() {
        repository.reload();
    }
}

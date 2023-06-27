package com.example.ap2_4.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap2_4.entities.MessageEntity;
import com.example.ap2_4.repositories.ChatRepository;

import java.util.List;

public class ChatViewModel extends ViewModel {
    private ChatRepository repository;
    private LiveData<List<MessageEntity>> messages;

    public ChatViewModel() {

    }

    public void init(String chatId, String token, String username) {
        repository = new ChatRepository(chatId, token, username);
        messages = repository.getMessages();
    }

    public LiveData<List<MessageEntity>> getMessages() {
        return messages;
    }

    public void sendMessage(String message) {
        repository.sendMessage(message);
    }

    public void reload() {
        repository.reload();
    }
}

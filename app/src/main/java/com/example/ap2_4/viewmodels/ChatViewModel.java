package com.example.ap2_4.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap2_4.entities.MessageEntity;
import com.example.ap2_4.repositories.ChatRepository;

import java.util.List;

public class ChatViewModel extends ViewModel {
    private ChatRepository repository;
    private LiveData<List<MessageEntity>> messages;
    private static ChatViewModel instance;

    private String chatId;

    public ChatViewModel() {
    }

    public static void add(String chatId, String message) {
        if (instance != null && chatId.equals(instance.chatId)) {
            instance.addMessage(message);
        }
    }

    public void init(String chatId, String token, String username) {
        this.chatId = chatId;
        repository = new ChatRepository(chatId, token, username);
        messages = repository.getMessages();
        instance = this;
    }

    public LiveData<List<MessageEntity>> getMessages() {
        return messages;
    }

    public void sendMessage(String message) {
        repository.sendMessage(message);
    }

    public void addMessage(String message) {
        repository.addMessage(message);
    }

    public void reload() {
        repository.reload();
    }
}

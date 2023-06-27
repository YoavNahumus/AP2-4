package com.example.ap2_4.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_4.AppDB;
import com.example.ap2_4.MessageDao;
import com.example.ap2_4.api.API;
import com.example.ap2_4.entities.Message;
import com.example.ap2_4.entities.MessageEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {
    private final MessageDao messageDao;
    private final MessageListData messageListData;
    private final API api = API.instance;
    private final String token;
    private final String chatId;
    private final String username;

    public ChatRepository(String chatId, String token, String username) {
        this.chatId = chatId;
        this.token = token;
        this.username = username;
        AppDB db = AppDB.getInstance();
        messageDao = db.messageDao();
        messageListData = new MessageListData();
    }

    class MessageListData extends MutableLiveData<List<MessageEntity>> {
        public MessageListData() {
            super();
            setValue(messageDao.index());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(ChatRepository.this::reload).start();
        }
    }

    public LiveData<List<MessageEntity>> getMessages() {
        return messageListData;
    }

    public void sendMessage(String message) {
        api.sendMessage(chatId, token, message, new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Message message = response.body();
                    messageDao.insert(new MessageEntity(true, message.message, message.date));
                    messageListData.postValue(messageDao.index());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }

    public void addMessage(String message) {
        messageDao.insert(new MessageEntity(false, message, new Date()));
        messageListData.postValue(messageDao.index());
    }

    public void reload() {
        api.getMessages(chatId, token, new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> messages = response.body();
                    MessageEntity[] chatEntities = messages.stream().map(message ->
                                    new MessageEntity(message.user.username.equals(username), message.message, message.date))
                            .toArray(MessageEntity[]::new);
                    messageDao.deleteAll();
                    messageDao.insert(chatEntities);
                    messageListData.postValue(Arrays.asList(chatEntities));
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }
}

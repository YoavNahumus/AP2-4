package com.example.ap2_4.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_4.AppDB;
import com.example.ap2_4.ChatDao;
import com.example.ap2_4.Converters;
import com.example.ap2_4.api.API;
import com.example.ap2_4.entities.ChatDescription;
import com.example.ap2_4.entities.ChatEntity;
import com.example.ap2_4.entities.SimplifiedChat;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatsRepository {
    private final ChatDao chatDao;
    private final ChatListData chatListData;
    private final API api = API.instance;
    private final String token;

    public ChatsRepository(String token) {
        this.token = token;
        AppDB db = AppDB.getInstance();
        chatDao = db.chatDao();
        chatListData = new ChatListData();
    }

    class ChatListData extends MutableLiveData<List<ChatEntity>> {
        public ChatListData() {
            super();
            setValue(chatDao.index());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(ChatsRepository.this::reload).start();
        }
    }

    public LiveData<List<ChatEntity>> getChats() {
        return chatListData;
    }

    public void addChat(ChatEntity chat) {
        api.createChat(chat.username, token, new Callback<ChatDescription>() {
            @Override
            public void onResponse(Call<ChatDescription> call, Response<ChatDescription> response) {
                if (response.isSuccessful()) {
                    ChatDescription chatDescription = response.body();
                    chatDao.insert(new ChatEntity(chatDescription.id, chatDescription.user.username, chatDescription.user.displayName, Converters.toBitmap(chatDescription.user.image), ""));
                    chatListData.postValue(chatDao.index());
                }
            }

            @Override
            public void onFailure(Call<ChatDescription> call, Throwable t) {

            }
        });
    }

    public void reload() {
        api.getChats(token, new Callback<List<SimplifiedChat>>() {
            @Override
            public void onResponse(Call<List<SimplifiedChat>> call, Response<List<SimplifiedChat>> response) {
                if (response.isSuccessful()) {
                    List<SimplifiedChat> chats = response.body();
                    ChatEntity[] chatEntities = chats.stream().map(chat ->
                            new ChatEntity(chat.id, chat.user.username, chat.user.displayName, Converters.toBitmap(chat.user.image), chat.lastMessage == null ? "" : chat.lastMessage.message))
                            .toArray(ChatEntity[]::new);
                    chatDao.deleteAll();
                    chatDao.insert(chatEntities);
                    chatListData.postValue(Arrays.asList(chatEntities));
                }
            }

            @Override
            public void onFailure(Call<List<SimplifiedChat>> call, Throwable t) {

            }
        });
    }
}

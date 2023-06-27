package com.example.ap2_4.api;

import com.example.ap2_4.entities.Chat;
import com.example.ap2_4.entities.ChatDescription;
import com.example.ap2_4.entities.FireToken;
import com.example.ap2_4.entities.Message;
import com.example.ap2_4.entities.MessageContent;
import com.example.ap2_4.entities.SimplifiedChat;
import com.example.ap2_4.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    public static final API instance = new API();
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    private API() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void login(String username, String password, Callback<String> callback) {
        Call<String> call = webServiceAPI.login(new User(username, "", "", password));
        call.enqueue(callback);
    }

    public void updateFireToken(String fireToken, String Token, Callback<String> callback) {
        Call<String> call = webServiceAPI.firetoken(new FireToken(fireToken), "Bearer " + Token);
        call.enqueue(callback);
    }

    public void register(String username, String password, String displayName, String image, Callback<String> callback) {
        Call<String> call = webServiceAPI.register(new User(username, displayName, image, password));
        call.enqueue(callback);
    }

    public void getUser(String username, String token, Callback<User> callback) {
        Call<User> call = webServiceAPI.getUser(username, "Bearer " + token);
        call.enqueue(callback);
    }

    public void createChat(String username, String token, Callback<ChatDescription> callback) {
        Call<ChatDescription> call = webServiceAPI.createChat(new User(username, "", "", ""), "Bearer " + token);
        call.enqueue(callback);
    }

    public void getChats(String token, Callback<List<SimplifiedChat>> callback) {
        Call<List<SimplifiedChat>> call = webServiceAPI.getChats("Bearer " + token);
        call.enqueue(callback);
    }

    public void sendMessage(String chatId, String token, String content, Callback<Message> callback) {
        Call<Message> call = webServiceAPI.sendMessage(chatId, new MessageContent(content), "Bearer " + token);
        call.enqueue(callback);
    }

    public void getMessages(String chatId, String token, Callback<List<Message>> callback) {
        Call<List<Message>> call = webServiceAPI.getMessages(chatId, "Bearer " + token);
        call.enqueue(callback);
    }

    public void getChat(String chatId, String token, Callback<Chat> callback) {
        Call<Chat> call = webServiceAPI.getChat(chatId, "Bearer " + token);
        call.enqueue(callback);
    }

    public void deleteChat(String chatId, String token, Callback<Void> callback) {
        Call<Void> call = webServiceAPI.deleteChat(chatId, "Bearer " + token);
        call.enqueue(callback);
    }
}

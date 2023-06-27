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
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @POST("Firebase")
    Call<String> firetoken(@Body FireToken token, @Header("authorization") String authorization);

    @POST("Tokens")
    Call<String> login(@Body User user);

    @POST("Users")
    Call<String> register(@Body User user);

    @GET("Users/{username}")
    Call<User> getUser(@Path("username") String username, @Header("authorization") String authorization);

    @POST("Chats")
    Call<ChatDescription> createChat(@Body User user, @Header("authorization") String authorization);

    @GET("Chats")
    Call<List<SimplifiedChat>> getChats(@Header("authorization") String authorization);

    @POST("Chats/{chatId}/Messages")
    Call<Message> sendMessage(@Path("chatId") String chatId, @Body MessageContent msg, @Header("authorization") String authorization);

    @GET("Chats/{chatId}/Messages")
    Call<List<Message>> getMessages(@Path("chatId") String chatId, @Header("authorization") String authorization);

    @GET("Chats/{chatId}")
    Call<Chat> getChat(@Path("chatId") String chatId, @Header("authorization") String authorization);

    @DELETE("Chats/{chatId}")
    Call<Void> deleteChat(@Path("chatId") String chatId, @Header("authorization") String authorization);
}

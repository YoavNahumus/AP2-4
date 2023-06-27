package com.example.ap2_4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.ap2_4.api.API;
import com.example.ap2_4.entities.ChatDescription;
import com.example.ap2_4.entities.ChatEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);


        Button btnAdd = findViewById(R.id.btnAddChat);

        btnAdd.setOnClickListener(v -> {
            String username = ((EditText) findViewById(R.id.usernameInput)).getText().toString();
              API.getInstance().createChat(username, getIntent().getStringExtra("token"), new Callback<ChatDescription>() {
                  @Override
                  public void onResponse(Call<ChatDescription> call, Response<ChatDescription> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(AddChatActivity.this, "Failed to create chat", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        finish();
                  }

                  @Override
                  public void onFailure(Call<ChatDescription> call, Throwable t) {

                  }
              });
        });

        FloatingActionButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}

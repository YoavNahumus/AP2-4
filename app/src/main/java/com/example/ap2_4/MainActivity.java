package com.example.ap2_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.example.ap2_4.api.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.submit).setOnClickListener(this::login);

    }

    private void login(View v) {
        API.instance.login(
                ((EditText) findViewById(R.id.usernameInput)).getText().toString(),
                ((EditText) findViewById(R.id.passwordInput)).getText().toString(),
                new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String token = response.body();
                        Intent intent = new Intent(MainActivity.this, ChatsActivity.class);
                        intent.putExtra("token", token);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
package com.example.ap2_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ap2_4.api.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final API api = new API();
    private static final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFzIiwiaWF0IjoxNjg3Njg2MDE2fQ.BdEwB-SqhVR2UkTO2Zpagw4BeA-36OrGKwI9jmy0kPg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.submit).setOnClickListener(this::login);
    }

    private void login(View v) {
        api.login(
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
                        // TODO: save token and redirect to chats activity
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
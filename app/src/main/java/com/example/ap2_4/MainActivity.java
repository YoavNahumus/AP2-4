package com.example.ap2_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.submit).setOnClickListener(v -> {
            ExecutorService executor = Executors.newFixedThreadPool(1);
            Future<?> result = executor.submit(this::login);
            try {
                result.get(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void login() {

        try {
            URL url = new URL("http://10.0.2.2:5000/Tokens");
            String username = ((EditText)findViewById(R.id.usernameInput)).getText().toString();
            String password = ((EditText)findViewById(R.id.passwordInput)).getText().toString();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
            conn.getOutputStream().write(jsonInputString.getBytes());
            String response = conn.getResponseMessage();
            if (conn.getResponseCode() == 200) {
                System.out.println(response);
            } else {
                System.out.println(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.example.ap2_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    //TODO -- add picture picker
    private String profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.submit).setOnClickListener(this::register);
    }

    private void register(View v) {
        try {
            URL url = new URL("http://localhost:5000/Users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = String.format(
                    "{" +
                            "\"username\": \"%s\"," +
                            "\"password\": \"%s\"," +
                            "\"profilePic\": \"%s\"," +
                            "\"displayName\": \"%s\"" +
                    "}", ((EditText) findViewById(R.id.username)).getText(),
                    ((EditText) findViewById(R.id.password)).getText(),
                    profilePic,
                    ((EditText) findViewById(R.id.display_name)).getText());

            conn.getOutputStream().write(jsonInputString.getBytes());
            if (conn.getResponseCode() == 200) {
                Intent myIntent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(myIntent);
            } else if (conn.getResponseCode() == 409) {
                Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error: " + conn.getResponseCode(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
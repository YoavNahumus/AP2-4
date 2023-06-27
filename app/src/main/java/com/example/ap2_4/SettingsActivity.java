package com.example.ap2_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ap2_4.api.API;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        EditText input =  (EditText)findViewById(R.id.btnURL);
        input.setText(API.getURL());
        findViewById(R.id.submit).setOnClickListener(v -> {
            String URL = input.getText().toString();
            API.changeURL(URL);
        });




        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
package com.example.ap2_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button clickButton = (Button) findViewById(R.id.register_btn_id);
        clickButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean sucessfulLogin = true;
                if(sucessfulLogin){
                    Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    myIntent.putExtra("token","placeholder");//TODO -- after loging with server get token
                    MainActivity.this.startActivity(myIntent);
                }
            }
        });
    }
}
package com.example.ap2_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    LinearLayout msg_container;
    LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        msg_container = findViewById(R.id.message_container);


        inflater = getLayoutInflater();

        addLeftMessage("bla");
    }

    void addLeftMessage(String text){
        LinearLayout msg_factory_layout = (LinearLayout) inflater.inflate(R.layout.massages , null);
        TextView v = msg_factory_layout.findViewById(R.id.msg_left);
        v.setText(text);
        msg_factory_layout.removeAllViews();
        msg_container.addView(v);
    }

    void addRightMessage(String text){
        LinearLayout msg_factory_layout = (LinearLayout) inflater.inflate(R.layout.massages , null);
        TextView v = msg_factory_layout.findViewById(R.id.msg_right);
        v.setText(text);
        msg_factory_layout.removeAllViews();
        msg_container.addView(v);
    }
}
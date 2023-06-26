package com.example.ap2_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ap2_4.adapters.ChatAdapter;
import com.example.ap2_4.api.API;
import com.example.ap2_4.viewmodels.ChatViewModel;
import com.google.android.material.imageview.ShapeableImageView;

public class ChatActivity extends AppCompatActivity {

    private AppDB db;
    private MessageDao chatDao;

    private ChatViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        RecyclerView messageList = findViewById(R.id.lstMsgs);
        final ChatAdapter adapter = new ChatAdapter(this);
        messageList.setAdapter(adapter);
        messageList.setLayoutManager(new LinearLayoutManager(this));

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ChatDB")
                .allowMainThreadQueries()
                .build();
        AppDB.setInstance(db);

        chatDao = db.messageDao();

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.init(getIntent().getStringExtra("chatId"), getIntent().getStringExtra("token"), getIntent().getStringExtra("username"));

        viewModel.getMessages().observe(this, adapter::setMessages);

        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            viewModel.reload();
            refreshLayout.setRefreshing(false);
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnSend).setOnClickListener(v -> {
            viewModel.sendMessage(((EditText)findViewById(R.id.msgInput)).getText().toString());
            ((EditText)findViewById(R.id.msgInput)).setText("");
        });

        ShapeableImageView profilePic = findViewById(R.id.profilePicture);
        profilePic.setImageBitmap(Converters.toBitmap(getIntent().getStringExtra("profilePic")));

        TextView displayName = findViewById(R.id.displayName);
        displayName.setText(getIntent().getStringExtra("displayName"));
    }
}
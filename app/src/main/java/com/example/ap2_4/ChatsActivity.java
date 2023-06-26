package com.example.ap2_4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ap2_4.adapters.ChatsListAdapter;
import com.example.ap2_4.viewmodels.ChatsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

public class ChatsActivity extends AppCompatActivity {

    private AppDB db;
    private ChatDao chatDao;

    private ChatsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        RecyclerView chatsList = findViewById(R.id.lstChats);
        final ChatsListAdapter adapter = new ChatsListAdapter(this, (chat) -> {
            Intent intent = new Intent(this, ChatActivity.class)
                    .putExtra("chatId", chat.id)
                    .putExtra("token", getIntent().getStringExtra("token"))
                    .putExtra("username", getIntent().getStringExtra("username"))
                    .putExtra("displayName", chat.displayName)
                    .putExtra("profilePic", Converters.fromBitmap(chat.image));
            startActivity(intent);
        });
        chatsList.setAdapter(adapter);
        chatsList.setLayoutManager(new LinearLayoutManager(this));

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ChatDB")
                .allowMainThreadQueries()
                .build();
        AppDB.setInstance(db);

        chatDao = db.chatDao();

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddChatActivity.class).putExtra("token", getIntent().getStringExtra("token"));
            startActivity(intent);
        });

        viewModel = new ViewModelProvider(this).get(ChatsViewModel.class);
        viewModel.insertToken(getIntent().getStringExtra("token"));

        viewModel.getChats().observe(this, adapter::setChats);

        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            viewModel.reload();
            refreshLayout.setRefreshing(false);
        });
    }
}

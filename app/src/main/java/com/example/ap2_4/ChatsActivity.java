package com.example.ap2_4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ap2_4.adapters.ChatsListAdapter;
import com.example.ap2_4.api.API;
import com.example.ap2_4.entities.FireToken;
import com.example.ap2_4.viewmodels.ChatsViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatsActivity extends AppCompatActivity {

    private AppDB db;
    private ChatDao chatDao;

    private String firebaseToken;
    private ChatsViewModel viewModel;

    public void updateFirebaseToken(String tok){
        firebaseToken = tok;
    }

    public void firebaseStart(String Token){
        Log.d("firebase","StartFunc");
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ChatsActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                firebaseToken = instanceIdResult.getToken();
                Log.d("firebase",firebaseToken);

                API.instance.updateFireToken(
                        firebaseToken,
                        Token,
                        new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (!response.isSuccessful()) {
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                return;
                            }
                        });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        firebaseStart(getIntent().getStringExtra("token"));

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

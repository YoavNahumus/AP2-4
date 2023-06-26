package com.example.ap2_4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_4.R;
import com.example.ap2_4.entities.ChatEntity;

import java.util.List;

public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ChatsViewHolder> {

    static class ChatsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView profilePictureView;
        private final TextView displayNameView;
        private final TextView lastMessageView;

        private ChatsViewHolder(View itemView) {
            super(itemView);
            profilePictureView = itemView.findViewById(R.id.profilePicture);
            displayNameView = itemView.findViewById(R.id.displayName);
            lastMessageView = itemView.findViewById(R.id.lastMessage);
        }
    }

    private final LayoutInflater inflater;
    private List<ChatEntity> chats;

    public ChatsListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.chat_layout, parent, false);
        return new ChatsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {
        if (chats != null) {
            ChatEntity current = chats.get(position);
            holder.displayNameView.setText(current.displayName);
            holder.lastMessageView.setText(current.lastMessage);
            holder.profilePictureView.setImageBitmap(current.image);
        }
    }

    public void setChats(List<ChatEntity> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chats != null ? chats.size() : 0;
    }
}

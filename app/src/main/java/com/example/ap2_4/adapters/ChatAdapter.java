package com.example.ap2_4.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_4.R;
import com.example.ap2_4.entities.MessageEntity;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    static class ChatViewHolder extends RecyclerView.ViewHolder {
        private final TextView message;

        private ChatViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
        }
    }

    private final LayoutInflater inflater;
    private List<MessageEntity> messages;

    public ChatAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.messages, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        if (messages != null) {
            MessageEntity current = messages.get(position);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = 10;
            if (current.fromMe) {
                params.gravity = Gravity.RIGHT;
                holder.message.setBackgroundResource(R.drawable.message_bg_right);
            } else {
                params.gravity = Gravity.LEFT;
                holder.message.setBackgroundResource(R.drawable.message_bg_left);
            }
            holder.message.setLayoutParams(params);
            holder.message.setText(current.message);
        }
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messages != null ? messages.size() : 0;
    }
}

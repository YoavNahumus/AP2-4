package com.example.ap2_4;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ap2_4.viewmodels.ChatViewModel;
import com.example.ap2_4.viewmodels.ChatsViewModel;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;

public class FireService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("firebase", "MessageRecived");
        if (remoteMessage.getNotification() != null) {
            createNotificationChannel();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.baseline_add_a_photo_24)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            notificationManager.notify(1, builder.build());

            JSONObject dat = null;
            try {
                dat = new JSONObject(remoteMessage.getData().get("my_key"));
                Log.d("JSON",dat.toString());
                Log.d("JSON",dat.get("type").toString());
                Log.d("JSON",dat.get("dat").toString()); //CHAT ID IS HERE
                //

                if (dat.get("type").toString().equals("newMessage")) {
                    Log.d("firebase","newMessage!");
                    ChatViewModel.add(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                }
                if (dat.get("type").toString().equals("newChat")) {
                    ChatsViewModel.reloadIfPresent();
                }


            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
            //


        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", "notifications", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
package com.example.photogallery.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import com.example.photogallery.R;

public class NotificationService {
    private static NotificationManager mManager;

    public static void initialize(Context context) {
        mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String id = "poll_channel";
        CharSequence name = context.getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);

        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel channel = new NotificationChannel(id, name, importance);

        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(false);
        mManager.createNotificationChannel(channel);
    }
}

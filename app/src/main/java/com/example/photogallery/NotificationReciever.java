package com.example.photogallery;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import com.example.photogallery.service.PollService;

public class NotificationReciever extends BroadcastReceiver {
    private static final String TAG = "TAG";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "reescuved result => " + getResultCode() + "");

        if (getResultCode() != Activity.RESULT_OK) {
            return;
        }

        int requestCode = intent.getIntExtra(PollService.REQUEST_CODE, 0);
        Notification notification = (Notification) intent.getParcelableExtra(PollService.NOTIFICATION);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(requestCode, notification);
    }
}

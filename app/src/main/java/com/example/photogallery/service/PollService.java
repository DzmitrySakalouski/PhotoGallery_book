package com.example.photogallery.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.photogallery.MainActivity;
import com.example.photogallery.QueryPreferensies;
import com.example.photogallery.R;
import com.example.photogallery.fetcher.FlickrFetchr;
import com.example.photogallery.models.GalleryItem;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PollService extends IntentService {
    private static final String TAG = "TAG";
    private static final String CHANNEL_ID = "pollServiceChannel";
    private static final long POLL_INTERVAL_MS = TimeUnit.SECONDS.toMillis(10);
    public static final String ACTION_SHOW_NOTOFICATION = "com.example.android.photogallery.SHOW_NOTIFICATION";

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String query = QueryPreferensies.getStoredQuery(this);
        String lastResId = QueryPreferensies.getLastResultId(this);
        List<GalleryItem> galleryItems;
        if (query == null) {
            galleryItems = new FlickrFetchr().fetchRecentPhotos();
        } else {
            galleryItems = new FlickrFetchr().searchPhotos(query);
        }

        String resultId = galleryItems.get(0).getId();
        if (resultId.equals(lastResId)) {
            Log.d(TAG, "Got old request");
        } else {
            Log.i(TAG, "New reult");
            Intent i = MainActivity.newIntent(this);
            PendingIntent pi = PendingIntent.getService(this, 0, i ,0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle(getResources().getString(R.string.new_picture_title))
                    .setSmallIcon(R.drawable.test)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(0, notification);

            sendBroadcast(new Intent(ACTION_SHOW_NOTOFICATION));
        }

        QueryPreferensies.setLastResultId(this, resultId);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetwork() != null;
        boolean isNetworkConnected = cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected && isNetworkAvailable;
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Log.d(TAG, "isOn => " + isOn);
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime(), POLL_INTERVAL_MS, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
        QueryPreferensies.setAlarmOn(context, isOn);
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }
}
package com.example.photogallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.photogallery.service.PollService;

public class StartupReciever extends BroadcastReceiver {
    private static final String TAG = "TAG";

    // TODO : check on rel device

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Recieved new broadcast => " + intent.getAction());
        boolean isOn = QueryPreferensies.isAlarmOn(context);
        PollService.setServiceAlarm(context, isOn);
    }
}

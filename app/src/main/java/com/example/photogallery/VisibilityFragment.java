package com.example.photogallery;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photogallery.service.PollService;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class VisibilityFragment extends Fragment {

    private final static String TAG = "TAG";

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(PollService.ACTION_SHOW_NOTOFICATION);
        getActivity().registerReceiver(mOnShowNotification, filter, PollService.PREM_PRIVATE, null);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mOnShowNotification);
    }

    private BroadcastReceiver mOnShowNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Cancelling notifications");
            setResultCode(Activity.RESULT_CANCELED);
        }
    };

}

package org.maktab.photogallery.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ScreenReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Screen Changed: " + intent);
        Toast.makeText(context, "Screen Changed: " + intent, Toast.LENGTH_LONG).show();
    }
}

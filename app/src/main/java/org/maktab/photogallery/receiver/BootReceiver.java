package org.maktab.photogallery.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.maktab.photogallery.service.PollService;
import org.maktab.photogallery.utilities.QueryPreferences;

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "PhotoGalleryBootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "received intent: " + intent.getAction());

        boolean isAlarmOn = QueryPreferences.isAlarmOn(context);
        if (isAlarmOn) {
            Log.d(TAG, "alarm is scheduled at the boot");
            PollService.scheduleAlarm(context, isAlarmOn);
        } else {
            Log.d(TAG, "there is not alarm scheduled");
        }
    }
}
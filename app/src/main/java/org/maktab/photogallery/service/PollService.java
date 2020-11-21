package org.maktab.photogallery.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;

import org.maktab.photogallery.utilities.QueryPreferences;
import org.maktab.photogallery.utilities.ServicesUtils;

import java.util.concurrent.TimeUnit;

//I use alarm manager to start this service
public class PollService extends IntentService {

    private static final String TAG = "PollService";

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: " + intent);
        if (!isNetworkAvailableAndConnected()) {
            Log.d(TAG, "Network not available");
            return;
        }

        ServicesUtils.pollAndShowNotification(this, TAG);
    }

    public static void scheduleAlarm(Context context, boolean isOn) {
        Log.d(TAG, "scheduleAlarm");
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent operation = getAlarmPendingIntent(context, 0);

        if (isOn) {
            Log.d(TAG, "schedule On");
            alarmManager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(),
                    TimeUnit.MINUTES.toMillis(1),
                    operation);
        } else {
            Log.d(TAG, "schedule Off");
            alarmManager.cancel(operation);
            operation.cancel();
        }
        QueryPreferences.setIsAlarmOn(context, isOn);
    }

    public static boolean isAlarmSet(Context context) {
        PendingIntent operation = getAlarmPendingIntent(context, PendingIntent.FLAG_NO_CREATE);
        return operation != null;
    }

    private static PendingIntent getAlarmPendingIntent(Context context, int flags) {
        Intent intent = PollService.newIntent(context);
        return PendingIntent.getService(
                context,
                0,
                intent,
                flags);
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected())
            return true;

        return false;
    }
}
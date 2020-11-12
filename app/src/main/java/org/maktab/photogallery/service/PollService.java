package org.maktab.photogallery.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.maktab.photogallery.R;
import org.maktab.photogallery.data.model.GalleryItem;
import org.maktab.photogallery.data.repository.PhotoRepository;
import org.maktab.photogallery.utilities.QueryPreferences;
import org.maktab.photogallery.view.activity.PhotoGalleryActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;


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

        String query = QueryPreferences.getSearchQuery(this);

        PhotoRepository repository = new PhotoRepository();
        List<GalleryItem> items;
        if (query == null)
            items = repository.fetchPopularItems();
        else
            items = repository.fetchSearchItems(query);

        if (items == null || items.size() == 0) {
            Log.d(TAG, "Items from server not fetched");
            return;
        }

        String serverId = items.get(0).getId();
        String lastSavedId = QueryPreferences.getLastId(this);
        if (!serverId.equals(lastSavedId)) {
            Log.d(TAG, "show notification");
            createAndShowNotification();
        } else {
            Log.d(TAG, "do nothing");
        }

        QueryPreferences.setLastId(this, serverId);
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected())
            return true;

        return false;
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

    private void createAndShowNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                PhotoGalleryActivity.newIntent(this),
                0);

        String channelId = getResources().getString(R.string.channel_id);
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(getResources().getString(R.string.new_pictures_title))
                .setContentText(getResources().getString(R.string.new_pictures_text))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, notification);
    }
}
package org.maktab.photogallery.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;

import org.maktab.photogallery.model.GalleryItem;
import org.maktab.photogallery.repository.PhotoRepository;
import org.maktab.photogallery.utilities.QueryPreferences;

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
            //TODO: show notification
            Log.d(TAG, "show notification");
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

    public static void scheduleAlarm(Context context) {
        Log.d(TAG, "scheduleAlarm");
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = newIntent(context);
        PendingIntent operation = PendingIntent.getService(
                context,
                0,
                intent,
                0);

        alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime(),
                TimeUnit.MINUTES.toMillis(1),
                operation);
    }
}
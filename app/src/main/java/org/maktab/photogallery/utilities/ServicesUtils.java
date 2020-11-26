package org.maktab.photogallery.utilities;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.greenrobot.eventbus.EventBus;
import org.maktab.photogallery.R;
import org.maktab.photogallery.data.model.GalleryItem;
import org.maktab.photogallery.data.repository.PhotoRepository;
import org.maktab.photogallery.event.NotificationEvent;
import org.maktab.photogallery.view.activity.PhotoGalleryActivity;

import java.util.List;

public class ServicesUtils {

    private static final int NOTIFICATION_ID = 1;

    public static void pollAndShowNotification(Context context, String tag) {
        String query = QueryPreferences.getSearchQuery(context);

        PhotoRepository repository = new PhotoRepository();
        List<GalleryItem> items;
        if (query == null)
            items = repository.fetchPopularItems();
        else
            items = repository.fetchSearchItems(query);

        if (items == null || items.size() == 0) {
            Log.d(tag, "Items from server not fetched");
            return;
        }

        String serverId = items.get(0).getId();
        String lastSavedId = QueryPreferences.getLastId(context);
        if (!serverId.equals(lastSavedId)) {
            Log.d(tag, "show notification");

            sendNotificationEvent(context);
        } else {
            Log.d(tag, "do nothing");
        }

        QueryPreferences.setLastId(context, serverId);
    }

    private static void sendNotificationEvent(Context context) {
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                PhotoGalleryActivity.newIntent(context),
                0);

        String channelId = context.getResources().getString(R.string.channel_id);
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(context.getResources().getString(R.string.new_pictures_title))
                .setContentText(context.getResources().getString(R.string.new_pictures_text))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationEvent notificationEvent = new NotificationEvent(NOTIFICATION_ID, notification);
        EventBus.getDefault().post(notificationEvent);
    }
}

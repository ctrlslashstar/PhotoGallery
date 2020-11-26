package org.maktab.photogallery.event;

import android.app.Notification;

public class NotificationEvent {

    private final int mNotificationId;
    private final Notification mNotification;

    public int getNotificationId() {
        return mNotificationId;
    }

    public Notification getNotification() {
        return mNotification;
    }

    public NotificationEvent(int notificationId, Notification notification) {
        mNotificationId = notificationId;
        mNotification = notification;
    }
}

package org.maktab.photogallery.view.fragment;

import android.util.Log;

import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.maktab.photogallery.PhotoGalleryApplication;
import org.maktab.photogallery.event.NotificationEvent;

public class VisibleFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    public void onNotificationEventListener(NotificationEvent notificationEvent) {
        String msg = "The fragment received the notification event";
        Log.d(PhotoGalleryApplication.TAG_EVENT_BUS, msg);

        EventBus.getDefault().cancelEventDelivery(notificationEvent);
    }
}

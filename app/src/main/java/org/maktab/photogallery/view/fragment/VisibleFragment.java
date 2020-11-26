package org.maktab.photogallery.view.fragment;

import android.util.Log;

import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.maktab.photogallery.PhotoGalleryApplication;
import org.maktab.photogallery.event.NotificationEvent;

public class VisibleFragment extends Fragment {

    /*private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(NotificationReceiver.TAG, "Fragment is visible: " + intent);
            Toast.makeText(
                    context,
                    "The app is visible and just received a notification event",
                    Toast.LENGTH_LONG).show();

            setResultCode(Activity.RESULT_CANCELED);
        }
    };*/

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);

        /*IntentFilter intentFilter = new IntentFilter(ServicesUtils.ACTION_PRIVATE_NOTIFICATION);
        getActivity().registerReceiver(
                mReceiver,
                intentFilter,
                ServicesUtils.PERMISSION_PRIVATE_NOTIFICATION,
                null);*/
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
        /*getActivity().unregisterReceiver(mReceiver);*/
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    public void onNotificationEventListener(NotificationEvent notificationEvent) {
        String msg = "The fragment received the notification event";
        Log.d(PhotoGalleryApplication.TAG_EVENT_BUS, msg);

        EventBus.getDefault().cancelEventDelivery(notificationEvent);
    }
}

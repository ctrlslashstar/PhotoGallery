package org.maktab.photogallery.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.maktab.photogallery.receiver.NotificationReceiver;
import org.maktab.photogallery.utilities.ServicesUtils;

public class VisibleFragment extends Fragment {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(NotificationReceiver.TAG, "Fragment is visible: " + intent);
            Toast.makeText(
                    context,
                    "The app is visible and just received a notification event",
                    Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(ServicesUtils.ACTION_PRIVATE_NOTIFICATION);
        getActivity().registerReceiver(
                mReceiver,
                intentFilter,
                ServicesUtils.PERMISSION_PRIVATE_NOTIFICATION,
                null);
    }

    @Override
    public void onStop() {
        super.onStop();

        getActivity().unregisterReceiver(mReceiver);
    }
}

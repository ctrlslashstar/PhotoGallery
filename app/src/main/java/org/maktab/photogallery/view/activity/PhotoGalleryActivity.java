package org.maktab.photogallery.view.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.maktab.photogallery.service.MyService;
import org.maktab.photogallery.view.fragment.PhotoGalleryFragment;

public class PhotoGalleryActivity extends SingleFragmentActivity {

    private MyService mMyService;
    private boolean mBounded = false;
    static int instances = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent serviceIntent = MyService.newIntent(this, instances);
        startService(serviceIntent);

        mBinding.buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBounded) {
                    int randomNumber = mMyService.getRandomNumber();
                    Toast.makeText(mMyService, "Service gerated: " + randomNumber, Toast.LENGTH_LONG).show();
                }
            }
        });

        //only create the service
//        bindService(serviceIntent, null, 0);

        //create and start the service
//        startService(serviceIntent);

        /*if (instances <= 3) {
            startActivity(new Intent(this, PhotoGalleryActivity.class));
            instances++;
        }*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = MyService.newIntent(this, 0);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unbindService(mConnection);
        mBounded = false;
    }

    @Override
    public Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof MyService.MyBinder) {
                mBounded = true;

                MyService.MyBinder myBinder = (MyService.MyBinder) service;
                mMyService = myBinder.getMyService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBounded = false;
        }
    };
}
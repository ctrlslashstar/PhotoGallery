package org.maktab.photogallery.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class MyService extends Service {

    private static final String EXTRA_SERVICE_NUMBER = "org.maktab.photogallery.ServiceNumber";

    private MyBinder mMyBinder = new MyBinder();
    private final Random mGenerator = new Random();

    public static Intent newIntent(Context context, int number) {
        Intent intent = new Intent(context, MyService.class);
        intent.putExtra(EXTRA_SERVICE_NUMBER, number);

        return intent;
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMyBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int number = intent.getIntExtra(EXTRA_SERVICE_NUMBER, -1);
        Log.d("MyService", "service started with: " + number);

        return START_NOT_STICKY;
    }

    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

    public class MyBinder extends Binder {
        public MyService getMyService() {
            return MyService.this;
        }
    }
}
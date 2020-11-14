package org.maktab.photogallery.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.maktab.photogallery.utilities.ServicesUtils;

import java.util.concurrent.TimeUnit;

public class PollWorker extends Worker {

    private static final String TAG = "PollWorker";

    public PollWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ServicesUtils.pollAndShowNotification(getApplicationContext(), TAG);
        return Result.success();
    }

    public static void enqueueWork(Context context, boolean isOn) {
        WorkManager workManager = WorkManager.getInstance(context);

        if (isOn) {
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED)
                    .build();

            WorkRequest workRequest =
                    new PeriodicWorkRequest.Builder(PollWorker.class, 15, TimeUnit.MINUTES)
                            .setConstraints(constraints)
                            .build();

            workManager.enqueue(workRequest);
        } else {
            workManager.cancelAllWork();
        }
    }

    public static boolean isWorkEnqueued(Context context) {
        //TODO
        return true;
    }
}

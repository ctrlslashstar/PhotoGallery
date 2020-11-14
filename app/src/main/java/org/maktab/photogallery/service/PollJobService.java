package org.maktab.photogallery.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.maktab.photogallery.utilities.ServicesUtils;

import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PollJobService extends JobService {

    private static final String TAG = "PollJobService";
    private static final int JOB_ID = 1;
    private PollTask mPollTask;

    public PollJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        mPollTask = new PollTask(params);
        mPollTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mPollTask.cancel(true);
        return false;
    }

    public static void scheduleJob(Context context, boolean isOn) {
        Log.d(TAG, "scheduleJob");
        JobScheduler jobScheduler =
                (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);

        if (isOn) {
            Log.d(TAG, "schedule On");

            ComponentName pollJobServiceName = new ComponentName(context, PollJobService.class);
            JobInfo jobInfo = new JobInfo.Builder(JOB_ID, pollJobServiceName)
                    .setPeriodic(TimeUnit.MINUTES.toMillis(15))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setPersisted(true)
                    .build();
            jobScheduler.schedule(jobInfo);
        } else {
            Log.d(TAG, "schedule Off");
            jobScheduler.cancel(JOB_ID);
        }
    }

    public static boolean isJobScheduled(Context context) {
        JobScheduler jobScheduler =
                (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);

        for (JobInfo jobInfo: jobScheduler.getAllPendingJobs()) {
            if (jobInfo.getId() == JOB_ID)
                return true;
        }

        return false;
    }

    private class PollTask extends AsyncTask<Void, Void, Void> {

        private JobParameters mParameters;

        public PollTask(JobParameters parameters) {
            mParameters = parameters;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServicesUtils.pollAndShowNotification(PollJobService.this, TAG);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            jobFinished(mParameters, false);
        }
    }
}
package com.example.rkjc.news_app_2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;

public class NewsReminderFirebaseJobService extends JobService {
    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.d("DISMISS", "------NewsReminderFirebaseJobService 1------");
        mBackgroundTask = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                Log.d("DISMISS", "------NewsReminderFirebaseJobService 2------");
                Context context = NewsReminderFirebaseJobService.this;
                ReminderTasks.executeTask(context, ReminderTasks.ACTION_NEWS_NOTIFICATION);
                ReminderTasks.executeTask(context, ReminderTasks.ACTION_NEWS_SYNC);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                Log.d("DISMISS", "------NewsReminderFirebaseJobService 3------");
                jobFinished(jobParameters, true);
            }
        };
        Log.d("DISMISS", "------NewsReminderFirebaseJobService 4------");
        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("DISMISS", "------NewsReminderFirebaseJobService 5------");
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}

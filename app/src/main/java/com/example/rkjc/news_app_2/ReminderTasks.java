package com.example.rkjc.news_app_2;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.util.Log;

import com.example.rkjc.news_app_2.utilities.NotificationUtils;

public class ReminderTasks {
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static final String ACTION_NEWS_NOTIFICATION = "news-notification";
    public static final String ACTION_NEWS_SYNC = "news-sync";

    public static void executeTask(Context context, String action) {
        Log.d("DISMISS", "------ACTION------" + action);
        if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            Log.d("DISMISS", "------IN DISMISS------");
            NotificationUtils.clearAllNotifications(context);
        } else if (ACTION_NEWS_NOTIFICATION.equals(action) ){
            Log.d("NEW_NOTIFICATION", "------IN NEWS NOTIFICATION------");
            issueNewsNotification(context);
        } else if (ACTION_NEWS_SYNC.equals(action) ){
            Log.d("NEW_SYNC", "------IN NEWS SYNC------");
            syncNews(context);
        }
    }

    private static void issueNewsNotification(Context context) {
        NotificationUtils.sendNewsNotification(context);
    }

    private static void syncNews(Context context) {
        NotificationUtils.syncDatabase(context);
    }
}

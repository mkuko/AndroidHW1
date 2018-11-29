package com.example.rkjc.news_app_2;

import android.app.IntentService;
import android.content.Intent;

public class NewsReminderIntentService extends IntentService {
    public NewsReminderIntentService() {
        super("NewsReminderIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        ReminderTasks.executeTask(this, action);
    }
}

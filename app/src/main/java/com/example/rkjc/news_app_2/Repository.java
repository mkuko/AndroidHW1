package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import java.util.List;

public class Repository {
    private NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mAllNewsItems;
    private static final String NEWS_URL = "newsURL";

    public Repository(Application application){
        NewsItemRoomDatabase db = NewsItemRoomDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.newsItemDao();
    }

    public Repository(Context context){
        NewsItemRoomDatabase db = NewsItemRoomDatabase.getDatabase(context);
        mNewsItemDao = db.newsItemDao();
    }

    public LiveData<List<NewsItem>> loadAllNewsItems() {

        try {
            return new loadAllNewsItemsAsyncTask(mNewsItemDao).execute().get();
        } catch(Exception e) {
            Log.d("interrupted", "got interrupted!");
            return null;
        }
    }

    public void insert (List<NewsItem> newsItem) {
        new insertAsyncTask(mNewsItemDao).execute(newsItem);
    }

    public void clearAll(){
        new clearAllAsyncTask(mNewsItemDao).execute();
    }

    public void syncDB () {
        new syncDBAllAsyncTask(mNewsItemDao).execute();
    }

    private class loadAllNewsItemsAsyncTask extends AsyncTask<Void, Void, LiveData<List<NewsItem>>> {
        private NewsItemDao mAsyncTaskDao;
        loadAllNewsItemsAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<NewsItem>> doInBackground(Void... params) {
            LiveData<List<NewsItem>> newsItems = mAsyncTaskDao.loadAllNewsItems();
            return newsItems;
        }
    }

    private static class insertAsyncTask extends AsyncTask<List<NewsItem>, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        insertAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<NewsItem>... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class clearAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        clearAllAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.clearAll();
            return null;
        }
    }

    private class syncDBAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        private List<NewsItem> newsItems;
        syncDBAllAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            URL url = NetworkUtils.buildURL();
            try {
                String newAPISearchResults = NetworkUtils.getResponseFromHttpUrl(url);
                newsItems = JsonUtils.parseNews(newAPISearchResults);
                if (newsItems != null) {
                    mAsyncTaskDao.clearAll();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (newsItems != null) {
                insert(newsItems);
            }
        }
    }
}

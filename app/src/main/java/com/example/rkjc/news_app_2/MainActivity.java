package com.example.rkjc.news_app_2;
// Powered by News API

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private String newAPISearchResults;
    private static final String TAG = "MainActivity";
    private static final int LOADER_ID = 1;
    private static final String NEWS_URL = "newsURL";
    private static final String NEWS_RESULTS = "newsResults";
    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mAdapter;
    private ArrayList<NewsItem> newsItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mRecyclerView = (RecyclerView)findViewById(R.id.news_recyclerview);
        mAdapter = new NewsRecyclerViewAdapter(this, newsItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState != null && savedInstanceState.containsKey(NEWS_RESULTS)){
            String newsResults = savedInstanceState.getString(NEWS_RESULTS);
            populateRecyclerView(newsResults);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(NEWS_RESULTS, newAPISearchResults);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            URL url = NetworkUtils.buildURL();
            Bundle bundle = new Bundle();
            bundle.putString(NEWS_URL, url.toString());

            NewsQueryTask task = new NewsQueryTask();
            task.execute(bundle);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void populateRecyclerView(String newsResults){
        Log.d("news results", newsResults);
        newsItems = JsonUtils.parseNews(newsResults);
        mAdapter.mNewsItems.addAll(newsItems);
        mAdapter.notifyDataSetChanged();
    }

    class NewsQueryTask extends AsyncTask<Bundle, Void, Void> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Bundle... args) {
            try {
                Bundle bundle = args[0];
                Log.d(TAG, "Getting News Api");

                String urlString = bundle.getString(NEWS_URL);
                if(urlString == null || urlString.isEmpty()){
                    return null;
                }
                URL url = new URL(urlString);
                newAPISearchResults = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressBar.setVisibility(View.GONE);
            Log.d("Post Execute", newAPISearchResults);
            populateRecyclerView(newAPISearchResults);
        }
    }
}

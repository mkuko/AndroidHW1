package com.example.rkjc.news_app_2;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String NEWS_API_BASE_URL = "https://newsapi.org/v1/articles";

    final static String PARAM_SOURCE = "source";
    final static String SOURCE = "the-next-web";

    final static String PARAM_SORTBY = "sortBy";
    final static String SORTBY = "latest";

    final static String PARAM_APIKEY = "apiKey";
    final static String APIKEY = "d278a28bd1334805a162d7d87dfb66e7";

    public static URL buildURL() {
        Uri builtUri = Uri.parse(NEWS_API_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, SOURCE)
                .appendQueryParameter(PARAM_SORTBY, SORTBY)
                .appendQueryParameter(PARAM_APIKEY, APIKEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

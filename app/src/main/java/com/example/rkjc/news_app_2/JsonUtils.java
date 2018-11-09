package com.example.rkjc.news_app_2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {
    public static ArrayList<NewsItem> parseNews(String JSONString){
        ArrayList<NewsItem> newsItemList = new ArrayList<>();
        try {
            JSONObject jObject = new JSONObject(JSONString);
            JSONArray items = jObject.getJSONArray("articles");

            for(int i = 0; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                String title = item.getString("title");
                String description = item.getString("description");
                String url = item.getString("url");
                String urlToImage = item.getString("urlToImage");
                String author  = item.getString("author");
                String date = item.getString("publishedAt");
                newsItemList.add(new NewsItem(title, description, url, urlToImage, author, date));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsItemList;
    }
}



package com.example.rkjc.news_app_2;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsItemDao {

    @Query("SELECT * FROM news_item ORDER BY date ASC")
    LiveData<List<NewsItem>> loadAllNewsItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NewsItem> items);

    @Query("DELETE FROM news_item")
    void clearAll();
}

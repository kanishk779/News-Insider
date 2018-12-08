package com.example.android.news_insider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;

import com.example.android.news_insider.ModelClasses.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 09-07-2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<Article>> {
    private List<Article> newsArticles;
    private String mUrl;
    public NewsLoader(@NonNull Context context,String url) {
        super(context);
        mUrl = url;
    }

    @Nullable
    @Override
    public List<Article> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        newsArticles = QueryUtils.fetchNewsData(mUrl);
        return newsArticles;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}

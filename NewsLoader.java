package com.example.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import androidx.annotation.Nullable;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<Topics>> {

    private static final String LOG_TAG = NewsLoader.class.getName();

    private String nurl;

    public NewsLoader(Context context, String url) {
        super(context);
        nurl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Topics> loadInBackground() {
        if(nurl == null) {
            return null;
        }
        List<Topics> topic = Utils.fetchNewsData(nurl);
        return topic;
    }
}


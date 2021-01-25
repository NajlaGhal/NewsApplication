package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<Topics> {

    public NewsAdapter(Context context, ArrayList<Topics> topicsList) {
        super(context, 0, topicsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_listitem, parent, false);
        }

        //find the position of the given news from news list
        Topics recentNews = getItem(position);

        //find and retrive title
        TextView titleView = listItemView.findViewById(R.id.title);
        titleView.setText(recentNews.gettitle());

        //Find and retrive type of news article
        TextView typeView = (TextView) listItemView.findViewById(R.id.section_type);
        typeView.setText(recentNews.getSection());

        //find and retrive web site for the news article
        TextView webView = (TextView) listItemView.findViewById(R.id.site);
        webView.setText(recentNews.getUrl());

        // find and retrive the date of the news
        Date dateObject = new Date(recentNews.getDate());
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String simpleDate = formatDate(dateObject);
        dateView.setText(simpleDate);



        return listItemView;
    }


    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(date);
    }
}



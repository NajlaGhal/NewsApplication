package com.example.newsapp;

public class Topics {

    private String title;
    private long date;
    private String url;
    private String section;
    private String writer;

    public Topics(String ntitle, long ndate, String nUrl, String sectionType, String nWriter){
        title = ntitle;
        date = ndate;
        url = nUrl;
        section = sectionType;
        writer = nWriter;
    }

    public String getUrl(){
        return url;
    }

    public long getDate(){
        return date;
    }

    public String gettitle(){
        return title;
    }

    public String getSection(){
        return section;
    }

    public String getWriter(){
        return writer;
    }
}

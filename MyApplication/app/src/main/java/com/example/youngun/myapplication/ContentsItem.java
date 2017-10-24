package com.example.youngun.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by youngun on 2017-10-18.
 */
public class ContentsItem {
    private Bitmap bitmap;
    private String title;
    private String date;
    private String code;
    private String place;

    private boolean unWatched = false;//true면 colored로 바뀜
    private boolean watched = false;//true면 colored로 바뀜


    public ContentsItem(String code,Bitmap bitmap, String title, String date, String place, boolean unWatched, boolean watched) {
        this.code = code;
        this.bitmap = bitmap;
        this.title = title;
        this.date = date;
        this.place = place;
        this.unWatched = unWatched;
        this.watched = watched;
    }


    public String getCode(){
        return code;
    }
    public String getTitle() {
        return title;
    }
    public String getPlace() {
        return place;
    }
    public String getDate() {
        return date;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public boolean isUnWatched() {
        return unWatched;
    }
    public boolean isWatched() {
        return watched;
    }
}

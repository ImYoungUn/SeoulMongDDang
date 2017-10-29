package com.example.youngun.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by youngun on 2017-10-18.
 */
public class ContentsItem implements Parcelable{
    private Bitmap bitmap;
    private String title;
    private String time;
    private String date;
    private String code;
    private String place;
    private String expectScore;
    private String url;
    private String janre;
    private String startDate;
    private String endDate;
    private boolean unWatched = false;//true면 colored로 바뀜
    private boolean watched = false;//true면 colored로 바뀜

//String janre,String startDate,String endDate는 Server DB에 저장후, 나중에 받아올 때 contentsInfo에 저장시키기 위함.
    public ContentsItem(String code, Bitmap bitmap, String title, String time, String date, String place, String Url,String expectScore,String janre,String startDate,String endDate, boolean unWatched, boolean watched) {
        this.code = code;
        this.bitmap = bitmap;
        this.title = title;
        this.time = time;
        this.date = date;
        this.place = place;
        this.unWatched = unWatched;
        this.watched = watched;
        this.url = Url;
        this.expectScore=  expectScore;
        this.janre=janre;
        this.startDate=startDate;
        this.endDate = endDate;
    }
    public ContentsItem(Bitmap bitmap, String title, String time, String date, String place) {
        this.bitmap = bitmap;
        this.title = title;
        this.time = time;
        this.date = date;
        this.place = place;
    }


    protected ContentsItem(Parcel in) {
        //bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        title = in.readString();
        time = in.readString();
        date = in.readString();
        code = in.readString();
        place = in.readString();
        expectScore = in.readString();
        url = in.readString();
        janre = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        unWatched = in.readByte() != 0;
        watched = in.readByte() != 0;
    }

    public static final Creator<ContentsItem> CREATOR = new Creator<ContentsItem>() {
        @Override
        public ContentsItem createFromParcel(Parcel in) {
            return new ContentsItem(in);
        }

        @Override
        public ContentsItem[] newArray(int size) {
            return new ContentsItem[size];
        }
    };

    public String getCode() {
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
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public String getJanre() {
        return janre;
    }

    public String getTime() {
        return time;
    }

    public String getExpectScore() {
        return expectScore;
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

    public String getUrl() {
        return url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //parcel.writeParcelable(bitmap, i);
        parcel.writeString(title);
        parcel.writeString(time);
        parcel.writeString(date);
        parcel.writeString(code);
        parcel.writeString(place);
        parcel.writeString(expectScore);
        parcel.writeString(url);
    }
}
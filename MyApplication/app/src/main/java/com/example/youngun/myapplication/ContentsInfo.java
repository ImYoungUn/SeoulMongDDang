package com.example.youngun.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by youngun on 2017-09-12.
 */
public class ContentsInfo {
    String contentsCode;
    String title;
    Bitmap contentsImage;
    String startDate;
    String endDate;
    String place;
    String time;
    String janre;

    public void setContentsCode(String code){
        this.contentsCode = code;
    }
    public void setContentsTime(String time){
        this.time = time;
    }

    public void setContentsStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setContentsEndDate(String endDate) {
        this.endDate = endDate;
    }
    public void setContentsTitle(String title){
        this.title = title;
    }
    public void setContentsJanre(String janre){
        this.janre = janre;
    }
    public void setContentsImage(Bitmap bmImg){
        this.contentsImage = bmImg;
        //ImageView.setImageBitmap(bmImg);
    }
    public void setContentsPlace(String place){
        this.place = place;
    }

    public String getTitle(){
        String temp = "("+janre+")"+title;
        temp = temp.replaceAll("&#39;", "'");
        return temp;
    }
    public String getTIme(){
        return "날짜 : " +startDate+" ~ "+endDate+" 시간 : "+time;
    }
    public String getContentsCode(){
        return contentsCode;
    }
    public String getPlace(){
        return place;
    }
    public Bitmap getContentsImage(){
        return contentsImage;
    }
}

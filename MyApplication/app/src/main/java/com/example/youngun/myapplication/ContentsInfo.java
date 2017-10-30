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
    String expectScore;
    String url;
    String homepage;

    //Information class에서 저장됨.
    public void setContentsCode(String code){
        this.contentsCode = code;
    }
    public void setContentsTime(String time){
        this.time = time;
    }
    public void setContentsHomepage(String homepage){
        this.homepage = homepage;
    }
    public void setContentsURL(String url){this.url = url;}
    public void setContentsStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setContentsEndDate(String endDate) {
        if(endDate!="")
            this.endDate = "날짜 : "+startDate + " ~ "+ endDate;
        else
            this.endDate = startDate;
    }

    public void setContentsTitle(String title){
        this.title = title;
    }
    public void setContentsJanre(String janre){
        if(janre!="")
            this.janre = "<"+janre+">";
        else
            this.janre = janre;
    }
    public void setContentsExpectScore(String expectScore){
        this.expectScore = expectScore;
    }
    public void setContentsImage(Bitmap bmImg){
        this.contentsImage = bmImg;
        //ImageView.setImageBitmap(bmImg);
    }
    public void setContentsPlace(String place){
        this.place = place;
    }

    public String getTitle(){
        String temp = janre+title;
        temp = temp.replaceAll("&#39;", "'");
        temp = temp.replaceAll("&quot;", "\"");
        temp = temp.replaceAll("&gt;", ">");
        temp = temp.replaceAll("&lt;", "<");
        temp = temp.replaceAll("&nbsp", " ");
        temp = temp.replaceAll("&amp;", "&");
        return temp;
    }
    public String getTIme(){
        if(time==null)
            return "";
        if(time.contains("시간"))
            return time;
        return " 시간 : "+time;
    }
    public String getDate(){
        return endDate;
    }
    public String getStartDate(){
        return startDate;
    }
    public String getEndDate(){
        return endDate;
    }
    public String getJanre(){
        return janre;
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
    public String getExpectScore(){return expectScore;}
    public String getUrl(){return url;}
    public String getHomepage(){return homepage;}
}

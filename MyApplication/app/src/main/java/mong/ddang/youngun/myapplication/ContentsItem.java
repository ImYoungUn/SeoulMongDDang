package mong.ddang.youngun.myapplication;

import android.graphics.Bitmap;
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
    private String homepage;
    private String rated="보고 싶은 만큼 별 주기";
    private int i;

//String janre,String startDate,String endDate는 Server DB에 저장후, 나중에 받아올 때 contentsInfo에 저장시키기 위함.
    public ContentsItem(String code, Bitmap bitmap, String title, String time, String date, String place, String Url,String expectScore,String janre,String startDate,String endDate,String homepage, int i) {
        this.code = code;
        this.bitmap = bitmap;
        this.title = title;
        this.time = time;
        this.date = date;
        this.place = place;
        this.url = Url;
        this.expectScore=  expectScore;
        this.janre=janre;
        this.startDate=startDate;
        this.endDate = endDate;
        this.homepage = homepage;
        this.i = i;
    }
    public ContentsItem(Bitmap bitmap, String title, String time, String date, String place, String homepage) {
        this.bitmap = bitmap;
        this.title = title;
        this.time = time;
        this.date = date;
        this.place = place;
        this.homepage = homepage;
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
        rated = in.readString();
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

    public int getI(){
        return i+1;
    }
    public String getRated(){
        return rated;
    }
    public void setRated(String s){
        rated = s;
    }
    public void setTitle(String title){
        this.title=title;
    }
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
    public String getHomepage(){return homepage;}


    public String getTime() {
        return time;
    }

    public String getExpectScore() {
        return expectScore;
    }

    public Bitmap getBitmap() {
        return bitmap;
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
        parcel.writeString(janre);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeString(rated);
        parcel.writeInt(i);
    }
}
package mong.ddang.youngun.myapplication;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by youngun on 2017-11-15.
 */
public class StringParsing  extends Thread implements Parcelable {
    static String recommendString;
    public ArrayList<ContentsInfo> ar;
    public ArrayList<ContentsInfo> ar3;
    public ArrayList<ContentsInfo> welcome_ar;
    ContentsInfo ci;
    String[] recommend;
    String[] famous;

    StringParsing() {
        ar = new ArrayList<>();
        ar3 = new ArrayList<>();
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //31235==예상점수==장르==타이틀==시작날짜==끝날짜==시간==장소==홈페이지==이미지==split23152==예상점수==장르==타이틀==시작날짜==끝날짜==시간==장소==홈페이지==이미지==
        //추천목록split인기목록
        String[] lists = recommendString.split("split");
        if(lists[0].isEmpty()){
            //추천 다 해서 더이상 나오는 추천이 없을 때
            recommend = null;
            famous = lists[1].split("==");
        }
        else {
            //정상적일 때
            recommend = lists[0].split("==");
            famous = lists[1].split("==");
            for (int i = 0; i < recommend.length; i+=10) {
                ci = new ContentsInfo();
                ci.setContentsCode(recommend[i]);
                ci.setContentsExpectScore(recommend[i + 1]);
                ci.setContentsJanre(recommend[i + 2]);
                ci.setContentsTitle(recommend[i + 3]);
                ci.setContentsStartDate(recommend[i + 4]);
                ci.setContentsEndDate(recommend[i + 5]);
                ci.setContentsTime(recommend[i + 6]);
                ci.setContentsPlace(recommend[i + 7]);
                ci.setContentsHomepage(recommend[i + 8]);
                ci.setContentsURL(recommend[i + 9]);
                try {
                    URL urlImage = new URL(recommend[i + 9]);
                    InputStream is2 = urlImage.openStream();
                    try {
                        ci.setContentsImage(BitmapFactory.decodeStream(is2));
                    } catch (OutOfMemoryError e) {
                        ci.setContentsImage(null);
                        Log.e("sp_OutOfMemory", "bomb");
                    }
                } catch (SocketException e) {
                    Log.e("sp_soket문제발생", "");
                } catch (UnknownHostException e) {
                    Log.e("sp_host문제발생", "");
                } catch (IOException e) {
                    Log.e("sp_IOException문제발생", "");
                }
                ar.add(ci);
            }
            Log.e("sp", "" + ar.size());
        }

        for (int i = 0; i < famous.length; i+=10) {
            ci = new ContentsInfo();
            ci.setContentsCode(famous[i]);
            ci.setContentsExpectScore(famous[i + 1]);
            ci.setContentsJanre(famous[i + 2]);
            ci.setContentsTitle(famous[i + 3]);
            ci.setContentsStartDate(famous[i + 4]);
            ci.setContentsEndDate(famous[i + 5]);
            ci.setContentsTime(famous[i + 6]);
            ci.setContentsPlace(famous[i + 7]);
            ci.setContentsHomepage(famous[i + 8]);
            ci.setContentsURL(famous[i + 9]);
            try {
                URL urlImage = null;
                urlImage = new URL(famous[i + 9]);
                InputStream is2 = urlImage.openStream();
                try {
                    ci.setContentsImage(BitmapFactory.decodeStream(is2));
                } catch (OutOfMemoryError e) {
                }
            } catch (SocketException e) {
                Log.e("soket문제발생", "");
            } catch (UnknownHostException e) {
                Log.e("host문제발생", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            ar3.add(ci);
        }
        Log.e("StringParsing", "finish");
    }
    StringParsing(String welcome) {
        welcome_ar = new ArrayList<>();
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //31235==예상점수==장르==타이틀==시작날짜==끝날짜==시간==장소==홈페이지==이미지==split23152==예상점수==장르==타이틀==시작날짜==끝날짜==시간==장소==홈페이지==이미지==
        String[] recommend = welcome.split("==");
        for (int i = 0; i < recommend.length; i += 10) {
            ci = new ContentsInfo();
            ci.setContentsCode(recommend[i]);
            ci.setContentsJanre(recommend[i + 2]);
            ci.setContentsTitle(recommend[i + 3]);
            ci.setContentsStartDate(recommend[i + 4]);
            ci.setContentsEndDate(recommend[i + 5]);
            ci.setContentsTime(recommend[i + 6]);
            ci.setContentsPlace(recommend[i + 7]);
            ci.setContentsHomepage(recommend[i + 8]);
            ci.setContentsURL(recommend[i + 9]);
            try {
                URL urlImage = new URL(recommend[i + 9]);
                InputStream is2 = urlImage.openStream();
                try {
                    ci.setContentsImage(BitmapFactory.decodeStream(is2));
                } catch (OutOfMemoryError e) {
                    ci.setContentsImage(null);
                    Log.e("sp_OutOfMemory", "bomb");
                }
            } catch (SocketException e) {
                Log.e("sp_soket문제발생", "");
            } catch (UnknownHostException e) {
                Log.e("sp_host문제발생", "");
            } catch (IOException e) {
                Log.e("sp_IOException문제발생", "");
            }
            welcome_ar.add(ci);
        }
    }

    public ArrayList<ContentsInfo> getAr(){
        return ar;
    }
    public ArrayList<ContentsInfo> getAr3(){
        return ar3;
    }
    public ArrayList<ContentsInfo> getWelcome_ar(){
        return welcome_ar;
    }


    protected StringParsing(Parcel in) {
    }
    public static final Creator<StringParsing> CREATOR = new Creator<StringParsing>() {
        @Override
        public StringParsing createFromParcel(Parcel in) {
            return new StringParsing(in);
        }

        @Override
        public StringParsing[] newArray(int size) {
            return new StringParsing[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}

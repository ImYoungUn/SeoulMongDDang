package com.example.youngun.myapplication;

import android.content.SyncStatusObserver;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by youngun on 2017-09-12.
 */
public class Information extends Thread implements Parcelable {
    static String recommendString;
    private ContentsInfo[] ci_r;
    private ContentsInfo[] ci_f;
    private ArrayList<ContentsInfo> recommendlist;
    private ArrayList<ContentsInfo> famouslist;
    private JSONpart json;
    private String[] codeList;
    private String[] famousCodeList;
    private String[] expectScoreList;
    private String[] famousScoreList;
    public int cultureSize = 30;
    public int famousSize = 10;


    Information() {
    }

    protected Information(Parcel in) {
        codeList = in.createStringArray();
        expectScoreList = in.createStringArray();
        cultureSize = in.readInt();
    }

    public static final Creator<Information> CREATOR = new Creator<Information>() {
        @Override
        public Information createFromParcel(Parcel in) {
            return new Information(in);
        }

        @Override
        public Information[] newArray(int size) {
            return new Information[size];
        }
    };

    void get_server_recommendList() {
        String page[] = Information.recommendString.split("split");
        String parse[] = page[0].split(",");
        //int ci_recomSize = Integer.parseInt(parse[parse.length-1]);
        codeList = new String[cultureSize];
        expectScoreList = new String[cultureSize];
        //30개까지만 받음
        for (int i = 0; i < cultureSize * 2; i += 2) {
            expectScoreList[i / 2] = parse[i];
            codeList[i / 2] = parse[i + 1];
        }
    }

    void get_server_famousContents() {
        String page[] = Information.recommendString.split("split");
        String parse[] = page[1].split(",");
        Log.e("Information", "famousString");
        //int ci_recomSize = Integer.parseInt(parse[parse.length-1]);
        famousCodeList = new String[famousSize];
        famousScoreList = new String[famousSize];
        //30개까지만 받음
        for (int i = 0; i < famousSize * 2; i += 2) {
            famousScoreList[i / 2] = parse[i];
            famousCodeList[i / 2] = parse[i + 1];
        }
    }

    public void makeList() {
        Log.e("Information", "getRecommendList_start");
        get_server_recommendList();
        get_server_famousContents();
        int count = getList_total_count();
        recommendlist = new ArrayList<ContentsInfo>();
        famouslist = new ArrayList<ContentsInfo>();
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            URL url = new URL("http://openapi.seoul.go.kr:8088/554a764c56796f7531364e77764250/xml/SearchConcertDetailService/1/" + count + "/");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), null);

            ci_r = new ContentsInfo[count + 1];
            ci_f = new ContentsInfo[count + 1];
            ci_r[0] = new ContentsInfo();
            ci_f[0] = new ContentsInfo();
            int parserEvent = parser.getEventType();
            String tag;
            boolean title = false, code = false, image = false, start = false, end = false, time = false, place = false, janre = false;
            boolean skip_r = false, skip_f = false, skip = false;
            int i = 0;
            int j = 0;
            while (recommendlist.size() != cultureSize) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if (tag.compareTo("CULTCODE") == 0) {
                            //Log.e("Information_cultcode : ",tag);
                            code = true;
                            skip_r = false;
                            skip_f = false;
                        }

                        //해당 cultCode가 30위 안에 없어서 skip됨. (다음 cultCode가 나올때까지.)
                        if (skip_r == true && skip_f == true) {
                            break;
                        }
                        if (tag.compareTo("CULTCODE") == 0)
                            code = true;
                        else if (tag.compareTo("CODENAME") == 0)
                            janre = true;
                        else if (tag.compareTo("TITLE") == 0)
                            title = true;
                        else if (tag.compareTo("STRTDATE") == 0) {
                            start = true;
                        } else if (tag.compareTo("END_DATE") == 0)
                            end = true;
                        else if (tag.compareTo("TIME") == 0)
                            time = true;
                        else if (tag.compareTo("PLACE") == 0)
                            place = true;
                        else if (tag.compareTo("MAIN_IMG") == 0)
                            image = true;
                        break;
                    case XmlPullParser.TEXT:
                        //tag = parser.getName();
                        if (code) {
                            if (parser.getText() == null) {
                                skip = true;
                            }
                            //순위별로 저장된 30개의 cultCode에 속하지 않으면 skip함.
                            else {
                                String cultCode = parser.getText();
                                skip_r = true;
                                skip_f = true;
                                //Log.e("Information_culcode2 : ",cultCode);
                                for (int k = 0; k < cultureSize; k++) {
                                    // Log.e("codeLis1t",codeList[j]);
                                    if (cultCode.compareTo(codeList[k]) == 0) {
                                        //Log.e("codeList2",codeList[j]);
                                        ci_r[i].setContentsCode(parser.getText());
                                        ci_r[i].setContentsExpectScore(expectScoreList[k]);
                                        skip_r = false;
                                        break;
                                    }
                                }
                                for (int k = 0; k < famousSize; k++) {
                                    if (cultCode.compareTo(famousCodeList[k]) == 0) {
                                        //Log.e("codeList2",codeList[j]);
                                        ci_f[j].setContentsCode(parser.getText());
                                        ci_f[j].setContentsExpectScore(famousScoreList[k]);
                                        skip_f = false;
                                        break;
                                    }
                                }
                            }
                        } else if (janre) {
                            if (parser.getText() == null)
                                skip = true;
                            else {
                                if (!skip_r)
                                    ci_r[i].setContentsJanre(parser.getText());
                                if (!skip_f)
                                    ci_f[j].setContentsJanre(parser.getText());
                            }
                        } else if (title) {
                            if (parser.getText() == null)
                                skip = true;
                            else {
                                if (!skip_r)
                                    ci_r[i].setContentsTitle(parser.getText());
                                if (!skip_f)
                                    ci_f[j].setContentsTitle(parser.getText());
                            }
                        } else if (start) {
                            if (parser.getText() == null)
                                skip = true;
                            else {
                                if (!skip_r)
                                    ci_r[i].setContentsStartDate(parser.getText());
                                if (!skip_f)
                                    ci_f[j].setContentsStartDate(parser.getText());
                            }
                        } else if (end) {
                            if (parser.getText() == null)
                                skip = true;
                            else {
                                if (!skip_r)
                                    ci_r[i].setContentsEndDate(parser.getText());
                                if (!skip_f)
                                    ci_f[j].setContentsEndDate(parser.getText());
                            }
                        } else if (place) {
                            if (parser.getText() == null)
                                skip = true;
                            else {

                                if (!skip_r)
                                    ci_r[i].setContentsPlace(parser.getText());
                                if (!skip_f)
                                    ci_f[j].setContentsPlace(parser.getText());
                            }
                        } else if (time) {
                            if (parser.getText() == null)
                                skip = true;
                            else {

                                if (!skip_r)
                                    ci_r[i].setContentsTime(parser.getText());
                                if (!skip_f)
                                    ci_f[j].setContentsTime(parser.getText());
                            }
                        } else if (image) {
                            if (parser.getText() == null)
                                skip = true;
                            else {
                                String tmp = parser.getText();
                                URL urlImage = new URL(tmp);
                                if (!skip_r)
                                    ci_r[i].setContentsURL(tmp);
                                InputStream is = urlImage.openStream();
                                try {
                                    if (!skip_r)
                                        ci_r[i].setContentsImage(BitmapFactory.decodeStream(is));
                                    if (!skip_f)
                                        ci_f[j].setContentsImage(BitmapFactory.decodeStream(is));
                                } catch (OutOfMemoryError e) {
                                    skip = true;
                                    Log.e("skip", tmp + "가 skip됐습니다");
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.compareTo("CULTCODE") == 0)
                            code = false;
                        else if (tag.compareTo("CODENAME") == 0) {
                            janre = false;
                        } else if (tag.compareTo("TITLE") == 0) {
                            title = false;
                        } else if (tag.compareTo("STRTDATE") == 0) {
                            start = false;
                        } else if (tag.compareTo("END_DATE") == 0) {
                            end = false;
                        } else if (tag.compareTo("TIME") == 0) {
                            time = false;
                        } else if (tag.compareTo("PLACE") == 0) {
                            place = false;
                        } else if (tag.compareTo("MAIN_IMG") == 0) {
                            image = false;
                            if (skip == false) {
                                if (!skip_r) {
                                    recommendlist.add(ci_r[i]);
                                    i++;
                                }
                                if (!skip_f) {
                                    famouslist.add(ci_f[j]);
                                    j++;
                                }
                                if (!skip_r)
                                    ci_r[i] = new ContentsInfo();
                                if (!skip_f)
                                    ci_f[j] = new ContentsInfo();
                            }
                            skip = false;
                            skip_r = false;
                            skip_f = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("Information", "getRecommendationList_finish");
        DescendingObj descendingObj = new DescendingObj();
        Collections.sort(recommendlist, descendingObj);
        Collections.sort(famouslist, descendingObj);
    }
    public ArrayList<ContentsInfo> getRecommendlist(){
        return recommendlist;
    }
    public ArrayList<ContentsInfo> getFamouslist(){
        return famouslist;
    }
    JSONpart getJson() {
        return json;
    }


    int getList_total_count() {
        //url에서 총 컨텐츠 갯수를 알려줌.
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            //String key = "554a764c56796f7531364e77764250";
            URL url = new URL("http://openapi.seoul.go.kr:8088/554a764c56796f7531364e77764250/xml/SearchConcertDetailService/1/2/");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            String tag;
            String count;
            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                if (parserEvent == XmlPullParser.START_TAG) {
                    tag = parser.getName();
                    if (tag.compareTo("list_total_count") == 0) {
                        count = parser.nextText();
                        return Integer.parseInt(count);
                    }
                }
                parserEvent = parser.next();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(codeList);
        parcel.writeStringArray(expectScoreList);
        parcel.writeInt(cultureSize);
    }
}

class DescendingObj implements Comparator<ContentsInfo> {

    @Override
    public int compare(ContentsInfo contentsInfo, ContentsInfo t1) {
        return t1.getExpectScore().compareTo(contentsInfo.getExpectScore());
    }
}

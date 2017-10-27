package com.example.youngun.myapplication;

import android.content.SyncStatusObserver;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by youngun on 2017-09-12.
 */
public class Information extends Thread {
    static String recommendString;
    private ContentsInfo[] ci;
    private ArrayList<ContentsInfo> list;
    private JSONpart json;

    int getList_total_count() {
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            //String key = "554a764c56796f7531364e77764250";
            URL url = new URL("http://openapi.seoul.go.kr:8088/554a764c56796f7531364e77764250/xml/SearchConcertDetailService/1/5/");
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

    Information() {
        json = new JSONpart();
        int count = getList_total_count();
        list = new ArrayList<ContentsInfo>();
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            URL url = new URL("http://openapi.seoul.go.kr:8088/554a764c56796f7531364e77764250/xml/SearchConcertDetailService/1/" + count + "/");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), null);

            ci = new ContentsInfo[count + 1];
            ci[0] = new ContentsInfo();
            int parserEvent = parser.getEventType();
            String tag;
            boolean title = false, code = false, image = false, start = false, end = false, time = false, place = false, janre = false;
            boolean skip = false;
            int i = 0;
            while (i < 30) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
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
                            if (parser.getText() == null)
                                skip = true;
                            else
                                ci[i].setContentsCode(parser.getText());
                        } else if (janre) {
                            if (parser.getText() == null)
                                skip = true;
                            else
                                ci[i].setContentsJanre(parser.getText());
                        } else if (title) {
                            if (parser.getText() == null)
                                skip = true;
                            else
                                ci[i].setContentsTitle(parser.getText());
                        } else if (start) {
                            if (parser.getText() == null)
                                skip = true;
                            else
                                ci[i].setContentsStartDate(parser.getText());
                        } else if (end) {
                            if (parser.getText() == null)
                                skip = true;
                            else
                                ci[i].setContentsEndDate(parser.getText());
                        } else if (place) {
                            if (parser.getText() == null)
                                skip = true;
                            else
                                ci[i].setContentsPlace(parser.getText());
                        } else if (time) {
                            if (parser.getText() == null)
                                skip = true;
                            else
                                ci[i].setContentsTime(parser.getText());
                        } else if (image) {
                            if (parser.getText() == null)
                                skip = true;
                            else {
                                String tmp = parser.getText();
                                URL urlImage = new URL(tmp);
                                InputStream is = urlImage.openStream();
                                try {
                                    ci[i].setContentsImage(BitmapFactory.decodeStream(is));
                                }catch (OutOfMemoryError e){
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
                                list.add(ci[i]);
                                json.addCode(ci[i].getContentsCode());
                                i++;
                                ci[i] = new ContentsInfo();
                            }
                            skip = false;
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

    }
    JSONpart getJson(){
        return json;
    }

    ArrayList<ContentsInfo> getList() {
        return list;
    }

    ArrayList<ContentsInfo> getRecommendationList(){
        String parse[] = Information.recommendString.split(",");
        int ci_recomSize = Integer.parseInt(parse[parse.length-1]);
        ContentsInfo ci_recom[] = new ContentsInfo[ci_recomSize];
        ArrayList<ContentsInfo> list_recom = new ArrayList<>();
        for(int i=0;i<parse.length-2;i+=2){
            for(int j=0;j<ci.length;i++){
                if(parse[i].compareTo(ci[j].getContentsCode())==0){
                    ci_recom[i] = ci[j];
                    ci_recom[i].setContentsExpectScore(parse[i+1]);
                    list_recom.add(ci_recom[i]);
                    break;
                }
            }
        }
        return list_recom;
    }
    private BitmapFactory.Options getBitmapSize(File imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        return options;
    }


}

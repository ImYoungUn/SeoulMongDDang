package mong.ddang.youngun.myapplication;

import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by youngun on 2017-10-29.
 */
public class Save extends Thread{
    static String saveString;
    public ArrayList<ContentsInfo> getSaveList() throws JSONException, IOException {
        //저장했던 찜목록
        ArrayList<ContentsInfo> list = new ArrayList<ContentsInfo>();
        if (Save.saveString != null) {
            //Log.e("Save", saveString+"...?");
            String temp = Save.saveString.replaceAll("\"", "'");
            JSONArray jarray = new JSONArray(temp);
            ContentsInfo ci[] = new ContentsInfo[jarray.length()];
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject job = jarray.getJSONObject(i);

                String tmp = job.getString("contentsImage");
                Log.e("Save", tmp);
                URL urlImage = new URL(tmp);
                URLConnection conn = urlImage.openConnection();
                conn.connect();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                InputStream is = urlImage.openStream();
                if (BitmapFactory.decodeStream(is) != null) {
                    ci[i] = new ContentsInfo();
                    ci[i].setContentsStartDate(job.getString("startDate"));
                    ci[i].setContentsEndDate("");
                    ci[i].setContentsJanre("");
                    ci[i].setContentsTitle(job.getString("title"));
                    ci[i].setContentsTime(job.getString("time"));
                    ci[i].setContentsPlace(job.getString("place"));
                    ci[i].setContentsImage(BitmapFactory.decodeStream(bis));
                    ci[i].setContentsHomepage(job.getString("homePage"));
                    list.add(ci[i]);
                }
            }
        }
        return list;
        /*
        [{
            "title":"영화_달빛궁궐", "contentsImage":
            "HTTP://CULTURE.SEOUL.GO.KR/data/ci/20170926185737.JPG", "startDate":
            "날짜 : 2017-10-31 ~ 2017-10-31", "place":"강서문화원  2층공연장", "time":
            " 시간 : 화요일 오후4시~", "facebook_id":"1581059065303931"
        },{
            "title":"콘서트_우리은행과 함께하는 서울시향 우리동네음악회", "contentsImage":
            "HTTP://CULTURE.SEOUL.GO.KR/data/ci/20171016151844.jpg", "startDate":
            "날짜 : 2017-11-01 ~ 2017-11-01", "place":"노원어울림극장", "time":
            " 시간 : 오후 7시30분", "facebook_id":"1581059065303931"
        }
        ,{
            "title":"콘서트_우리은행과 함께하는 서울시향 우리동네음악회", "contentsImage":
            "HTTP://CULTURE.SEOUL.GO.KR/data/ci/20171016151844.jpg", "startDate":
            "날짜 : 2017-11-01 ~ 2017-11-01", "place":"노원어울림극장", "time":
            " 시간 : 오후 7시30분", "facebook_id":"1581059065303931"
        },{
            "title":"클래식_첼리스타 첼로앙상블과 함께하는 EASY CLASSIC", "contentsImage":
            "http://www.sfac.or.kr/upload/artsnet/program/6D2AA57C-8379-46F4-A60A-271360D24D14.jpg", "startDate":
            "날짜 : 2017-10-29 ~ 2017-10-29", "place":"예술의전당 IBK챔버홀", "time":
            " 시간 : 오후 8시", "facebook_id":"1581059065303931"
        }
        ,{
            "title":"문화교양/강좌_서대문 어린이 잔디마당", "contentsImage":
            "http://www.youthnavi.com/data/yseoul_img/TOSelL6Jz32u1bvIUirAJJ3hFxVtVy.JPG", "startDate":
            "날짜 : 2017-10-01 ~ 2017-10-31", "place":"서대문 어린이 잔디마당", "time":"", "facebook_id":
            "1581059065303931"
        }]
        */
    }
}

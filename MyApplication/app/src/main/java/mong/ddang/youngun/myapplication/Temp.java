package mong.ddang.youngun.myapplication;

import android.os.Build;
import android.os.StrictMode;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by youngun on 2017-10-19.
 */
public class Temp extends Thread {
    public InputStream getUrl() {
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        URL urlImage = null;
        try {
            urlImage = new URL("http://www.culture.go.kr/upload/rdf/17/10/show_201710129202102644.JPG");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStream is = null;
        try {
            is = urlImage.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }
}

package com.example.youngun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by youngun on 2017-10-31.
 */
public class ProgressBarActivity extends AppCompatActivity {
    ProgressBarActivity progressBarActivity;
    ProgressBar bar;
    String cultId;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1003) {
            if (data != null) {
                Intent intent = new Intent();
                intent.putExtra("NotNull", "true");
                setResult(1003,intent);
                finish();
            }
            setResult(1004);
            finish();
        }
        setResult(1004);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);
        progressBarActivity = this;

        bar = (ProgressBar) findViewById(R.id.progressBar_bar);
        bar.setVisibility(View.GONE);
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                bar.setVisibility(View.VISIBLE);
            }

            protected String doInBackground(String... urls) {
                try {
                    Log.e("Server", "doinBackground");
                    SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
                    String id = sp.getString("id", "");
                    Intent intent = getIntent();
                    cultId = intent.getStringExtra("cultId");
                    //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("cultId", cultId);
                    jsonObject.accumulate("id", id);//faceBookId

                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        //URL url = new URL("http://192.168.25.16:3000/users");
                        URL url = new URL(urls[0]);
                        //연결을 함
                        con = (HttpURLConnection) url.openConnection();

                        con.setRequestMethod("POST");//POST방식으로 보냄
                        con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                        con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                        con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                        con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                        con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                        con.connect();

                        //서버로 보내기위해서 스트림 만듬
                        OutputStream outStream = con.getOutputStream();
                        //버퍼를 생성하고 넣음
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                        writer.write(jsonObject.toString());
                        writer.flush();
                        writer.close();//버퍼를 받아줌

                        //서버로 부터 데이터를 받음
                        InputStream stream = con.getInputStream();

                        reader = new BufferedReader(new InputStreamReader(stream));

                        StringBuffer buffer = new StringBuffer();

                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            buffer.append(line);
                        }

                        return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (con != null) {
                            con.disconnect();
                        }
                        try {
                            if (reader != null) {
                                reader.close();//버퍼를 닫아줌
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }


            @Override
            public void onPostExecute(String result) {
                super.onPostExecute(result);
                Intent intent = new Intent(progressBarActivity, CommentActivity.class);
                //Log.e("Progress_", result);
                //main.startActivityForResult(intent, REQEST_CODE_RATING1);
                intent.putExtra("cultId", cultId);
                intent.putExtra("result",result);
                startActivityForResult(intent,1003);
            }
        }.execute("http://18.221.180.219:3000/getComment");
    }
}

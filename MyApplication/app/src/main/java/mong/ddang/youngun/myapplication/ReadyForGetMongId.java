package mong.ddang.youngun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.youngun.myapplication.R;

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

public class ReadyForGetMongId extends Activity {
    static String NewUser = null;
    static String Pharase = null;
    ReadyForGetMongId r;
    ProgressBar bar;
    StringParsing stringParsing;
    boolean saveCheck;//불가
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //서버에서 첫 로그인시 MongId를 받아 오는 속도차가 있어서 해당 Activity가 필요하며,
        //첫 사용자에게는 선별된 x개의 contents를 검사받도록 제공해야 한다.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_for_recommend_progress);
        saveCheck = false;
        r = this;
        SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        String name = sp.getString("name","고객");

        bar = (ProgressBar) findViewById(R.id.progressBar_ready);
       // bar.setVisibility(View.GONE);


        while (true) {
             Log.e("rfgm", NewUser);
            if (NewUser != null) {
                bar.setVisibility(View.VISIBLE);
                getPharase();
                break;
            }
        }
    }


    public void getPharase(){
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected String doInBackground(String... urls) {
                SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                Log.e("ReadyForGet_mongId:", LoginActivity.mongId);
                editor.putString("mongId", LoginActivity.mongId);
                editor.apply();
                String id = sp.getString("id", "");

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("mongId", LoginActivity.mongId);
                    jsonObject.accumulate("id", id);//faceBookId
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 <- 이 값이 onPostExecute에서 result로 나옴.

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
                return null;
            }


            @Override
            public void onPostExecute(String result) {
                TextView pharase[] = {(TextView)findViewById(R.id.phrase1),(TextView)findViewById(R.id.phrase2),(TextView)findViewById(R.id.phrase3),(TextView)findViewById(R.id.phrase4),(TextView)findViewById(R.id.phrase5)};

                String ph[] =  result.split(",");
                int count=0;
                for(int i=0; i<ph.length;i++){
                    if(ph[i]=="") {
                        ph[i] = "";
                        pharase[i-1].setTextSize(15);
                    }
                    pharase[i].setText(ph[i]);
                }
                getRecommendString();
            }
        }.execute("http://18.221.180.219:3000/pharase");
    }











    public void getRecommendString(){
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected String doInBackground(String... urls) {
                SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                Log.e("ReadyForGet_mongId:", LoginActivity.mongId);
                editor.putString("mongId", LoginActivity.mongId);
                editor.apply();
                String id = sp.getString("id", "");

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("mongId", LoginActivity.mongId);
                    jsonObject.accumulate("id", id);//faceBookId
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 <- 이 값이 onPostExecute에서 result로 나옴.

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
                return null;
            }


            @Override
            public void onPostExecute(String result) {
                Log.e("ReadyForGet", result);
                StringParsing.recommendString = result;
                if (result.contains("에러")) {
                    //공공데이터 api상의 에러시 적용.
                    Intent intent = new Intent(r, ErrorActivity.class);
                    intent.putExtra("errorCode", result);
                    startActivity(intent);
                    finish();
                }
                else {
                    getSaveInBackGround();
                }
            }
        }.execute("http://18.221.180.219:3000/recommend");
    }













    public void getSaveInBackGround() {
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected String doInBackground(String... urls) {
                SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.apply();//?
                String id = sp.getString("id", "");

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("id", id);//faceBookId
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    //URL url = new URL("http://192.168.25.16:3000/getSave");
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
                return null;
            }


            @Override
            public void onPostExecute(String result) {
                Log.e("second", "s");
                Save.saveString = result;
                Intent intent = new Intent(r, HomeActivity.class);
                //intent.putExtra("stringParsing", stringParsing);
                //Log.e("Progress_", result);
                //main.startActivityForResult(intent, REQEST_CODE_RATING1);
                startActivity(intent);
                finish();
            }
        }.execute("http://18.221.180.219:3000/getSave");
    }

}


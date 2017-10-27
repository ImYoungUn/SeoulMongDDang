package com.example.youngun.myapplication;

/**
 * Created by youngun on 2017-10-08.
 */

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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

public class Server{

    private String func;
    private String item;
    private String item2;
    private String mongId;
    private String result;
    private JSONTask js;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient client;
    //private LoginActivity loginActivity;
    //private HomeActivity homeActivity;
    //************임시 사용자 형성*****************
    protected void register(HomeActivity loginActivity) {
        //버튼이 클릭되면 여기 리스너로 옴
        js = (JSONTask) new JSONTask().execute("http://18.221.180.219:3000/register");//AsyncTask 시작시킴
        client = new GoogleApiClient.Builder(loginActivity).addApi(AppIndex.API).build();
        onStart();

        onStop();
    }

    public String getResultServer(){
        return js.innerResult;
    }
    //************임시 사용자 형성*****************
    //실행할때는 항상 setFunction을 하여서 getFunction이 가능하도록 해야 함.
    protected void register(LoginActivity loginActivity) {
        //버튼이 클릭되면 여기 리스너로 옴
        new JSONTask().execute("http://18.221.180.219:3000/register");//AsyncTask 시작시킴
        client = new GoogleApiClient.Builder(loginActivity).addApi(AppIndex.API).build();
        onStart();

        onStop();
    }
    //home - 왼쪽 버튼
    protected void insert(RatingActivity1 ratingActivity1) {
        //버튼이 클릭되면 여기 리스너로 옴
        new JSONTask().execute("http://18.221.180.219:3000/insert");//AsyncTask 시작시킴
        client = new GoogleApiClient.Builder(ratingActivity1).addApi(AppIndex.API).build();
        onStart();

        onStop();
    }
    //home - 오른쪽 버튼
    protected void insert(RatingActivity2 ratingActivity2) {
        //버튼이 클릭되면 여기 리스너로 옴
        new JSONTask().execute("http://18.221.180.219:3000/insert");//AsyncTask 시작시킴
        client = new GoogleApiClient.Builder(ratingActivity2).addApi(AppIndex.API).build();
        onStart();

        onStop();
    }
    protected void recommend(LoginActivity loginActivity) {
        //버튼이 클릭되면 여기 리스너로 옴
        new JSONTask().execute("http://18.221.180.219:3000/recommend");//AsyncTask 시작시킴
        client = new GoogleApiClient.Builder(loginActivity).addApi(AppIndex.API).build();
        onStart();

        onStop();
    }
    public void onStart() {
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.youngun.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    public void onStop() {
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.youngun.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void setFunction(String func, String item, String item2) {
        this.func = func;
        this.item = item;
        this.item2 = item2;
    }
    public void setFunction(String func, String item, String item2, String mongId) {
        this.func = func;
        this.item = item;
        this.item2 = item2;
        this.mongId = mongId;
    }
    public String getFunction(){
        if(func.compareTo("register")==0)
                return "name";
        else if(func.compareTo("insert")==0)
                return "rate";
        else if(func.compareTo("recommend")==0)
                return "json";
        return "Null";
    }


    public class JSONTask extends AsyncTask<String, String, String> {

        private String innerResult;
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate(getFunction(), item);
                jsonObject.accumulate("id", item2);
                Log.e("Server_mongId",LoginActivity.mongId);
                //mongId는 현재 register할때 서버에서 받아오고, insert를 할때 서버로 보낸다.
                //recommend할때는 jason에 미리 들어가있다.
                jsonObject.accumulate("mongId", LoginActivity.mongId);


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
            if(result==null){
                Log.e("Server","아직 아무 result 없음");
                return;
            }

            if(result.length()<5) {
                Log.e("Server", "result");
                LoginActivity.mongId = result;
            }
            else {
                Log.e("Server","result");
                Information.recommendString = result;
            }
        }
    }
}



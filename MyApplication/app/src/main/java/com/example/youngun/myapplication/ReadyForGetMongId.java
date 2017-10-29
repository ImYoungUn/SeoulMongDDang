package com.example.youngun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

public class ReadyForGetMongId extends Activity {

    static String NewUser;
    Information info ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //서버에서 첫 로그인시 MongId를 받아 오는 속도차가 있어서 해당 Activity가 필요하며,
        //첫 사용자에게는 선별된 x개의 contents를 검사받도록 제공해야 한다.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_for_recommend_progress);

        Button gettingInbtn = (Button) findViewById(R.id.gettingInButton);
        gettingInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NewUser!=null) {
                    if (NewUser.compareTo("true") == 0) {
                        //첫 사용자
                        getUserPerference();
                        info = loading();
                        Intent intent2 = new Intent(ReadyForGetMongId.this, LoadingActivity.class);
                        intent2.putExtra("info", info);
                        startActivity(intent2);
                        finish();
                    } else {
                        //mongId 받고 바로 넘어가기
                        info = loading();
                        Intent intent2 = new Intent(ReadyForGetMongId.this, LoadingActivity.class);
                        intent2.putExtra("info", info);
                        startActivity(intent2);
                        finish();
                    }
                }
            }
        });




    }
    public String getUserPerference(){
        String result = null;
        return result;
    }
    public Information loading() {
        Information info = new Information();
        SharedPreferences sp = getSharedPreferences("myFile",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Log.e("ReadyForGet_mongId:",LoginActivity.mongId);
        editor.putString("mongId",LoginActivity.mongId);
        editor.apply();

        //JSONpart클래스 실행하여 recommend시 필요한 정보 server로 넘겨주기(받아오는건 HomeActivity에서)
        JSONpart json = info.getJson();
        try {
            json.addIdAndReady(LoginActivity.mongId, this);
            json.send("firstRecommend");
            Log.e("Home", "send Recommend");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

}


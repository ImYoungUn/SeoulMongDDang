package com.example.youngun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

public class LoadingActivity extends AppCompatActivity {
    Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        intent1 = getIntent();
        Button b = (Button) findViewById(R.id.loadingButton);
        Toast.makeText(getApplicationContext(), "버튼을 누르시면 추천 시스템을 가동 합니다.\n 조금만 기다려 주세요!^^", Toast.LENGTH_SHORT).show();
        SharedPreferences sp = getSharedPreferences("myFile",Activity.MODE_PRIVATE);
        Server server = new Server();
        server.setFunction("getSave","non",sp.getString("id",""));
        server.getSave(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Information.recommendString!=null) {
                    Information inf = intent1.getParcelableExtra("info");
                    Intent in = new Intent(LoadingActivity.this, HomeActivity.class);
                    in.putExtra("info", inf);
                    startActivity(in);
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Button b = (Button) findViewById(R.id.loadingButton);
        Toast.makeText(getApplicationContext(), "버튼을 누르시면 추천 시스템을 가동 합니다.\n 조금만 기다려 주세요!^^", Toast.LENGTH_SHORT).show();
        Log.e("LoadingActivity","RESULT");
        Server server = new Server();
        server.setFunction("getSave","non",intent1.getStringExtra("id"));
        server.getSave(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Information.recommendString!=null) {
                    Information inf = intent1.getParcelableExtra("info");
                    Intent in = new Intent(LoadingActivity.this, HomeActivity.class);
                    in.putExtra("info", inf);
                    startActivity(in);
                }
            }
        });
    }
}

package com.example.youngun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingActivity1 extends AppCompatActivity {

    TextView textView;
    RatingBar ratingBar;
    Button b;
    float score;
    TextView scoreText;
    RatingActivity1 ra;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating1);
        ra = this;

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        code = intent.getStringExtra("code");
        textView = (TextView) findViewById(R.id.ratingTitle);
        textView.setText(title);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        scoreText = (TextView) findViewById(R.id.scoreText);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                scoreText.setText("점수 : " + rating);
                score = rating;
            }
        });


        b = (Button) findViewById(R.id.ratingButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                Log.e("SCORE_RATINGACTIVITY1", Float.toString(score));
                SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
                String id = sp.getString("id","");
                //서버에 score, id 보내기
                String send = Float.toString(score)+":"+code+":0";
                Server server = new Server();
                server.setFunction("insert",send,id);
                server.insert(ra);
                setResult(RESULT_OK, intent1);
                finish();
            }
        });
    }
}

package com.example.youngun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by youngun on 2017-10-22.
 */
public class RatingActivity2 extends AppCompatActivity {

    TextView textView;
    RatingBar ratingBar;
    Button b;
    float score = -1;
    TextView scoreText;
    RatingActivity2 ra;
    String code;
    int unWatchedButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating1);
        ra = this;

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        code = intent.getStringExtra("code");
        unWatchedButtonId = intent.getIntExtra("unWatched", 0);
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
        //점수를 체크한 후에 버튼을 눌러야 보내짐.
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (score != -1) {
                    Intent intent1 = new Intent(getApplicationContext(), HomeActivity.class);
                    SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
                    String id = sp.getString("id", "");
                    //서버에 score, id 보내기
                    String send = Float.toString(score) + ":" + code + ":0";
                    Server server = new Server();
                    server.setFunction("insert", send, id);
                    server.insert(ra);
                    intent1.putExtra("score", Float.toString(score));
                    intent1.putExtra("unWatched", unWatchedButtonId);
                    //Result_Ok 정보와 intent1 보내기
                    setResult(RESULT_OK, intent1);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "기존 점수를 유지하려면, 뒤로가기를 누르세요.", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}

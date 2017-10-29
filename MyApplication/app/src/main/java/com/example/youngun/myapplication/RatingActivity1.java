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
import android.widget.Toast;

public class RatingActivity1 extends AppCompatActivity {

    TextView textView;
    RatingBar ratingBar;
    Button rateButton;
    Button saveButton;
    float score = -1;
    TextView scoreText;
    RatingActivity1 ra;
    String code;
    int unWatchedButtonId;
    ContentsItem contentsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating1);
        ra = this;
Log.e("ratingActivity","Rating");
        Intent intent = getIntent();
        //contentsView에서 가져온 해당 contentsItem
        contentsItem = intent.getParcelableExtra("contentsItem");
        //final String title = intent.getStringExtra("title");
        Log.e("RatingActivity",contentsItem.getTitle());
        String title = contentsItem.getTitle();
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

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
                String id = sp.getString("id","");
                Server server = new Server();
                server.setFunction("save",contentsItem,id);
                server.save(ra);
                Toast.makeText(getApplicationContext(), "'찜 목록'에 저장되었습니다!^^", Toast.LENGTH_SHORT).show();
            }
        });

       rateButton = (Button) findViewById(R.id.ratingButton);
        //점수를 체크한 후에 버튼을 눌러야 보내짐.
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (score != -1) {
                    Intent intent1 = new Intent(getApplicationContext(), HomeActivity.class);
                    SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
                    Log.e("사용자",LoginActivity.mongId);
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

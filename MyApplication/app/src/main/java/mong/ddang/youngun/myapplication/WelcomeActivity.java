package mong.ddang.youngun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youngun.myapplication.R;

public class WelcomeActivity extends AppCompatActivity {

    WelcomeActivity wActivity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Log.e("Welcome", "onCreate");

        ImageButton welcomeButton = (ImageButton) findViewById(R.id.welcomeButton);
        TextView welcomeText = (TextView) findViewById(R.id.welcomeText);
        TextView welcomeComment = (TextView) findViewById(R.id.welcomeComment2);
        TextView welcomeComment2 = (TextView) findViewById(R.id.welcomeComment5);

        SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        String name = sp.getString("name", "고객");
        String comm = name + "님의 성향을 먼저 알아보겠습니다.";
        String comm2 = "혹시 보고싶은 컨텐츠가 있으면 '찜해놓기!'를 눌러 저장하세요";

        welcomeText.setSelected(true);
        welcomeComment.setText(comm);
        welcomeComment2.setText(comm2);

        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NewUser는 시작을 하기 위해 고객의 mongId를 발급받는 작업이 완료 되었는지를 검사.
                if (ReadyForGetMongId.NewUser != null) {
                    if (ReadyForGetMongId.NewUser.compareTo("first") == 0) {
                        Intent intent = new Intent(wActivity, WelcomeRatingDialog.class);
                        startActivityForResult(intent, 1);
                    } else if (ReadyForGetMongId.NewUser.compareTo("rated") == 0) {
                        Toast.makeText(getApplicationContext(), "이미 다 하셨었군요! 추천페이지로 넘어갑니다.", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(wActivity, ReadyForGetMongId.class);
                        startActivity(intent1);
                        finish();
                    }
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        final int REQEST_CODE_RATING1 = 1001;
        final int REQEST_CODE_RATING3 = 1003;
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQEST_CODE_RATING1) {
            //점수를 다 주었을 때
            Intent intent1 = new Intent(this, ReadyForGetMongId.class);
            startActivity(intent1);
            finish();
        }
        if (resultCode == RESULT_OK) {
            Log.e("RESULT_OK", "good");
            Intent intent1 = new Intent(this, ReadyForGetMongId.class);
            startActivity(intent1);
            finish();
        }
    }

}

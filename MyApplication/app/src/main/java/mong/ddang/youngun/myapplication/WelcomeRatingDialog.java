package mong.ddang.youngun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youngun.myapplication.R;

import java.util.ArrayList;


/**
 * Created by M on 2017-12-13.
 */

public class WelcomeRatingDialog extends AppCompatActivity {
    WelcomeRatingDialog wrd = this;
    String id;
    ImageView imageViewButton;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView time;
    TextView scoreText;
    HomeActivity main;
    RatingBar ratingBar;
    Button rate;
    Button save;
    int count;
    ArrayList<ContentsInfo> ar;
    String welcomeString = "96291====전시/미술==흥인지문, 왕을 배웅하다==2017-09-07==2017-12-17==평일 9:00-19:00 , 토·일·공휴일 09:00-18:00==한양도성박물관 기획전시실(성동구)==http://www.museum.seoul.kr/==HTTP://CULTURE.SEOUL.GO.KR/data/ci/20171030105615.jpg==96417====전시/미술==그래피티 전시 K-RAFFITI 2017 : The New Wave 展==2017-10-06==2017-12-13==11:00~20:00 (입장마감 19:30) *휴관일 11/8(수)~11/12(일)==언더스탠드에비뉴 아트스탠드(성동구)==http://www.understandavenue.com/art/program_now_detail?idx=37==HTTP://CULTURE.SEOUL.GO.KR/data/ci/20171107150936.jpg==96685====전시/미술==다빈치 얼라이브/천재의 공간==2017-11-03==2018-03-04==9시~18시(매주 월요일 정기휴관)==용산전쟁기념관 기획전시실(용산구)==https://www.warmemo.or.kr/newwm/sub05/sub05_03_view.jsp?noteid=38241==HTTP://CULTURE.SEOUL.GO.KR/data/ci/20171124095605.jpg==96556====클래식==베토벤 교향곡 제9번 + ①==2017-12-21==2017-12-21==20:00==예술의전당 콘서트홀(서초구)==http://www.seoulphil.or.kr/perform/concert/detail.do?idx=1105==HTTP://CULTURE.SEOUL.GO.KR/data/ci/20171115134611.jpg==96197====뮤지컬/오페라==SISTER ACT 내한공연(용산구)==2017-11-24==2018-01-21==화목금 8시/수 3시, 8시, 토일공휴일 2시, 7시==블루스퀘어 인터파크홀==http://www.bluesquare.kr/Goods/PerformanceDetail.asp?PlayNo=108391&m_menu=performance&s_menu=info==HTTP://CULTURE.SEOUL.GO.KR/data/ci/20171024175530.jpg==89444====전시/미술==군기시유적전시실==2017-01-01==2020-12-31==09:00-21:00==시민청 군기시유적전시실(B1)(중구)==http://seoulcitizenshall.kr/nr/?c=2/12/58&uid=2==HTTP://CULTURE.SEOUL.GO.KR/data/ci/20170710170213.jpg==96659====콘서트==자우림 연말콘서트==2017-12-22==2017-12-24==2017년 12월 23일(토) 7PM / 12월 24일(일) 6PM==블루스퀘어 아이마켓홀(용산구)==http://www.bluesquare.kr/Goods/PerformanceDetail.asp?PlayNo=116750&m_menu=performance&s_menu=info==HTTP://CULTURE.SEOUL.GO.KR/data/ci/20171123132011.jpg";
    float score = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome_rating_dialog);
        Log.e("WelcomeRate", "onCreate");


        SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        id = sp.getString("id","0");

        imageViewButton = (ImageView) findViewById(R.id.imageView_button);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        rate = (Button) findViewById(R.id.ratingButton);
        save = (Button) findViewById(R.id.saveButton);
        textView1 = (TextView) findViewById(R.id.titleText);
        textView2 = (TextView) findViewById(R.id.dateText);
        time = (TextView) findViewById(R.id.timeText);
        textView3 = (TextView) findViewById(R.id.placeText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                scoreText.setText("점수 : " + rating);
                score = rating;
            }
        });
        count = 0;
        ar = new StringParsing(welcomeString).getWelcome_ar();
        setView();
    }

    public void setView() {
        ContentsInfo ci = ar.get(count);
        ContentsItem contentsItem = new ContentsItem(ar.get(count).getContentsCode(), ar.get(count).getContentsImage(), ar.get(count).getTitle(), ar.get(count).getTIme(), ar.get(count).getDate(), ar.get(count).getPlace(), ar.get(count).getUrl(), ar.get(count).getExpectScore(), ar.get(count).getJanre(), ar.get(count).getStartDate(), ar.get(count).getEndDate(), ar.get(count).getHomepage(), 0);
        imageViewButton.setImageBitmap(ci.getContentsImage());

        textView1.setText(ci.getTitle());
        textView2.setText(ci.getDate());
        textView3.setText(ci.getPlace());
        time.setText(ci.getTIme());
        ratingBar.setRating(0);
        scoreText.setText("");
        new ButtonClass(rate, save, contentsItem);
    }

    public class ButtonClass {
        ButtonClass(Button rateButton, Button saveButton, final ContentsItem contentsItem) {
            imageViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contentsItem.getUrl()));
                    startActivity(intent);
                }
            });
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
                    String id = sp.getString("id", "");
                    Server server = new Server();
                    server.setFunction("save", contentsItem, id);
                    server.save(wrd);
                    Toast.makeText(getApplicationContext(), "'찜 목록'에 저장되었습니다!^^", Toast.LENGTH_SHORT).show();
                }
            });

            //점수를 체크한 후에 버튼을 눌러야 보내짐.
            rateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (score != -1) {
                        //서버에 score, id 보내기
                        String send = Float.toString(score) + ":" + contentsItem.getCode() + ":0";
                        Server server = new Server();
                        server.setFunction("insert", send, id);
                        server.insert(wrd);

                        if (++count < ar.size()) {
                            //다음 화면 보여주기
                            setView();
                        } else {
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                }

            });
        }
    }
}

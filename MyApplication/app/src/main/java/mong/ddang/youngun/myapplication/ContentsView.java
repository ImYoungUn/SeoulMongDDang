package mong.ddang.youngun.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by youngun on 2017-10-18.
 */
public class ContentsView extends LinearLayout {
    static String comments;
    ContentsView contentsView;
    ContentsItem contentsItem;
    ImageView imageViewButton;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView time;
    TextView expection;
    HomeActivity main;
    Button rate;
    Button save;
    ImageButton comment;
    String url;
    RatingBar ratingBar;
    TextView ratingText;
    float ratingScore;
    String id;

    public ContentsView(Context context, ContentsItem contentsItem, HomeActivity main, String page, String facebook_id) {
        super(context);
        contentsView = this;
        id = facebook_id;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (page.compareTo("recommend") == 0 || page.compareTo("famous") == 0)
            inflater.inflate(R.layout.activity_contentlist, this, true);
        else if (page.compareTo("save") == 0) {
            inflater.inflate(R.layout.activity_savelist, this, true);
        }

        imageViewButton = (ImageView) findViewById(R.id.imageView_button);
        rate = (Button) findViewById(R.id.rateButton);
        save = (Button) findViewById(R.id.saveButton);
        textView1 = (TextView) findViewById(R.id.titleText);
        textView2 = (TextView) findViewById(R.id.dateText);
        time = (TextView) findViewById(R.id.timeText);
        textView3 = (TextView) findViewById(R.id.placeText);
        expection = (TextView) findViewById(R.id.expectScore);
        comment = (ImageButton) findViewById(R.id.comment);
        ratingText = (TextView) findViewById(R.id.scoreText);

        this.main = main;
        //changeToNew(contentsItem, page);
    }

    public void changeToNew(ContentsItem contentsItem1, String page, int position) {
        this.contentsItem = contentsItem1;
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);//ratingBar 초기화
        if (page.compareTo("recommend") == 0 || page.compareTo("famous") == 0) {
            //Picasso.with(main).load(contentsItem.getUrl()).placeholder(R.drawable.loading).error(R.drawable.icon).resize(0,400).into(imageViewButton);
            imageViewButton.setImageBitmap(contentsItem.getBitmap());
            if (contentsItem.getBitmap() == null) {
                //Log.e("contentsView_랭킹", contentsItem.getTitle());
                imageViewButton.setImageResource(R.drawable.no_image);
            }
            rate.setText(contentsItem.getRated());
            textView1.setText(contentsItem.getTitle());
            textView2.setText(contentsItem.getDate());
            textView3.setText(contentsItem.getPlace());
            time.setText(contentsItem.getTime());

            //ratingBar세팅
            ratingScore = contentsItem.getRatingScore();
            ratingBar.setRating(ratingScore);
            ratingText.setText("");
            if(ratingScore==0) {
                //점수를 안주었을 때
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                boolean fromUser) {
                        ratingText.setText("점수 : " + rating);
                        ratingScore = rating;
                        contentsItem.setRatingScore(rating);
                    }
                });
            }
            else{
                //점수를 주었을 때 ratingBar만 있고, "점수:"는 없어짐
            }

            //예상점수 세팅
            String score = "";
            //place의 길이가 길면
            if (contentsItem.getPlace().length() > 10)
                score = "\n";
            if (page.compareTo("recommend") == 0)
                score += "(" + contentsItem.getI() + "위)" + "예상점수 : " + contentsItem.getExpectScore();
            else
                score += "(" + contentsItem.getI() + "위)" + "평균점수 : " + contentsItem.getExpectScore();

            expection.setText(score);
            if (page.compareTo("famous") == 0)
                expection.setText(score);
            String code = contentsItem.getCode();
            url = contentsItem.getUrl();


            // inflater- 평가내리는 창 열리고,
            // if(평가 후) -> unWatched_colored로 바뀜

            new ButtonClass(code, page, position);

        } else if (page.compareTo("save") == 0) {
            imageViewButton.setImageBitmap(contentsItem.getBitmap());
            textView1.setText(contentsItem.getTitle());
            textView2.setText(contentsItem.getDate());
            textView3.setText(contentsItem.getPlace());
            time.setText(contentsItem.getTime());
            new ButtonClass("", page, position);
        }
    }

    class ButtonClass extends AppCompatActivity {
        public static final int REQEST_CODE_RATING1 = 1001;//recommend에서 평가
        public static final int REQEST_CODE_RATING2 = 1002;//famous에서 평가
        public static final int REQEST_CODE_RATING3 = 1003;//comment
        public static final int REQEST_CODE_RATING4 = 1004;//save버튼
        public static final int REQEST_CODE_RATING5 = 1005;//image click
        public static final int REQEST_CODE_Err1 = 4001;//별점을 메긴 후 눌러주세요!
        public static final int REQEST_CODE_Err2 = 4002;//이미 별점을 주셨어요

        ButtonClass(final String code, final String tag, final int position) {
            if (tag.compareTo("recommend") == 0 || tag.compareTo("famous") == 0) {
                rate.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (contentsItem.getRated().compareTo("보고 싶은 만큼 별 주기") == 0) {
                            ratingScore = contentsItem.getRatingScore();
                            if (ratingScore != 0) {
                                contentsItem.setRated(Float.toString(ratingScore) + "점을 주셨습니다.");
                                Intent intent1 = new Intent(main, RateSaveTemporaryActivity.class);

                                //서버에 score, id 보내기
                                String send = Float.toString(ratingScore) + ":" + code + ":0";
                                Server server = new Server();
                                server.setFunction("insert", send, id);
                                server.insert(main);
                                intent1.putExtra("score", Float.toString(ratingScore));
                                intent1.putExtra("ContentsItem", contentsItem);
                                intent1.putExtra("position", position);

                                if (tag.compareTo("recommend") == 0)
                                    main.startActivityForResult(intent1, REQEST_CODE_RATING1);
                                else if (tag.compareTo("famous") == 0)
                                    main.startActivityForResult(intent1, REQEST_CODE_RATING2);
                            }else{
                                Intent intent1 = new Intent(main, RateSaveTemporaryActivity.class);
                                main.startActivityForResult(intent1, REQEST_CODE_Err1);
                            }
                        } else {
                            Intent intent1 = new Intent(main, RateSaveTemporaryActivity.class);
                            main.startActivityForResult(intent1, REQEST_CODE_Err2);
                        }
                    }
                });
                imageViewButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contentsItem.getHomepage()));
                        main.startActivityForResult(intent, REQEST_CODE_RATING5);
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Server server = new Server();
                        server.setFunction("save", contentsItem, id);
                        server.save(main);
                        Intent intent1 = new Intent(main,RateSaveTemporaryActivity.class);
                        main.startActivityForResult(intent1, REQEST_CODE_RATING4);
                    }
                });
            } else if (tag.compareTo("save") == 0) {
                imageViewButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contentsItem.getHomepage()));
                        main.startActivityForResult(intent, REQEST_CODE_RATING5);
                    }
                });

                //Toast.makeText(getApplicationContext(), "(홈페이지 접속 구현예정입니다.)", Toast.LENGTH_SHORT).show();
            }
            comment.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(main, CommentWatingActivity.class);
                    intent.putExtra("cultId", contentsItem.getCode());
                    main.startActivityForResult(intent, REQEST_CODE_RATING3);
                }
            });
        }
    }

}
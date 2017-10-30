package com.example.youngun.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Rating;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by youngun on 2017-10-18.
 */
public class ContentsView extends LinearLayout {
    static String comments;
    ContentsView contentsView;
    ImageView imageViewButton;
    ImageView imageViewButton2;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView time;
    TextView expection;
    HomeActivity main;
    ImageButton rate;
    ImageButton homepage;
    ImageButton comment;
    String url;

    public ContentsView(Context context, ContentsItem contentsItem, HomeActivity main, String page) {
        super(context);
        contentsView = this;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (page.compareTo("recommend") == 0 || page.compareTo("famous") == 0)
            inflater.inflate(R.layout.activity_contentlist, this, true);
        else if (page.compareTo("save") == 0) {
            inflater.inflate(R.layout.activity_savelist, this, true);
        }

        imageViewButton = (ImageView) findViewById(R.id.imageView_button);
        imageViewButton2 = (ImageView) findViewById(R.id.imageView_button2);
        rate = (ImageButton) findViewById(R.id.unWatched);
        textView1 = (TextView) findViewById(R.id.titleText);
        textView2 = (TextView) findViewById(R.id.dateText);
        time = (TextView) findViewById(R.id.timeText);
        textView3 = (TextView) findViewById(R.id.placeText);
        expection = (TextView) findViewById(R.id.expectScore);
        homepage = (ImageButton) findViewById(R.id.homePage);
        comment = (ImageButton) findViewById(R.id.comment);

        this.main = main;
        changeToNew(contentsItem, page);
    }

    public void changeToNew(final ContentsItem contentsItem, String page) {
        if (page.compareTo("recommend") == 0 || page.compareTo("famous") == 0) {
            imageViewButton.setImageBitmap(contentsItem.getBitmap());

            if (contentsItem.getRated()) {
                rate.setImageResource(R.drawable.star_colored);
                Log.e("contentsView_랭킹", contentsItem.getTitle());
            } else
                rate.setImageResource(R.drawable.star);

            textView1.setText(contentsItem.getTitle());
            textView2.setText(contentsItem.getDate());
            textView3.setText(contentsItem.getPlace());
            time.setText(contentsItem.getTime());
            expection.setText("(" + contentsItem.getI() + "위)" + "예상점수 : " + contentsItem.getExpectScore());
            if (page.compareTo("famous") == 0)
                expection.setText("(" + contentsItem.getI() + "위) 평균점수 : " + contentsItem.getExpectScore());
            String code = contentsItem.getCode();
            url = contentsItem.getUrl();


            // inflater- 평가내리는 창 열리고,
            // if(평가 후) -> unWatched_colored로 바뀜

            new ButtonClass(rate, code, contentsItem);
        } else if (page.compareTo("save") == 0) {
            imageViewButton2.setImageBitmap(contentsItem.getBitmap());
            textView1.setText(contentsItem.getTitle());
            textView2.setText(contentsItem.getDate());
            textView3.setText(contentsItem.getPlace());
            time.setText(contentsItem.getTime());
        }
    }

    class ButtonClass extends AppCompatActivity {
        public static final int REQEST_CODE_RATING1 = 1001;
        public static final int REQEST_CODE_RATING2 = 1002;
        public static final int REQEST_CODE_RATING3 = 1003;
        ButtonClass buttonClass = this;

        ButtonClass(ImageButton button1, final String code, final ContentsItem contentsItem) {
            button1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(main, RatingActivity1.class);
                    intent.putExtra("title", textView1.getText().toString());
                    intent.putExtra("contentsItem", contentsItem);
                    intent.putExtra("code", code);
                    //누르기만 하면 true로 저장이 됨.
                    contentsItem.setRate(true);

                    //버튼은 그냥 R.layout.unWatchedButton만 가지고 오는것일 뿐이더라
                    intent.putExtra("unWatched", v.getId());
                    main.startActivityForResult(intent, REQEST_CODE_RATING1);
                }
            });
            homepage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contentsItem.getHomepage()));
                    main.startActivityForResult(intent, REQEST_CODE_RATING2);
                }
            });
            comment.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(main,ProgressBarActivity.class);
                    intent.putExtra("cultId", contentsItem.getCode());
                    main.startActivityForResult(intent,REQEST_CODE_RATING3);
                }
            });

        }


    }
}

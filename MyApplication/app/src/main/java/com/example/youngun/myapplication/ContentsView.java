package com.example.youngun.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by youngun on 2017-10-18.
 */
public class ContentsView extends LinearLayout {
    ImageView imageViewButton;
    ImageView imageViewButton2;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView time;
    TextView expection;
    HomeActivity main;
    String url;

    public ContentsView(Context context, ContentsItem contentsItem, HomeActivity main, String page) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(page.compareTo("recommend")==0)
            inflater.inflate(R.layout.activity_contentlist, this, true);
        else if(page.compareTo("save")==0) {
            inflater.inflate(R.layout.activity_savelist, this, true);
            Log.e("ContentsView_s",contentsItem.getBitmap().toString());
        }

        imageViewButton = (ImageView) findViewById(R.id.imageView_button);
        imageViewButton2 = (ImageView) findViewById(R.id.imageView_button2);
        textView1 = (TextView) findViewById(R.id.titleText);
        textView2 = (TextView) findViewById(R.id.dateText);
        time = (TextView) findViewById(R.id.timeText);
        textView3 = (TextView) findViewById(R.id.placeText);
        expection = (TextView) findViewById(R.id.expectScore);


        this.main = main;
        changeToNew(contentsItem,page);
    }

    public void changeToNew(final ContentsItem contentsItem,String page) {
        if(page.compareTo("recommend")==0) {
            imageViewButton.setImageBitmap(contentsItem.getBitmap());

            ImageButton unWatched = (ImageButton) findViewById(R.id.unWatched);

            textView1.setText(contentsItem.getTitle());
            textView2.setText(contentsItem.getDate());
            textView3.setText(contentsItem.getPlace());
            time.setText(contentsItem.getTime());
            expection.setText("예상점수 : " + contentsItem.getExpectScore());
            String code = contentsItem.getCode();
            url = contentsItem.getUrl();


            if (contentsItem.isUnWatched() == false)
                unWatched.setImageResource(R.drawable.star);
            else
                unWatched.setImageResource(R.drawable.star_colored);
            // inflater- 평가내리는 창 열리고,
            // if(평가 후) -> unWatched_colored로 바뀜

            new RatingClass(unWatched, code, contentsItem);
        }
        else if(page.compareTo("save")==0){
            imageViewButton2.setImageBitmap(contentsItem.getBitmap());
            textView1.setText(contentsItem.getTitle());
            textView2.setText(contentsItem.getDate());
            textView3.setText(contentsItem.getPlace());
            time.setText(contentsItem.getTime());
        }
    }

    class RatingClass extends AppCompatActivity {
        public static final int REQEST_CODE_RATING1 = 1001;

        RatingClass(ImageButton button1, final String code, final ContentsItem contentsItem) {
            button1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(main, RatingActivity1.class);
                    intent.putExtra("title",textView1.getText().toString());
                    intent.putExtra("contentsItem",contentsItem);
                    intent.putExtra("code",code);
                    Log.e("ContentsView_code :",code);

                    //버튼은 그냥 R.layout.unWatchedButton만 가지고 오는것일 뿐이더라
                    intent.putExtra("unWatched", v.getId());
                    main.startActivityForResult(intent, REQEST_CODE_RATING1);
                }
            });
        }


    }
}

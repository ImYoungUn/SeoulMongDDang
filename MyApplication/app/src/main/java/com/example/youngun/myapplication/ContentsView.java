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

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by youngun on 2017-10-18.
 */
public class ContentsView extends LinearLayout {
    ImageView imageViewButton;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    ImageButton unWatched;
    ImageButton watched;
    HomeActivity main;
    ContentsItem contentsItem;

    public ContentsView(Context context, ContentsItem contentsItem, HomeActivity main) {
        super(context);
        this.contentsItem = contentsItem;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_contentlist, this, true);

        imageViewButton = (ImageView) findViewById(R.id.imageView_button);
        textView1 = (TextView) findViewById(R.id.titleText);
        textView2 = (TextView) findViewById(R.id.dateText);
        textView3 = (TextView) findViewById(R.id.placeText);
        unWatched = (ImageButton) findViewById(R.id.unWatched);
        watched = (ImageButton) findViewById(R.id.watched);

        this.main = main;
        changeToNew(contentsItem);
    }

    public void changeToNew(final ContentsItem contentsItem) {
        imageViewButton.setImageBitmap(contentsItem.getBitmap());

        textView1.setText(contentsItem.getTitle());
        textView2.setText(contentsItem.getDate());
        textView3.setText(contentsItem.getPlace());


        if (contentsItem.isUnWatched() == false)
            unWatched.setImageResource(R.drawable.star);
        else
            unWatched.setImageResource(R.drawable.star_colored);

        // inflater- 평가내리는 창 열리고,
        // if(평가 후) -> unWatched_colored로 바뀜

        if (contentsItem.isWatched() == false)
            watched.setImageResource(R.drawable.customer);
        else
            watched.setImageResource(R.drawable.customer_colored);

        RatingClass rc = new RatingClass(unWatched,watched);
    }

    class RatingClass extends AppCompatActivity {
        public static final int REQEST_CODE_RATING1 = 1001;
        public static final int REQEST_CODE_RATING2 = 1002;

        ImageButton b1, b2;
        RatingClass(ImageButton button1, ImageButton button2) {
            this.b1 = button1;
            this.b2 = button2;
            b1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(main, RatingActivity1.class);
                    intent.putExtra("title",textView1.getText().toString());
                    intent.putExtra("code",contentsItem.getCode().toString());
                    main.startActivityForResult(intent, REQEST_CODE_RATING1);
                }
            });
            b2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(main, RatingActivity2.class);
                    intent.putExtra("title",textView1.getText().toString());
                    intent.putExtra("code",contentsItem.getCode().toString());
                    startActivityForResult(intent, REQEST_CODE_RATING2);
                }
            });
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            super.onActivityResult(requestCode, resultCode, intent);
            if (requestCode == REQEST_CODE_RATING1) {
                Intent resultIntent = getIntent();
                Log.e("ContentsView", "왼쪽");
                float rating = resultIntent.getIntExtra("rating", -1);
                //rating 서버로 보내기, true로 바꾸기
                b1.setImageResource(R.drawable.star_colored);
            }
            if (requestCode == REQEST_CODE_RATING2) {
                Intent resultIntent = getIntent();
                Log.e("ContentsView", "오른쪽");
                float rating = resultIntent.getIntExtra("rating", -1);
                //rating 서버로 보내기, true로 바꾸기, 코멘트 저장하기
                b2.setImageResource(R.drawable.customer_colored);

            }
            if (resultCode == RESULT_OK) {
                Log.e("RESULT_OK", "good");
            }
        }
    }
}

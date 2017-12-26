package mong.ddang.youngun.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.youngun.myapplication.R;

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
    Button rate;
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
        rate = (Button) findViewById(R.id.unWatched);
        textView1 = (TextView) findViewById(R.id.titleText);
        textView2 = (TextView) findViewById(R.id.dateText);
        time = (TextView) findViewById(R.id.timeText);
        textView3 = (TextView) findViewById(R.id.placeText);
        expection = (TextView) findViewById(R.id.expectScore);
        comment = (ImageButton) findViewById(R.id.comment);

        this.main = main;
        changeToNew(contentsItem, page);
    }

    public void changeToNew(final ContentsItem contentsItem, String page) {
        if (page.compareTo("recommend") == 0 || page.compareTo("famous") == 0) {
            //Picasso.with(main).load(contentsItem.getUrl()).placeholder(R.drawable.loading).error(R.drawable.icon).resize(0,400).into(imageViewButton);
            imageViewButton.setImageBitmap(contentsItem.getBitmap());

            if (contentsItem.getRated()) {
                //rate.setImageResource(R.drawable.star_colored);
                Log.e("contentsView_랭킹", contentsItem.getTitle());
            } else
               // rate.setImageResource(R.drawable.star);

            textView1.setText(contentsItem.getTitle());
            textView2.setText(contentsItem.getDate());
            textView3.setText(contentsItem.getPlace());
            time.setText(contentsItem.getTime());
            String score = "";
            //place의 길이가 길면
            if (contentsItem.getPlace().length() > 15)
                score = "\n";
            if(page.compareTo("recommend") == 0)
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

            new ButtonClass(rate, code, contentsItem,"recommend&famous");

        } else if (page.compareTo("save") == 0) {
            imageViewButton2.setImageBitmap(contentsItem.getBitmap());
            textView1.setText(contentsItem.getTitle());
            textView2.setText(contentsItem.getDate());
            textView3.setText(contentsItem.getPlace());
            time.setText(contentsItem.getTime());
            new ButtonClass(rate, "", contentsItem,"save");
        }
    }

    /*
    피카소 쓸 때 Bitmap사이즈 조절
    public int targetWidth = 200;

    public Transformation resizeTransformation = new Transformation() {
        @Override
        public Bitmap transform(Bitmap source) {
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }
        @Override
        public String key() {
            return "resizeTransformation#" + System.currentTimeMillis();
        }
    };
*/
    class ButtonClass extends AppCompatActivity {
        public static final int REQEST_CODE_RATING1 = 1001;
        public static final int REQEST_CODE_RATING2 = 1002;
        public static final int REQEST_CODE_RATING3 = 1003;

        ButtonClass(Button rate, final String code, final ContentsItem contentsItem, String tag) {
            if (tag.compareTo("recommend&famous") == 0) {
                rate.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(main, RatingActivity.class);
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
                imageViewButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contentsItem.getHomepage()));
                        main.startActivityForResult(intent, REQEST_CODE_RATING2);
                    }
                });
            } else if (tag.compareTo("save") == 0) {
                imageViewButton2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contentsItem.getHomepage()));
                        main.startActivityForResult(intent, REQEST_CODE_RATING2);
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
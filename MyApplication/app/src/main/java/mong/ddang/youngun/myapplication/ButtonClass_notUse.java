package mong.ddang.youngun.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by M on 2017-12-15.
 */

public class ButtonClass_notUse {
    public static final int REQEST_CODE_RATING1 = 1001;
    public static final int REQEST_CODE_RATING2 = 1002;
    public static final int REQEST_CODE_RATING3 = 1003;

    public ButtonClass_notUse(Button button1, final TextView textView1, final HomeActivity main, ImageView imageViewButton, ImageView imageViewButton2, ImageButton comment, final String code, final ContentsItem contentsItem, String tag) {
        super();
        if (tag.compareTo("recommend&famous") == 0) {
            button1.setOnClickListener(new View.OnClickListener() {
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
            imageViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contentsItem.getHomepage()));
                    main.startActivityForResult(intent, REQEST_CODE_RATING2);
                }
            });
        } else if (tag.compareTo("save") == 0) {
            /***********************************************************************구현예정!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            imageViewButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contentsItem.getHomepage()));
                    main.startActivityForResult(intent, REQEST_CODE_RATING2);
                }
            });
            */
        }
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main, CommentWatingActivity.class);
                intent.putExtra("cultId", contentsItem.getCode());
                main.startActivityForResult(intent, REQEST_CODE_RATING3);
            }
        });
    }
}
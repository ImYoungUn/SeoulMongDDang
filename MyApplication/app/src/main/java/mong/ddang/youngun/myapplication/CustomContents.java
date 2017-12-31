package mong.ddang.youngun.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.youngun.myapplication.R;

/**
 * Created by caucse on 2017-12-31.
 */

public class CustomContents extends LinearLayout {
    TextView score;
    public CustomContents(Context context) {
        super(context);
        initView();
    }

    public CustomContents(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
        getAttrs(attributeSet);
    }

    public CustomContents(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView(){
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.contents, this, false);
        addView(v);

        score = (TextView) findViewById(R.id.expectScore);
    }

    private void getAttrs(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.Contents);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attributeSet, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.Contents, defStyle, 0);
        setTypeArray(typedArray);
    }


    private void setTypeArray(TypedArray ta) {
        String text_string = ta.getString(R.styleable.Contents_score);
        score.setText(text_string);
        ta.recycle();
    }

    void setText(String text_string) {
        score.setText(text_string);
    }
}

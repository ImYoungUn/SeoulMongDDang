package mong.ddang.youngun.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.youngun.myapplication.R;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        Intent intent = getIntent();

        TextView t = (TextView) findViewById(R.id.errorText);
        t.setText(intent.getStringExtra("errorCode"));
    }
}

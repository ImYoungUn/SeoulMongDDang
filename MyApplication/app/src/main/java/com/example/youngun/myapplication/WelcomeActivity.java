package com.example.youngun.myapplication;

import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    WelcomeActivity wActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final Button welcome = (Button) findViewById(R.id.welcomeButton);
        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ReadyForGetMongId.NewUser != null) {
                    Intent intent = new Intent(wActivity, ReadyForGetMongId.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}

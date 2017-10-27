package com.example.youngun.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import layout.FamousFragment;
import layout.NewsFragment;
import layout.RecommendationFragment;

/**
 * Created by youngun on 2017-10-18.
 */
public class HomeActivity extends FragmentActivity implements RecommendationFragment.OnFragmentInteractionListener, NewsFragment.OnFragmentInteractionListener, FamousFragment.OnFragmentInteractionListener {
    private Context context;
    ListView listView;
    ListView listView2;
    ListView listView3;
    ImageButton search;
    JSONpart json;
    ContentsAdapter adapter;
    String userName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
/*
        FragPagerAdapter mpageAdapter = new FragPagerAdapter(getSupportFragmentManager());
        ViewPager mviewPager = (ViewPager) findViewById(R.id.view_pager);
        mviewPager.setAdapter(mpageAdapter);
*/
        ViewPager mviewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout mTab = (TabLayout) findViewById(R.id.tabs);

        listView = new ListView(context);
        listView2 = new ListView(context);
        listView3 = new ListView(context);
        adapter = new ContentsAdapter(this, this);

        Vector<View> pages = new Vector<View>();
        pages.add(listView);
        pages.add(listView2);
        pages.add(listView3);

        CustomFragPagerAdapter customAdapter = new CustomFragPagerAdapter(context, pages);
        mviewPager.setAdapter(customAdapter);
        mTab.setupWithViewPager(mviewPager);
        mTab.getTabAt(0).setIcon(R.drawable.recommended);
        mTab.getTabAt(1).setIcon(R.drawable.newspaper);
        mTab.getTabAt(2).setIcon(R.drawable.crown);


        //CheckTypesTask task = new CheckTypesTask(this);
        //task.execute();
        //Information info = task.getInfo();

        SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        userName = sp.getString("name", "");


        //ArrayList<ContentsInfo> ar = info.getRecommendationList();
        Intent intent = getIntent();
        Information info = (Information) intent.getExtras().getSerializable("info");

        ArrayList<ContentsInfo> ar = info.getRecommendList();

        for (int i = 0; i < info.cultureSize; i++) {
            //adapter.setMain(this);
            adapter.addContents(new ContentsItem(ar.get(i).getContentsCode(), ar.get(i).getContentsImage(), ar.get(i).getTitle(), ar.get(i).getTIme(), ar.get(i).getDate(), ar.get(i).getPlace(), false, false));
        }
        listView.setAdapter(adapter);
        listView2.setAdapter(adapter);
        listView3.setAdapter(adapter);
        search = (ImageButton) findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //************임시 사용자 형성*****************
                try {
                    Log.e("search", "SEND");
                    SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
                    int tempUser = sp.getInt("tempUser", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    tempUser++;
                    //tempUser가 1에서 더이상 안넘어가네
                    editor.putInt("tempUser", tempUser);
                    json.setTempUser(tempUser);
                    json.send("tempUser");
                    Toast.makeText(getApplicationContext(), "계정이 전환되었습니다. tempUser : " + tempUser, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("search", "Fail");
                    e.printStackTrace();
                }

                //************임시 사용자 형성*****************
            }
        });
        Toast.makeText(getApplicationContext(), "반갑습니다." + userName + "님, 서울몽땅입니다.", Toast.LENGTH_SHORT).show();

    }

    //그냥 뒤로가기눌러서 나왔을 때도 체크
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        final int REQEST_CODE_RATING1 = 1001;
        final int REQEST_CODE_RATING2 = 1002;
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQEST_CODE_RATING1) {
            //점수를 주었을 때
            if (intent != null) {
                String score = intent.getExtras().getString("score");
                int unWatchedButtonId = intent.getExtras().getInt("unWatched");
                ImageButton unWatched = (ImageButton) findViewById(unWatchedButtonId);
                unWatched.setImageResource(R.drawable.star_colored);
                Toast.makeText(getApplicationContext(), score + "점을 주셨습니다.", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQEST_CODE_RATING2) {
            Intent resultIntent = getIntent();
            Log.e("ContentsView", "오른쪽");
            float rating = resultIntent.getIntExtra("rating", -1);
            //rating 서버로 보내기, true로 바꾸기, 코멘트 저장하기
            //b2.setImageResource(R.drawable.customer_colored);

        }
        if (resultCode == RESULT_OK) {
            Log.e("RESULT_OK", "good");
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        HomeActivity homeActivity;
        Information info;

        CheckTypesTask(HomeActivity h) {
            homeActivity = h;
        }

        ProgressDialog asyncDialog = new ProgressDialog(
                HomeActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }

        public Information getInfo() {
            return info;
        }
    }
}

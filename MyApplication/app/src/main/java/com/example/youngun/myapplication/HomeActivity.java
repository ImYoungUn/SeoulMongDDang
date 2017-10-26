package com.example.youngun.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
    static String mongId;
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
        adapter = new ContentsAdapter(this,this);

        Vector<View> pages = new Vector<View>();
        pages.add(listView);
        pages.add(listView2);
        pages.add(listView3);

        CustomFragPagerAdapter customAdapter = new CustomFragPagerAdapter(context,pages);
        mviewPager.setAdapter(customAdapter);
        mTab.setupWithViewPager(mviewPager);
        mTab.getTabAt(0).setIcon(R.drawable.recommended);
        mTab.getTabAt(1).setIcon(R.drawable.newspaper);
        mTab.getTabAt(2).setIcon(R.drawable.crown);

        Information info = new Information();
        int infoCount = info.getList_total_count();

        //JSONpart클래스 실행하여 recommend시 필요한 정보 server로 넘겨주기
        SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        userName = sp.getString("name","");
        json = info.getJson();
        try {
            json.addIdAndHome(sp.getString("id",""),this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<ContentsInfo> ar = info.getList();
        for(int i=0;i<30; i++){
            //adapter.setMain(this);
            adapter.addContents(new ContentsItem(ar.get(i).getContentsCode(),ar.get(i).getContentsImage(), ar.get(i).getTitle(), ar.get(i).getTIme(),ar.get(i).getDate(),ar.get(i).getPlace(),false,false));
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
                    int tempUser = sp.getInt("tempUser",0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("tempUser", tempUser+1);
                    json.setTempUser(tempUser+1);
                    json.send();
                    Toast.makeText(getApplicationContext(), "계정이 전환되었습니다. tempUser : "+tempUser+1, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("search", "Fail");
                    e.printStackTrace();
                }

                //************임시 사용자 형성*****************
            }
        });
        Toast.makeText(getApplicationContext(), "반갑습니다."+userName+"님, 서울몽땅입니다.", Toast.LENGTH_SHORT).show();

    }

    //그냥 뒤로가기눌러서 나왔을 때도 체크
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        final int REQEST_CODE_RATING1 = 1001;
        final int REQEST_CODE_RATING2 = 1002;
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQEST_CODE_RATING1) {
            //점수를 주었을 때
            if(intent!=null) {
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

}

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
    ContentsAdapter adapter2;
    ContentsAdapter adapter3;
    String userName;
    HomeActivity home;
    StringParsing stringParsing;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        home=this;
/*
*/
        ViewPager mviewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout mTab = (TabLayout) findViewById(R.id.tabs);

        listView = new ListView(context);
        listView2 = new ListView(context);
        listView3 = new ListView(context);
        adapter = new ContentsAdapter(this, this, "recommend");
        adapter2 = new ContentsAdapter(this, this, "save");
        adapter3 = new ContentsAdapter(this, this, "famous");

        Vector<View> pages = new Vector<View>();
        pages.add(listView);
        pages.add(listView2);
        pages.add(listView3);

        CustomFragPagerAdapter customAdapter = new CustomFragPagerAdapter(context, pages);
        mviewPager.setAdapter(customAdapter);
        mTab.setupWithViewPager(mviewPager);
        mTab.getTabAt(0).setIcon(R.drawable.ic_recommended);
        mTab.getTabAt(1).setIcon(R.drawable.ic_books);
        mTab.getTabAt(2).setIcon(R.drawable.ic_cup);
        customAdapter.setTab(mTab);


        //CheckTypesTask task = new CheckTypesTask(this);
        //task.execute();
        //Information info = task.getInfo();

        SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        userName = sp.getString("name", "");
        search = (ImageButton) findViewById(R.id.searchButton);


        //ArrayList<ContentsInfo> ar = info.getRecommendationList();
//info는 정보를 받고, item은 그 정보를 통해 item객체를 생성하고 그 item을 adapter에 넣음.
        //Intent in = getIntent();
        //StringParsing stringParsing = in.getParcelableExtra("stringParsing");

        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                stringParsing = new StringParsing();
            }

            protected String doInBackground(String... urls) {
                return "finish";
            }
            @Override
                public void onPostExecute(String result) {
                Log.e("Home", "start");
                ArrayList<ContentsInfo> ar = stringParsing.getAr();
                ArrayList<ContentsInfo> ar2 = null;
                ArrayList<ContentsInfo> ar3 = stringParsing.getAr3();
                try {
                    ar2 = new Save().getSaveList();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < ar.size(); i++) {
                    //adapter.setMain(this);
                    adapter.addContents(new ContentsItem(ar.get(i).getContentsCode(), ar.get(i).getContentsImage(), ar.get(i).getTitle(), ar.get(i).getTIme(), ar.get(i).getDate(), ar.get(i).getPlace(), ar.get(i).getUrl(), ar.get(i).getExpectScore(),ar.get(i).getJanre(), ar.get(i).getStartDate(), ar.get(i).getEndDate(),ar.get(i).getHomepage(),i));
                }
                listView.setAdapter(adapter);

                if (ar2 != null) {
                    Log.e("HomeActivity","ar2!=null");
                    /*
                    for (int i = 0; i < ar2.size(); i++) {
                        Log.e("Home_s",ar2.get(i).getContentsImage().toString());
                        adapter2.addContents(new ContentsItem(ar2.get(i).getContentsImage(), ar2.get(i).getTitle(), ar2.get(i).getTIme(), ar2.get(i).getDate(), ar2.get(i).getPlace(),ar2.get(i).getHomepage()));
                    }
                    */
                    //최신 save가 가장 먼저 나오도록 역순으로 제공
                    for (int i = ar2.size()-1; i>=0; i--) {
                        Log.e("Home_s",ar2.get(i).getContentsImage().toString());
                        adapter2.addContents(new ContentsItem(ar2.get(i).getContentsImage(), ar2.get(i).getTitle(), ar2.get(i).getTIme(), ar2.get(i).getDate(), ar2.get(i).getPlace(),ar2.get(i).getHomepage()));
                    }
                    listView2.setAdapter(adapter2);
                }

                for (int i = 0; i < ar3.size(); i++) {
                    //adapter.setMain(this);
                    adapter3.addContents(new ContentsItem(ar3.get(i).getContentsCode(), ar3.get(i).getContentsImage(), ar3.get(i).getTitle(), ar3.get(i).getTIme(), ar3.get(i).getDate(), ar3.get(i).getPlace(), ar3.get(i).getUrl(), ar3.get(i).getExpectScore(),ar3.get(i).getJanre(), ar3.get(i).getStartDate(), ar3.get(i).getEndDate(),ar3.get(i).getHomepage(),i));
                }
                listView3.setAdapter(adapter3);
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*
                        //************임시 사용자 형성*****************
                        try {
                            Log.e("search", "SEND");
                            SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
                            int tempUser = sp.getInt("tempUser", 0);
                            SharedPreferences.Editor editor = sp.edit();
                            tempUser++;
                            //tempUser가 1에서 더이상 안넘어가네
                            editor.putInt("tempUser", tempUser);
                            json = new JSONpart();
                            json.setTempUser(tempUser,home);
                            json.send("tempUser");
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "계정이 전환되었습니다. tempUser : " + tempUser, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Log.e("search", "Fail");
                            e.printStackTrace();
                        }

                        //************임시 사용자 형성*****************
                        */
                        Toast.makeText(getApplicationContext(), "구현예정입니다.", Toast.LENGTH_SHORT).show();

                    }
                });
                Toast.makeText(getApplicationContext(), "반갑습니다." + userName + "님, 서울몽땅입니다.", Toast.LENGTH_SHORT).show();

            }
            }.execute();

    }

    //그냥 뒤로가기눌러서 나왔을 때도 체크
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        final int REQEST_CODE_RATING1 = 1001;
        final int REQEST_CODE_RATING3 = 1003;
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
        if (requestCode == REQEST_CODE_RATING3) {
            //점수를 주었을 때
            if (intent != null) {
                Toast.makeText(getApplicationContext(), userName+"님의 코멘트를 저장하였습니다.", Toast.LENGTH_SHORT).show();
            }
        }
        if (resultCode == RESULT_OK) {
            Log.e("RESULT_OK", "good");
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

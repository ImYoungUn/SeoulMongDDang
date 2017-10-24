package com.example.youngun.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

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
    ContentsAdapter adapter;
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
        ArrayList<ContentsInfo> ar = info.getList();
        for(int i=0;i<30; i++){
            //adapter.setMain(this);
            adapter.addContents(new ContentsItem(ar.get(i).getContentsCode(),ar.get(i).getContentsImage(), ar.get(i).getTitle(), ar.get(i).getTIme(),ar.get(i).getPlace(),false,false));
        }
        listView.setAdapter(adapter);
        listView2.setAdapter(adapter);
        listView3.setAdapter(adapter);


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

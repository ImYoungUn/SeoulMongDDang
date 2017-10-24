package com.example.youngun.myapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Vector;

import layout.FamousFragment;
import layout.NewsFragment;
import layout.RecommendationFragment;

/**
 * Created by youngun on 2017-10-20.
 */
public class CustomFragPagerAdapter extends PagerAdapter {

    private Context mContext;
    private Vector<View> pages;

    public CustomFragPagerAdapter(Context context, Vector<View> pages) {
        this.mContext=context;
        this.pages=pages;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("position",Integer.toString(position));
        Log.e("position_page", Integer.toString(pages.size()));
        View page = pages.get(position);
        container.addView(page);
        return page;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RecommendationFragment.newInstance();
            case 1:
                return NewsFragment.newInstance();
            case 2:
                return FamousFragment.newInstance();
            default:
                return null;
        }
    }


    private static int PAGE_NUMBER = 3;

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "추천";
            case 1:
                return "뉴스피드";
            case 2:
                return "인기멤버";
            default:
                return null;
        }
    }
}
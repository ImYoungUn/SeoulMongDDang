package com.example.youngun.myapplication;

import android.content.Context;
import android.support.design.widget.TabLayout;
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
    private TabLayout mTab=null;

    public CustomFragPagerAdapter(Context context, Vector<View> pages) {
        this.mContext = context;
        this.pages = pages;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("position", Integer.toString(position));
        View page = pages.get(position);
        container.addView(page);
        return page;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public int getItemPosition(Object object) {
        int itemPosition = super.getItemPosition(object);
        Log.e("pager", Integer.toString(itemPosition));
        if (itemPosition == 0 && mTab!=null) {
            mTab.getTabAt(0).setIcon(R.drawable.recommended_colored);
            mTab.getTabAt(1).setIcon(R.drawable.newspaper);
            mTab.getTabAt(2).setIcon(R.drawable.crown);
        }
        else if (itemPosition == 2 && mTab!=null) {
            mTab.getTabAt(0).setIcon(R.drawable.recommended);
            mTab.getTabAt(1).setIcon(R.drawable.newspaper);
            mTab.getTabAt(2).setIcon(R.drawable.crown_colored);
        }
        return itemPosition;
    }

    public void setTab(TabLayout tab){
        mTab = tab;
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
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "추천";
            case 1:
                return "찜 목록";
            case 2:
                return "\"TOP10 컨텐츠\"";
            default:
                return null;
        }
    }
}
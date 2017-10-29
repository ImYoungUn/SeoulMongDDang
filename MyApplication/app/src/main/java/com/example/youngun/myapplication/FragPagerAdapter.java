package com.example.youngun.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import layout.FamousFragment;
import layout.NewsFragment;
import layout.RecommendationFragment;

/**
 * Created by youngun on 2017-10-18.
 */
public class FragPagerAdapter extends FragmentPagerAdapter {
    public FragPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
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
                return "찜목록";
            case 2:
                return "인기멤버";
            default:
                return null;
        }
    }
}

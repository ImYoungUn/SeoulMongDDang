package com.example.youngun.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by youngun on 2017-10-18.
 */
public class ContentsAdapter extends BaseAdapter {
    private Context context;
    private List<ContentsItem> contents = new ArrayList<ContentsItem>();
    private HomeActivity Main;
    private Boolean unWatched[];
    public ContentsAdapter(Context context, HomeActivity main) {
        this.context = context;
        Main=main;
    }

    public void addContents(ContentsItem c) {
        contents.add(c);
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ContentsView itemView;
        //없으면 새로 만들고, 있으면 전에 만들었던 view를 그대로 다시 씀(4개 돌려쓰기)
        if (view == null) {
            itemView = new ContentsView(context, contents.get(i),Main);
        } else {
            itemView = (ContentsView) view;
        }
        itemView.changeToNew(contents.get(i));
        return itemView;
    }
    public void setMain(HomeActivity Main){
        this.Main = Main;
    }
}

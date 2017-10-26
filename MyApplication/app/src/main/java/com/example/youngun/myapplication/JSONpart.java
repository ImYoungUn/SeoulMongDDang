package com.example.youngun.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by youngun on 2017-10-25.
 */
public class JSONpart {
    JSONArray arr;
    JSONObject obj;
    String id;
    HomeActivity homeActivity;
    String gettingMongId = "notYet";
    int tempUser;
    JSONpart(){
        obj = new JSONObject();
        arr = new JSONArray();
    }
    public void addCode(String code){
        arr.put(code);
    }
    public void addIdAndHome(String id,HomeActivity homeActivity) throws JSONException {
        this.id = id;
        this.homeActivity = homeActivity;
        obj.put("userId", id);
    }
    //임시 유저 생성
    public void setTempUser(int user){
        tempUser = user;
    }
    public void send() throws JSONException {
        obj.put("allContents", arr);
        Server server = new Server();
        //원래 함수 : server.setFunction("register",obj.toString(),id);
        server.setFunction("register","임시유저",Integer.toString(tempUser));
        server.register(homeActivity);
    }
    public String getMongId(){
        return gettingMongId;
    }
}

package mong.ddang.youngun.myapplication;

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
    LoginActivity loginActivity;
    ReadyForGetMongId readyForGetMongId;
    String gettingMongId = "notYet";
    int tempUser;
    JSONpart(){
        obj = new JSONObject();
        arr = new JSONArray();
    }
    public void addCode(String code){
        arr.put(code);
    }

    public void addIdAndLogin(String id,LoginActivity loginActivity) throws JSONException {
        this.id = id;
        this.loginActivity = loginActivity;
    }
    public void addIdAndReady(String id,ReadyForGetMongId ready) throws JSONException {
        this.id = id;
        this.readyForGetMongId = ready;
        obj.put("userId", id);
    }
    //임시 유저 생성
    public void setTempUser(int user,HomeActivity homeActivity){
        tempUser = user;
        this.homeActivity = homeActivity;
    }
    public void send(String user) throws JSONException {
        Server server = new Server();
        if(user.compareTo("tempUser")==0) {
            server.setFunction("register", "임시유저", Integer.toString(tempUser));
            server.register(homeActivity);
        }
        else if(user.compareTo("firstRecommend")==0){
            server.setFunction("recommend",LoginActivity.mongId,id);
            server.recommend(readyForGetMongId);
        }
        else {
            Log.e("JSONpart","server.recommend");
            server.setFunction("recommend",LoginActivity.mongId,id);
            server.recommend(loginActivity);
        }
    }
    public String getMongId(){
        return gettingMongId;
    }
}

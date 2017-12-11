package com.example.youngun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    static String mongId;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String name;
    private String userId;
    private SharedPreferences sp;
    private Server server;
    private LoginActivity loginActivity;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
/*
첫 로그인시 /register 서버로 들어가 mongId를 받아옴.
그 후 해당 mongId를 저장하고, x개의 선별된 컨텐츠에 대해 평점을 내리면 /insert 서버를 통해 DB에 저장됨.
그 후 /recommend 서버에 접속시 사용자의 예상점수가 가장 높은 컨텐츠를 우선적으로 제공함.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //server 클래스에서 함수 실행 할 때 activity.this정보가 필요하므로 받아옴.
        loginActivity = this;
        FacebookSdk.sdkInitialize(getApplicationContext()); // SDK 초기화 (setContentView 보다 먼저 실행되어야합니다. 안그럼 에러납니다.)
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();  //로그인 응답을 처리할 콜백 관리자
        loginButton = findViewById(R.id.login_facebook_button); //페이스북 로그인 버튼
        sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        server = new Server();

        /*자동로그인시 바로 넘어가게해놓음*/

        if (AccessToken.getCurrentAccessToken() != null) { //이미 로그인 여부 확인//
            //뒤로가기 해서 나갔다가 바로 다시 들어올 경우에는 recommendString의 데이터가 남아있는 듯 함.
            StringParsing.recommendString=null;
            server.setFunction("recommend", sp.getString("name", ""), sp.getString("id", ""));
            //************임시 사용자 형성****************
            LoginActivity.mongId = sp.getString("mongId", "x");
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("tempUser", 0);
            editor.apply();
            Log.e("login_mongId", LoginActivity.mongId);
            //************임시 사용자 형성*****************
            //loading();

            //mongId를 못받아왔을 때, 다시 가입유도!
            if(LoginActivity.mongId.equals("x"))
                registerAgain();

            ReadyForGetMongId.NewUser = "기존유저";
            Intent intent1 = new Intent(this, ReadyForGetMongId.class);
            startActivity(intent1);
            finish();
            Log.d("Tag", "user id : " + AccessToken.getCurrentAccessToken().getUserId());
        } else {
            Log.e("Tag", "로그인을 하세요");
        }


        //유저 정보, 친구정보, 이메일 정보등을 수집하기 위해서는 허가(퍼미션)를 받아야 합니다.
        loginButton.setReadPermissions("public_profile", "user_friends", "email");
        //버튼에 바로 콜백을 등록하는 경우 LoginManager에 콜백을 등록하지 않아도됩니다.
        //반면에 커스텀으로 만든 버튼을 사용할 경우 아래보면 CustomloginButton OnClickListener안에 LoginManager를 이용해서
        //로그인 처리를 해주어야 합니다.
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) { //로그인 성공시 호출되는 메소드
                Log.e("토큰", loginResult.getAccessToken().getToken());
                Log.e("유저아이디", loginResult.getAccessToken().getUserId());
                Log.e("퍼미션 리스트", loginResult.getAccessToken().getPermissions() + "");

                //loginResult.getAccessToken() 정보를 가지고 유저 정보를 가져올수 있습니다.
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Log.e("user profile", object.toString());
                                    name = object.getString("name");
                                    userId = object.getString("id");
                                    //server에 register 후 mong Id 받아옴 (In server.class) 그러나 이 작업이 시간이 좀 걸림
                                    Log.e("Login", "register");
                                    server.setFunction("register", name, userId);
                                    server.register(loginActivity);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("name", name);
                                    editor.putString("id", userId);
                                    editor.putInt("tempUser",0);
                                    editor.apply();
                                } catch (Exception e) {
                                    Log.e("loginErr", e.toString());
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,name,birthday");
                request.setParameters(parameters);
                request.executeAsync();

                Intent intent2 = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(intent2);
                finish();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("loginErr", error.toString());
            }

            @Override
            public void onCancel() {
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loading() {
        //JSONpart클래스 실행하여 recommend시 필요한 정보 server로 넘겨주기
        Log.e("loading()", LoginActivity.mongId);
        JSONpart json = new JSONpart();
        try {
            json.addIdAndLogin(userId, this);
            json.send("recommend");
            Log.e("Login", "send Recommend");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void registerAgain(){
        //다시 가입하기
        Log.e("Login", "registerAgain"+name);
        sp.getString("name", "홍길동");
        sp.getString("id", userId);

        server.setFunction("register", name, userId);
        server.register(loginActivity);
    }










    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.youngun.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.youngun.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
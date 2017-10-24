package com.example.youngun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.Serializable;
import java.util.Arrays;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private Button CustomloginButton;
    private CallbackManager callbackManager;
    private boolean first_login;
    private String name;
    private String userId;
    private SharedPreferences sp;
    private Server server;
    private LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity = this;
        FacebookSdk.sdkInitialize(getApplicationContext()); // SDK 초기화 (setContentView 보다 먼저 실행되어야합니다. 안그럼 에러납니다.)
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();  //로그인 응답을 처리할 콜백 관리자
        loginButton = (LoginButton) findViewById(R.id.login_facebook_button); //페이스북 로그인 버튼
        sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        server = new Server();
        /*자동로그인시 바로 넘어가게해놓음*/
        if (AccessToken.getCurrentAccessToken() != null) { //이미 로그인 여부 확인//
            Log.e("at", "로그인 여부확인");
            first_login = false;
            server.setFunction("recommend", sp.getString("name", ""), sp.getString("id", ""));
            //server.recommend();@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            Intent intent1 = new Intent(this, HomeActivity.class);
            startActivity(intent1);
            finish();
            Log.d("Tag", "user id : " + AccessToken.getCurrentAccessToken().getUserId());
        } else {
            Log.d("Tag", "로그인을 하세요");
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
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("name", name);
                                    editor.putString("id", userId);
                                    editor.apply();
                                    server.setFunction("register", name, userId);
                                    server.register(loginActivity);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                request.executeAsync();
                Intent intent2 = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent2);
                finish();
            }

            @Override
            public void onError(FacebookException error) {
            }

            @Override
            public void onCancel() {
            }
        });

        CustomloginButton = (Button) findViewById(R.id.login_button);
        CustomloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginManager - 요청된 읽기 또는 게시 권한으로 로그인 절차를 시작합니다.
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("public_profile", "user_friends"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.e("onSuccess", "onSuccess");
                            }

                            @Override
                            public void onCancel() {
                                Log.e("onCancel", "onCancel");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.e("onError", "onError " + exception.getLocalizedMessage());
                            }
                        });
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
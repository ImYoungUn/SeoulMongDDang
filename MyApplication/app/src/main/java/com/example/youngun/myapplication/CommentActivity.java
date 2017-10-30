package com.example.youngun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.SocketHandler;

/**
 * Created by youngun on 2017-10-31.
 */
public class CommentActivity extends AppCompatActivity {
    EditText editText;
    CommentActivity commentActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        commentActivity = this;
        editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.commentSend);

        ListView listView = (ListView) findViewById(R.id.commentListView);
        List<String> list = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        try {
            list = addList(list,result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if (text.length() != 0) {
                    //DB 저장에 원할하게끔
                    //나중에 '입력하지마세요' 라고 text띄우기
                    text = text.replaceAll("'"," ");
                    SharedPreferences sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
                    String name = sp.getString("name", "");
                    String id = sp.getString("id", "");
                    Intent intent = getIntent();
                    String cultId = intent.getStringExtra("cultId");

                    Server server = new Server();
                    server.setFunction("saveComment", text, id, name, cultId);
                    server.saveComment(commentActivity);

                    //Intent intent1 = new Intent(commentActivity, HomeActivity.class);
                    //intent1.putExtra("notnull", "true");
                    //startActivityForResult(intent1,1003);
                    Intent in = new Intent();
                    in.putExtra("data", "complete comment");
                    setResult(1003);
                    finish();
                }
            }
        });
    }public List<String> addList(List<String> list,String temp) throws JSONException {
        JSONArray jarray = new JSONArray(temp);
        String oneComment;
        for(int i=0;i<jarray.length();i++) {
            JSONObject job = jarray.getJSONObject(i);
            oneComment = job.getString("name")+" : "+job.getString("comment");
            list.add(oneComment);
        }
        return list;
    }
}

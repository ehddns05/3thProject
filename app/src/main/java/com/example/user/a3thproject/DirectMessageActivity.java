package com.example.user.a3thproject;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DirectMessageActivity extends AppCompatActivity {

    Spinner spinner;
    RecyclerView dmList;
    RecyclerView.Adapter dmListAdapter;
    RecyclerView.LayoutManager dmListLayoutManager;
    SharedPreferences autoLogin;
    ArrayList<DirectMessage> dmListData;

    String id_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_message);

        spinner = findViewById(R.id.directMessage_spinner);

        autoLogin = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        id_data = autoLogin.getString("id_data", null);

        //recyclerView 를 위한 설정. 세로 리스트뷰
        dmList = findViewById(R.id.directMessage_list);
        dmListLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        dmList.setLayoutManager(dmListLayoutManager);

        //dmList에 넣을 메시지 데이터
        dmListData = new ArrayList<>();

        GetDMThread thread = new GetDMThread();
        thread.start();
    }

    /**
     * 유저에게 온 DM이 있으면 가져와서 나타내주기.
     */
    class GetDMThread extends Thread{
        @Override
        public void run() {
            //IP바꿔서 사용하기
            String addr = "http://10.10.15.87:8888/escape/getDirectMessage?id=" + id_data;
            try{
                URL url = new URL(addr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn != null){
                    conn.setConnectTimeout(1000);
                    conn.setRequestMethod("GET");
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        InputStreamReader inr = new InputStreamReader(conn.getInputStream());
                        int ch;
                        StringBuilder sb = new StringBuilder();
                        while((ch = inr.read()) != -1){
                            sb.append((char) ch);
                        }
                        //ArrayList에 받아온 정보를 넣는다
                        JSONArray json = new JSONArray(sb.toString());
                        for(int i = 0; i<json.length(); i++){
                            JSONObject jo = json.getJSONObject(i);
                            int no = jo.getInt("num");
                            String writer = jo.getString("writer");
                            String title = jo.getString("title");
                            String content = jo.getString("content");
                            String date = jo.getString("date");

                            dmListData.add(new DirectMessage(no, writer, title, content, date));
                        }
                        dmHandler.sendEmptyMessage(0);
                    }else{
                        Toast.makeText(DirectMessageActivity.this, "서버 접속 불가"
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            } catch(Exception e){}
        }
    }

    Handler dmHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                if(dmListData.size() != 0){
                    dmListAdapter = new DirectMessageAdapter(dmListData, DirectMessageActivity.this);
                    dmList.setAdapter(dmListAdapter);
                }else{
                    //DM이 없을 경우 할 설정 - 아직미정
                }
            }
        }
    };
}

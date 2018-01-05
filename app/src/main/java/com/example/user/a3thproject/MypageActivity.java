package com.example.user.a3thproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class MypageActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    ImageButton imgbtn;
    SharedPreferences autoLogin;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyAdapter;
    RecyclerView.LayoutManager recyLayoutManager;
    ArrayList<ClearRecode> recodes;

    StringBuffer sb = new StringBuffer();
    TextView temp;

    int profileName;
    String id_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        //로그인 완성 후 SharedPreferences 의 이름 저장된걸로 바꿔주기
        autoLogin = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        textView = findViewById(R.id.user_profile_name);
        imageView = findViewById(R.id.user_profile_pic);
        imgbtn = findViewById(R.id.user_directMessage);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, DirectMessageActivity.class);
                startActivity(intent);
            }
        });

        recodes = new ArrayList<>();
        if(autoLogin != null) myPageSetting();

        //recyclerView 를 위한 설정. 가로 리스트뷰
        recyclerView = findViewById(R.id.user_profile_clearRecord);
        recyLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyLayoutManager);

        temp = findViewById(R.id.tempTest);
    }

    /**
     * 로그인 되어있을 경우, 저장되어있는 SharedPreferences 에서 로그인 정보를 받아와 마이페이지에 세팅
     */
    public void myPageSetting(){
        //이름, 프로필사진
        id_data = autoLogin.getString("id_data", null).trim();
        textView.setText(id_data);

        GetProfile getProfile = new GetProfile(id_data);
        getProfile.start();

        //클리어 기록
        Thread clearThread = new ClearRecodeThread(id_data);
        clearThread.start();

        //DM창으로 이동
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, DirectMessageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 유저의 아이디로 유저가 클리어한 맵의 기록을 받아온다.
     */
    class ClearRecodeThread extends Thread{
        String id_data;
        public ClearRecodeThread(String id_data){ this.id_data = id_data; }

        @Override
        public void run() {
            String addr = "http://10.10.15.87:8888/escape/getClearRecord?id=" + id_data;
            try{
                URL url = new URL(addr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn != null){
                    conn.setConnectTimeout(1000);
                    conn.setRequestMethod("GET");
                    conn.addRequestProperty("dataType", "json");
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        int ch;
                        InputStreamReader inReader = new InputStreamReader(conn.getInputStream());
                        while ((ch = inReader.read()) != -1) sb.append((char) ch);
                        inReader.close();
                        infoHandler.sendEmptyMessage(1);

                        //ArrayList에 받아온 정보를 넣는다
                        JSONArray json = new JSONArray(sb.toString());
                        for(int i = 0; i<json.length(); i++){
                            JSONObject jo = json.getJSONObject(i);
                            String id = jo.getString("id");
                            String mapTitle = jo.getString("mapTitle");
                            String clearTime = jo.getString("clearTime");
                            String mapTitleImg = jo.getString("mapTitleImg");
                            double star = jo.getInt("star");

                            recodes.add(new ClearRecode(id, mapTitle, clearTime, mapTitleImg, star));
                        }
                    }else{
                        Toast.makeText(MypageActivity.this, "서버 접속 불가"
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 프로필 사진 받아오기
     */
    class GetProfile extends Thread{
        String id_data;
        public GetProfile(String id_data){ this.id_data = id_data; }

        @Override
        public void run() {
            String addr = "http://10.10.15.87:8888/escape/getUserProfile?id=" + id_data;
            Log.v("보낼 주소" , addr);
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
                        if(sb.length() != 0){
                            profileName = getResources().getIdentifier("@drawable/" + sb.toString()
                                    , "drawable", MypageActivity.this.getPackageName());
                            Log.v("프로필 사진 아이디 : ", ""+profileName);
                            infoHandler.sendEmptyMessage(0);
                        }
                    }
                }
            } catch(Exception e){}
        }
    }

    Handler infoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                imageView.setImageResource(profileName);
            }else if(msg.what == 1){
//                recyAdapter = new ClaerRecodeAdapter(recodes, MypageActivity.this);
//                recyclerView.setAdapter(recyAdapter);
                temp.setText(sb.toString());
            }
        }
    };
}

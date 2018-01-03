package com.example.user.a3thproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MypageActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    ImageButton imgbtn;
    SharedPreferences autoLogin;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyAdapter;
    RecyclerView.LayoutManager recyLayoutManager;
    ArrayList<ClearRecode> recodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        //로그인 완성 후 SharedPreferences 의 이름 저장된걸로 바꿔주기
        autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
        textView = findViewById(R.id.user_profile_name);
        imageView = findViewById(R.id.user_profile_pic);
        imgbtn = findViewById(R.id.user_directMessage);

        //레코드 체워넣어야함
        recodes = new ArrayList<>();
        myPageSetting();

        //recyclerView 를 위한 설정. 가로 리스트뷰
        recyclerView = findViewById(R.id.user_profile_clearRecord);
        recyLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyLayoutManager);
        recyAdapter = new ClaerRecodeAdapter(recodes, this);
        recyclerView.setAdapter(recyAdapter);

    }

    /**
     * 로그인 되어있을 경우, 저장되어있는 SharedPreferences 에서 로그인 정보를 받아와 마이페이지에 세팅
     */
    public void myPageSetting(){
        String userNickname = autoLogin.getString("nickname", null);
        String userProfilePic = "@drawable/" + autoLogin.getString("profile", null);
        int profileId = getResources()
                .getIdentifier(userProfilePic, "drawable", this.getPackageName());

        //이름, 프로필사진
        textView.setText(userNickname);
        imageView.setImageResource(profileId);

        //클리어 기록
        Thread clearThread = new ClearRecodeThread(userNickname);
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
     * 유저의 닉네임으로 유저가 클리어한 맵의 기록을 받아온다.
     */
    class ClearRecodeThread extends Thread{
        String nickName;
        public ClearRecodeThread(String nickname){ this.nickName = nickname; }

        @Override
        public void run() {
            String addr = "http://10.10.12.116:8888/escape/getClearRecord?nickName=" + nickName;
            try{
                URL url = new URL(addr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn != null){
                    conn.setConnectTimeout(1000);
                    conn.setRequestMethod("GET");
                    conn.addRequestProperty("dataType", "json");
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        JSONArray json= new JSONArray(conn.toString());
                        for(int i = 0; i< json.length(); i++){
                            Object jsonData = json.getJSONObject(i).optString("null");
                            recodes.add((ClearRecode) jsonData);
                        }
                    }
                }

            } catch(Exception e){}
        }
    }
}

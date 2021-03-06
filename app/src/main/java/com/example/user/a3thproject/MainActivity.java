package com.example.user.a3thproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    final static int EDIT_VALUE = 0;
    ImageView imageView;
    TextView textView;
    Button logout_btn;
    SharedPreferences autoLogin;
    String id;
    Users user;
    int image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        autoLogin = getSharedPreferences("autoLogin_checkbox", Activity.MODE_PRIVATE);

        Intent intent = getIntent();
        id = intent.getStringExtra("loginUser");
        textView.setText(id);

        GetInfoThread thread = new GetInfoThread();
        thread.start();

    }
    //맵 정보 게시판으로 넘어가는 메서드
    public void go_map_info(View view){
        Intent intent = new Intent(this, MapInfo_Activity.class);
        startActivityForResult(intent,EDIT_VALUE);
    }

    //공략 게시판으로 넘어가는 메서드
    public void go_map_tactics(View view){
        Intent intent = new Intent(this, MapTacticsListActivity.class);
        startActivityForResult(intent,EDIT_VALUE);
    }

    //마이페이지로 넘어가는 메서드
    public void go_mypage(View view){
        Intent intent = new Intent(this, MypageActivity.class);
        startActivityForResult(intent,EDIT_VALUE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case EDIT_VALUE :
                if (resultCode == RESULT_OK){
                    imageView.setImageResource(R.drawable.profile_03);
                    textView.setText(id);

                    String message = data.getStringExtra("Textout");
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }else if (resultCode == RESULT_CANCELED){
                    String message = "처리실패";
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void logout_btn(View view){
        logout_btn = findViewById(R.id.logout_btn_main);

        SharedPreferences.Editor autoLogin_editor = autoLogin.edit();
        autoLogin_editor.putString("autoLogin_checked", "false");
        autoLogin_editor.commit(); // commit 안 하면 데이터 초기화 안 됨.
        Log.v("테스트 :", autoLogin.getString("autoLogin_checked", "false"));

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }//logout_btn

    class GetInfoThread extends Thread{
        String address = "http://10.10.15.87:8088/escape/app_getInfo?id="+id;
        JSONArray json;
        JSONObject jo;
        @Override
        public void run() {
            try {
                URL url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                StringBuilder sb = new StringBuilder();

                if (connection != null) {

                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("dataType", "json");
                    connection.setConnectTimeout(10000);
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                        int ch;
                        while ((ch = reader.read()) != -1) {sb.append((char) ch);}
                        reader.close();
                        Log.v("dd",sb.toString());
                        //ArrayList에 받아온 정보를 넣는다
                        jo = new JSONObject(sb.toString());
                        System.out.println(json);
                        Log.v("dd","길이는 : "+json);

                        String id = jo.getString("id");
                        String pw  = jo.getString("pw");
                        String email = jo.getString("email");
                        String name = jo.getString("name");
                        String nickname = jo.getString("nickname");
                        String profile = jo.getString("profile");
                        String room_title = jo.getString("room_title");
                        user = new Users(id,pw,email,name,nickname,profile,room_title);
                        Log.v("dde",user.toString());
                        image = getResources().getIdentifier("@drawable/"+user.getProfile(),"drawable", getApplicationContext().getPackageName());

                        handler.sendEmptyMessage(0);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                imageView.setImageResource(image);
            }
        }
    };


}

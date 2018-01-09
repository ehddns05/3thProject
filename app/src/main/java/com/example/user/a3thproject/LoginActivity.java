package com.example.user.a3thproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText id_input, pw_input;
    String id_data, pw_data;
    CheckBox autoLogin_check;
    boolean isAutoLogin;
    final int LOGIN_REQUEST_CODE = 0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences isCheckedForAutoLogin = getSharedPreferences("autoLogin_checkbox", Activity.MODE_PRIVATE);
        autoLogin_check = findViewById(R.id.autoLogin_check);

        Log.v("테스트 :", isCheckedForAutoLogin.getString("autoLogin_checked", null));

        if(autoLogin_check.isChecked() || isCheckedForAutoLogin.getString("autoLogin_checked", null).equals("true")){

            // 자동 로그인 체크 여부 저장
            SharedPreferences.Editor isCheckedForAutoLogin_editor = isCheckedForAutoLogin.edit();
            isCheckedForAutoLogin_editor.putString("autoLogin_checked", "false");
            isCheckedForAutoLogin_editor.commit(); // commit 안 하면 데이터 초기화 안 됨.

            //자동 로그인이 체크되어 있다면 실행
            SharedPreferences autoLogin = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);

            id_data = autoLogin.getString("id_data", null);
            pw_data = autoLogin.getString("pw_data", null);

            Log.v("MY_TEST", id_data + ", " + pw_data);

            if(id_data != null && pw_data != null){
                Log.v("TEST_LOGIN", id_data + ", " + pw_data);
                // 로그인 데이터가 있을 때
                isAutoLogin = true;
                SendThread thread = new SendThread();
                thread.start();
            }else{
                // 로그인 데이터가 없을 때
                isAutoLogin = false;

            }//inner else if
        }//outer if

    }//onCrate

    /**
     * 로그인 버튼을 누르면 입력된 ID와 PW를 디비에서 확인하고 결과를 반환한다.
     * */
    public void loginBtn(View view){

        SharedPreferences isCheckedForAutoLogin = getSharedPreferences("autoLogin_checkbox", Activity.MODE_PRIVATE);
        if(autoLogin_check.isChecked() || isCheckedForAutoLogin.getString("autoLogin_checked", null).equals("true")){

            // 자동 로그인 체크 여부 저장
            SharedPreferences.Editor isCheckedForAutoLogin_editor = isCheckedForAutoLogin.edit();
            isCheckedForAutoLogin_editor.putString("autoLogin_checked", "true");
            isCheckedForAutoLogin_editor.commit(); // commit 안 하면 데이터 초기화 안 됨.

            //자동 로그인이 체크되어 있다면 실행
            SharedPreferences autoLogin = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);

            id_data = autoLogin.getString("id_data", null);
            pw_data = autoLogin.getString("pw_data", null);

            Log.v("MY_TEST", id_data + ", " + pw_data);

            if(id_data != null && pw_data != null){
                Log.v("TEST_LOGIN", id_data + ", " + pw_data);
                // 로그인 데이터가 있을 때
                isAutoLogin = true;
                SendThread thread = new SendThread();
                thread.start();
            }else{
                // 로그인 데이터가 없을 때
                isAutoLogin = false;

            }//inner else if
        }else{
            id_input = findViewById(R.id.id_input);
            pw_input = findViewById(R.id.pw_input);

            id_data =  id_input.getText().toString();
            pw_data =  pw_input.getText().toString();

            Log.v("TEST >>", "id : " + id_data + ", pw : " + pw_data);

            SendThread thread = new SendThread();
            thread.start();
        }//outer if-else

    }//loginBtn

    public void isNotMember(View view){
        Toast.makeText(this, "홈페이지에서 가입해주세요.", Toast.LENGTH_SHORT).show();
    }//isNotMember

    /**
     * 로그인하는 사용자 당 하나의 쓰레드 적용
     * */
    class SendThread extends Thread{

        // 각자의 ip 주소 써주셔야 합니당~~
        String addr = "http://203.233.199.108:8888/escape/app_login?id=" + id_data + "&pw=" + pw_data;

        @Override
        public void run() {
            try {
                // URL 연결 및 데이터 전송을 위한 기본 세팅
                URL url = new URL(addr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("dataType", "json");
                urlConnection.setConnectTimeout(30000);

                // 클라이언트로부터 받아오는 데이터를 StringBuilder로 받는다.
                StringBuilder responseFromClient = new StringBuilder();

                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream());

                    int dataFromClient;
                    // inputStream에서 들어오는 데이터가 '-1(데이터가 없을때)'가 아니면 데이터를 읽어들인다.
                    while((dataFromClient=inputStream.read()) != -1){
                        // 읽어들인 데이터를 String화 시킨다.
                        responseFromClient.append((char)dataFromClient);
                    }//while

                    if(isAutoLogin == true && responseFromClient.toString().equals("success")){

                        // 아이디 및 비번이 일치했을 때 - 자동로그인 시

                        //Toast.makeText(LoginActivity.this, "자동로그인입니다.", Toast.LENGTH_SHORT).show();
                        Log.v("TEST_LOGIN", "자동 로그인입니다.");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("loginUser", id_data);
                        startActivity(intent);

                    }else if(isAutoLogin == false && responseFromClient.toString().equals("success")){

                        // 아이디 및 비번이 일치했을 때
                        SharedPreferences autoLogin = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                        Log.v("TEST_LOGIN", "일반 로그인입니다.");

                        // SharedPreferences에 사용자가 입력한 로그인 데이터를 저장한다.

                        SharedPreferences.Editor login_info_editor = autoLogin.edit();
                        login_info_editor.putString("id_data", id_data);
                        login_info_editor.putString("pw_data", pw_data);
                        login_info_editor.commit();// 사용자가 입력한 로그인데이터를 최종적으로 commit 함으로써 데이터를 저장할 수 있다. (commit 필수!)

                        //Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("loginUser", id_data);
                        startActivity(intent);
                    }else if(isAutoLogin == true && responseFromClient.toString().equals("fail")){

                        // 아이디 및 비번이 일치하지 않을 때 - 자동로그인 시
                        Log.v("TEST_LOGIN", "자동 로그인 실패!");
                        messageHandler.sendEmptyMessage(0);
                        //Toast.makeText(LoginActivity.this, "자동로그인 실패!", Toast.LENGTH_SHORT).show();

                        return;
                    }else if(isAutoLogin == false && responseFromClient.toString().equals("fail")){

                        // 아이디 및 비번이 일치하지 않을 때
                        Log.v("TEST_LOGIN", "일반 로그인 실패!");
                        messageHandler.sendEmptyMessage(0);
                        //Toast.makeText(LoginActivity.this, "로그인 실패!", Toast.LENGTH_SHORT).show();
                        return;
                    }//inner if

                    finish();

                }//if

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }//run
    }//sendThread - inner Class

    Handler messageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                Log.v("HandlerTEST", "IN");
                Toast.makeText(LoginActivity.this, "로그인 실패!", Toast.LENGTH_SHORT).show();
            }//if
        }//handleMessage
    };// Handler

}//LoginActivity

package com.example.user.a3thproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }//onCrate

    /**
     * 로그인 버튼을 누르면 입력된 ID와 PW를 디비에서 확인하고 결과를 반환한다.
     * */
    public void loginBtn(View view){
        id_input = findViewById(R.id.id_input);
        pw_input = findViewById(R.id.pw_input);

        id_data =  id_input.getText().toString();
        pw_data =  pw_input.getText().toString();

        Log.v("TEST >>", "id : " + id_data + ", pw : " + pw_data);

        SendThread thread = new SendThread();
        thread.start();

    }//loginBtn

    public void isNotMember(View view){
        Toast.makeText(this, "홈페이지에서 가입해주세요.", Toast.LENGTH_SHORT).show();
    }//isNotMember

    /**
     * 로그인하는 사용자 당 하나의 쓰레드 적용
     * */
    class SendThread extends Thread{

        String addr = "http://203.233.199.108:8088/escape/app_login?id=" + id_data + "&pw=" + pw_data;

        @Override
        public void run() {
            try {

                // URL 연결 및 데이터 전송을 위한 기본 세팅
                URL url = new URL(addr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("dataType", "json");
                urlConnection.setConnectTimeout(10000);

                // 클라이언트로부터 받아오는 데이터를 StringBuilder로 받는다.
                StringBuilder responseFromClient = new StringBuilder();

                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream());

                    int dataFromClient;
                    // inputStream에서 들어오는 데이터가 '-1(데이터가 없을때)'가 아니면 데이터를 읽어들인다.
                    while((dataFromClient=inputStream.read()) != -1){
                        // 읽어들인 데이터를 String화 시킨다.
                        responseFromClient.append(dataFromClient);
                    }//while

                    if(responseFromClient.equals("success")){
                        // 아이디 및 비번이 일치

                    }else if(responseFromClient.equals("fail")){
                        // 아이디 및 비번이 일치하지 않을 때

                    }//inner if

                }//if


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }//run
    }//sendThread - inner Class

}//LoginActivity

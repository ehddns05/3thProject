package com.example.user.a3thproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DMDetailActivity extends AppCompatActivity {

    TextView num, title, date, writer, content;
    EditText recentMessage;
    ScrollView scrollView;
    Button sendBtn;
    DirectMessage dm;

    int DM_NUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmdetail);

        Intent getIntent = getIntent();
        dm = (DirectMessage) getIntent.getSerializableExtra("directMessage");
        DM_NUM = getIntent.getIntExtra("DM_NO",1);
        Log.v("받아온 Extra", dm + "");

        num = findViewById(R.id.dmDetail_num);
        title = findViewById(R.id.dmDetail_title);
        date = findViewById(R.id.dmDetail_date);
        writer = findViewById(R.id.dmDetail_writer);
        content = findViewById(R.id.dmDetail_content);
        scrollView = findViewById(R.id.dmDetail_scroll);
        recentMessage = findViewById(R.id.dmDetail_inputDM);
        //자동으로 올라오는 소프트 키보드 막기
        recentMessage.setInputType(0);
        recentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recentMessage.setInputType(1);
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.showSoftInput(recentMessage, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        sendBtn = findViewById(R.id.dmDetail_sendDM);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recent = recentMessage.getText().toString();
                Log.v("메시지 보내기 : ", "시작");
                SendMessage sendMessage = new SendMessage(recent);
                sendMessage.start();
            }

            class SendMessage extends Thread {
                String message;
                String re_writer = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE).getString("id_data", null);
                String user_id = writer.getText().toString();

                public SendMessage(String message){ this.message = message; }

                @Override
                public void run() {
                    String addr = "http://10.10.15.87:8888/escape/sendDM?" +
                            "writer=" + re_writer + "&user_id=" + user_id + "&content=" + message;
                    Log.v("메시지 보내기 : ", addr);
                    try{
                        URL url = new URL(addr);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        if(conn != null){
                            conn.setRequestMethod("GET");
                            conn.setConnectTimeout(1000);
                            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                                InputStreamReader inr = new InputStreamReader(conn.getInputStream());
                                if(inr.read() != 1 && inr.read() != 0){
                                    Bundle data = new Bundle();
                                    data.putString("msg", user_id + " 님께 DM을 전송하였습니다.");
                                    Message message = new Message();
                                    message.setData(data);
                                    ToastHandler.sendMessage(message);
                                }
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                }
            }
        });

        scrollView.setVerticalScrollBarEnabled(true);

        setDirectMessage();
    }

    public void setDirectMessage(){
        num.setText(String.valueOf(DM_NUM));
        date.setText(dm.getDate());
        writer.setText(dm.getWriter());
        title.setText(dm.getContent());
        content.setText(dm.getContent());
    }

    Handler ToastHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String message = msg.getData().getString("msg");
            Toast.makeText(DMDetailActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}

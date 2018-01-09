package com.example.user.a3thproject;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetInformationThread extends Thread {
    String key;
    String id_data;
    String addr;
    Context context;
    StringBuilder sb = new StringBuilder();

    public GetInformationThread(String key, String id_data, String addr, Context context){
        this.key = key;
        this.id_data = id_data;
        this.addr = addr;
        this.context = context;
    }

    @Override
    public void run() {
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

                    Bundle bundle = new Bundle();
                    bundle.putString(key, sb.toString());
                    Message message = new Message();
                    message.setData(bundle);

                    if(context == MypageActivity.getContext()){
                        //MypageHandler로 메시지 보내기
                    }else if(context == DirectMessageActivity.getContext()){
                        //DirectMessageHandler으로 메시지 보내기
                    }

                }else{
                    Toast.makeText(context, "서버 접속 불가", Toast.LENGTH_SHORT).show();
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

package com.example.user.a3thproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DirectMessageActivity extends AppCompatActivity {

    Spinner spinner;
    ListView dmList;
    SharedPreferences autoLogin;
    ArrayList<DirectMessage> dmListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_message);

        dmList = findViewById(R.id.directMessage_list);
        dmListData = new ArrayList<>();
        spinner = findViewById(R.id.directMessage_spinner);
        autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.dm_spinner, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        GetDMThread thread = new GetDMThread();
        thread.start();
    }

    /**
     * 유저에게 클리어 기록이 있으면 기록을 받아와 만들어져 있는 ArrayList 에 집어넣음
     */
    class GetDMThread extends Thread{
        String nickName = autoLogin.getString("nickname", null);

        @Override
        public void run() {
            String addr = "http://10.10.12.116:8888/escape/getDM?nickName="+nickName;
            try{
                URL url = new URL(addr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn != null){
                    conn.setConnectTimeout(1000);
                    conn.setRequestMethod("GET");
                    conn.addRequestProperty("dataType", "json");
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        JSONArray json= new JSONArray(conn.toString());
                        for(int i = 0; i<json.length(); i++){
                            Object jsonData = json.getJSONObject(i).optString("null");
                            dmListData.add((DirectMessage) jsonData);
                        }
                    }
                }
            } catch(Exception e){}
        }
    }
}

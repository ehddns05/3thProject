package com.example.user.a3thproject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapInfo_Activity extends AppCompatActivity {

    private List<GameMap_VO> game_maps;
    private RecyclerView game_map_list;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info_list);
        game_maps = new ArrayList<>();

        RecyclerView game_map_list = findViewById(R.id.rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        game_map_list.setLayoutManager(linearLayoutManager);

        SendThread thread = new SendThread();
        Log.v("THREADTEST", "COMPLETE1");
        thread.start();
        Log.v("THREADTEST", "COMPLETE2");
    }//onCreate

    private class SendThread extends Thread{

        String addr = "http://10.10.11.162:8888/escape/getMapInfo";

        @Override
        public void run() {
            try {
                Log.v("THREADTEST", "COMPLETE3");
                URL url = new URL(addr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("dataType", "json");
                urlConnection.setConnectTimeout(30000);

                Log.v("THREADTEST", "COMPLETE4");
                StringBuilder mapData_json = new StringBuilder();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    Log.v("THREADTEST", "COMPLETE5");
                    InputStreamReader receivingMapData = new InputStreamReader(urlConnection.getInputStream());


                    /// 스프링 디비 코드 작성해라.......정신차리자


                    int stream_data;
                    while((stream_data = receivingMapData.read()) != -1){
                        Log.v("THREADTEST", "COMPLETE6");

                        mapData_json.append((char)stream_data);
                    }//while
                    JSONArray json = new JSONArray(mapData_json.toString());
                    for(int i=0; i < json.length(); i++){
                        // 데이터 가지고 오기
                        JSONObject jsonObject = json.getJSONObject(i);
                        Log.v("THREADTEST", "COMPLETE_0");
                        int no = jsonObject.getInt("no");
                        String user_id = jsonObject.getString("user_id");
                        String title = jsonObject.getString("title");
                        int inputdate = Integer.parseInt(jsonObject.getString("inputdate"));

                        game_maps.add(new GameMap_VO(title, user_id, inputdate));
                    }//for
                    Log.v("THREADTEST", "COMPLETE7");
                    GameMap_Handler.sendEmptyMessage(0);
                    Log.v("THREADTEST", "COMPLETE8");
                }//if







            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }//run
    }//SendThread

    Handler GameMap_Handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                if(game_maps.size() > 0){
                    adapter = new RVAapter(game_maps);
                    game_map_list.setAdapter(adapter);
                }//inner if
            }//if
        }//handleMessage
    };



}//MapInfo_Activity

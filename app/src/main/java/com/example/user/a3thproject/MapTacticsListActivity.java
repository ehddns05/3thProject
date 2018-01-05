package com.example.user.a3thproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapTacticsListActivity extends AppCompatActivity {

    BaseExpandableAdapter listAdapter;
    ExpandableListView expListView;
    List<TacticsParentGroup> parentList;
    Map<Integer, List<TacticsChildGroup>> childList;
    TacticsParentGroup parentGroup;
    TacticsChildGroup childGroup;
    List<TacticsChildGroup> innerChild;
    List<String> testArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tactics_list);

        expListView = findViewById(R.id.tacticsList);
        preparedListData();

        listAdapter = new BaseExpandableAdapter(this, parentList, childList);
        expListView.setAdapter(listAdapter);

        TacticsThread tacticsThread = new TacticsThread();
        tacticsThread.start();

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            int lastClickedPosition = -1;

            @Override
            public boolean onGroupClick(ExpandableListView expandableListView,
                                        View view, int groupPosition, long id) {

                if (lastClickedPosition != groupPosition) {
                    expListView.collapseGroup(lastClickedPosition);
                    expListView.expandGroup(groupPosition);
                } else {
                    expListView.collapseGroup(groupPosition);
                }

                lastClickedPosition = groupPosition;
                return true;
            }
        });
    }

    class TacticsThread extends Thread {
        String address = "http://10.10.17.63:8088/androidTest/login";
        private HttpURLConnection urlConnection;
        private URL url;

        @Override
        public void run() {
            try {
                setupConnection();

                if (!isConnectionOK()) {
                    Toast.makeText(MapTacticsListActivity.this, "다시 시도해주세요",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONArray jsonArray = new JSONArray(urlConnection.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object jsonData = jsonArray.getJSONObject(i).optString("null");
                    testArray.add(String.valueOf(jsonArray));
                }

                for (String string : testArray) {
                    System.out.println(string);
                }
                /*InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
                int dataFromClient;
                StringBuilder responseFromClient = new StringBuilder();
                while ((dataFromClient = reader.read()) != -1) {
                    responseFromClient.append((char) dataFromClient);
                }

                if (responseFromClient.toString().equals("success"))
                    Log.d("서버갔다오기테스트", "띠요요요요요요요요요요요요요요요요요요용옹");
                else
                    Log.d("서버갔다오기테스트", "실패!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                reader.close();
                */
            } catch (Exception e) {
                e.printStackTrace();
                /*Toast.makeText(MapTacticsListActivity.this, "초기화면으로 돌아갑니다",
                        Toast.LENGTH_SHORT).show();*/
            }
        }

        private boolean isConnectionOK() throws IOException {
            return urlConnection.getResponseCode()
                    == HttpURLConnection.HTTP_OK;
        }

        private void setupConnection() throws IOException {
            url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("dataType", "json");
            urlConnection.setConnectTimeout(10000);
        }
    }

    public void preparedListData() {
        parentList = new ArrayList<>();
        childList = new HashMap<>();
        innerChild = new ArrayList<>();

        parentGroup = new TacticsParentGroup(1, "Notice1", "20170102");
        parentList.add(parentGroup);
        childGroup = new TacticsChildGroup(
                "완큐의방", "박완규1", "이방은탈출할수없다!!!!");
        innerChild.add(childGroup);
        childList.put(parentGroup.getListNo(), innerChild);

        parentGroup = new TacticsParentGroup(2, "Notice2", "20170102");
        parentList.add(parentGroup);
        childGroup = new TacticsChildGroup(
                "완큐의방", "박완규2", "이방은탈출할수없다!!!!");
        innerChild.add(childGroup);
        childList.put(parentGroup.getListNo(), innerChild);

        parentGroup = new TacticsParentGroup(3, "Notice3", "20170102");
        parentList.add(parentGroup);
        childGroup = new TacticsChildGroup(
                "완큐의방", "박완규3", "이방은탈출할수없다!!!!");
        innerChild.add(childGroup);
        childList.put(parentGroup.getListNo(), innerChild);

        parentGroup = new TacticsParentGroup(4, "Notice4", "20170102");
        parentList.add(parentGroup);
        childGroup = new TacticsChildGroup(
                "완큐의방", "박완규4", "이방은탈출할수없다!!!!");
        innerChild.add(childGroup);
        childList.put(parentGroup.getListNo(), innerChild);

        Log.d("innerChild의 갯수", String.valueOf(innerChild.size()));
    }
}


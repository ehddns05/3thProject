package com.example.user.a3thproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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
    List<TacticsChildGroup> innerChild;
    Map<Integer, List<TacticsChildGroup>> childList;
    TacticsParentGroup parentGroup;
    TacticsChildGroup childGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tactics_list);

        //preparedListData();
        TacticsThread tacticsThread = new TacticsThread();
        tacticsThread.start();

        expListView = findViewById(R.id.tacticsList);
        listAdapter = new BaseExpandableAdapter(this, tacticsThread.parentList, tacticsThread.childList);

        System.out.println("getgroupcount" + listAdapter.getGroupCount());
        expListView.setAdapter(listAdapter);

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
        public List<TacticsParentGroup> parentList;
        public List<TacticsChildGroup> innerChild;
        public Map<Integer, List<TacticsChildGroup>> childList;

        String address = "http://192.168.25.17:8088/escape/getTacticsList";
        StringBuilder stringBuilder = new StringBuilder();
        private HttpURLConnection urlConnection;
        private URL url;

        @Override
        public void run() {
            try {
                setupConnection();

                if (!isConnectionOK()) {
                    Log.d("에러발생", "connection 불량");
                    return;
                }

                getTacticsData();

            } catch (Exception e) {
                e.printStackTrace();
                /*Toast.makeText(MapTacticsListActivity.this, "초기화면으로 돌아갑니다",
                        Toast.LENGTH_SHORT).show();*/
            }
        }

        public void setupConnection() throws IOException {
            url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();

            if (urlConnection == null) return;

            urlConnection.setConnectTimeout(1000);
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("dataType", "json");
        }

        public boolean isConnectionOK() throws IOException {
            return urlConnection.getResponseCode()
                    == HttpURLConnection.HTTP_OK;
        }

        private void getTacticsData() throws IOException, JSONException {
            int count;
            InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
            JSONObject jsonObject;
            parentList = new ArrayList<>();
            innerChild = new ArrayList<>();
            childList = new HashMap<>();

            while ((count = reader.read()) != -1) {
                stringBuilder.append((char) count);
            }

            reader.close();

            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            JSONArray parentArray = (JSONArray) jsonArray.get(0);
            JSONArray childArray = (JSONArray) jsonArray.get(1);

            for (int i = 0; i < parentArray.length(); i++) {
                jsonObject = parentArray.getJSONObject(i);
                parentGroup = new TacticsParentGroup(
                        (Integer) jsonObject.get("listNo"),
                        (String) jsonObject.get("tacticsTitle"),
                        (String) jsonObject.get("date"));
                parentList.add(parentGroup);

                jsonObject = childArray.getJSONObject(i);
                childGroup = new TacticsChildGroup(
                        (String) jsonObject.get("mapTitle"),
                        (String) jsonObject.get("tacticsWriter"),
                        (String) jsonObject.get("tacticsContent"));
                innerChild.add(childGroup);

                childList.put(parentGroup.getListNo(), innerChild);
            }
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
    }
}


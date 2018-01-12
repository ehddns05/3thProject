package com.example.user.a3thproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapTacticsListActivity extends AppCompatActivity {

    private ExpandableListView expListView;
    private TacticsThread tacticsThread;
    private final MyHandler myHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tactics_list);

        //tacticsThread = new TacticsThread();
        //tacticsThread.start();

        Button searchButton = findViewById(R.id.search_btn);
        searchButton.setOnClickListener(v -> {
            tacticsThread = new TacticsThread();
            tacticsThread.start();
        });

        //searchButton.performClick();
    }

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                expListView = findViewById(R.id.tacticsList);
                BaseExpandableAdapter listAdapter
                        = new BaseExpandableAdapter(MapTacticsListActivity.this,
                        tacticsThread.parentList, tacticsThread.childList);
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
                break;
            case 1:
                Toast.makeText(MapTacticsListActivity.this,
                        "네트워크 상태가 불안정 합니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class TacticsThread extends Thread {
        private StringBuilder stringBuilder = new StringBuilder();
        private HttpURLConnection urlConnection;
        private URL url;

        List<TacticsParentGroup> parentList;
        List<TacticsChildGroup> innerChild;
        Map<Integer, List<TacticsChildGroup>> childList;

        @Override
        public void run() {
            try {
                setupConnection();
                if (!isConnectionOK()) {
                    myHandler.sendEmptyMessage(1);
                    return;
                }

                getTacticsData();
                myHandler.sendEmptyMessage(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void setupConnection() throws IOException {
            EditText vSearchWord = findViewById(R.id.search_bar);
            Spinner vSearchType = findViewById(R.id.spinner);
            String searchType = vSearchType.getSelectedItem().toString();
            String searchWord = vSearchWord.getText().toString();

            Log.d("검색타입", searchType);
            Log.d("검색단어", searchWord);
            String address = "http://10.10.15.87:8888/escape/getTacticsList?"
                    + "searchType=" + searchType + "&searchWord=" + searchWord;
            url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();

            if (urlConnection == null) return;

            urlConnection.setConnectTimeout(1000);
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("dataType", "json");
        }

        boolean isConnectionOK() throws IOException {
            return urlConnection.getResponseCode()
                    == HttpURLConnection.HTTP_OK;
        }

        void getTacticsData() throws IOException, JSONException {
            int count;
            InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
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

            convertJsonToArray(parentArray, childArray);
        }

        void convertJsonToArray(JSONArray parentArray, JSONArray childArray) throws JSONException {
            JSONObject jsonObject;

            for (int i = 0; i < parentArray.length(); i++) {
                jsonObject = parentArray.getJSONObject(i);
                TacticsParentGroup parentGroup = new TacticsParentGroup(
                        (Integer) jsonObject.get("listNo"),
                        (String) jsonObject.get("tacticsTitle"),
                        (String) jsonObject.get("date"));

                parentList.add(parentGroup);

                jsonObject = childArray.getJSONObject(i);
                TacticsChildGroup childGroup = new TacticsChildGroup(
                        (String) jsonObject.get("mapTitle"),
                        (String) jsonObject.get("tacticsWriter"),
                        (String) jsonObject.get("tacticsContent"));

                innerChild.add(childGroup);
                childList.put(parentGroup.getListNo(), innerChild);
            }
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<MapTacticsListActivity> mActivity;

        private MyHandler(MapTacticsListActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MapTacticsListActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

}


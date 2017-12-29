package com.example.user.a3thproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class MapTacticsListActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private TacticsExpandableListView tacticsExpandableListView;
    private List<String> parentList;
    private List<List<String>> childList;
    private List<String> childListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tactics_list);

        parentList = new ArrayList<>();
        parentList.add("공지사항");

        childListData = new ArrayList<>();
        childListData.add("데모맵");

        childList = new ArrayList<>();
        childList.add(childListData);

        //expandableListView = findViewById(R.id.tacticsList);
        //tacticsExpandableListView = new TacticsExpandableListView(this, parentList, childList);
        //expandableListView.setAdapter(tacticsExpandableListView);
    }
}

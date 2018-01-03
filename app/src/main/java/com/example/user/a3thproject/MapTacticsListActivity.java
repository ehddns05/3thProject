package com.example.user.a3thproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tactics_list);

        expListView = findViewById(R.id.tacticsList);
        preparedListData();

        listAdapter = new BaseExpandableAdapter(this, parentList, childList);
        expListView.setAdapter(listAdapter);
    }

    public void preparedListData() {
        parentList = new ArrayList<>();
        parentGroup = new TacticsParentGroup(1,"Notice", "20170102");

        parentList.add(parentGroup);

        childList = new HashMap<>();
        innerChild = new ArrayList<>();
        childGroup = new TacticsChildGroup(
                "완큐의방", "박완규", "이방은탈출할수없다!!!!");

        innerChild.add(childGroup);
        childList.put(parentGroup.getListNo(), innerChild);
    }
}

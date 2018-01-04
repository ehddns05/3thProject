package com.example.user.a3thproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

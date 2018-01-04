package com.example.user.a3thproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by KiM on 2017-12-28.
 */

public class BaseExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<TacticsParentGroup> parentList;
    private Map<Integer, List<TacticsChildGroup>> childList;

    public BaseExpandableAdapter (Context context, List<TacticsParentGroup> parentList,
                                  Map<Integer, List<TacticsChildGroup>> childList) {
        this.context = context;
        this.parentList = parentList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return parentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1 /*this.childList.get(this.parentList.get(groupPosition).getListNo()).size()*/;
    }

    @Override
    public TacticsParentGroup getGroup(int groupPosition) {
        return parentList.get(groupPosition);
    }

    @Override
    public TacticsChildGroup getChild(int groupPosition, int childPosition) {
        return this.childList.get(this.parentList.get(groupPosition).getListNo()).get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TacticsParentGroup parentGroup = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tacticslist_parent, null);
        }

        TextView tacticsNo = convertView
                .findViewById(R.id.parentListNo);
        TextView tacticsTitle = convertView
                .findViewById(R.id.parentListTitle);
        TextView tacticsDate = convertView
                .findViewById(R.id.parentListDate);

        String listNo = String.valueOf(parentGroup.getListNo());

        tacticsNo.setText(listNo);
        tacticsTitle.setText(parentGroup.getTacticsTitle());
        tacticsDate.setText(parentGroup.getDate());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        TacticsChildGroup childText = getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tacticslist_child, null);
        }

        TextView mapTitle = convertView.findViewById(R.id.mapTitle);
        TextView tacticsWriter = convertView.findViewById(R.id.tacticsWriter);
        TextView tacticsContents= convertView.findViewById(R.id.testChildText);

        mapTitle.setText(childText.getMapTitle());
        tacticsWriter.setText(childText.getTacticsWriter());
        tacticsContents.setText(childText.getTacticsContent());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

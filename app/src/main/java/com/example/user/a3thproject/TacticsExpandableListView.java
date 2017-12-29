package com.example.user.a3thproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by KiM on 2017-12-28.
 */

public class TacticsExpandableListView extends BaseExpandableListAdapter {

    private List<String> parentList;
    private List<List<String>> childList;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;
    private Context context;

    private class ViewHolder{
        public TextView parentText;
        public TextView childText;
    }

    public TacticsExpandableListView(Context context, List<String> parentList,
                                     List<List<String>> childList) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.parentList = parentList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return parentList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childList.get(i).size();
    }

    @Override
    public String getGroup(int i) {
        return parentList.get(i);
    }

    @Override
    public String getChild(int i, int i1) {
        return childList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

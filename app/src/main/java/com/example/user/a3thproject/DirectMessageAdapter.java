package com.example.user.a3thproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by LSH on 2018-01-02.
 */

public class DirectMessageAdapter extends BaseAdapter {

    ArrayList<DirectMessage> dmData = new ArrayList<>();

    @Override
    public int getCount() {
        return dmData.size();
    }

    @Override
    public Object getItem(int i) {
        return dmData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 아이템이 들어있는 뷰를 생성해서 반환한다.
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DirectMessageView itemView = new DirectMessageView(viewGroup.getContext());
        DirectMessage dm = dmData.get(i);

        itemView.setNum(dm.getNum());
        itemView.setTitle(dm.getTitle());
        itemView.setDate(dm.getDate());
        itemView.setWriter(dm.getWriter());
        return itemView;
    }
}

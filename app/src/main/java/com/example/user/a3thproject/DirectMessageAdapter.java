package com.example.user.a3thproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LSH on 2018-01-02.
 */

public class DirectMessageAdapter extends RecyclerView.Adapter<DirectMessageAdapter.ViewHolder> {

    private ArrayList<DirectMessage> data;
    private Context context;

    public DirectMessageAdapter() {
    }

    public DirectMessageAdapter(ArrayList<DirectMessage> data, Context context) {
        this.data = data;
        this.context = context;
    }

    //리사이클러 뷰 조작을 위한 뷰홀더
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView no, title, writer, date;

        public ViewHolder(View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.list_number);
            title = itemView.findViewById(R.id.list_title);
            writer = itemView.findViewById(R.id.list_writer);
            date = itemView.findViewById(R.id.list_writeDate);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, null, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 새롭게 만들어진 뷰 안에 데이터를 채워넣는다.
     * @param holder 새로 만들어진 뷰
     * @param position 가져올 아이템의 위치
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DirectMessage dm = data.get(position);

        holder.no.setText(String.valueOf(dm.getNum()));
        holder.title.setText(dm.getTitle());
        holder.writer.setText(dm.getWriter());
        holder.date.setText(dm.getDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

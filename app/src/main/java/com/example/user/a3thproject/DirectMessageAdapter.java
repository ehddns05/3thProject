package com.example.user.a3thproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by LSH on 2018-01-02.
 */

public class DirectMessageAdapter extends RecyclerView.Adapter<DirectMessageAdapter.ViewHolder> {

    private ArrayList<DirectMessage> data;
    private Context context;

    //리사이클러 뷰 조작을 위한 뷰홀더
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView no, writer, date, content;
        LinearLayout list;
        public ViewHolder(View view) {
            super(view);
            content = view.findViewById(R.id.list_title);
            list = view.findViewById(R.id.list_listLayout);
            no = view.findViewById(R.id.list_number);
            writer = view.findViewById(R.id.list_writer);
            date = view.findViewById(R.id.list_writeDate);
        }
    }

    public DirectMessageAdapter(ArrayList<DirectMessage> data, Context context) {
        this.data = data;
        this.context = context;
    }

    //레이아웃 관리자가 호출하는 새 뷰 만들기
    @Override
    public DirectMessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DirectMessage dm = data.get(position);

                //리스트를 클릭하면 isChecked 를 true로 바꿈
                CheckDM checkDM = new CheckDM(dm.getNum());
                checkDM.start();

                //클래스 전체를 intent 안에 넣어 전송하기 위해 Bundle 객체를 사용한다
                Intent intent = new Intent(context, DMDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("directMessage", dm);
                intent.putExtras(bundle);
                intent.putExtra("DM_NO", position + 1);
                context.startActivity(intent);
            }
        });

        DirectMessage dm = data.get(position);

        holder.content.setText(dm.getContent());
        holder.no.setText(String.valueOf(position + 1));
        holder.writer.setText(dm.getWriter());
        holder.date.setText(dm.getDate());
        if(dm.getIsChecked().equals("true")){
            int color = Color.rgb(230,230,230);
            holder.content.setTextColor(color);
            holder.no.setTextColor(color);
            holder.writer.setTextColor(color);
            holder.date.setTextColor(color);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CheckDM extends Thread{

        int num;

        CheckDM(int num){ this.num = num; }

        @Override
        public void run() {
            try{
                URL url = new URL("http://10.10.15.87:8888/escape/checkDM?num=" + num);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            } catch (Exception e){e.printStackTrace();}
        }
    }
}

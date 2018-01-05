package com.example.user.a3thproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SCITMASTER on 2017-12-29.
 */

public class ClaerRecodeAdapter extends RecyclerView.Adapter<ClaerRecodeAdapter.ViewHolder> {

    private ArrayList<ClearRecode> data;
    private Context context;

    //리사이클러 뷰 조작을 위한 뷰홀더
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView clearMapPic;
        public TextView clearMapTitle;
        public TextView clearMapstar;
        public TextView clearMapTime;
        public ViewHolder(View view) {
            super(view);
            clearMapPic = view.findViewById(R.id.clear_map_info_box_img);
            clearMapTitle = view.findViewById(R.id.clear_map_info_box_title);
            clearMapstar = view.findViewById(R.id.clear_map_info_box_star);
            clearMapTime = view.findViewById(R.id.clear_map_info_box_time);
        }

    }

    public ClaerRecodeAdapter(ArrayList<ClearRecode> data, Context context) {
        this.data = data;
        this.context = context;
    }

    //레이아웃 관리자가 호출하는 새 뷰 만들기
    @Override
    public ClaerRecodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clear_map_info_box, null, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClearRecode recode = data.get(position);

        holder.clearMapTitle.setText(recode.getMapTitle());
        holder.clearMapTime.setText(recode.getClearTime());

        //별점
        double starGrade = recode.getStar();
        StringBuffer star = new StringBuffer("☆☆☆☆☆");
        for(int i = 0; i< (int)starGrade; i++) star.replace(i,i+1,"★");
        holder.clearMapstar.setText(star.toString());

        //이미지 찾아오기
        String userProfilePic = "@drawable/" + data.get(position).getMapTitleImg();
        Log.v("받아온 이미지 이름 : ", userProfilePic);
        int profileId = context.getResources()
                .getIdentifier(userProfilePic, "drawable", context.getPackageName());
        holder.clearMapPic.setImageResource(profileId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

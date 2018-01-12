package com.example.user.a3thproject;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class RVAapter extends RecyclerView.Adapter<RVAapter.GameMap_ViewHolder> {

    List<GameMap_VO> game_maps;

    public RVAapter(List<GameMap_VO> game_maps) {
        this.game_maps = game_maps;
        Log.v("cardviewTest", "RVAapter IN");
    }//constructor

    @Override
    public GameMap_ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.v("cardviewTest", "onCreateViewHolder IN");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_map_cardview, viewGroup, false);
        GameMap_ViewHolder gameMap_viewHolder = new GameMap_ViewHolder(view);
        return gameMap_viewHolder;
    }

    @Override
    public void onBindViewHolder(GameMap_ViewHolder gameMap_viewHolder, int position) {
        Log.v("cardviewTest", "onBindViewHolder IN");

        Log.v("getInfoTEST", game_maps.get(position).getMap_name());
        Log.v("getInfoTEST", game_maps.get(position).getMaster_name());
        Log.v("getInfoTEST", game_maps.get(position).getCreated_date());
        Log.v("getInfoTEST", game_maps.get(position).getTitleimg());

        // 맵 생성날짜 및 맵 이름 도 가지고는 오지만..........귀차니즘으로 인하여 하지 않음..

        gameMap_viewHolder.map_name.setText(game_maps.get(position).getMap_name());
        gameMap_viewHolder.master_name.setText(game_maps.get(position).getMaster_name());

        //맵 이미지
        String titleimg = game_maps.get(position).getTitleimg();
        switch(titleimg){
            case "mapTitle01":
                gameMap_viewHolder.map_image.setImageResource(R.drawable.map_title_01);
                break;
            case "mapTitle02":
                gameMap_viewHolder.map_image.setImageResource(R.drawable.map_title_02);
                break;
            case "mapTitle03":
                gameMap_viewHolder.map_image.setImageResource(R.drawable.map_title_03);
                break;
            default:
                gameMap_viewHolder.map_image.setImageResource(R.drawable.map_title_06);
                break;
        }//switch

        //생성날짜
        gameMap_viewHolder.inputDate.setText(game_maps.get(position).getCreated_date());

        //별점
        double starGrade = game_maps.get(position).getStar();
        StringBuffer star = new StringBuffer("☆☆☆☆☆");
        for(int i = 0; i< (int)starGrade; i++) star.replace(i,i+1,"★");
        gameMap_viewHolder.starScore.setText(star.toString());

    }

    @Override
    public int getItemCount() {
        Log.v("cardviewTest", "getItemCount IN : " + game_maps.size());
        return game_maps.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class GameMap_ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView map_name;
        TextView master_name, inputDate, starScore;
        ImageView map_image;


        public GameMap_ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            map_name = itemView.findViewById(R.id.map_name);
            master_name = itemView.findViewById(R.id.master_name);
            map_image = itemView.findViewById(R.id.ex_photo);
            inputDate = itemView.findViewById(R.id.input_date);
            starScore = itemView.findViewById(R.id.star);
        }//constractor
    }//GameMap_ViewHolder
}//RVAapter

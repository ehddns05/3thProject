package com.example.user.a3thproject;

public class GameMap_VO {
    private String map_name;
    private String master_name;
    private String created_date;

    public GameMap_VO(String map_name, String master_name, String created_date) {
        this.map_name = map_name;
        this.master_name = master_name;
        this.created_date = created_date;
    }

    public String getMap_name() {
        return map_name;
    }

    public void setMap_name(String map_name) {
        this.map_name = map_name;
    }

    public String getMaster_name() {
        return master_name;
    }

    public void setMaster_name(String master_name) {
        this.master_name = master_name;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    @Override
    public String toString() {
        return "GameMap_VO{" +
                "map_name='" + map_name + '\'' +
                ", master_name='" + master_name + '\'' +
                ", created_date=" + created_date +
                '}';
    }
}//GameMap_VO

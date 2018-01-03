package com.example.user.a3thproject;


public class ClearRecode {
    String id;
    String mapTitle;
    String clearTime;
    String mapTitleImg;
    double star;

    public ClearRecode(){}

    public ClearRecode(String id, String mapTitle, String clearTime, double star) {
        this.id = id;
        this.mapTitle = mapTitle;
        this.clearTime = clearTime;
        this.star = star;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMapTitle(String mapTitle) {
        this.mapTitle = mapTitle;
    }

    public void setClearTime(String clearTime) {
        this.clearTime = clearTime;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public void setMapTitleImg(String mapTitleImg) {
        this.mapTitleImg = mapTitleImg;
    }

    public String getId() {
        return id;
    }

    public String getMapTitle() {
        return mapTitle;
    }

    public String getClearTime() {
        return clearTime;
    }

    public double getStar() {
        return star;
    }

    public String getMapTitleImg() {
        return mapTitleImg;
    }
}

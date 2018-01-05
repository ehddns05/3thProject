package com.example.user.a3thproject;

/**
 * Created by user on 2018-01-05.
 */

public class MapInfoDetailListReview {

    private String content;
    private String date;
    private String star_score;

    public MapInfoDetailListReview(String content, String date, String star_score) {
        this.content = content;
        this.date = date;
        this.star_score = star_score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStar_score() {
        return star_score;
    }

    public void setStar_score(String star_score) {
        this.star_score = star_score;
    }

    @Override
    public String toString() {
        return "MapInfoDetailListReview{" +
                "content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", star_score='" + star_score + '\'' +
                '}';
    }
}

package com.example.user.a3thproject;

/**
 * Created by KIM on 2017-12-29.
 */

public class TacticsChildGroup {

    private String mapTitle;
    private String tacticsWriter;
    private String tacticsContent;

    public TacticsChildGroup (String mapTitle, String tacticsWriter, String tacticsContent) {
        this.mapTitle = mapTitle;
        this.tacticsWriter = tacticsWriter;
        this.tacticsContent = tacticsContent;
    }

    public String getMapTitle() {
        return mapTitle;
    }

    public String getTacticsWriter() {
        return tacticsWriter;
    }

    public String getTacticsContent() {
        return this.tacticsContent;
    }
}

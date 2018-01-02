package com.example.user.a3thproject;

/**
 * Created by KIM on 2017-12-29.
 */

public class TacticsParentGroup {
    private int listNo;
    private String tacticsTitle;
    private String date;

    public TacticsParentGroup(int listNo, String title, String date) {
        this.listNo = listNo;
        this.tacticsTitle = title;
        this.date = date;
    }

    public int getListNo() {
        return listNo;
    }

    public String getTacticsTitle() {
        return tacticsTitle;
    }

    public String getDate() {
        return date;
    }
}

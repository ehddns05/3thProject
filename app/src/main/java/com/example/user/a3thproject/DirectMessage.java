package com.example.user.a3thproject;

import java.io.Serializable;

/**
 * Created by SCITMASTER on 2018-01-02.
 */

@SuppressWarnings("serial")
public class DirectMessage implements Serializable{
    int num;
    String writer;
    String content;
    String date;
    String isChecked;


    public DirectMessage(){}

    public DirectMessage(int num, String writer, String content, String date, String isChecked) {
        this.num = num;
        this.writer = writer;
        this.content = content;
        this.date = date;
        this.isChecked = isChecked;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public int getNum() {
        return num;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "DirectMessage [num=" + num + ", writer=" + writer + ", content=" + content
                + ", date=" + date + ", isChecked=" + isChecked + "]";
    }
}

package com.example.user.a3thproject;

/**
 * Created by user on 2018-01-10.
 */

public class Users {

    private String id;
    private String pw;
    private String email;
    private String name;
    private String nickname;
    private String profile;
    private String room_title;

    public Users() {

    }

    public Users(String id, String pw, String email, String name, String nickname, String profile, String room_title) {
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.profile = profile;
        this.room_title = room_title;
    }

    public Users(String id, String pw){
        this.id = id;
        this.pw = pw;
    }//cons

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getRoom_title() {
        return room_title;
    }

    public void setRoom_title(String room_title) {
        this.room_title = room_title;
    }

    @Override
    public String toString() {
        return "Users [id=" + id + ", pw=" + pw + ", email=" + email + ", name=" + name + ", nickname=" + nickname
                + ", profile=" + profile + ", room_title=" + room_title + "]";
    }
}

package com.devhub.mirza.devhub_v1.UIObjects;

import java.io.Serializable;

public class Comments implements Serializable {
    private String Username;
    private String UserProfilePhoto;
    private String Date;
    private String Text;

    public Comments(String username, String userProfilePhoto, String date, String text) {
        Username = username;
        UserProfilePhoto = userProfilePhoto;
        Date = date;
        Text = text;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getUserProfilePhoto() {
        return UserProfilePhoto;
    }

    public void setUserProfilePhoto(String userProfilePhoto) {
        UserProfilePhoto = userProfilePhoto;
    }
}

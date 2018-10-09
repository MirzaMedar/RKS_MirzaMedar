package com.devhub.mirza.devhub_v1.UIObjects;

import java.io.Serializable;
import java.util.Date;

public class PostsDTO implements Serializable {
    private Integer PostID;
    private String Title;
    private String Text;
    private String PhotoPath;
    private String Date;
    private String User;
    private String UserPhotoPath;


    public PostsDTO(Integer postID, String title, String text, String photoPath, String date, String user, String userPhotoPath) {
        PostID = postID;
        Title = title;
        Text = text;
        PhotoPath = photoPath;
        Date = date;
        User = user;
        UserPhotoPath = userPhotoPath;
    }

    public Integer getPostID() {
        return PostID;
    }

    public void setPostID(Integer postID) {
        PostID = postID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getUserPhotoPath() {
        return UserPhotoPath;
    }

    public void setUserPhotoPath(String userPhotoPath) {
        UserPhotoPath = userPhotoPath;
    }
}

package com.devhub.mirza.devhub_v1.UIObjects;

import java.io.Serializable;

public class UIAddPost implements Serializable {
    private int UserID;
    private String Title;
    private String Post;
    private byte[] Photo;
    private String photoBase64;

    public UIAddPost(int userID, String title, String post, String photoBase64) {
        UserID = userID;
        Title = title;
        Post = post;
        this.photoBase64 = photoBase64;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }
}

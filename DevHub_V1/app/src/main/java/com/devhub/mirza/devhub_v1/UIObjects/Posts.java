package com.devhub.mirza.devhub_v1.UIObjects;

import java.io.Serializable;
import java.util.Date;

public class Posts implements Serializable {
    private int PostID;
    private String Title;
    private int UserID;
    private String PostText;
    private String PhotoPath;
    private Date PostDate;
    private boolean IsDeleted;

    public Posts(int postID, String title, int userID, String postText, String photoPath, Date postDate, boolean isDeleted) {
        PostID = postID;
        Title = title;
        UserID = userID;
        PostText = postText;
        PhotoPath = photoPath;
        PostDate = postDate;
        IsDeleted = isDeleted;
    }

    public int getPostID() {
        return PostID;
    }

    public void setPostID(int postID) {
        PostID = postID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getPostText() {
        return PostText;
    }

    public void setPostText(String postText) {
        PostText = postText;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public Date getPostDate() {
        return PostDate;
    }

    public void setPostDate(Date postDate) {
        PostDate = postDate;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }
}

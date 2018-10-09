package com.devhub.mirza.devhub_v1.UIObjects;

import java.io.Serializable;

public class NewCommentDTO implements Serializable {
    private int PostID;
    private String Comment;
    private int UserID;
    public NewCommentDTO(int postID, String comment, int userID) {
        PostID = postID;
        Comment = comment;
        UserID = userID;
    }

    public int getPostID() {
        return PostID;
    }

    public void setPostID(int postID) {
        PostID = postID;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}

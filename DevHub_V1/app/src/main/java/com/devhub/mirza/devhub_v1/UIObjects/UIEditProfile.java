package com.devhub.mirza.devhub_v1.UIObjects;

import java.io.Serializable;

public class UIEditProfile implements Serializable{
    private Integer UserID;
    private String newPosition;
    private String newSkills;
    private String newEmail;
    private String newUsername;
    public UIEditProfile(int userID,String newEmail,String newPosition,String newSkills,String newUsername){
        this.newPosition = newPosition;
        this.UserID = userID;
        this.newSkills = newSkills;
        this.newEmail = newEmail;
        this.newUsername = newUsername;
    }
    public String getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(String newPosition) {
        this.newPosition = newPosition;
    }

    public String getNewSkills() {
        return newSkills;
    }

    public void setNewSkills(String newSkills) {
        this.newSkills = newSkills;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}

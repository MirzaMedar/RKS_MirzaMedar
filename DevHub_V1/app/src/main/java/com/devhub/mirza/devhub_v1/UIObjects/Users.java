package com.devhub.mirza.devhub_v1.UIObjects;

public class Users {
    private int UserID;
    private String FirstName;
    private String LastName;
    private String Email;
    private String ProfilePhotoPath;
    private Integer PositionID;
    private String Position;
    private String Skills;
    private String Username;


    public Users(int userId,String firstName,String lastName,String email,String profilePhotoPath,Integer positionID,String position,String skills,String username) {
        UserID = userId;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        ProfilePhotoPath = profilePhotoPath;
        Position = position;
        Skills = skills;
        Username = username;
        this.PositionID = positionID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProfilePhotoPath() {
        return ProfilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        ProfilePhotoPath = profilePhotoPath;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getSkills() {
        return Skills;
    }

    public void setSkills(String skills) {
        Skills = skills;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Integer getPositionID() {
        return PositionID;
    }

    public void setPositionID(Integer positionID) {
        PositionID = positionID;
    }
}

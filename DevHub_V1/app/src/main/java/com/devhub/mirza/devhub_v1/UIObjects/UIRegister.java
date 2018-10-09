package com.devhub.mirza.devhub_v1.UIObjects;

import java.io.Serializable;

public class UIRegister implements Serializable{
    private String FirstName;
    private String LastName;
    private String Email;
    private byte[] Photo;
    private String stringPhoto;
    private String Position;
    private String Skills;
    private String Username;
    private String Password;

    public UIRegister(String firstName,String lastName,String email,byte[] photo,String stringPhoto,String position,String skills,String username,String password)
    {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Photo = photo;
        Position = position;
        Skills = skills;
        Username = username;
        Password = password;
        this.stringPhoto = stringPhoto;
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

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        this.Photo = photo;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getStringPhoto() {
        return stringPhoto;
    }

    public void setStringPhoto(String stringPhoto) {
        this.stringPhoto = stringPhoto;
    }
}

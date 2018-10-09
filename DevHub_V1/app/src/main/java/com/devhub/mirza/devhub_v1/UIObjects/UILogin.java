package com.devhub.mirza.devhub_v1.UIObjects;

import java.io.Serializable;

public class UILogin implements Serializable {
    private String Username;
    private String Password;
    private String DeviceID;

    public UILogin(String username,String password,String deviceID){
        Username = username;
        Password = password;
        DeviceID = deviceID;
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

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }
}

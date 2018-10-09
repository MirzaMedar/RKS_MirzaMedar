package com.devhub.mirza.devhub_v1.UIObjects;

import java.io.Serializable;
import java.util.Date;

public class AuthenticationTokens implements Serializable {
    private int AuthenticationTokenID;
    private int UserID;
    private String DeviceID;
    private String Token;
    private Date TokenDate;
    private boolean IsDeleted;
    public AuthenticationTokens(int authenticationTokenID,int userID,String deviceID,String token,Date tokenDate,boolean isDeleted)
    {
        AuthenticationTokenID= authenticationTokenID;
        UserID = userID;
        DeviceID = deviceID;
        Token = token;
        TokenDate = tokenDate;
        IsDeleted = isDeleted;
    }
    public Integer getAuthenticationTokenID() {
        return AuthenticationTokenID;
    }

    public void setAuthenticationTokenID(Integer authenticationTokenID) {
        AuthenticationTokenID = authenticationTokenID;
    }

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(Integer userID) {
        UserID = userID;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public Date getTokenDate() {
        return TokenDate;
    }

    public void setTokenDate(Date tokenDate) {
        TokenDate = tokenDate;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }
}

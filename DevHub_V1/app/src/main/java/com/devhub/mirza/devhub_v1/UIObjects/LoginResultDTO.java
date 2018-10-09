package com.devhub.mirza.devhub_v1.UIObjects;

import java.io.Serializable;

public class LoginResultDTO implements Serializable {
    private AuthenticationTokens token;
    private Users user;
    public AuthenticationTokens getToken() {
        return token;
    }

    public void setToken(AuthenticationTokens token) {
        this.token = token;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}

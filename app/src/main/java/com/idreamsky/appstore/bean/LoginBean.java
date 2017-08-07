package com.idreamsky.appstore.bean;

/**
 * Created by idreamsky on 2017/8/7.
 */

public class LoginBean {

    private String token;
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

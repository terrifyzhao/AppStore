package com.idreamsky.appstore.data;

import com.idreamsky.appstore.data.http.ApiService;

/**
 * Created by idreamsky on 2017/8/7.
 */

public class LoginModel {

    private ApiService mApiService;

    public LoginModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    public void login(String userName, String password){

    }
}

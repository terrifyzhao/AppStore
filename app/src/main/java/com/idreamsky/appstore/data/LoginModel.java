package com.idreamsky.appstore.data;

import com.idreamsky.appstore.bean.BaseBean;
import com.idreamsky.appstore.bean.LoginBean;
import com.idreamsky.appstore.bean.LoginRequestBean;
import com.idreamsky.appstore.data.http.ApiService;

import io.reactivex.Observable;

/**
 * Created by idreamsky on 2017/8/7.
 */

public class LoginModel {

    private ApiService mApiService;

    public LoginModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    public Observable<BaseBean<LoginBean>> login(LoginRequestBean bean){
        return mApiService.login(bean);
    }
}

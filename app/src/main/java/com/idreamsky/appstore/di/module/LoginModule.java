package com.idreamsky.appstore.di.module;

import com.idreamsky.appstore.data.LoginModel;
import com.idreamsky.appstore.data.http.ApiService;
import com.idreamsky.appstore.presenter.contract.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by idreamsky on 2017/8/7.
 */

@Module
public class LoginModule {

    private LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @Provides
    public LoginContract.View provideView(){
        return view;
    }

    @Provides
    public LoginModel provideModel(ApiService apiService){
        return new LoginModel(apiService);
    }

}

package com.idreamsky.appstore.di.module;

import com.idreamsky.appstore.data.AppInfoModel;
import com.idreamsky.appstore.data.http.ApiService;
import com.idreamsky.appstore.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhaojiuzhou on 2017/8/4.
 */

@Module
public class AppInfoModule {

    private AppInfoContract.View view;

    public AppInfoModule(AppInfoContract.View view) {
        this.view = view;
    }

    @Provides
    public AppInfoContract.View provideView(){
        return view;
    }

    @Provides
    public AppInfoModel provideModel(ApiService apiService){
        return new AppInfoModel(apiService);
    }
}

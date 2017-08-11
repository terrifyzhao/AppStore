package com.idreamsky.appstore.di.module;

import com.idreamsky.appstore.data.AppInfoModel;
import com.idreamsky.appstore.data.http.ApiService;
import com.idreamsky.appstore.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by idreamsky on 2017/8/10.
 */

@Module
public class AppDetailModule {

    private AppInfoContract.DetailView mView;

    public AppDetailModule(AppInfoContract.DetailView mView) {
        this.mView = mView;
    }

    @Provides
    public AppInfoContract.DetailView provideDetailView(){
        return mView;
    }

    @Provides
    public AppInfoModel provideAppModel(ApiService apiService){
        return new AppInfoModel(apiService);
    }
}

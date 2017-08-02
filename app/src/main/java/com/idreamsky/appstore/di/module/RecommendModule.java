package com.idreamsky.appstore.di.module;

import com.idreamsky.appstore.data.RecommendModel;
import com.idreamsky.appstore.data.http.ApiService;
import com.idreamsky.appstore.presenter.contract.RecommendContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhaojiuzhou on 2017/7/27.
 */


@Module
public class RecommendModule {

    private RecommendContract.View mView;


    public RecommendModule(RecommendContract.View view) {
        this.mView = view;
    }

    @Provides
    public RecommendContract.View provideView(){
        return mView;
    }

    @Provides
    public RecommendModel provideModel(ApiService apiService){
        return new RecommendModel(apiService);
    }
}

package com.idreamsky.appstore.di.module;

import com.idreamsky.appstore.data.RankModel;
import com.idreamsky.appstore.data.http.ApiService;
import com.idreamsky.appstore.presenter.contract.RankContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhaojiuzhou on 2017/8/4.
 */

@Module
public class RankModule {

    private RankContract.View view;

    public RankModule(RankContract.View view) {
        this.view = view;
    }

    @Provides
    public RankContract.View provideView(){
        return view;
    }

    @Provides
    public RankModel provideModel(ApiService apiService){
        return new RankModel(apiService);
    }
}

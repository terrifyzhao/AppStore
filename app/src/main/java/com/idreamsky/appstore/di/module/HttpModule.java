package com.idreamsky.appstore.di.module;

import android.app.Application;

import com.idreamsky.appstore.common.rx.RxErrorHandle;
import com.idreamsky.appstore.data.http.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhaojiuzhou on 2017/7/27.
 */

@Module
public class HttpModule {

    @Singleton
    @Provides
    public Retrofit provideRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    @Singleton
    @Provides
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);

    }

    @Singleton
    @Provides
    public RxErrorHandle provideErrorHandle(Application application) {
        return new RxErrorHandle(application);
    }

}

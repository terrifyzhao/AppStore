package com.idreamsky.appstore.di.module;

import android.app.Application;
import android.util.TimeUtils;

import com.google.gson.Gson;
import com.idreamsky.appstore.common.http.OkHttpInterceptor;
import com.idreamsky.appstore.common.rx.RxErrorHandle;
import com.idreamsky.appstore.data.http.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
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
    public OkHttpClient provideOkHttp(Application application, Gson gson){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new OkHttpInterceptor(application,gson))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        return client;
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BaseUrl)
                .client(client)
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

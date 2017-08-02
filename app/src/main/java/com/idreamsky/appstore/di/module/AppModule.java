package com.idreamsky.appstore.di.module;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhaojiuzhou on 2017/7/27.
 */

@Module
public class AppModule {

    private Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Singleton
    @Provides
    public Application provideApplication(){
        return mApplication;
    }

    @Singleton
    @Provides
    public Gson provideGson(){
        return new Gson();
    }
}

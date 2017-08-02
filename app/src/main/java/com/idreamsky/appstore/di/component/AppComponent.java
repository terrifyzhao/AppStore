package com.idreamsky.appstore.di.component;

import android.app.Application;

import com.idreamsky.appstore.AppApplication;
import com.idreamsky.appstore.common.rx.RxErrorHandle;
import com.idreamsky.appstore.data.http.ApiService;
import com.idreamsky.appstore.di.module.AppModule;
import com.idreamsky.appstore.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zhaojiuzhou on 2017/7/27.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    ApiService getApiService();

    Application getApplication();

    RxErrorHandle getErrorHandle();
}



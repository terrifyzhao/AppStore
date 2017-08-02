package com.idreamsky.appstore;

import android.app.Application;
import android.content.Context;

import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.di.component.DaggerAppComponent;
import com.idreamsky.appstore.di.module.AppModule;
import com.idreamsky.appstore.di.module.HttpModule;

/**
 * Created by zhaojiuzhou on 2017/7/27.
 */

public class AppApplication extends Application {

    private AppComponent mAppComponent;

    public static Application get(Context context){
        return (Application) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .httpModule(new HttpModule())
                .build();
    }

}

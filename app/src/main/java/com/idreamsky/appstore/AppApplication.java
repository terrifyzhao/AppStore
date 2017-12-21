package com.idreamsky.appstore;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.view.View;

import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.di.component.DaggerAppComponent;
import com.idreamsky.appstore.di.module.AppModule;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by zhaojiuzhou on 2017/7/27.
 */

public class AppApplication extends Application {

    private AppComponent mAppComponent;

    private View mView;

    public AppComponent getmAppComponent() {
        return mAppComponent;
    }

    public void setmAppComponent(AppComponent mAppComponent) {
        this.mAppComponent = mAppComponent;
    }

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }

    public static Application get(Context context){
        return (Application) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

}

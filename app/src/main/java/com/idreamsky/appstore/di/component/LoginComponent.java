package com.idreamsky.appstore.di.component;

import com.idreamsky.appstore.di.annotation.ActivityScope;
import com.idreamsky.appstore.di.module.LoginModule;
import com.idreamsky.appstore.ui.activity.LoginActivity;

import dagger.Component;

/**
 * Created by idreamsky on 2017/8/7.
 */

@ActivityScope
@Component(modules = LoginModule.class,dependencies = AppComponent.class)
public interface LoginComponent {

    void inject(LoginActivity activity);
}

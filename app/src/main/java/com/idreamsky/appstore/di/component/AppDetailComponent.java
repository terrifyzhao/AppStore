package com.idreamsky.appstore.di.component;

import com.idreamsky.appstore.di.annotation.FragmentScope;
import com.idreamsky.appstore.di.module.AppDetailModule;
import com.idreamsky.appstore.ui.fragment.AppDetailFragment;

import dagger.Component;

/**
 * Created by idreamsky on 2017/8/10.
 */


@FragmentScope
@Component(modules = AppDetailModule.class, dependencies = AppComponent.class)
public interface AppDetailComponent {

    void inject(AppDetailFragment fragment);
}

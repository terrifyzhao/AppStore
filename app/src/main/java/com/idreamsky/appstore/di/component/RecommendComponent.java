package com.idreamsky.appstore.di.component;

import com.idreamsky.appstore.di.annotation.FragmentScope;
import com.idreamsky.appstore.di.module.RecommendModule;
import com.idreamsky.appstore.ui.fragment.RecommendFragment;

import dagger.Component;

/**
 * Created by zhaojiuzhou on 2017/7/27.
 */

@FragmentScope
@Component(modules = RecommendModule.class,dependencies = AppComponent.class)
public interface RecommendComponent {

    void inject(RecommendFragment fragment);
}

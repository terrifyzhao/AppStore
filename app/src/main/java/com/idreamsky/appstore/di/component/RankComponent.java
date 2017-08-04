package com.idreamsky.appstore.di.component;

import com.idreamsky.appstore.di.annotation.FragmentScope;
import com.idreamsky.appstore.di.module.RankModule;
import com.idreamsky.appstore.ui.fragment.RankFragment;

import dagger.Component;

/**
 * Created by zhaojiuzhou on 2017/8/4.
 */

@FragmentScope
@Component(modules = RankModule.class,dependencies = AppComponent.class)
public interface RankComponent {

    void inject(RankFragment fragment);
}

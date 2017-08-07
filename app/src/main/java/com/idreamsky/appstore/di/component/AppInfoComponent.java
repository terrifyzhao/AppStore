package com.idreamsky.appstore.di.component;

import com.idreamsky.appstore.di.annotation.FragmentScope;
import com.idreamsky.appstore.di.module.AppInfoModule;
import com.idreamsky.appstore.ui.fragment.GameFragment;
import com.idreamsky.appstore.ui.fragment.RankFragment;

import dagger.Component;

/**
 * Created by zhaojiuzhou on 2017/8/4.
 */

@FragmentScope
@Component(modules = AppInfoModule.class,dependencies = AppComponent.class)
public interface AppInfoComponent {

    void injectRank(RankFragment fragment);
    void injectGame(GameFragment fragment);
}

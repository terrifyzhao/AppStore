package com.idreamsky.appstore.di.component;

import com.idreamsky.appstore.di.annotation.FragmentScope;
import com.idreamsky.appstore.di.module.CategoryModule;
import com.idreamsky.appstore.ui.fragment.CategoryFragment;

import dagger.Component;

/**
 * Created by idreamsky on 2017/8/9.
 */

@FragmentScope
@Component(modules = CategoryModule.class,dependencies = AppComponent.class)
public interface CategoryComponent {

    void inject(CategoryFragment fragment);

}

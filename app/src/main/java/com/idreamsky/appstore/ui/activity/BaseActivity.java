package com.idreamsky.appstore.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.idreamsky.appstore.AppApplication;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.presenter.BasePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaojiuzhou on 2017/7/28.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private Unbinder bind;
    protected AppApplication appApplication;
    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        bind = ButterKnife.bind(this);
        appApplication = (AppApplication) getApplication();
        setActivityComponent(appApplication.getAppComponent());
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != Unbinder.EMPTY){
            bind.unbind();
        }
    }

    protected abstract int setLayoutId();

    protected abstract void setActivityComponent(AppComponent appComponent);

    protected abstract void init();
}

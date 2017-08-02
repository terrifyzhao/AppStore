package com.idreamsky.appstore.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idreamsky.appstore.AppApplication;
import com.idreamsky.appstore.R;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.presenter.BasePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaojiuzhou on 2017/7/28.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    private Unbinder mUnbinder;
    private AppApplication mAppApplication;
    private View mRootView;
    @Inject
    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mAppApplication = (AppApplication) getActivity().getApplication();
        setFragmentComponent(this.mAppApplication.getAppComponent());
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
    }

    protected abstract int setLayoutId();

    protected abstract void setFragmentComponent(AppComponent appComponent);

    protected abstract void init();
}

package com.idreamsky.appstore.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.idreamsky.appstore.AppApplication;
import com.idreamsky.appstore.R;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.ui.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class ProgressFragment<T> extends Fragment implements BaseView {

    private FrameLayout mainView;
    private FrameLayout contentLayout;
    private TextView errorText;

    protected AppApplication mAppApplication;
    @Inject
    protected T mPresenter;
    private Unbinder bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = (FrameLayout) inflater.inflate(R.layout.fragment_progress, container, false);
        contentLayout = (FrameLayout) mainView.findViewById(R.id.contentLayout);
        errorText = (TextView) mainView.findViewById(R.id.errorText);
        mainView.findViewById(R.id.errorLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorLayoutClick();
            }
        });

        return mainView;
    }

    public void showContentLayout() {
        showLayout(R.id.contentLayout);
    }

    public void showErrorLayout(String text) {
        showLayout(R.id.errorLayout);
        errorText.setText(text);

    }

    public void showProgressLayout() {
        showLayout(R.id.loadLayout);

    }

    public void showLayout(int resId) {
        for (int i = 0; i < mainView.getChildCount(); i++) {
            if (mainView.getChildAt(i).getId() == resId) {
                mainView.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                mainView.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAppApplication = (AppApplication) getActivity().getApplication();
        setFragmentComponent(mAppApplication.getAppComponent());

        setRealContentView();
        init();
    }

    private void setRealContentView() {
        View realContentView = LayoutInflater.from(getActivity()).inflate(setLayoutId(), contentLayout, true);
        bind = ButterKnife.bind(this, realContentView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != Unbinder.EMPTY) {
            bind.unbind();
        }
    }

    protected abstract int setLayoutId();

    protected abstract void setFragmentComponent(AppComponent appComponent);

    protected abstract void init();

    protected abstract void errorLayoutClick();

    @Override
    public void showLoading() {
        showProgressLayout();
    }

    @Override
    public void dismissLoading() {
        showContentLayout();
    }

    @Override
    public void showError(String text) {
        showErrorLayout(text);
    }
}

package com.idreamsky.appstore.common.rx.observer;

import com.idreamsky.appstore.common.excption.BaseException;
import com.idreamsky.appstore.ui.BaseView;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhaojiuzhou on 2017/8/2.
 */

public abstract class ProgressObserver<T> extends DefaultObserver<T> {

    private BaseView mView;

    public ProgressObserver(BaseView view) {
        this.mView = view;
    }

    protected boolean isShowProgress() {
        return true;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (isShowProgress()) {
            mView.showLoading();
        }
    }

    @Override
    public void onComplete() {
        mView.dismissLoading();
    }


    @Override
    public void onError(@NonNull Throwable e) {
        mView.showError(((BaseException)e).getMsg());
    }
}

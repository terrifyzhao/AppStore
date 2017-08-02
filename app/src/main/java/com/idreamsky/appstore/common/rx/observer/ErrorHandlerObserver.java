package com.idreamsky.appstore.common.rx.observer;

import android.content.Context;

import com.idreamsky.appstore.common.excption.BaseException;
import com.idreamsky.appstore.common.rx.RxErrorHandle;

import io.reactivex.annotations.NonNull;

/**
 * Created by zhaojiuzhou on 2017/7/31.
 */

public abstract class ErrorHandlerObserver<T> extends DefaultObserver<T> {

    protected RxErrorHandle mErrorHandle;

    public ErrorHandlerObserver(Context context) {

        mErrorHandle = new RxErrorHandle(context);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        BaseException exception = mErrorHandle.handleError(e);
        mErrorHandle.showErrorMsg(exception);
        dismissProgress();
    }

    protected abstract void dismissProgress();



}

package com.idreamsky.appstore.common.rx.observer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhaojiuzhou on 2017/8/1.
 */

public abstract  class ProgressDialogObserver<T> extends ErrorHandlerObserver<T> {

    private Context mContext;
    private ProgressDialog mDialog;
    private Disposable mDisposable;


    public ProgressDialogObserver(Context context) {
        super(context);
        this.mContext = context;
        initDialog();
    }

    protected boolean isShowDialog(){
        return true;
    }


    private void initDialog() {
        if (mDialog == null){
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("正在加载...");
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dispose();
                }
            });
        }
    }

    private void dispose() {
        mDisposable.dispose();
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.mDisposable = d;
        if (mDialog != null && isShowDialog()){
            mDialog.show();
        }
    }

    @Override
    public void onComplete() {
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
//
//    @Override
//    protected void dismissDialog() {
//        if (mDialog.isShowing()){
//            mDialog.dismiss();
//        }
//    }
}

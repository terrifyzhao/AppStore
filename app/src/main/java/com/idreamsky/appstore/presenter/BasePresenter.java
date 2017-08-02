package com.idreamsky.appstore.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.idreamsky.appstore.ui.BaseView;

/**
 * Created by zhaojiuzhou on 2017/7/26.
 */

public class BasePresenter<M,V extends BaseView> {

    protected M mModel;
    protected V mView;

    protected Context mContext;

    public BasePresenter(M m, V v) {
        this.mModel = m;
        this.mView = v;
        mContext = initContext();
    }

    public Context initContext(){
        Activity activity = null;
        if (mView instanceof Activity){
            activity = (Activity) mView;
        }else if (mView instanceof Fragment){
            activity = ((Fragment) mView).getActivity();
        }
        return activity;
    }
}

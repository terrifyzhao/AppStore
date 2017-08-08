package com.idreamsky.appstore.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.idreamsky.appstore.bean.LoginBean;
import com.idreamsky.appstore.bean.LoginRequestBean;
import com.idreamsky.appstore.common.Constant;
import com.idreamsky.appstore.common.excption.ApiExcption;
import com.idreamsky.appstore.common.rx.RxHttpResponseCompat;
import com.idreamsky.appstore.common.util.SharedUtil;
import com.idreamsky.appstore.data.LoginModel;
import com.idreamsky.appstore.presenter.contract.LoginContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by idreamsky on 2017/8/7.
 */

public class LoginPresenter extends BasePresenter<LoginModel,LoginContract.View> {

    @Inject
    public LoginPresenter(LoginModel loginModel, LoginContract.View view) {
        super(loginModel, view);
    }

    public void login(LoginRequestBean bean){
        mModel.login(bean)
                .compose(RxHttpResponseCompat.<LoginBean>compatResult())
                .subscribe(new Observer<LoginBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mView.showLoading();
            }

            @Override
            public void onNext(@NonNull LoginBean loginBean) {
                saveUserData(loginBean);
                mView.startActivity();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (e instanceof ApiExcption){
                    mView.showError(((ApiExcption) e).getMsg());
                }else{
                    mView.showError(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                mView.dismissLoading();
            }
        });
    }

    private void saveUserData(LoginBean loginBean){
        SharedPreferences.Editor editor = SharedUtil.with(mContext).edit();
        editor.putString("token",loginBean.getToken());
        editor.putString("photo",loginBean.getUser().getLogo_url());
        editor.putString("userName",loginBean.getUser().getUsername());
        editor.apply();

    }

}

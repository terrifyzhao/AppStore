package com.idreamsky.appstore.presenter;

import com.idreamsky.appstore.data.LoginModel;
import com.idreamsky.appstore.presenter.contract.LoginContract;

/**
 * Created by idreamsky on 2017/8/7.
 */

public class LoginPresenter extends BasePresenter<LoginModel,LoginContract.View> {

    public LoginPresenter(LoginModel loginModel, LoginContract.View view) {
        super(loginModel, view);
    }
}

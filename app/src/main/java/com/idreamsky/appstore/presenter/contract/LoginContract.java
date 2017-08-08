package com.idreamsky.appstore.presenter.contract;

import com.idreamsky.appstore.bean.LoginBean;
import com.idreamsky.appstore.ui.BaseView;

/**
 * Created by idreamsky on 2017/8/7.
 */

public interface LoginContract {

    interface View extends BaseView{
        void startActivity();
    }

}

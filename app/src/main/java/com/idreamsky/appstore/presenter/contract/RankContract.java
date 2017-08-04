package com.idreamsky.appstore.presenter.contract;

import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.bean.PageBean;
import com.idreamsky.appstore.ui.BaseView;

/**
 * Created by zhaojiuzhou on 2017/8/4.
 */

public interface RankContract {

    interface View extends BaseView{

        void showResult(PageBean<AppInfo> data);
    }
}

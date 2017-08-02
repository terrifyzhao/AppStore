package com.idreamsky.appstore.presenter.contract;

import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.presenter.BasePresenter;
import com.idreamsky.appstore.ui.BaseView;

import java.util.List;

/**
 * Created by zhaojiuzhou on 2017/7/26.
 */

public interface RecommendContract {

    interface View extends BaseView{

        void showResult(List<AppInfo> datas);
    }
}

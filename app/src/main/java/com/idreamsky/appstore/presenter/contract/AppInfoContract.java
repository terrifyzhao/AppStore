package com.idreamsky.appstore.presenter.contract;

import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.bean.BaseBean;
import com.idreamsky.appstore.bean.PageBean;
import com.idreamsky.appstore.ui.BaseView;

import io.reactivex.Observable;

/**
 * Created by zhaojiuzhou on 2017/8/4.
 */

public interface AppInfoContract {

    interface View extends BaseView{

        void showResult(PageBean<AppInfo> data);
    }

    interface DetailView extends BaseView{
        void showResult(AppInfo data);
    }

    interface IAppInfoModel{

        //应用数据
        Observable<BaseBean<PageBean<AppInfo>>> getListData(int type, int page);

        //分类列表的数据
        Observable<BaseBean<PageBean<AppInfo>>> getCategoryData(int type, int categoryId, int page);

        //获取应用详情
        Observable<BaseBean<AppInfo>> getAppDetail(int id);

    }

}

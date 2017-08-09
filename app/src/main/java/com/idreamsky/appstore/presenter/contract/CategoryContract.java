package com.idreamsky.appstore.presenter.contract;

import com.idreamsky.appstore.bean.BaseBean;
import com.idreamsky.appstore.bean.CategoryBean;
import com.idreamsky.appstore.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by idreamsky on 2017/8/9.
 */

public interface CategoryContract {

    interface ICategoryModel{
        Observable<BaseBean<List<CategoryBean>>> getCategoryList();
    }

    interface CategoryView extends BaseView{
        void showResult(List<CategoryBean> data);
    }

}

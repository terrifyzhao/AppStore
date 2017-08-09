package com.idreamsky.appstore.data;

import com.idreamsky.appstore.bean.BaseBean;
import com.idreamsky.appstore.bean.CategoryBean;
import com.idreamsky.appstore.data.http.ApiService;
import com.idreamsky.appstore.presenter.contract.CategoryContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by idreamsky on 2017/8/9.
 */

public class CategoryModel implements CategoryContract.ICategoryModel {

    private ApiService mApiService;

    public CategoryModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    public Observable<BaseBean<List<CategoryBean>>> getCategoryList(){
        return mApiService.category();
    }
}

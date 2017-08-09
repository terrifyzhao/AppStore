package com.idreamsky.appstore.di.module;

import com.idreamsky.appstore.data.CategoryModel;
import com.idreamsky.appstore.data.http.ApiService;
import com.idreamsky.appstore.presenter.contract.CategoryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by idreamsky on 2017/8/9.
 */

@Module
public class CategoryModule {

    private CategoryContract.CategoryView mView;

    public CategoryModule(CategoryContract.CategoryView mView) {
        this.mView = mView;
    }

    @Provides
    public CategoryContract.CategoryView provideCategoryView(){
        return mView;
    }

    @Provides
    public CategoryContract.ICategoryModel provideCategoryModel(ApiService apiService){
        return new CategoryModel(apiService);
    }
}

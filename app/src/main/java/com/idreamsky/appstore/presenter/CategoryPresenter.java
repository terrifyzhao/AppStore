package com.idreamsky.appstore.presenter;

import com.idreamsky.appstore.bean.CategoryBean;
import com.idreamsky.appstore.common.rx.RxHttpResponseCompat;
import com.idreamsky.appstore.common.rx.observer.ProgressObserver;
import com.idreamsky.appstore.presenter.contract.CategoryContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Created by idreamsky on 2017/8/9.
 */

public class CategoryPresenter extends BasePresenter<CategoryContract.ICategoryModel,CategoryContract.CategoryView> {


    @Inject
    public CategoryPresenter(CategoryContract.ICategoryModel iCategoryModel, CategoryContract.CategoryView categoryView) {
        super(iCategoryModel, categoryView);
    }

    public void requestData(){
        mModel.getCategoryList()
                .compose(RxHttpResponseCompat.<List<CategoryBean>>compatResult())
                .subscribe(new ProgressObserver<List<CategoryBean>>(mView) {
                    @Override
                    public void onNext(@NonNull List<CategoryBean> categoryBeen) {
                        mView.showResult(categoryBeen);
                    }
                });
    }


}

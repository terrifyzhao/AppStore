package com.idreamsky.appstore.presenter;

import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.bean.PageBean;
import com.idreamsky.appstore.common.rx.RxHttpResponseCompat;
import com.idreamsky.appstore.common.rx.observer.ProgressObserver;
import com.idreamsky.appstore.data.AppInfoModel;
import com.idreamsky.appstore.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhaojiuzhou on 2017/8/4.
 */

public class AppInfoPresenter extends BasePresenter<AppInfoModel, AppInfoContract.View> {

    @Inject
    public AppInfoPresenter(AppInfoModel appInfoModel, AppInfoContract.View view) {
        super(appInfoModel, view);
    }

    public void RequestData(int type, int page) {

        Observer<PageBean<AppInfo>> observer;
        if (page > 0) {
            observer = new Observer<PageBean<AppInfo>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull PageBean<AppInfo> pageBean) {
                    if (pageBean != null) {
                        mView.showResult(pageBean);
                    } else {
                        mView.showError("无数据");
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
        } else {
            observer = new ProgressObserver<PageBean<AppInfo>>(mView) {
                @Override
                public void onNext(@NonNull PageBean<AppInfo> pageBean) {
                    if (pageBean != null) {
                        mView.showResult(pageBean);
                    } else {
                        mView.showError("无数据");
                    }
                }
            };
        }

        mModel.getListData(type, page)
                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
                .subscribe(observer);
    }


    public void RequestCategoryData(int type, int categoryId, int page) {
        Observer<PageBean<AppInfo>> observer;
        if (page > 0) {
            observer = new Observer<PageBean<AppInfo>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull PageBean<AppInfo> pageBean) {
                    if (pageBean != null) {
                        mView.showResult(pageBean);
                    } else {
                        mView.showError("无数据");
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
        } else {
            observer = new ProgressObserver<PageBean<AppInfo>>(mView) {
                @Override
                public void onNext(@NonNull PageBean<AppInfo> pageBean) {
                    if (pageBean != null) {
                        mView.showResult(pageBean);
                    } else {
                        mView.showError("无数据");
                    }
                }
            };
        }

        mModel.getCategoryData(type, categoryId, page)
                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
                .subscribe(observer);
    }



}

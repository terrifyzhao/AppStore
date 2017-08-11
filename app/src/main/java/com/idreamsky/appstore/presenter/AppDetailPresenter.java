package com.idreamsky.appstore.presenter;

import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.common.rx.RxHttpResponseCompat;
import com.idreamsky.appstore.common.rx.observer.ProgressObserver;
import com.idreamsky.appstore.data.AppInfoModel;
import com.idreamsky.appstore.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Created by idreamsky on 2017/8/10.
 */

public class AppDetailPresenter extends BasePresenter<AppInfoModel,AppInfoContract.DetailView> {


    @Inject
    public AppDetailPresenter(AppInfoModel appInfoModel, AppInfoContract.DetailView detailView) {
        super(appInfoModel, detailView);
    }


    public void RequestAppDetail(int id){
        mModel.getAppDetail(id)
                .compose(RxHttpResponseCompat.<AppInfo>compatResult())
                .subscribe(new ProgressObserver<AppInfo>(mView) {
                    @Override
                    public void onNext(@NonNull AppInfo appInfo) {
                        mView.showResult(appInfo);
                    }
                });
    }
}

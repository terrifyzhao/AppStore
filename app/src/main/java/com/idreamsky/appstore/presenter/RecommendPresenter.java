package com.idreamsky.appstore.presenter;

import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.bean.PageBean;
import com.idreamsky.appstore.common.rx.RxHttpResponseCompat;
import com.idreamsky.appstore.common.rx.observer.ProgressDialogObserver;
import com.idreamsky.appstore.common.rx.observer.ProgressObserver;
import com.idreamsky.appstore.data.RecommendModel;
import com.idreamsky.appstore.presenter.contract.RecommendContract;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Created by zhaojiuzhou on 2017/7/26.
 */

public class RecommendPresenter extends BasePresenter<RecommendModel, RecommendContract.View> {

    @Inject
    public RecommendPresenter(RecommendModel recommendModel, RecommendContract.View view) {
        super(recommendModel, view);
    }

    public void requestData() {

        mModel.getApps()
                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
                .subscribe(new ProgressObserver<PageBean<AppInfo>>(mView) {
                    @Override
                    public void onNext(@NonNull PageBean<AppInfo> pageBean) {
                        if (pageBean != null){
                            mView.showResult(pageBean.getDatas());
                        }else{
                            mView.showError("无数据");
                        }
                    }
                });
    }
}



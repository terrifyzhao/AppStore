package com.idreamsky.appstore.presenter;

import android.Manifest;
import android.app.Activity;
import android.widget.Toast;

import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.bean.IndexBean;
import com.idreamsky.appstore.bean.PageBean;
import com.idreamsky.appstore.common.rx.RxHttpResponseCompat;
import com.idreamsky.appstore.common.rx.observer.ProgressObserver;
import com.idreamsky.appstore.data.RecommendModel;
import com.idreamsky.appstore.presenter.contract.RecommendContract;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by zhaojiuzhou on 2017/7/26.
 */

public class RecommendPresenter extends BasePresenter<RecommendModel, RecommendContract.View> {

    @Inject
    public RecommendPresenter(RecommendModel recommendModel, RecommendContract.View view) {
        super(recommendModel, view);
    }

    public void requestData() {

        RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                .flatMap(new Function<Boolean, ObservableSource<IndexBean>>() {
                    @Override
                    public ObservableSource<IndexBean> apply(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            return mModel.getIndex().compose(RxHttpResponseCompat.<IndexBean>compatResult());
                        }else{
                            Toast.makeText(mContext, "无权限", Toast.LENGTH_SHORT).show();
                            return Observable.empty();
                        }
                    }
                }).subscribe(new ProgressObserver<IndexBean>(mView) {
                    @Override
                    public void onNext(@NonNull IndexBean pageBean) {
                        if (pageBean != null){
                            mView.showResult(pageBean);
                        }else{
                            mView.showError("无数据");
                        }
                    }
                });
    }
}



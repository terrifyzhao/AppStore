package com.idreamsky.appstore.common.rx;

import android.util.Log;

import com.idreamsky.appstore.bean.BaseBean;
import com.idreamsky.appstore.bean.PageBean;
import com.idreamsky.appstore.common.excption.ApiExcption;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhaojiuzhou on 2017/7/31.
 */

public class RxHttpResponseCompat {

    public static <T> ObservableTransformer<BaseBean<T>,T> compatResult(){
        return new ObservableTransformer<BaseBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<BaseBean<T>> upstream) {
                return upstream.flatMap(new Function<BaseBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull final BaseBean<T> tBaseBean) throws Exception {
                        if (tBaseBean.success()){
                            return Observable.create(new ObservableOnSubscribe<T>() {
                                @Override
                                public void subscribe(@NonNull ObservableEmitter<T> e){
                                    try{
                                        e.onNext(tBaseBean.getData());
                                        e.onComplete();
                                    }catch (Exception ex){
                                        ex.printStackTrace();
                                        e.onError(ex);
                                    }
                                }
                            });
                        }else{
                            return Observable.error(new ApiExcption(tBaseBean.getStatus(),tBaseBean.getMessage()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}


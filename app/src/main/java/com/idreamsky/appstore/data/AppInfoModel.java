package com.idreamsky.appstore.data;

import android.graphics.pdf.PdfDocument;

import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.bean.BaseBean;
import com.idreamsky.appstore.bean.PageBean;
import com.idreamsky.appstore.data.http.ApiService;

import io.reactivex.Observable;

/**
 * Created by zhaojiuzhou on 2017/8/4.
 */

public class AppInfoModel {

    public static final int RANKTYPE = 1;
    public static final int GAMETYPE = 2;

    private ApiService mApiService;

    public AppInfoModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    public Observable<BaseBean<PageBean<AppInfo>>> getListData(int type, int page){
        if (type == RANKTYPE){
            return mApiService.rank(page);
        }else if (type == GAMETYPE){
            return mApiService.game(page);
        }else {
            return Observable.empty();
        }
    }
}

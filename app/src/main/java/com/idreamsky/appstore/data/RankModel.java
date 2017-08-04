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

public class RankModel {
    private ApiService mApiService;

    public RankModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    public Observable<BaseBean<PageBean<AppInfo>>> getRankList(int page){
        return mApiService.rank(page);
    }
}

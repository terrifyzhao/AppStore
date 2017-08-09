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

    public static final int FEATURED = 1;
    public static final int TOPLIST = 2;
    public static final int NEWLIST = 3;

    private ApiService mApiService;

    public AppInfoModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }


    //返回应用列表项
    public Observable<BaseBean<PageBean<AppInfo>>> getListData(int type, int page) {
        if (type == RANKTYPE) {
            return mApiService.rank(page);
        } else if (type == GAMETYPE) {
            return mApiService.game(page);
        } else {
            return Observable.empty();
        }
    }

    //分类列表的数据
    public Observable<BaseBean<PageBean<AppInfo>>> getCategoryData(int type, int categoryId, int page) {
        if (type == FEATURED) {
            return mApiService.categoryFeatured(categoryId, page);
        } else if (type == TOPLIST) {
            return mApiService.categoryTop(categoryId, page);
        } else if (type == NEWLIST) {
            return mApiService.categoryNew(categoryId, page);
        } else {
            return Observable.empty();

        }
    }


}

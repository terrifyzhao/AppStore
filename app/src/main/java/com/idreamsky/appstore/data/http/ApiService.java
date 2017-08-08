package com.idreamsky.appstore.data.http;

import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.bean.BaseBean;
import com.idreamsky.appstore.bean.IndexBean;
import com.idreamsky.appstore.bean.LoginBean;
import com.idreamsky.appstore.bean.LoginRequestBean;
import com.idreamsky.appstore.bean.PageBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhaojiuzhou on 2017/7/25.
 */

public interface ApiService {

    String BaseUrl = "http://112.124.22.238:8081/course_api/cniaoplay/";

    @GET("featured2")
    Observable<BaseBean<PageBean<AppInfo>>> getAppInfo(@Query("page") int page);


    @GET("index")
    Observable<BaseBean<IndexBean>> index();

    @GET("toplist")
    Observable<BaseBean<PageBean<AppInfo>>> rank(@Query("page") int page);

    @GET("game")
    Observable<BaseBean<PageBean<AppInfo>>> game(@Query("page") int page);

    @POST("login")
    Observable<BaseBean<LoginBean>> login(@Body LoginRequestBean loginRequest);

}

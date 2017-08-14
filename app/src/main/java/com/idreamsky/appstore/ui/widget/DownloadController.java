package com.idreamsky.appstore.ui.widget;

import android.content.Context;
import android.support.v4.app.NavUtils;

import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.AppDownloadInfo;
import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.bean.BaseBean;
import com.idreamsky.appstore.common.rx.RxHttpResponseCompat;
import com.idreamsky.appstore.common.rx.RxSchedulers;
import com.idreamsky.appstore.common.util.AppUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.http.GET;
import retrofit2.http.Path;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;

/**
 * Created by idreamsky on 2017/8/14.
 */

public class DownloadController {

    private RxDownload mDownload;
    private String downPath = "";
    private Context mContext;

    private Api mApi;


    public DownloadController(RxDownload mDownload, Context context) {
        this.mDownload = mDownload;
        this.mContext = context;
        if (mDownload != null){
            mApi = mDownload.getRetrofit().create(Api.class);
        }
    }


    private void handleClick(final DownloadProgressButton btn, final AppInfo info) {

        bindClick(btn,info);

        //判断本地是否安装了该应用
        isInstallApp(info.getPackageName())
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event) throws Exception {
                        //如果没有安装则判断是否存在安装包
                        if (event.getFlag() == DownloadFlag.NORMAL) {
                            return isExitApp(info);
                        }
                        //否则直接返回
                        return Observable.just(event);
                    }
                })
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event) throws Exception {
                        //如果存在安装包
                        if (event.getFlag() == DownloadFlag.COMPLETED) {
                            return getDownloadInfo(info).flatMap(new Function<AppDownloadInfo, ObservableSource<DownloadEvent>>() {
                                @Override
                                public ObservableSource<DownloadEvent> apply(@NonNull AppDownloadInfo appDownloadInfo) throws Exception {
                                    info.setDownloadInfo(appDownloadInfo);
                                    return downloadStatus(appDownloadInfo);
                                }
                            });
                        }
                        //否则直接返回
                        return Observable.just(event);
                    }
                })
                .compose(RxSchedulers.<DownloadEvent>io_main())
                .subscribe(new DownloadConsumer(btn));

    }

    private void bindClick(final DownloadProgressButton btn, final AppInfo appinfo) {
        RxView.clicks(btn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                int flag = (int) btn.getTag(R.id.tag_apk_flag);

                switch (flag) {
                    case DownloadFlag.INSTALLED:
                        install(appinfo.getPackageName());
                        break;

                    case DownloadFlag.STARTED:
                        pauseDownload(appinfo);
                        break;

                    case DownloadFlag.NORMAL:
                    case DownloadFlag.PAUSED:
                        startDownload(btn,appinfo);
                        break;

                    case DownloadFlag.COMPLETED:
                        installApk(appinfo);
                        break;

                    default:
                        break;
                }

            }
        });
    }

    private void installApk(AppInfo info) {
        String path = downPath + File.separator + info.hashCode();
        AppUtils.installApk(mContext,path);
    }

    private void startDownload(final DownloadProgressButton btn,final AppInfo info) {

        final AppDownloadInfo downloadInfo = info.getDownloadInfo();
        if (downloadInfo == null) {
            getDownloadInfo(info).subscribe(new Consumer<AppDownloadInfo>() {
                @Override
                public void accept(AppDownloadInfo appDownloadInfo) throws Exception {
                    info.setDownloadInfo(appDownloadInfo);
                    download(btn,appDownloadInfo);
                }
            });
        }else{
            download(btn,downloadInfo);
        }

    }

    private void download(DownloadProgressButton btn,AppDownloadInfo downloadInfo){
        mDownload.startAll(downloadInfo.getDownloadUrl()).subscribe();
        mDownload.receiveDownloadStatus(downloadInfo.getDownloadUrl()).subscribe(new DownloadConsumer(btn));

    }

    private void pauseDownload(AppInfo appinfo) {
        mDownload.pauseServiceDownload(appinfo.getDownloadInfo().getDownloadUrl());
    }

    private void install(String packageName) {
        AppUtils.runApp(mContext, packageName);
    }


    //判断是否安装
    private Observable<DownloadEvent> isInstallApp(String packName) {
        DownloadEvent event = new DownloadEvent();

        event.setFlag(AppUtils.isInstalled(mContext, packName) ? DownloadFlag.INSTALLED : DownloadFlag.NORMAL);

        return Observable.just(event);
    }


    //文件是否存在
    private Observable<DownloadEvent> isExitApp(AppInfo info) {
        File file = new File(downPath + File.separator + info.hashCode());

        DownloadEvent event = new DownloadEvent();
        event.setFlag(file.exists() ? DownloadFlag.COMPLETED : DownloadFlag.NORMAL);

        return Observable.just(event);
    }

    //获取下载状态
    private Observable<DownloadEvent> downloadStatus(AppDownloadInfo info) {
        return mDownload.receiveDownloadStatus(info.getDownloadUrl());

    }

    //获取应用的下载信息
    private Observable<AppDownloadInfo> getDownloadInfo(AppInfo info) {
        return mApi.getAppDownload(info.getId()).compose(RxHttpResponseCompat.<AppDownloadInfo>compatResult());
    }


    class DownloadConsumer implements Consumer<DownloadEvent> {

        private DownloadProgressButton btn;

        public DownloadConsumer(DownloadProgressButton btn) {
            this.btn = btn;
        }

        @Override
        public void accept(DownloadEvent event) throws Exception {
            btn.setTag(R.id.tag_apk_flag, event.getFlag());

            switch (event.getFlag()) {
                case DownloadFlag.NORMAL:
                    //按钮设置为下载状态
                    btn.download();
                    break;
                case DownloadFlag.STARTED:
                    //正在下载
                    btn.setProgress((int) event.getDownloadStatus().getPercentNumber());
                    break;
                case DownloadFlag.PAUSED:
                    //暂停
                    btn.paused();
                    break;
                case DownloadFlag.COMPLETED:
                    //按钮设置为安装状态
                    btn.setText("安装");
                    break;
                case DownloadFlag.INSTALLED:
                    //按钮设置为打开状态
                    btn.setText("打开");
                    break;
                default:
                    break;
            }
        }
    }



    interface Api{

        @GET("download/{id}")
        Observable<BaseBean<AppDownloadInfo>> getAppDownload(@Path("id") int id);
    }

}

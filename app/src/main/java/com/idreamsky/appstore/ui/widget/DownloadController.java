package com.idreamsky.appstore.ui.widget;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.AppDownloadInfo;
import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.bean.BaseBean;
import com.idreamsky.appstore.common.rx.RxHttpResponseCompat;
import com.idreamsky.appstore.common.rx.RxSchedulers;
import com.idreamsky.appstore.common.util.AppUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * Created by idreamsky on 2017/8/14.
 */

public class DownloadController {

    private RxDownload mDownload;
    private String downPath = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath() + File.separator + "apk" + File.separator;
    private Context mContext;

    private Api mApi;


//    private Retrofit retrofit;

    public DownloadController(RxDownload download, Context context, Retrofit retrofit) {
        this.mDownload = download;
        this.mContext = context;

        if (retrofit != null){
            mApi = retrofit.create(Api.class);
        }
    }



    //初始化按钮的状态
    public void handleClick(final DownloadProgressButton btn, final AppInfo info) {

        if (mApi == null) {
            return;
        }

        //判断是否安装了该应用并返回一个Observable
        isInstallApp(info.getPackageName())
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event) throws Exception {
                        //如果没有安装则判断是否存在安装包
                        if (event.getFlag() == DownloadFlag.COMPLETED) {
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

    //判断应用是否安装
    private Observable<DownloadEvent> isInstallApp(String packName) {
        DownloadEvent event = new DownloadEvent();

        event.setFlag(AppUtils.isInstalled(mContext, packName) ? DownloadFlag.INSTALLED : DownloadFlag.COMPLETED);

        return Observable.just(event);
    }

    //判断文件是否存在
    private Observable<DownloadEvent> isExitApp(AppInfo info) {

        File file = new File(downPath + info.getReleaseKeyHash());
        Log.i("app", "isExitApp: "+file.getPath());
        DownloadEvent event = new DownloadEvent();
        event.setFlag(file.exists() ? DownloadFlag.COMPLETED : DownloadFlag.NORMAL);

        return Observable.just(event);
    }

    //安装应用
    private void installApk(AppInfo info) {
        String path = downPath + info.getReleaseKeyHash();
        Log.i("app", "installApk: path = "+path);
        AppUtils.installApk(mContext, path);
    }

    //点击事件
    public void bindClick(final DownloadProgressButton btn, final AppInfo appinfo) {

        int flag = (int) btn.getTag(R.id.tag_apk_flag);
        Log.i("app", "按钮点击" + flag);
        switch (flag) {
            case DownloadFlag.INSTALLED:
                install(appinfo.getPackageName());
                break;

            case DownloadFlag.STARTED:
                Log.i("app", "暂停下载");
                pauseDownload(appinfo);
                break;

            case DownloadFlag.NORMAL:
            case DownloadFlag.PAUSED:
                Log.i("app", "开始下载");
                startDownload(btn, appinfo);
                break;

            case DownloadFlag.COMPLETED:
                installApk(appinfo);
                break;

            default:
                break;
        }

    }

    //获取下载信息
    private void startDownload(final DownloadProgressButton btn, final AppInfo info) {

        AppDownloadInfo downloadInfo = info.getDownloadInfo();
        if (downloadInfo == null) {
            getDownloadInfo(info).subscribe(new Consumer<AppDownloadInfo>() {
                @Override
                public void accept(AppDownloadInfo appDownloadInfo) throws Exception {
                    info.setDownloadInfo(appDownloadInfo);
                    download(btn, appDownloadInfo);
                }
            });
        } else {
            download(btn, downloadInfo);
        }
    }

    private void download(DownloadProgressButton btn, AppDownloadInfo downloadInfo) {


        Log.i("app", "下载地址: "+downloadInfo.getDownloadUrl());

        mDownload.serviceDownload(downloadInfo.getDownloadUrl(),downloadInfo.getReleaseKeyHash())
                .subscribe();

        mDownload.receiveDownloadStatus(downloadInfo.getDownloadUrl())
                .subscribe(new DownloadConsumer(btn));

    }

    private void pauseDownload(AppInfo appinfo) {
        mDownload.pauseServiceDownload(appinfo.getDownloadInfo().getDownloadUrl()).subscribe();
    }

    private void install(String packageName) {
        AppUtils.runApp(mContext, packageName);
    }


    //获取下载状态
    private Observable<DownloadEvent> downloadStatus(AppDownloadInfo info) {
        return mDownload.receiveDownloadStatus(info.getDownloadUrl());

    }

    //获取应用的下载信息
    private Observable<AppDownloadInfo> getDownloadInfo(AppInfo info) {
        return mApi.getAppDownload(info.getId()).compose(RxHttpResponseCompat.<AppDownloadInfo>compatResult());
    }


    private class DownloadConsumer implements Consumer<DownloadEvent> {

        private DownloadProgressButton btn;

        private DownloadConsumer(DownloadProgressButton btn) {
            this.btn = btn;
        }

        @Override
        public void accept(DownloadEvent event) throws Exception {
            Log.i("app", "改变下载按钮状态 "+event.getFlag() + " progress = " + event.getDownloadStatus().getPercentNumber());

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


    interface Api {

        @GET("download/{id}")
        Observable<BaseBean<AppDownloadInfo>> getAppDownload(@Path("id") int id);
    }

}

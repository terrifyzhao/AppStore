package com.idreamsky.www.download;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxDownload rxDownload = RxDownload.getInstance(this)
                .defaultSavePath(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath() + File.separator + "apk")
                .maxDownloadNumber(3)
                .maxThread(3);

        String url = "http://f7.market.xiaomi.com/download/AppStore/00f71949d661e45a520cc90e2b67d3e74c3a9b8d8";

        rxDownload.serviceDownload(url)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object o) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        rxDownload.receiveDownloadStatus(url)
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(DownloadEvent event) throws Exception {
                        Log.i("app", "accept: "+event.getDownloadStatus().getPercentNumber());
                    }
                });
    }
}

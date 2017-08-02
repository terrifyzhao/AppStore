package com.idreamsky.appstore.ui;

/**
 * Created by zhaojiuzhou on 2017/7/26.
 */

public interface BaseView {
    void showLoading();
    void dismissLoading();
    void showError(String text);
}

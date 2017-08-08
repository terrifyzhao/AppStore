package com.idreamsky.appstore.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.LoginBean;
import com.idreamsky.appstore.bean.LoginRequestBean;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.di.component.DaggerLoginComponent;
import com.idreamsky.appstore.di.module.LoginModule;
import com.idreamsky.appstore.presenter.LoginPresenter;
import com.idreamsky.appstore.presenter.contract.LoginContract;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.layoutPhone)
    TextInputLayout layoutPhone;
    @BindView(R.id.etPsw)
    EditText etPsw;
    @BindView(R.id.layoutPsw)
    TextInputLayout layoutPsw;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    private ProgressDialog mProgress;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule(this)).build().inject(this);
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        Observable<CharSequence> observablePhone = RxTextView.textChanges(etPhone);
        Observable<CharSequence> observablePsw = RxTextView.textChanges(etPsw);

        Observable.combineLatest(observablePhone, observablePsw, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(@NonNull CharSequence charSequence, @NonNull CharSequence charSequence2) throws Exception {
                return availablePhone(charSequence) && availablePsw(charSequence2);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                RxView.enabled(btnLogin).accept(aBoolean);
            }
        });

        RxView.clicks(btnLogin).subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object o) throws Exception {
                LoginRequestBean request = new LoginRequestBean();
                request.setEmail(etPhone.getText().toString().trim());
                request.setPassword(etPsw.getText().toString().trim());
                mPresenter.login(request);
            }
        });

    }

    private boolean availablePhone(CharSequence text) {
        return text.length() == 11;
    }

    private boolean availablePsw(CharSequence text) {
        return text.length() >= 6;
    }

    @Override
    public void showLoading() {
        if (mProgress == null){
            mProgress = new ProgressDialog(this);
            mProgress.setMessage("登录中...");
        }
        mProgress.show();
    }

    @Override
    public void dismissLoading() {
        if (mProgress.isShowing()){
            mProgress.dismiss();
        }
    }

    @Override
    public void showError(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        mProgress.dismiss();
    }

    @Override
    public void startActivity(LoginBean bean) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

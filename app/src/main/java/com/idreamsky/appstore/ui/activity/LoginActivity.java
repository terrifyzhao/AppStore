package com.idreamsky.appstore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import com.idreamsky.appstore.R;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.presenter.LoginPresenter;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class LoginActivity extends BaseActivity<LoginPresenter> {


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

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

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
                if (aBoolean){
                    RxView.enabled(btnLogin).accept(aBoolean);
                }else{
                    RxView.enabled(btnLogin).accept(aBoolean);
                }
            }
        });

        RxView.clicks(btnLogin).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });

    }

    private boolean availablePhone(CharSequence text) {
        return text.length() == 11;
    }

    private boolean availablePsw(CharSequence text) {
        return text.length() >= 6;
    }

}

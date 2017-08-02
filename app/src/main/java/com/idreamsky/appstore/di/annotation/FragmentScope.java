package com.idreamsky.appstore.di.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by zhaojiuzhou on 2017/7/28.
 */

@Scope
@Documented
@Retention(RUNTIME)
public @interface FragmentScope {
}

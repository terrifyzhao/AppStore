package com.idreamsky.appstore.di.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by idreamsky on 2017/8/8.
 */

@Scope
@Documented
@Retention(RUNTIME)
public @interface ActivityScope {
}

package com.idreamsky.appstore.common.excption;

/**
 * Created by zhaojiuzhou on 2017/7/31.
 */

public class ApiExcption extends BaseException {


    public ApiExcption(int code, String msg) {
        super(code, msg);
    }
}

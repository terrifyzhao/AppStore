package com.idreamsky.appstore.common.excption;

/**
 * Created by zhaojiuzhou on 2017/7/31.
 */

public class BaseException extends Exception {


    public final static int ERROR_HTTP = 1000;
    public final static int ERROR_LOGIN = 2000;
    public final static int ERROR_OTHER = 9000;

    private int code;
    private String msg;

    public BaseException() {
    }

    public BaseException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

package com.idreamsky.appstore.bean;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by zhaojiuzhou on 2017/7/31.
 */

public class BaseBean<T> implements Serializable {
    public static final int SUCCESS = 1;
    private String msg;
    private int status;
    private T data;

    public boolean success(){
        return (status== SUCCESS);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

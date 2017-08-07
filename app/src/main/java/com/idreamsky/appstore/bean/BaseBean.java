package com.idreamsky.appstore.bean;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by zhaojiuzhou on 2017/7/31.
 */

public class BaseBean<T> implements Serializable {
    public static final int SUCCESS = 1;
    private String message;
    private int status;
    private T data;

    public boolean success(){
        return (status== SUCCESS);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

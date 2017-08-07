package com.idreamsky.appstore.common.excption;

import android.content.Context;

import com.idreamsky.appstore.R;

import static com.idreamsky.appstore.common.excption.BaseException.ERROR_HTTP;
import static com.idreamsky.appstore.common.excption.BaseException.ERROR_LOGIN;
import static com.idreamsky.appstore.common.excption.BaseException.ERROR_OTHER;

/**
 * Created by zhaojiuzhou on 2017/7/31.
 */

public class ErrorMessageFactory {

    public static String create(Context context, int code){
        String errMsg;

        switch (code){
            case ERROR_HTTP:
                errMsg = context.getResources().getString(R.string.error_http);
                break;
            case ERROR_LOGIN:
                errMsg = context.getResources().getString(R.string.error_login);
                break;
            case ERROR_OTHER:
                errMsg = context.getResources().getString(R.string.error_other);
                break;
            default:
                errMsg = context.getResources().getString(R.string.error_no);
                break;
        }
        return errMsg;
    }
}

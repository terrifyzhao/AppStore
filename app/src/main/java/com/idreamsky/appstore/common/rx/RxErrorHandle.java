package com.idreamsky.appstore.common.rx;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.idreamsky.appstore.common.excption.ApiExcption;
import com.idreamsky.appstore.common.excption.BaseException;
import com.idreamsky.appstore.common.excption.ErrorMessageFactory;

import static com.idreamsky.appstore.common.excption.BaseException.ERROR_OTHER;

/**
 * Created by zhaojiuzhou on 2017/7/31.
 */

public class RxErrorHandle {

    private Context context;

    public RxErrorHandle(Context context) {
        this.context = context;
    }

    public BaseException handleError(Throwable e){
        BaseException exception = new BaseException();
        if (e instanceof ApiExcption){
            ((ApiExcption)exception).setCode(exception.getCode());
        }else{
            exception.setCode(ERROR_OTHER);
        }
        exception.setMsg(ErrorMessageFactory.create(context,exception.getCode()));
        return exception;
    }

    public void showErrorMsg(BaseException e){
        Toast.makeText(context, e.getMsg(), Toast.LENGTH_SHORT).show();
    }


}

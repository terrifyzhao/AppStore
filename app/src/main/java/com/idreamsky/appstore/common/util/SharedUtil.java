package com.idreamsky.appstore.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.idreamsky.appstore.common.Constant;

/**
 * Created by idreamsky on 2017/8/8.
 */

public class SharedUtil {

//    public static SharedPreferences sharedPreferences;

    public static SharedPreferences with(Context context){
        return context.getSharedPreferences(Constant.SHAREDPREFERENCES, Context.MODE_PRIVATE);
    }
//
//    public void put(String key,String value){
//        sharedPreferences.edit().putString(key,value).apply();
//    }
//
//    public String get(String key){
//        return sharedPreferences.getString(key,"");
//    }

}

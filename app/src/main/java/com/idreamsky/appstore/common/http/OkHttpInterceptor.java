package com.idreamsky.appstore.common.http;

import android.content.Context;

import com.google.gson.Gson;
import com.idreamsky.appstore.common.Constant;
import com.idreamsky.appstore.common.util.DensityUtil;
import com.idreamsky.appstore.common.util.DeviceUtils;
import com.idreamsky.appstore.common.util.SharedUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by zhaojiuzhou on 2017/8/2.
 */

public class OkHttpInterceptor implements Interceptor {

    private static final MediaType JSON = MediaType.parse("Text");
    private Gson mGson;
    private Context mContext;

    public OkHttpInterceptor(Context context, Gson mGson) {
        this.mGson = mGson;
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String method = request.method();
        HttpUrl httpUrl = request.url();

//        if (httpUrl.url().toString().contains("download")){
//            return chain.proceed(request);
//        }

        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Constant.IMEI, DeviceUtils.getIMEI(mContext));
        paramsMap.put(Constant.MODEL, DeviceUtils.getModel());
        paramsMap.put(Constant.LANGUAGE, DeviceUtils.getLanguage());
        paramsMap.put(Constant.OS, DeviceUtils.getBuildVersionIncremental());
        paramsMap.put(Constant.RESOLUTION, DensityUtil.getScreenW(mContext) + "*" + DensityUtil.getScreenH(mContext));
        paramsMap.put(Constant.SDK, DeviceUtils.getBuildVersionSDK() + "");
        paramsMap.put(Constant.DENSITY_SCALE_FACTOR, mContext.getResources().getDisplayMetrics().density + "");
        paramsMap.put(Constant.TOKEN, SharedUtil.with(mContext).getString("token",""));

        Request newRequest = null;
        if (method.equals("GET")) {

            HashMap<String, Object> rootMap = new HashMap<>();

            Set<String> paramsNames = httpUrl.queryParameterNames();
            for (String key : paramsNames) {
                if (key.equals("p")) {
                    String oldParams = httpUrl.queryParameter("p");
                    if (oldParams != null) {
                        HashMap<String, Object> p = mGson.fromJson(oldParams, HashMap.class);

                        if (p != null) {
                            for (Map.Entry<String, Object> entry : p.entrySet()) {
                                rootMap.put(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                } else {
                    rootMap.put(key, httpUrl.queryParameter(key));
                }
            }

            rootMap.put("publicParams", paramsMap);
            String newParams = mGson.toJson(rootMap);
            String url = httpUrl.toString();

            int index = url.indexOf("?");
            if (index > 0) {
                url = url.substring(0, index) + "?p=" + newParams;
            }else{
                url = url + "?p=" + newParams;
            }
            newRequest = new Request.Builder().url(url).build();
        } else if (method.equals("POST")) {

            RequestBody body = request.body();
            HashMap<String, Object> rootMap = new HashMap<>();
            if (body instanceof FormBody) {
                for (int i = 0; i < ((FormBody) body).size(); i++) {
                    rootMap.put(((FormBody) body).encodedName(i), ((FormBody) body).encodedValue(i));
                }
            } else {
                Buffer buffer = new okio.Buffer();
                body.writeTo(buffer);
                String oldParams = buffer.readUtf8();
                rootMap = mGson.fromJson(oldParams, HashMap.class);
                rootMap.put("publicParams",paramsMap);
            }
            String newParams = mGson.toJson(rootMap);
            newRequest = new Request.Builder().url(httpUrl.toString()).post(RequestBody.create(JSON,newParams)).build();
        }else {
            newRequest = request;
        }

        return chain.proceed(newRequest);
    }
}

package com.idreamsky.appstore.common.http;

import com.google.gson.Gson;

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

    private static final MediaType JSON = MediaType.parse("application/json");
    private Gson mGson;

    public OkHttpInterceptor(Gson mGson) {
        this.mGson = mGson;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String method = request.method();
        HttpUrl httpUrl = request.url();

        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("sdk","24");

        Request newRequest = null;
        if (method.equals("GET")){

            HashMap<String, Object> rootMap = new HashMap<>();

            Set<String> paramsNames = httpUrl.queryParameterNames();
            for (String key : paramsNames){
                if (key.equals("p")){
                    String oldParams = httpUrl.queryParameter("p");
                    if (oldParams != null){
                        HashMap<String, Object> p = mGson.fromJson(oldParams,HashMap.class);

                        if (p != null){
                            for (Map.Entry<String,Object> entry : p.entrySet()){
                                rootMap.put(entry.getKey(),entry.getValue());
                            }
                        }
                    }
                }else{
                    rootMap.put(key,httpUrl.queryParameter(key));
                }
            }

            rootMap.put("publicParams",paramsMap);
            String newParams = mGson.toJson(rootMap);
            String url = httpUrl.toString();

            int index = url.indexOf("?");
            if (index > 0){
                url = url.substring(0,index)+"?p="+newParams;
            }
            newRequest = new Request.Builder().url(url).build();
        }else if (method.equals("POST")){

            RequestBody body = request.body();
            HashMap<String,Object> rootMap = new HashMap<>();
            if (body instanceof FormBody){
                for (int i=0; i<((FormBody) body).size(); i++){
                    rootMap.put(((FormBody) body).encodedName(i),((FormBody) body).encodedValue(i));
                }
            }else{
                Buffer buffer = new okio.Buffer();
                body.writeTo(buffer);
                String oldParams = buffer.readUtf8();
                rootMap = mGson.fromJson(oldParams,HashMap.class);
                rootMap.put("publicParams",mGson.toJson(paramsMap));
            }
            String newParams = mGson.toJson(rootMap);

            newRequest = request.newBuilder().post(RequestBody.create(JSON,newParams)).build();


        }

        return chain.proceed(newRequest);
    }
}

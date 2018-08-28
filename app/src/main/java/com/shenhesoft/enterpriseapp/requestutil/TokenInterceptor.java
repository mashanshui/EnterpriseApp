package com.shenhesoft.enterpriseapp.requestutil;

import android.util.Log;

import com.google.gson.Gson;
import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.MyApplication;

import java.io.IOException;
import java.util.HashMap;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @author mashanshui
 * @date 2018/4/18
 * @desc TODO
 */
public class TokenInterceptor implements Interceptor {
    private static final String TAG = "TokenInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
//        Request originalRequest = chain.request();
//        // get new request, add request header
////        Request updateRequest = originalRequest.newBuilder()
////                .header("token", token)
////                .build();
//
//        FormBody.Builder builder = new FormBody.Builder() ;
//        RequestBody requestBody =  originalRequest.body() ;
//        if(requestBody instanceof FormBody){
//            FormBody formBody = (FormBody)requestBody ;
//            for (int i=0;i<formBody.size();i++){
//                builder.add(formBody.encodedName(i),formBody.encodedValue(i));
//            }
//            builder.add("source","android");
//        }
//        Request requestProcess =  originalRequest.newBuilder().url(originalRequest.url().toString()).post(builder.build()).build() ;

        String sysOrgCode = SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.SysOrgCode, "");
        Request request = chain.request();
        RequestBody requestBody = request.body();
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        String oldParamsJson = buffer.readUtf8();
        Log.e(TAG, "intercept: " + oldParamsJson);
        HashMap<String, Object> rootMap;
        Gson mGson = new Gson();
        //原始参数
        rootMap = mGson.fromJson(oldParamsJson, HashMap.class);
        //重新组装
        rootMap.put("test", "test");
        //装换成json字符串
        String newJsonParams = mGson.toJson(rootMap);
        Log.e(TAG, "intercept: " + newJsonParams);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        request = request.newBuilder()
                .post(RequestBody.create(JSON, newJsonParams))
                .build();

        return chain.proceed(request);
    }
}

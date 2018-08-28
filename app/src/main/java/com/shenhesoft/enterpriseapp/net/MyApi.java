package com.shenhesoft.enterpriseapp.net;

import cn.droidlover.xdroidmvp.net.XApi;

import static com.shenhesoft.enterpriseapp.AppConstant.API_BASE_URL;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description Api管理
 */

public class MyApi {
    private static RequestFactory requestFactory;
    private static MyApi mInstance;

    private MyApi() {
        super();
        if (requestFactory == null) {
            synchronized (MyApi.class) {
                requestFactory = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(RequestFactory.class);
            }
        }
    }

    public static MyApi getInstance() {
        if (mInstance == null) {
            synchronized (MyApi.class) {
                mInstance = new MyApi();
            }
        }
        return mInstance;
    }

    public RequestFactory getRequestFactory() {
        return requestFactory;
    }
}

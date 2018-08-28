package com.shenhesoft.enterpriseapp.net;


import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.bean.UserinfoBean;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description 网络请求方式
 */

public interface RequestFactory {
    @GET
    Flowable<RequestResults> getRequestData(@Url String url, @Body RequestBody params);

    @POST
    Flowable<RequestResults<UserinfoBean>> postRequestData(@Url String url, @Body RequestBody params);

    @POST(AppConstant.URL_LOGIN)
    Flowable<RequestResults<UserinfoBean>> getUserinfo(@Body RequestBody params);

}

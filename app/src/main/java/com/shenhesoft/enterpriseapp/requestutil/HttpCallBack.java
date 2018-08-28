package com.shenhesoft.enterpriseapp.requestutil;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：Tornado
 * 创作日期：2017/8/9.
 * 描述：
 */

public class HttpCallBack<T extends HttpResult> implements Callback<T> {


    @Override
    public void onResponse(Call<T> call, Response<T> response) {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }
}

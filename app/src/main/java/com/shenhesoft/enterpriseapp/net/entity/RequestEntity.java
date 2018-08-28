package com.shenhesoft.enterpriseapp.net.entity;


import com.shenhesoft.enterpriseapp.net.RequestParams;

/**
 * @author 张继淮
 * @date 2017/9/28
 * @description 网络请求实体类
 */

public class RequestEntity {
    private int requestTag;        //请求标记
    private String url;           //地址
    private RequestParams params; //数据

    public RequestEntity() {
    }

    public RequestEntity(int requestTag, String url, RequestParams params) {
        this.requestTag = requestTag;
        this.url = url;
        this.params = params;
    }

    public static RequestEntity getLoad() {
        return new RequestEntity();
    }

    public int getRequestTag() {
        return requestTag;
    }

    public void setRequestTag(int requestTag) {
        this.requestTag = requestTag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestParams getParams() {
        return params;
    }

    public void setParams(RequestParams params) {
        this.params = params;
    }
}

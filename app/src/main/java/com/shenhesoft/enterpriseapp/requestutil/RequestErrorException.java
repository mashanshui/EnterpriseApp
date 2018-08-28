package com.shenhesoft.enterpriseapp.requestutil;

/**
 * 作者：Tornado
 * 创作日期：2017/8/4.
 * 描述： 请求接口得到响应 但没有获得业务所需的数据
 */

public class RequestErrorException extends Exception {


    public RequestErrorException(String message) {
        super(message);
    }
}

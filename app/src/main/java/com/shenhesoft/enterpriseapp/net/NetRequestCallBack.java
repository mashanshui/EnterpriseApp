package com.shenhesoft.enterpriseapp.net;


import com.shenhesoft.enterpriseapp.net.entity.RequestResults;

import cn.droidlover.xdroidmvp.net.NetError;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description 网络请求callBack
 */

public interface NetRequestCallBack {
    /**
     * 处理网络请求失败数据
     *
     * @param requestTag
     * @param error
     */
    void onRequestError(int requestTag, NetError error);

    /**
     * 处理网络请求成功数据
     *
     * @param requestTag
     * @param requestResults
     */
    void onRequestSuccess(int requestTag, RequestResults requestResults);

//    /**
//     * 处理网络请求成功但无数据
//     *
//     * @param requestTag
//     * @param gankResults
//     */
//    void requestOnFailure(int requestTag, RequestResults gankResults);
}

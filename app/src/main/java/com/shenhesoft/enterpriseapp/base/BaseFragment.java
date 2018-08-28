package com.shenhesoft.enterpriseapp.base;

import android.os.Bundle;

import com.shenhesoft.enterpriseapp.base.mvp.present.PBaseFragment;
import com.shenhesoft.enterpriseapp.net.NetRequestCallBack;
import com.shenhesoft.enterpriseapp.net.entity.RequestEntity;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description baseFragment
 */

public abstract class BaseFragment extends XLazyFragment<PBaseFragment> implements NetRequestCallBack {

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bindEventBus())
            BusProvider.getBus().unregister(this);
    }

    public boolean bindEventBus() {
        return false;
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        if (bindEventBus()) {
            BusProvider
                    .getBus()
                    .toFlowable(BaseEvent.class)
                    .subscribe((baseEvent) -> getBusEvent(baseEvent));
        }
    }

    /**
     * 消息获取 必须订阅消息
     */
    protected void getBusEvent(BaseEvent msg) {
    }

    /**
     * 消息发送
     */
    protected void postEvent(BaseEvent msg) {
        BusProvider.getBus().post(msg);
    }

    @Override
    public PBaseFragment newP() {
        return new PBaseFragment();
    }

    /**
     * 请求数据
     *
     * @param entity
     */
    public void requestData(RequestEntity entity) {
        getP().requestData(entity);
    }

    /**
     * 网络请求失败回调
     *
     * @param requestTag
     * @param error
     */
    @Override
    public void onRequestError(int requestTag, NetError error) {
    }

    /**
     * 网络请求成功回调
     *
     * @param requestTag
     * @param requestResults
     */
    @Override
    public void onRequestSuccess(int requestTag, RequestResults requestResults) {
    }

}

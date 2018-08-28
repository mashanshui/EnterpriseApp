package com.shenhesoft.enterpriseapp.base;

import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.shenhesoft.enterpriseapp.base.mvp.present.PBasePager;
import com.shenhesoft.enterpriseapp.net.NetRequestCallBack;
import com.shenhesoft.enterpriseapp.net.entity.RequestEntity;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.utils.ActivityManager;

import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;


/**
 * @author 张继淮
 * @date 2017/9/27
 * @description baseActivity
 */

public abstract class BaseActivity extends XActivity<PBasePager> implements NetRequestCallBack {
//    private ImmersionBar mImmersionBar;//沉浸式状态栏

    @Override
    public void initData(Bundle savedInstanceState) {
        //将activity加入栈中
        ActivityManager.getInstance().addActivity(this);
        //初始化沉浸式状态栏
//        mImmersionBar = ImmersionBar.with(this);
//        mImmersionBar.init();
        initView(savedInstanceState);
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

    /**
     * 请求网络
     *
     * @param entity
     */
    public void requestData(RequestEntity entity) {
        getP().requestData(entity);
    }

    /**
     * 视图初始化
     */
    protected abstract void initView(Bundle savedInstanceState);

    @Override
    public PBasePager newP() {
        return new PBasePager();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
//        if (mImmersionBar != null) {
//            mImmersionBar.destroy();
//            //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，
//            // 在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
//        }
        if (bindEventBus())
            BusProvider.getBus().unregister(this);
    }

}

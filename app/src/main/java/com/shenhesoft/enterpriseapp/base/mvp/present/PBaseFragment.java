package com.shenhesoft.enterpriseapp.base.mvp.present;

import com.shenhesoft.enterpriseapp.base.BaseFragment;
import com.shenhesoft.enterpriseapp.net.entity.RequestEntity;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.MyApi;
import com.shenhesoft.enterpriseapp.net.RequestParams;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.RequestBody;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description basePresent
 */

public class PBaseFragment extends XPresent<BaseFragment> {
    /**
     * 请求数据
     *
     * @param entity
     */
    public void requestData(RequestEntity entity) {
        int requestTag = entity.getRequestTag();        //请求标记
        String url = entity.getUrl();           //地址
        RequestParams params = entity.getParams(); //数据
        RequestBody body = params.getRequestBodyFromJson();
        MyApi.getInstance().getRequestFactory().postRequestData(url, body)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<RequestResults>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().onRequestError(requestTag, error);
                    }

                    @Override
                    public void onNext(RequestResults requestResults) {
                        getV().onRequestSuccess(requestTag, requestResults);
                    }

                });

    }
}

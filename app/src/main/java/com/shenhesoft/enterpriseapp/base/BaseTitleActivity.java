package com.shenhesoft.enterpriseapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.mvp.present.PBasePager;
import com.shenhesoft.enterpriseapp.net.NetRequestCallBack;
import com.shenhesoft.enterpriseapp.net.entity.RequestEntity;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.utils.ActivityManager;
import com.shenhesoft.enterpriseapp.utils.CheckUtils;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description baseActivity
 */

public abstract class BaseTitleActivity extends XTitleActivity<PBasePager> implements NetRequestCallBack {
    //    private ImmersionBar mImmersionBar;//沉浸式状态栏
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_right1)
    ImageView mIvRight1;
    @BindView(R.id.ll_iv1)
    LinearLayout mLlIv1;
    @BindView(R.id.ll_iv2)
    public LinearLayout mLlIv2;
    @BindView(R.id.iv_right2)
    ImageView mIvRight2;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.btn_left)
    Button mBtnLeft;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    FrameLayout mContent;//主布局

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() > 0) {
            setContentView(R.layout.base_title_activity);
            //将activity的layout放入content中
            View view = getLayoutInflater().inflate(getLayoutId(), null);
            mContent = (FrameLayout) findViewById(R.id.flt_context);
            mContent.addView(view);
            KnifeKit.bind(this);
            bindEvent();
        }
        initData(savedInstanceState);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //将activity加入栈中
        ActivityManager.getInstance().addActivity(this);
        setSupportActionBar(mToolbar);
        //初始化沉浸式状态栏
//        mImmersionBar = ImmersionBar.with(this);
//        mImmersionBar.barColor(R.color.white_80).init();
        initView(savedInstanceState);
        initTitle();
        mBtnLeft.setOnClickListener(v -> onBackPressed());
        setListener();
    }

    protected void setListener() {
    }

    public void setTitle(String title) {
        if (tv_title != null && !CheckUtils.isEmpty(title)) {
            if (!CheckUtils.isEmpty(tv_title)) {
                tv_title.setText(title);
            } else {
                tv_title.setText("");
            }
        } else {
            tv_title.setText("");
        }
    }

    protected abstract void initTitle();

    protected void setLeftListener(View.OnClickListener onClickListener) {
        if (mBtnLeft != null)
            mBtnLeft.setOnClickListener(onClickListener);
    }

    protected void setRight1Img(int id, View.OnClickListener onClickListener) {
        if (mToolbar != null) {
            mIvRight1.setImageResource(id);
            mLlIv1.setVisibility(View.VISIBLE);
            mLlIv1.setOnClickListener(onClickListener);
        }
    }

    protected void setRight2Img(int id, View.OnClickListener onClickListener) {
        if (mToolbar != null) {
            mIvRight2.setImageResource(id);
            mLlIv2.setVisibility(View.VISIBLE);
            mLlIv2.setOnClickListener(onClickListener);
        }
    }

    protected void setRightText(String text, View.OnClickListener onClickListener) {
        if (mToolbar != null) {
            mTvRight.setText(text);
            mTvRight.setVisibility(View.VISIBLE);
            mTvRight.setOnClickListener(onClickListener);
        }
    }

    protected void cancelBtnLeft(){
        mBtnLeft.setVisibility(View.GONE);
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
//        }
        if (bindEventBus())
            BusProvider.getBus().unregister(this);
    }

}

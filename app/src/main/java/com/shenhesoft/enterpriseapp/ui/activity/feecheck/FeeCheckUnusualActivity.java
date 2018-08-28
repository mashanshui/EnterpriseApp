package com.shenhesoft.enterpriseapp.ui.activity.feecheck;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.log.XLog;
import io.reactivex.Observable;

/**
 * @author 张继淮
 * @date 2017/12/6
 * @desc 填写异常原因
 */

public class FeeCheckUnusualActivity extends BaseTitleActivity implements BottomListChooseDialog.onSelectListener {
    @BindView(R.id.tv_choose_reason)
    TextView mTvChooseReason;
    @BindView(R.id.ll_choose_reason)
    LinearLayout mLlChooseReason;
    @BindView(R.id.et_other_reason)
    EditText mEtOtherReason;
    private BottomListChooseDialog mChooseDialog;
    private String orderid;
    private String remark = "";
    private List<LocationPopBean> list;


    @Override
    public int getLayoutId() {
        return R.layout.activity_unsual;
    }

    @Override
    protected void initTitle() {
        setTitle("异常原因");
        setRightText("提交", v -> {
            submit();
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        list = new ArrayList<>();
        orderid = getIntent().getStringExtra("orderId");
        if (TextUtils.isEmpty(orderid)) {
            IToast.showShort("运单编号为空");
            finish();
            return;
        }
        getlist();
    }

    @OnClick({R.id.ll_choose_reason, R.id.et_other_reason})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_choose_reason:
                Showlist();
                break;
            case R.id.et_other_reason:
                break;
            default:
                break;
        }
    }

    private void Showlist() {
        if (mChooseDialog == null) {
            mChooseDialog = new BottomListChooseDialog(context, list);
            mChooseDialog.setOnselectListener(this);
        }
        mChooseDialog.show();
    }

    @Override
    public void getposition(int position) {
        XLog.d("position=======" + position);
        remark = list.get(position).getName();
        mTvChooseReason.setText(remark);
    }

    /**
     * 提交
     */
    private void submit() {
        if (TextUtils.isEmpty(remark)) {
            IToast.showShort("请选择原因");
            return;
        }

        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .FeecheckUnusualSubmit(ApiRetrofit.getInstance().FeeunusalSubmit(orderid, remark, mEtOtherReason.getText().toString()));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (!(data.getState() >= 200 && data.getState() < 300)) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("提交成功！");
                    finish();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 获取列表
     */
    private void getlist() {
        list.add(new LocationPopBean("严重超损"));
        list.add(new LocationPopBean("货物增重"));
        list.add(new LocationPopBean("皮重不符"));
        list.add(new LocationPopBean("其他原因"));
    }
}

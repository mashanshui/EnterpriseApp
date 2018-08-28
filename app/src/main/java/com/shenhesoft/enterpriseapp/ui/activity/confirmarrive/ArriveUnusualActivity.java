package com.shenhesoft.enterpriseapp.ui.activity.confirmarrive;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseEvent;
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

import static com.shenhesoft.enterpriseapp.AppConstant.REJECT_ORDER;

/**
 * @author 张继淮
 * @date 2017/12/6
 * @desc 填写异常原因
 */

public class ArriveUnusualActivity extends BaseTitleActivity implements BottomListChooseDialog.onSelectListener {
    private static final String TAG = "ArriveUnusualActivity";
    @BindView(R.id.tv_choose_reason)
    TextView mTvChooseReason;
    @BindView(R.id.ll_choose_reason)
    LinearLayout mLlChooseReason;
    @BindView(R.id.et_other_reason)
    EditText mEtOtherReason;
    private BottomListChooseDialog mChooseDialog;
    private String orderid;
    private String remark;
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
        getlist(1);
    }

    @OnClick({R.id.ll_choose_reason, R.id.et_other_reason})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_choose_reason:
                if (list.size() <= 0) {
                    getlist(0);
                    return;
                }
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

//    /**
//     * 提交
//     */
//    private void submit() {
//
//        if (TextUtils.isEmpty(remark)) {
//            IToast.showShort("请选择原因");
//        }
//        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
//                .ArriveReasonSubmit(ApiRetrofit.getInstance().unusalSubmit(orderid, remark, mEtOtherReason.getText().toString()));
//
//        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
//                data -> {
//                    if (data.getState() != 1) {
//                        IToast.showShort(data.getMsg());
//                        return;
//                    }
//
//                    IToast.showShort("提交成功！");
//                    finish();
//
//                });
//
//        HttpManager.getInstance().statrPostTask(observable, observer);
//    }
    /**
     * 提交
     */
    private void submit() {
        if (TextUtils.isEmpty(remark)) {
            IToast.showShort("请选择原因");
            return;
        }

        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .FeecheckUnusualSubmit(ApiRetrofit.getInstance().ArriveunusalSubmit(orderid, remark, mEtOtherReason.getText().toString()));

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
     * 获取驳回原因列表
     */
    private void getlist(int type) {

        Observable<RequestResultsList<String>> observable = HttpManager.getInstance().getOrderService()
                .ArriveUnusualOrder(ApiRetrofit.getInstance().rejectOrder());

        HttpObserver<RequestResultsList<String>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    for (String name : data.getObj()) {
                        list.add(new LocationPopBean(name));
                    }
                    if (type == 0) {
                        Showlist();
                    }
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

}

package com.shenhesoft.enterpriseapp.ui.activity.waitdispatch;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseEvent;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.bean.WaitDispatchBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;
import com.shenhesoft.enterpriseapp.widget.LocationPop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.log.XLog;
import io.reactivex.Observable;
import retrofit2.http.GET;

import static com.shenhesoft.enterpriseapp.AppConstant.REJECT_ORDER;

/**
 * @author 张继淮
 * @date 2017/12/6
 * @desc 填写驳回原因
 */

public class RejectActivity extends BaseTitleActivity implements BottomListChooseDialog.onSelectListener {
    @BindView(R.id.tv_choose_reason)
    TextView mTvChooseReason;
    @BindView(R.id.ll_choose_reason)
    LinearLayout mLlChooseReason;
    @BindView(R.id.et_other_reason)
    EditText mEtOtherReason;
    private BottomListChooseDialog mChooseDialog;
    private String orderid;
    private String remark;
    private int position;
    private List<LocationPopBean> list;


    @Override
    public int getLayoutId() {
        return R.layout.activity_reject;
    }

    @Override
    protected void initTitle() {
        setTitle("驳回原因");
        setRightText("提交", v -> {
            submit();
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        list = new ArrayList<>();
        position = getIntent().getIntExtra("position", -1);
        orderid = getIntent().getStringExtra("orderid");
        if (orderid == null && position == -1) {
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

    /**
     * 提交
     */
    private void submit() {
//        if (TextUtils.isEmpty(remark)) {
//            IToast.showShort("请选择原因");
//        }
//        if (remark.equals("其他")) {
//            remark = mEtOtherReason.getText().toString();
//        }
        //改为可不选原因直接驳回
        remark = remark+":"+mEtOtherReason.getText().toString();
        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .rejectSubmit(ApiRetrofit.getInstance().rejectSubmit(orderid, remark));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }

                    if (data.getState() == 1) {
                        IToast.showShort("提交成功！");
                        postEvent(new BaseEvent(REJECT_ORDER, position));
                        finish();
                    }

                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 获取驳回原因列表
     */
    private void getlist(int type) {

        Observable<RequestResultsList<String>> observable = HttpManager.getInstance().getOrderService()
                .rejectOrder(ApiRetrofit.getInstance().rejectOrder());

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

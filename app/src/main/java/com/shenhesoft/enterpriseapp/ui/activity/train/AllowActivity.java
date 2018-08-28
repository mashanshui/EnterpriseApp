package com.shenhesoft.enterpriseapp.ui.activity.train;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.bean.TrainBean;
import com.shenhesoft.enterpriseapp.bean.TrainDetailsBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author
 * @date 2017/12/23
 * @desc 火运-承认车-承认车提交
 */

public class AllowActivity extends BaseTitleActivity {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 承认车数
     */
    @BindView(R.id.et_allow_number)
    EditText MEt_allow_number;

    /**
     * 项目编号
     */
    @BindView(R.id.tv_projectcode)
    TextView tv_projectcode;

    /**
     * 请车单号
     */
    @BindView(R.id.tv_pleasetrainnum)
    TextView tv_pleasetrainnum;

    /**
     * 货物品名
     */
    @BindView(R.id.tv_project_cargoName)
    TextView tv_project_cargoName;

    /**
     * 发货单位
     */
    @BindView(R.id.tv_project_sendCompany)
    TextView tv_project_sendCompany;

    /**
     * 始发站点
     */
    @BindView(R.id.tv_project_startstation)
    TextView tv_project_startstation;

    /**
     * 收货单位
     */
    @BindView(R.id.tv_project_receiptCompany)
    TextView tv_project_receiptCompany;

    /**
     * 收货单位
     */
    @BindView(R.id.tv_project_arrivestation)
    TextView tv_project_arrivestation;

    /**
     * 请车数
     */
    @BindView(R.id.tv_request_num)
    TextView tv_request_num;


    /**
     * 绑定text
     *
     * @param bean 运单bean
     */
    private void bindText(TrainBean bean) {
        tv_projectcode.setText(bean.getProjectCode());
        tv_pleasetrainnum.setText(bean.getPleaseTrainNumber());
        tv_project_cargoName.setText(bean.getCargoName());
        tv_project_sendCompany.setText(bean.getSendCompany());
        tv_project_startstation.setText(bean.getShipStation());
        tv_project_receiptCompany.setText(bean.getReceiptCompany());
        tv_project_arrivestation.setText(bean.getReceiptStation());
        tv_request_num.setText(bean.getInviteTrainNum());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_allow_train;
    }

    @Override
    protected void initTitle() {
        setTitle("承认车");
        setRightText("完成", v -> {
            ShowConfirmDialog();
        });
    }

    private ConfirmDialog mConfirmDialog;

    /***
     * 状态完成Dialog
     */
    private void ShowConfirmDialog() {
        if (mConfirmDialog == null) {
            mConfirmDialog = new ConfirmDialog(context);
            mConfirmDialog.setTitle1("确认分配");
            mConfirmDialog.setCancelListener(v -> mConfirmDialog.dismiss());
        }
        mConfirmDialog.setConfirmListener(v -> {
            SumbitData();
            mConfirmDialog.dismiss();
        });
        mConfirmDialog.show();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");
        if (TextUtils.isEmpty(orderId)) {
            IToast.showShort("订单号为空");
            finish();
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        TrainBean bean = (TrainBean) getIntent().getSerializableExtra("trainbean");
        if (bean != null) {
            bindText(bean);
        }
    }


    /**
     * 提交信息
     */
    private void SumbitData() {
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .saveTbTrainorderCarNum(ApiRetrofit.getInstance().saveTbTrainorderCarNum(orderId, MEt_allow_number.getText().toString()));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("承认车信息提交成功");
                    finish();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


}

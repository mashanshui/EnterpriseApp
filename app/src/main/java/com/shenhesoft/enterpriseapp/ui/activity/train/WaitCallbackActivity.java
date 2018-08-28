package com.shenhesoft.enterpriseapp.ui.activity.train;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderBean;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderDetailBean;
import com.shenhesoft.enterpriseapp.bean.TrainBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.adapter.EntruckListAdapter;
import com.shenhesoft.enterpriseapp.utils.DialogUtil;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;

/**
 * @author zmx
 * @date 2017/12/23
 * @desc 火运-等待回单
 */

public class WaitCallbackActivity extends BaseTitleActivity {


    @BindView(R.id.mylistview)
    ListView mListView;

    @BindView(R.id.tv_loading_train_more)
    TextView mTvStatus;

    //订单号
    private String orderId;

    private String projectType;

    private List<EntruckOrderDetailBean> mentrucklist;

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
     * 裝车数
     */
    @BindView(R.id.tv_request_num)
    TextView tv_request_num;

    /**
     * 承认车数
     */
    @BindView(R.id.tv_admit_num)
    TextView tv_admit_num;

    /**
     * 装载吨位
     */
    @BindView(R.id.tv_looad_num)
    TextView tv_looad_num;

    /**
     * 绑定text
     *
     * @param bean 运单bean
     */
    private void bindText(TrainBean bean) {
        tv_projectcode.setText(bean.getProjectCode());
        tv_pleasetrainnum.setText(bean.getPleaseTrainNumber());
        tv_project_cargoName.setText(bean.getCargoName());
        tv_project_sendCompany.setText(bean.getBeginPlace());
        tv_project_startstation.setText(bean.getShipStation());
        tv_project_receiptCompany.setText(bean.getEndPlace());
        tv_project_arrivestation.setText(bean.getReceiptStation());
        tv_request_num.setText(bean.getEntruckNumbe());
        tv_looad_num.setText(bean.getEntruckWeight());
        tv_admit_num.setText(bean.getAdmitTrainNum());
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_callback_train;
    }

    @Override
    protected void initTitle() {
        setTitle("等待回单");
        setRightText("完成", v -> {
            getgoodsPlace();
        });
    }

    private ConfirmDialog mConfirmDialog;

    /***
     * 状态完成Dialog
     */
    private void ShowConfirmDialog() {
        if (mConfirmDialog == null) {
            mConfirmDialog = new ConfirmDialog(context);
            mConfirmDialog.setTitle1("确认回单已经全部上传完成");
            mConfirmDialog.setCancelListener(v -> mConfirmDialog.dismiss());
        }
        mConfirmDialog.setConfirmListener(v -> {
            sendTrain();
            mConfirmDialog.dismiss();
        });
        mConfirmDialog.show();
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        orderId = getIntent().getStringExtra("orderId");
        if (TextUtils.isEmpty(orderId)) {
            IToast.showShort("订单号为空");
            finish();
        }
        TrainBean bean = (TrainBean) getIntent().getSerializableExtra("trainbean");
        if (bean != null) {
            projectType = bean.getProjectType();
            bindText(bean);
        }
        mTvStatus.setText("填写详单信息");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mentrucklist = new ArrayList<>();
    }


    @OnClick({R.id.tv_loading_train_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_loading_train_more:
                Router.newIntent(this).to(WaitCallBackDetailsActivity.class).requestCode(101).putString("orderId", orderId).putString("projectType",projectType).launch();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取运单列表
     */
    private void getgoodsPlace() {
        Observable<RequestResultsList<EntruckOrderDetailBean>> observable = HttpManager.getInstance().getUserService()
                .getOrderDetailList(ApiRetrofit.getInstance().getOrderDetaillist(orderId));

        HttpObserver<RequestResultsList<EntruckOrderDetailBean>> observer = new HttpObserver<>(context, DialogUtil.createLoading(context),
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    mentrucklist = data.getObj();
                    for (EntruckOrderDetailBean entruckOrderDetailBean : mentrucklist) {
                        if (!TextUtils.isEmpty(entruckOrderDetailBean.getImg()) && !TextUtils.isEmpty(entruckOrderDetailBean.getImgcall())) {

                        } else {
                            IToast.showShort("请上传所有回单信息");
                            return;
                        }
                    }
                    ShowConfirmDialog();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    //在途运载
    private void sendTrain() {
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .sendTrain(ApiRetrofit.getInstance().updataTrainParams(orderId, 7));

        HttpObserver observer = new HttpObserver<RequestResults>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("回单保存成功！");
                    finish();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);

    }

}

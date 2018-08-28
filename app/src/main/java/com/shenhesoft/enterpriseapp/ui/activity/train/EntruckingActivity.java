package com.shenhesoft.enterpriseapp.ui.activity.train;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderBean;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderDetailBean;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.bean.TrainBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.adapter.EntruckListAdapter;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;

/**
 * @author zmx
 * @date 2017/12/23
 * @desc 火运-装车
 */

public class EntruckingActivity extends BaseTitleActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "EntruckingActivity";
    @BindView(R.id.mylistview)
    ListView mListView;

    @BindView(R.id.ll_loading_train_parent)
    LinearLayout ll_train_loading_parent;

    private EntruckOrderBean mEntruckOrderBean;

    private List<EntruckOrderBean> mEntruckOrderlist;

    private EntruckListAdapter adapter;

    private int mposition;

    //订单号
    private String orderId;

    private String projectType;

    private double entrucTotalWeight=0;
    private int containerNums=0;
    private int entruckNumbe=0;

    /**
     * 承认车数，装车时最大不能超过这个数量
     */
    private int admitNum;
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
     * 承认车数
     */
    @BindView(R.id.tv_admit_num)
    TextView tv_admit_num;

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
        tv_request_num.setText(bean.getInviteTrainNum());
        tv_admit_num.setText(bean.getAdmitTrainNum());
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_loading_train;
    }

    @Override
    protected void initTitle() {
        setTitle("装车");
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
            mConfirmDialog.setTitle1("确认装车完成");
            mConfirmDialog.setCancelListener(v -> mConfirmDialog.dismiss());
        }
        mConfirmDialog.setConfirmListener(v -> {
            SharedPref.getInstance(context).putInt(AppConstant.AdmitNum, 0);
            submit(SubmitJson());
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
            admitNum = Integer.valueOf(bean.getAdmitTrainNum());
            projectType = bean.getProjectType();
            bindText(bean);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mEntruckOrderlist = new ArrayList<>();
        adapter = new EntruckListAdapter(context);
        adapter.setData(mEntruckOrderlist);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }


    @OnClick({R.id.tv_loading_train_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_loading_train_more:
                int num = SharedPref.getInstance(context).getInt(AppConstant.AdmitNum, 0);
                if (num >= admitNum) {
                    IToast.showShort("装车数量不能超过承认车数");
                } else {
                    Router.newIntent(this).to(AddEntruckActivity.class).requestCode(101).putString("orderId", orderId)
                            .putInt("admitNum", admitNum)
                            .putString("projectType", projectType).launch();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            mEntruckOrderBean = (EntruckOrderBean) data.getSerializableExtra("entruckOrderBean");
            mEntruckOrderlist.add(mEntruckOrderBean);
            adapter.setData(mEntruckOrderlist);
        }
        if (requestCode == 201 && resultCode == RESULT_OK && data != null) {
            mEntruckOrderBean = (EntruckOrderBean) data.getSerializableExtra("entruckOrderBean");
            mEntruckOrderlist.set(mposition, mEntruckOrderBean);
            adapter.setData(mEntruckOrderlist);
        }
    }

    private String SubmitJson() {
        JSONArray myarray1 = new JSONArray();
        try {
            for (EntruckOrderBean orderBean : mEntruckOrderlist) {

                for (EntruckOrderDetailBean etbean : orderBean.getOrderDetail()) {
                    JSONObject chobject = new JSONObject();
                    chobject.put("cargoPlaceName", orderBean.getCargoPlaceName());
                    chobject.put("cargoPlaceId", orderBean.getCargoPlaceId());
                    chobject.put("cargoSiteName", orderBean.getCargoSiteName());
                    chobject.put("cargoSiteId", orderBean.getCargoSiteId());
                    chobject.put("carType", etbean.getCarType());
                    chobject.put("carNumber", etbean.getCarNumber());
                    chobject.put("containerNumber1", etbean.getContainerNumber1());
                    chobject.put("containerNumber2", etbean.getContainerNumber2());
                    chobject.put("sendWeight", etbean.getSendWeight());
                    chobject.put("conSendWeight2", etbean.getConSendWeight2());
                    entrucTotalWeight = entrucTotalWeight + Double.valueOf(etbean.getSendWeight());
                    entruckNumbe = entruckNumbe + 1;
                    if (projectType.equals("0")) {
                        containerNums = containerNums + 2;
                    }
                    chobject.put("sendImg", etbean.getImg());
                    myarray1.put(chobject);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
        return myarray1.toString();
    }

    /**
     * 提交装车信息
     */
    private void submit(String myjson) {
        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .submitEntruckinfo(ApiRetrofit.getInstance().submitEntruckinfo(orderId, myjson,String.valueOf(entruckNumbe),String.valueOf(containerNums),String.valueOf(entrucTotalWeight)));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("装车信息提交成功！");
                    finish();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mposition = position;
        Router.newIntent(this).to(AddEntruckActivity.class).requestCode(201).putString("orderId", orderId)
                .putString("projectType", projectType)
                .putInt("admitNum", admitNum)
                .putSerializable("entruckOrderBean", mEntruckOrderlist.get(position)).launch();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPref.getInstance(context).putInt(AppConstant.AdmitNum, 0);
    }
}

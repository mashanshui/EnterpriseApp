package com.shenhesoft.enterpriseapp.ui.activity.train;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.util.LogTime;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderBean;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderDetailBean;
import com.shenhesoft.enterpriseapp.bean.PDetailsRootItem;
import com.shenhesoft.enterpriseapp.bean.TrainBean;
import com.shenhesoft.enterpriseapp.bean.TrainLocationBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.adapter.EntruckListAdapter;
import com.shenhesoft.enterpriseapp.ui.adapter.TraininthewayAdapter;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;
import com.shenhesoft.enterpriseapp.widget.NestedExpandaleListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;

/**
 * @author zmx
 * @date 2017/12/23
 * @desc 火运-卸车
 */

public class UnloadingActivity extends BaseTitleActivity {
    private static final String TAG = "UnloadingActivity";
    @BindView(R.id.mylistview)
    ListView mListView;

    @BindView(R.id.train_in_the_way)
    NestedExpandaleListView mExpandableListView;

    @BindView(R.id.et_mylocation)
    EditText et_mylocation;

    //订单号
    private String orderId;

    private String projectType;

    TraininthewayAdapter mTraininthewayAdapter;

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
        tv_request_num.setText(bean.getInviteTrainNum());
        tv_admit_num.setText(bean.getAdmitTrainNum());
        tv_looad_num.setText(bean.getEntruckWeight());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_unloading_train;
    }

    @Override
    protected void initTitle() {
        setTitle("卸车");
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
            mConfirmDialog.setTitle1("确认已经全部分配完成");
            mConfirmDialog.setCancelListener(v -> mConfirmDialog.dismiss());
        }
        mConfirmDialog.setConfirmListener(v -> {
            getgoodsPlace();
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
        getlistlocation();
        mExpandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> true);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


    @OnClick({R.id.tv_loading_train_more, R.id.img_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_loading_train_more:
                Router.newIntent(this).to(UnloadDetailsActivity.class).requestCode(101).putString("orderId", orderId).putString("projectType", projectType).launch();
                break;

            case R.id.img_submit:
                submitlocation(et_mylocation.getText().toString());
                et_mylocation.setText("");
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

        HttpObserver<RequestResultsList<EntruckOrderDetailBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    List<EntruckOrderDetailBean> mentrucklist = data.getObj();
                    for (EntruckOrderDetailBean entruckOrderDetailBean : mentrucklist) {
                        if (!TextUtils.isEmpty(entruckOrderDetailBean.getGoodsPlace()) && !TextUtils.isEmpty(entruckOrderDetailBean.getGoodsSite())) {
                        } else {
                            IToast.showShort("请先上传分配货位的卸货信息");
                            return;
                        }
                    }
                    submit();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    /**
     * 提交装车信息
     */
    private void submit()
    {
        //type  0只保存运单信息，不跟新状态  1只更新状态。

        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .submitUnloadinfo(ApiRetrofit.getInstance().submitUnloadinfo(orderId, "", "1"));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("卸货信息提交成功！");
                    finish();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    private List<PDetailsRootItem> createTopListData(List<TrainLocationBean> beans) {
        List<PDetailsRootItem> rootItems = new ArrayList<>();

        List<String> time;
        List<String> location;
        time = new ArrayList<>();
        location = new ArrayList<>();
        for (TrainLocationBean bean : beans) {
            time.add(bean.getTime());
            location.add(bean.getLocation());
        }

        PDetailsRootItem item = new PDetailsRootItem("在途位置", R.drawable.icon_xiangmxx);
        item.setChildItems(PDetailsRootItem.createChilds(
                time.toArray(new String[0]),
                location.toArray(new String[0])
        ));

        rootItems.add(item);

        return rootItems;
    }


    //获取位置列表
    private void getlistlocation() {
        Observable<RequestResultsList<TrainLocationBean>> observable = HttpManager.getInstance().getOrderService()
                .getTrainLocation(ApiRetrofit.getInstance().trainDetailParams(orderId));

        HttpObserver observer = new HttpObserver<RequestResultsList<TrainLocationBean>>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    mTraininthewayAdapter = new TraininthewayAdapter(createTopListData(data.getObj()), context);
                    mExpandableListView.setGroupIndicator(null);
                    mExpandableListView.setAdapter(mTraininthewayAdapter);
                    for (int i = 0; i < mTraininthewayAdapter.getGroupCount(); i++) {
                        mExpandableListView.expandGroup(i);
                    }
                });

        HttpManager.getInstance().statrPostTask(observable, observer);

    }

    //提交位置信息
    private void submitlocation(String loacation) {
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .saveTrainLocation(ApiRetrofit.getInstance().savetrainLocationParams(orderId, loacation));

        HttpObserver observer = new HttpObserver<RequestResults>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    getlistlocation();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);

    }
}

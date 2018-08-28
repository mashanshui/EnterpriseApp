package com.shenhesoft.enterpriseapp.ui.activity.train;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.PDetailsRootItem;
import com.shenhesoft.enterpriseapp.bean.TrainBean;
import com.shenhesoft.enterpriseapp.bean.TrainDetailsBean;
import com.shenhesoft.enterpriseapp.bean.TrainLoadingInfo;
import com.shenhesoft.enterpriseapp.bean.TrainLocationBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.adapter.PDetailsAdapter;
import com.shenhesoft.enterpriseapp.ui.adapter.TraininthewayAdapter;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * 作者：Tornado
 * 创作日期：2018/1/4.
 * 描述：在途运载
 */

public class TrackinthewayActivity extends BaseTitleActivity {

    @BindView(R.id.details_ex_listview)
    ExpandableListView exListview;

    @BindView(R.id.train_in_the_way)
    ExpandableListView mExpandableListView;
    TrainBean mTrainBean;
    TrainDetailsBean mTrainDetailsBean;

    private String orderId;

    @BindView(R.id.img_submit)
    ImageView img_submit;

    @BindView(R.id.et_mylocation)
    EditText et_mylocation;


    TraininthewayAdapter mTraininthewayAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_train_intheway_;
    }

    @Override
    protected void initTitle() {
        setTitle("在途追踪");
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
            mConfirmDialog.setTitle1("是否结束在途运载状态");
            mConfirmDialog.setTitle2("(进入卸货状态后可以继续更新在途位置)");
            mConfirmDialog.setCancelListener(v -> mConfirmDialog.dismiss());
        }
        mConfirmDialog.setConfirmListener(v -> {
            sendTrain();
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
        submit();
        getlistlocation();
        exListview.setOnGroupClickListener((parent, v, groupPosition, id) -> true);
        mExpandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> true);
    }

    @OnClick({R.id.img_submit})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_submit:
                submitlocation(et_mylocation.getText().toString());
                et_mylocation.setText("");
                break;
            default:
                break;
        }
    }


    private List<PDetailsRootItem> createListData(TrainDetailsBean bean) {
        List<PDetailsRootItem> rootItems = new ArrayList<>();

        //        项目类型（0 集装箱 1 散装） projectType

        if (bean.getProjectType().equals("0")) {
            bean.setProjectType("集装箱");
        } else if (bean.getProjectType().equals("1")) {
            bean.setProjectType("散装");
        }

        PDetailsRootItem item10 = new PDetailsRootItem("项目信息", R.drawable.icon_xiangmxx);
        item10.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"项目编号：", "请车单号：", "货物品名：", "发货企业：", "始发站点：", "收货企业：",
                        "到达站点：", "装车数：", "装载吨位："},
                new String[]{bean.getProjectCode(), bean.getPleaseTrainNumber(), bean.getCargoName(),
                        bean.getBeginPlace(), bean.getShipStation(), bean.getEndPlace(), bean.getReceiptStation(),
                        bean.getEntruckNumbe(), bean.getEntruckWeight()}
        ));

        rootItems.add(item10);

        if (bean.getLoadingInfos() == null) {
            return rootItems;
        }

        /**
         * 集装箱和散堆装区分显示
         */
        if ("集装箱".equals(bean.getProjectType())) {
            for (int i = 0; i < bean.getLoadingInfos().size(); i++) {
                PDetailsRootItem item = new PDetailsRootItem("装车详单", R.drawable.icon_jizxxx);
                TrainLoadingInfo info = bean.getLoadingInfos().get(i);
                item.setChildItems(PDetailsRootItem.createChilds(
                        new String[]{"车型：", "车号：", "集装箱号：", "发货载重：", "集装箱号：", "发货载重："},
                        new String[]{info.getTrainType(), info.getTrainNumber(), info.getBoxNumber1()
                                , info.getWeight(), info.getBoxNumber2(), info.getConSendWeight2()}
                ));
                rootItems.add(item);
            }
        } else {
            for (int i = 0; i < bean.getLoadingInfos().size(); i++) {
                PDetailsRootItem item = new PDetailsRootItem("装车详单", R.drawable.icon_jizxxx);
                TrainLoadingInfo info = bean.getLoadingInfos().get(i);
                item.setChildItems(PDetailsRootItem.createChilds(
                        new String[]{"车型：", "车号：", "发货载重："},
                        new String[]{info.getTrainType(), info.getTrainNumber(), info.getWeight()}
                ));
                rootItems.add(item);
            }
        }

        return rootItems;
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


    //获取列表
    private void submit() {
        Observable<RequestResults<TrainDetailsBean>> observable = HttpManager.getInstance().getOrderService()
                .getTrainDetail(ApiRetrofit.getInstance().trainDetailParams(orderId));

        HttpObserver observer = new HttpObserver<RequestResults<TrainDetailsBean>>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    mTrainDetailsBean = data.getObj();
                    PDetailsAdapter adapter = new PDetailsAdapter(createListData(mTrainDetailsBean), context);
                    exListview.setGroupIndicator(null);
                    exListview.setAdapter(adapter);
                    for (int i = 0; i < adapter.getGroupCount(); i++) {
                        exListview.expandGroup(i);
                    }
                });

        HttpManager.getInstance().statrPostTask(observable, observer);

    }

    //在途运载
    private void sendTrain() {
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .sendTrain(ApiRetrofit.getInstance().updataTrainParams(orderId, 5));

        HttpObserver observer = new HttpObserver<RequestResults>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("在途运载完成！");
                    finish();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);

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

package com.shenhesoft.enterpriseapp.ui.activity.train;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ExpandableListView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.PDetailsRootItem;
import com.shenhesoft.enterpriseapp.bean.TrainBean;
import com.shenhesoft.enterpriseapp.bean.TrainDetailsBean;
import com.shenhesoft.enterpriseapp.bean.TrainLoadingInfo;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.adapter.PDetailsAdapter;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 作者：Tornado
 * 创作日期：2018/1/4.
 * 描述：等待发运
 */

public class WaitSendActivity extends BaseTitleActivity {

    @BindView(R.id.details_ex_listview)
    ExpandableListView exListview;
    TrainBean mTrainBean;
    TrainDetailsBean mTrainDetailsBean;

    private String orderId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_project_details_new;
    }

    @Override
    protected void initTitle() {
        setTitle("等待发运");
        setRightText("发车", v -> {
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
            mConfirmDialog.setTitle1("确认开始发车");
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
                new String[]{bean.getProjectCode(), bean.getProjectType(), bean.getCargoName(),
                        bean.getBeginPlace(), bean.getBranchOrgan(), bean.getEndPlace(), bean.getReceiptStation(),
                        bean.getAdmitTrainNum(), bean.getUpdateDate()}
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

    //发车
    private void sendTrain() {
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .sendTrain(ApiRetrofit.getInstance().updataTrainParams(orderId, 4));

        HttpObserver observer = new HttpObserver<RequestResults>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("发车状态提交成功！");
                    finish();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);

    }
}

package com.shenhesoft.enterpriseapp.ui.activity.train;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.bean.PDetailsRootItem;
import com.shenhesoft.enterpriseapp.bean.TrainBean;
import com.shenhesoft.enterpriseapp.bean.TrainDetailsBean;
import com.shenhesoft.enterpriseapp.bean.TrainLoadingInfo;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.adapter.PDetailsAdapter;
import com.shenhesoft.enterpriseapp.utils.IToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 作者：Tornado
 * 创作日期：2018/1/4.
 * 描述：
 */

public class TrainDetailsActivity extends BaseTitleActivity {

    @BindView(R.id.details_ex_listview)
    ExpandableListView exListview;
    TrainBean mTrainBean;
    TrainDetailsBean mTrainDetailsBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_project_details_new;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTrainBean = (TrainBean) getIntent().getSerializableExtra("trainbean");
        exListview.setOnGroupClickListener((parent, v, groupPosition, id) -> true);
        submit();

    }

    private TrainDetailsBean getTrainDetails() {
//        TrainDetailsBean b = new TrainDetailsBean();
//        b.setProjectCode("BJ2018101761");
//        b.setProjectType("集装");
//        b.setShipper("高渐离");
//        b.setConsignee("盖聂");
//        b.setBranchOrgan("秦风镖局");
//        b.setYardman("李二狗");
//        b.setCreateDate("2017-12-29");
//        b.setOrderStatus("运载途中");
//        b.setUpdateDate("2018-1-1");
//        b.setCargoName("赤霄剑");
//        b.setCargoSpecification("1柄");
//        b.setAssayIndex("神器");
//        b.setInviteTrainNum("5");
//        b.setInviteTrainType("G520");
//        b.setAdmitTrainNum("4");
//        b.setRealityTrainNum("4");
//        b.setPredictWeight("10Kg");
//        b.setShipStation("墨家机关城站");
//        b.setShipGoodsYard("墨家机关城分拣中心");
//        b.setShipGoodsLocation("3-289");
//        b.setReceiptStation("咸阳西站");
//        b.setInviteTime("221-1-1");
//        b.setLoadingTime("221-1-8");
//        b.setDepartTime("221-1-12");
//        b.setAdvanceAccount("八斗钱庄000391939991283");
//        b.setFreightCharge("5000刀");
//        b = mTrainDetailsBean;
//        List<TrainLoadingInfo> tlist = new ArrayList<>();
//        tlist.add(new TrainLoadingInfo("汗血宝马", "秦300", "XM00001", "", "10kg"));
//        tlist.add(new TrainLoadingInfo("飞鹰空运", "翼200", "XY00211", "", "10kg"));
//        b.setLoadingInfos(tlist);
        return mTrainDetailsBean;
    }

    private List<PDetailsRootItem> createListData(TrainDetailsBean bean) {
        List<PDetailsRootItem> rootItems = new ArrayList<>();


        //        项目类型（0 集装箱 1 散装） projectType

        if (bean.getProjectType().equals("0")) {
            bean.setProjectType("集装箱");
        } else if (bean.getProjectType().equals("1")) {
            bean.setProjectType("散装");
        }

        String orderstatus = "";
//        运单状态（0:取消,1:等待承认,2:等待装车,3:等待发运,4:在途运载,5:等待卸货,6:等待回单,7:已完成） status
        if (bean.getOrderStatus() == 0) {
            orderstatus = "取消";
        } else if (bean.getOrderStatus() == 1) {
            orderstatus = "等待承认";
        } else if (bean.getOrderStatus() == 2) {
            orderstatus = "等待装车";
        } else if (bean.getOrderStatus() == 3) {
            orderstatus = "等待发运";
        } else if (bean.getOrderStatus() == 4) {
            orderstatus = "在途运载";
        } else if (bean.getOrderStatus() == 5) {
            orderstatus = "等待卸货";
        } else if (bean.getOrderStatus() == 6) {
            orderstatus = "等待回单";
        } else if (bean.getOrderStatus() == 7) {
            orderstatus = "已完成";
        }

        PDetailsRootItem item1 = new PDetailsRootItem("项目信息", R.drawable.icon_xiangmxx);
        item1.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"项目编号：", "项目类型：", "发货企业：", "收货企业：", "分支机构：", "物流员：",
                        "创建时间：", "运单状态：", "更新时间："},
                new String[]{bean.getProjectCode(), bean.getProjectType(), bean.getBeginPlace(),
                        bean.getEndPlace(), bean.getBranchOrgan(), bean.getYardman(), bean.getCreateDate(),
                        orderstatus, bean.getUpdateDate()}
        ));
        rootItems.add(item1);

        PDetailsRootItem item2 = new PDetailsRootItem("货物信息", R.drawable.icon_jizxxx);
        item2.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"货物名称：", "货物规格："},
                new String[]{bean.getCargoName(), bean.getCargoSpecification()}
        ));
        rootItems.add(item2);

        PDetailsRootItem item3 = new PDetailsRootItem("请车信息", R.drawable.ic_car);
        item3.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"请车数：", "请车类型：", "承认车数：", "落车数：", "预计载重："},
                new String[]{bean.getInviteTrainNum(), bean.getInviteTrainType(), bean.getAdmitTrainNum(),
                        bean.getRealityTrainNum(), bean.getPredictWeight()}
        ));
        rootItems.add(item3);

        PDetailsRootItem item4 = new PDetailsRootItem("收发货信息", R.drawable.icon_chelxx);
        item4.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"始发站点：", "发站货场：", "发站货位：", "到达站点：", "请车时间：",
                        "装车时间：", "发车时间："},
                new String[]{bean.getShipStation(), bean.getShipGoodsYard(), bean.getShipGoodsLocation(),
                        bean.getReceiptStation(), bean.getCreateDate(), bean.getLoadingTime(), bean.getDepartTime()}
        ));
        rootItems.add(item4);

        PDetailsRootItem item5 = new PDetailsRootItem("财务信息", R.drawable.ic_motor_fee);
        item5.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"预付款账户：", "运输费用："},
                new String[]{bean.getAdvanceAccount(), bean.getFreightCharge()}
        ));
        rootItems.add(item5);

        if (bean.getLoadingInfos() == null) {
            return rootItems;
        }

        /**
         * 如果运单到达等待回单状态时会有卸货载重，这里区分显示
         */
        if (bean.getOrderStatus() >= 6) {
            rootItems.addAll(getTruckInfo1(bean));
        } else {
            rootItems.addAll(getTruckInfo2(bean));
        }

        return rootItems;
    }

    private List<PDetailsRootItem> getTruckInfo1(TrainDetailsBean bean) {
        List<PDetailsRootItem> itemList = new ArrayList<>();
        /**
         * 集装箱和散堆装区分显示
         */
        if ("集装箱".equals(bean.getProjectType())) {
            for (int i = 0; i < bean.getLoadingInfos().size(); i++) {
                PDetailsRootItem item = new PDetailsRootItem("装车详单", R.drawable.icon_jizxxx);
                TrainLoadingInfo info = bean.getLoadingInfos().get(i);
                item.setChildItems(PDetailsRootItem.createChilds(
                        new String[]{"车型：", "车号：", "集装箱号：", "发货载重：", "到货载重：", "集装箱号：", "发货载重：", "到货载重："},
                        new String[]{info.getTrainType(), info.getTrainNumber()
                                , info.getBoxNumber1(), info.getWeight(), info.getUnloadWeight()
                                , info.getBoxNumber2(), info.getConSendWeight2(), info.getConUnloadWeight2()}
                ));
                itemList.add(item);
            }
        } else {
            for (int i = 0; i < bean.getLoadingInfos().size(); i++) {
                PDetailsRootItem item = new PDetailsRootItem("装车详单", R.drawable.icon_jizxxx);
                TrainLoadingInfo info = bean.getLoadingInfos().get(i);
                item.setChildItems(PDetailsRootItem.createChilds(
                        new String[]{"车型：", "车号：", "发货载重：", "到货载重："},
                        new String[]{info.getTrainType(), info.getTrainNumber(), info.getWeight(), info.getUnloadWeight()}
                ));
                itemList.add(item);
            }
        }
        return itemList;
    }

    private List<PDetailsRootItem> getTruckInfo2(TrainDetailsBean bean) {
        List<PDetailsRootItem> itemList = new ArrayList<>();
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
                itemList.add(item);
            }
        } else {
            for (int i = 0; i < bean.getLoadingInfos().size(); i++) {
                PDetailsRootItem item = new PDetailsRootItem("装车详单", R.drawable.icon_jizxxx);
                TrainLoadingInfo info = bean.getLoadingInfos().get(i);
                item.setChildItems(PDetailsRootItem.createChilds(
                        new String[]{"车型：", "车号：", "发货载重："},
                        new String[]{info.getTrainType(), info.getTrainNumber(), info.getWeight()}
                ));
                itemList.add(item);
            }
        }
        return itemList;
    }

    //获取列表
    private void submit() {
        Observable<RequestResults<TrainDetailsBean>> observable = HttpManager.getInstance().getOrderService()
                .getTrainDetail(ApiRetrofit.getInstance().trainDetailParams(String.valueOf(mTrainBean.getId())));

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
}

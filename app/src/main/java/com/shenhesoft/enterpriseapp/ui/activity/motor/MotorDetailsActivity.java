package com.shenhesoft.enterpriseapp.ui.activity.motor;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ExpandableListView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.bean.PDetailsRootItem;
import com.shenhesoft.enterpriseapp.ui.adapter.PDetailsAdapter;
import com.shenhesoft.enterpriseapp.utils.DoubleUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：Tornado
 * 创作日期：2018/1/3.
 * 描述：
 */

public class MotorDetailsActivity extends BaseTitleActivity {

    @BindView(R.id.details_ex_listview)
    ExpandableListView exListview;
    private MotorDetailsBean mMotorDetailsBean;
    private List<PDetailsRootItem> rootItems;

    @Override
    public int getLayoutId() {
        return R.layout.activity_project_details_new;
    }

    @Override
    protected void initTitle() {
        setTitle("运单详情");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mMotorDetailsBean = (MotorDetailsBean) getIntent().getSerializableExtra("motorbean");
        PDetailsAdapter adapter = new PDetailsAdapter(createtListData(mMotorDetailsBean), context);
        exListview.setGroupIndicator(null);
        exListview.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            exListview.expandGroup(i);
        }

        exListview.setOnGroupClickListener((parent, v, groupPosition, id) -> true);
    }

    private MotorDetailsBean getDetailsInfo() {
        MotorDetailsBean bean = new MotorDetailsBean();
        bean.setProjectCode("BJ2018101761");
        bean.setProjectType("集装");
        bean.setBranchOrgan("秦风镖局");
        bean.setYardman("无名氏");
        bean.setCreateDate("2017-12-29");
        bean.setOrderStatus("运载途中");
        bean.setUpdateDate("2018-1-1");
        bean.setCargoName("渊虹剑");
        bean.setCargoSpecification("1柄");
        bean.setAssayIndex("神器");
        bean.setCarPlateNumber("秦S·765567");
        bean.setCarType("汗血宝马");
        bean.setDriverName("金衣使002");
        bean.setContactTel("199129129");
        bean.setBoxNumber1("DQ0010283494");
        bean.setShipCompany("墨家机关城");
        bean.setClaimAddress("墨家机关城东山头");
        bean.setShipWeightM("10.8kg");
        bean.setShipWeightP("0.8kg");
        bean.setShipWeightJ1("10kg");
        bean.setReceiptCompany("秦风镖局");
        bean.setReceiptAddress("咸阳城流芳客栈");
        bean.setGoodsYard("天字房");
        bean.setGoodsLocation("36号");
        bean.setDeliveryWeightM("10.8kg");
        bean.setDeliveryWeightP("0.8kg");
        bean.setDeliveryWeightJ1("10kg");
        bean.setSpoilageWeight("0g");

        bean.setRejectCause("涉及朝廷通缉犯");
        bean.setSubmitDate("2018-1-1");
        bean.setRejecter("秦风镖局监察使");
        bean.setDisposeResult("扣押，上交朝廷");
        bean.setDisposeDate("2018-1-1");
        bean.setHandler("镖局长老");
        return mMotorDetailsBean;
    }

    private List<PDetailsRootItem> createtListData(MotorDetailsBean bean) {
        rootItems = new ArrayList<>();


        //if(异常有){..添加异常item}
        if (!TextUtils.isEmpty(bean.getRejectCause())) {
            PDetailsRootItem item = new PDetailsRootItem("异常信息", R.drawable.ic_point);
            item.setChildItems(PDetailsRootItem.createChilds(
                    new String[]{"驳回原因：", "提报时间：", "提报人：", "处理结果：", "处理时间：", "操作者："},
                    new String[]{bean.getRejectCause(), bean.getSubmitDate(), bean.getRejecter(),
                            bean.getDisposeResult(), bean.getDisposeDate(), bean.getHandler()}
            ));
            item.setStatus(1);
            rootItems.add(item);
        }

//        项目类型（0 集装箱 1 散装） projectType

        if (bean.getProjectType().equals("0")) {
            bean.setProjectType("集装箱");
        } else if (bean.getProjectType().equals("1")) {
            bean.setProjectType("散装");
        }

//        运单状态（1:等待调度 2:等待发运 3:在途运载 4:货位引导 5:等待回单 6:等待确认 7:已完成） status
        if (bean.getOrderStatus().equals("1")) {
            bean.setOrderStatus("等待调度");
            createStatus1Data(bean);
        } else if (bean.getOrderStatus().equals("2")) {
            bean.setOrderStatus("等待发运");
            createNormalData(bean);
        } else if (bean.getOrderStatus().equals("3")) {
            bean.setOrderStatus("在途运载");
            createNormalData(bean);
        } else if (bean.getOrderStatus().equals("4")) {
            bean.setOrderStatus("货位引导");
            createNormalData(bean);
        } else if (bean.getOrderStatus().equals("5")) {
            bean.setOrderStatus("等待回单");
            createNormalData(bean);
        } else if (bean.getOrderStatus().equals("6")) {
            bean.setOrderStatus("等待确认");
            createNormalData(bean);
        } else if (bean.getOrderStatus().equals("7")) {
            bean.setOrderStatus("已完成");
            createNormalData(bean);
        }

        if (!TextUtils.isEmpty(bean.getDeliveryWeightM()) && !TextUtils.isEmpty(bean.getShipWeightM())) {
//到货毛重-发货毛重             //        损耗重量（发货净重减到货净重）
            bean.setSpoilageWeight(String.valueOf(
                    DoubleUtil.sub(Double.valueOf(bean.getDeliveryWeightM()), Double.valueOf(bean.getShipWeightM()))));
        }

        return rootItems;
    }

    private void createNormalData(MotorDetailsBean bean){
        PDetailsRootItem item1 = new PDetailsRootItem("项目信息", R.drawable.icon_xiangmxx);
        item1.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"项目编号：", "项目类型：", "分支机构：", "调度员：",
                        "创建时间：", "运单状态：", "更新时间："},
                new String[]{bean.getProjectCode(), bean.getProjectType(), bean.getBranchOrgan(), bean.getYardman(), bean.getCreateDate(),
                        bean.getOrderStatus(), bean.getUpdateDate()}
        ));
        rootItems.add(item1);

        PDetailsRootItem item2 = new PDetailsRootItem("货物信息", R.drawable.icon_jizxxx);
        item2.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"货物名称：", "货物规格：", "化验指标："},
                new String[]{bean.getCargoName(), bean.getCargoSpecification(), bean.getAssayIndex()}
        ));
        rootItems.add(item2);

        PDetailsRootItem item3 = new PDetailsRootItem("车辆信息", R.drawable.icon_chelxx);
        item3.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"承运车辆：", "车辆类型：", "驾驶员：", "联系方式："},
                new String[]{bean.getCarPlateNumber(), bean.getCarType(), bean.getDriverName(), bean.getContactTel()}
        ));
        rootItems.add(item3);

        //if(..集装=true..){}
        if (bean.getProjectType().equals("集装箱")) {
            PDetailsRootItem item4 = new PDetailsRootItem("集装箱信息", R.drawable.icon_jizxxx);
            item4.setChildItems(PDetailsRootItem.createChilds(
                    new String[]{"箱号：", "箱号："},
                    new String[]{bean.getBoxNumber1(), bean.getBoxNumber2()}
            ));
            rootItems.add(item4);
        }

        PDetailsRootItem item5 = new PDetailsRootItem("收发货信息", R.drawable.icon_chelxx);
        item5.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"发货单位：", "取货地址：", "发货皮重：", "发货毛重：", "发货净重：", "发货净重：",
                        "收货单位：", "运抵地址：", "货场：", "货位：", "到货皮重：", "到货毛重：", "到货净重：",
                        "到货净重：", "损耗重量："},
                new String[]{bean.getShipCompany(), bean.getClaimAddress(), bean.getShipWeightP(),
                        bean.getDeliveryWeightM(), bean.getDeliveryWeightJ1(), bean.getDeliveryWeightJ2(),
                        bean.getReceiptCompany(), bean.getReceiptAddress(), bean.getGoodsYard(), bean.getGoodsLocation(),
                        bean.getDeliveryWeightP(), bean.getDeliveryWeightM(), bean.getDeliveryWeightJ1(),
                        bean.getDeliveryWeightJ2(), bean.getSpoilageWeight()}
        ));
        rootItems.add(item5);
    }

    private void createStatus1Data(MotorDetailsBean bean){

        PDetailsRootItem item3 = new PDetailsRootItem("车辆信息", R.drawable.icon_chelxx);
        item3.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"申请车辆：", "驾驶员：", "联系方式：", "车辆型号："},
                new String[]{bean.getCarPlateNumber(), bean.getDriverName(), bean.getContactTel(),bean.getCarType()}
        ));
        rootItems.add(item3);

        //if(..集装=true..){}
        PDetailsRootItem item4 = new PDetailsRootItem("其他信息", R.drawable.icon_jizxxx);
        item4.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"所属车队：", "历史运输："},
                new String[]{bean.getCarItemName(), bean.getHistoryTbOrderNumDriverId()}
        ));
        rootItems.add(item4);
    }
}

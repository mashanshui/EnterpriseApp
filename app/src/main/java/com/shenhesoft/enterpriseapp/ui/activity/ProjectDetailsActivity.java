package com.shenhesoft.enterpriseapp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.DriverInfoBean;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.bean.NewDriverInfoBean;
import com.shenhesoft.enterpriseapp.bean.PDetailsChildItem;
import com.shenhesoft.enterpriseapp.bean.PDetailsRootItem;
import com.shenhesoft.enterpriseapp.bean.WaitDispatchDetailsBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
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
 * @author 张继淮
 * @date 2017/12/6
 * @desc 项目详情
 */

public class ProjectDetailsActivity extends BaseTitleActivity {

    @BindView(R.id.details_ex_listview)
    ExpandableListView exListview;
    private MotorDetailsBean mDetailsBean;
    private NewDriverInfoBean driverInfo;
    private PDetailsAdapter adapter;

    @Override
    public int getLayoutId() {
        //return R.layout.activity_project_details;
        return R.layout.activity_project_details_new;
    }

    @Override
    protected void initTitle() {
        setTitle("项目详情");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDetailsBean = (MotorDetailsBean) getIntent().getSerializableExtra("motorDetailsBean");
        getcarinfo(mDetailsBean.getProjectId(),mDetailsBean.getDriverId(),mDetailsBean.getStepSelectCode());
    }

    private WaitDispatchDetailsBean getDetailsBean() {
        WaitDispatchDetailsBean bean = new WaitDispatchDetailsBean();
        bean.setCarPlateNumber(driverInfo.getPlateNumber());
        bean.setCarMasterName(driverInfo.getOwnerCarName());
        bean.setDriverName(driverInfo.getDriverName());
        bean.setDriversLicense("");
        bean.setCarBrand(driverInfo.getBrand());
        bean.setCarModelNumber(driverInfo.getModel());
        bean.setCarType(driverInfo.getMotorcycleType());
        bean.setMotorcade(driverInfo.getTeamName());
        bean.setChangeInfo("");
        if (!driverInfo.getTbAnchoredCompanyList().isEmpty()) {
            if (driverInfo.getTbAnchoredCompanyList().size() == 1) {
                bean.setSubCompany1(driverInfo.getTbAnchoredCompanyList().get(0).getName());
            } else if (driverInfo.getTbAnchoredCompanyList().size() > 1) {
                bean.setSubCompany2(driverInfo.getTbAnchoredCompanyList().get(1).getName());
            }
        }
        bean.setHisTransport("");
        bean.setBadnessRecord("");

//        bean.setCarMasterName("林红尘");
//        bean.setDriverName("穆如寒江");
//        bean.setDriversLicense("86764893930223");
//        bean.setCarBrand("梅赛德斯-奔驰");
//        bean.setCarModelNumber("GLC-300");
//        bean.setCarType("家用SUV");
//        bean.setMotorcade("Aurora丨光丨");
//        bean.setChangeInfo("无");
//        bean.setSubCompany1("华帝股份集团");
//        bean.setSubCompany2("天耀国际科技有限公司");
//        bean.setHisTransport("300次");
//        bean.setBadnessRecord("无");

//        bean.setRejectCause("钱不到位");
//        bean.setRejectDate("2018-1-1");
//        bean.setRejecter("梅八斗");
        return bean;
    }

    private List<PDetailsRootItem> createListData(WaitDispatchDetailsBean bean) {
        List<PDetailsRootItem> rootItems = new ArrayList<>();

        if (bean.getRejectCause() != null && !bean.getRejectCause().isEmpty()) {
            PDetailsRootItem item = new PDetailsRootItem("驳回信息", R.drawable.ic_point);
            item.setStatus(1);
            List<PDetailsChildItem> childItems = new ArrayList<>();
            childItems.add(new PDetailsChildItem("驳回原因：", bean.getRejectCause()));
            childItems.add(new PDetailsChildItem("驳回时间：", bean.getRejectDate()));
            childItems.add(new PDetailsChildItem("驳回人：", bean.getRejecter()));
            item.setChildItems(childItems);
            rootItems.add(item);
        }

        PDetailsRootItem item1 = new PDetailsRootItem("车辆信息", R.drawable.ic_car);
        List<PDetailsChildItem> childItems1 = new ArrayList<>();
        childItems1.add(new PDetailsChildItem("申请车辆：", bean.getCarPlateNumber()));
        childItems1.add(new PDetailsChildItem("车主姓名：", bean.getCarMasterName()));
        childItems1.add(new PDetailsChildItem("司机姓名：", bean.getDriverName()));
        childItems1.add(new PDetailsChildItem("驾驶证号：", bean.getDriversLicense()));
        childItems1.add(new PDetailsChildItem("车辆品牌：", bean.getCarBrand()));
        childItems1.add(new PDetailsChildItem("车辆型号：", bean.getCarModelNumber()));
        childItems1.add(new PDetailsChildItem("车辆类型：", bean.getCarType()));
        item1.setChildItems(childItems1);

        PDetailsRootItem item2 = new PDetailsRootItem("车队信息", R.drawable.ic_fleet);
        List<PDetailsChildItem> childItems2 = new ArrayList<>();
        childItems2.add(new PDetailsChildItem("所属车队：", bean.getMotorcade()));
        childItems2.add(new PDetailsChildItem("变更信息：", bean.getChangeInfo()));
        item2.setChildItems(childItems2);

        PDetailsRootItem item3 = new PDetailsRootItem("挂靠公司信息", R.drawable.ic_company);
        List<PDetailsChildItem> childItems3 = new ArrayList<>();
        childItems3.add(new PDetailsChildItem("挂靠公司1：", bean.getSubCompany1()));
        childItems3.add(new PDetailsChildItem("挂靠公司2：", bean.getSubCompany2()));
        item3.setChildItems(childItems3);

        PDetailsRootItem item4 = new PDetailsRootItem("信誉安全信息", R.drawable.ic_credit);
        List<PDetailsChildItem> childItems4 = new ArrayList<>();
        childItems4.add(new PDetailsChildItem("历史运输：", bean.getHisTransport()));
        childItems4.add(new PDetailsChildItem("不良记录：：", bean.getBadnessRecord()));
        item4.setChildItems(childItems4);

        rootItems.add(item1);
        rootItems.add(item2);
        rootItems.add(item3);
        //暂时取消信誉安全信息模块
//        rootItems.add(item4);
        return rootItems;
    }

    private void getcarinfo(String projectId,String driverId,String shortType) {

        Observable<RequestResults<NewDriverInfoBean>> observable = HttpManager.getInstance().getOrderService()
                .getCarinfoByDriverId(ApiRetrofit.getInstance().getCarinfoByDriverId(projectId,driverId,shortType));

        HttpObserver<RequestResults<NewDriverInfoBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    driverInfo = data.getObj();
                    adapter = new PDetailsAdapter(createListData(getDetailsBean()), context);
                    exListview.setGroupIndicator(null);
                    exListview.setAdapter(adapter);
                    for (int i = 0; i < adapter.getGroupCount(); i++) {
                        exListview.expandGroup(i);
                    }
                    exListview.setOnGroupClickListener((parent, v, groupPosition, id) -> true);
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }
}

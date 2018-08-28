package com.shenhesoft.enterpriseapp.ui.activity.motor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.ContainerBean;
import com.shenhesoft.enterpriseapp.bean.DriverInfoBean;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.bean.ProjectBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.confirmarrive.NewOrderActivity;
import com.shenhesoft.enterpriseapp.utils.DialogUtil;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.utils.SearchPupItenAdapter;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;
import com.shenhesoft.enterpriseapp.widget.BottomProjectChooseDialog;
import com.shenhesoft.enterpriseapp.widget.SearchFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * 作者：Tornado
 * 创作日期：2017/12/26.
 * 描述：汽车短驳》添加新运单
 */

public class AddNewOrderActivity extends BaseTitleActivity {

    private static final String TAG = "AddNewOrderActivity";
    public final static int NEW_ORDER_JIZX = 1; //集装箱运单
    public final static int NEW_ORDER_SAND = 2; //散堆运单
    @BindView(R.id.tv_project_sendGoods)
    TextView mTvProjectSendGoods;
    @BindView(R.id.tv_project_arriveGoods)
    TextView mTvProjectArriveGoods;

    private String projectId; //项目id
    private String projectCode;// 项目编号
    private String stepSelectCode = ""; // 阶段选择
    private String stepSelectCodeInt = "0";
    private String branchGroupName; // 分支机构
    private String arrivePlaceAddress; // 运抵地址
    private String cargoName; // 货物品名
    private String carTeamId; // 车队id
    private String carPlateNumber; // 承运车辆车牌号
    private String carrierVehicleId; // 承运车辆id
    private String carrierVehicleName; // 承运车辆名称
    private String carType; // 车辆类型
    private String driverName; // 驾驶员
    private String driverPhone; // 联系方式
    private String shortBargeCost; // 短驳运费
    private String deductionPrice; // 折损单价
    private String subsidy; // 补贴
    private String containerNumber1; //集装箱1号名称
    private String containerNumber2; // 集装箱2号名称

    @BindView(R.id.tv_project_code)
    TextView tv_project_code;
    @BindView(R.id.tv_project_type)
    TextView tv_project_type;
    @BindView(R.id.tv_project_branchGroupName)
    TextView tv_project_branchGroupName;
    @BindView(R.id.tv_project_stepSelectCode)
    TextView tv_project_stepSelectCode;
    @BindView(R.id.tv_project_sendpeople)
    TextView tv_project_sendpeople;
    @BindView(R.id.tv_project_acceptpeople)
    TextView tv_project_acceptpeople;
    //    @BindView(R.id.et_project_carno)
//    EditText et_project_carno;
    @BindView(R.id.tv_project_carno)
    TextView tv_project_carno;
    @BindView(R.id.tv_project_cartype)
    TextView tv_project_cartype;
    @BindView(R.id.tv_project_cardriver)
    TextView tv_project_cardriver;
    @BindView(R.id.tv_project_gettype)
    TextView tv_project_gettype;
    @BindView(R.id.et_project_containerNumber1)
    TextView et_project_containerNumber1;
    @BindView(R.id.et_project_containerNumber2)
    TextView et_project_containerNumber2;
    //        @BindView(R.id.et_project_shortBargeCost)
//    EditText et_project_shortBargeCost;
    @BindView(R.id.tv_project_shortBargeCost)
    TextView tv_project_shortBargeCost;
    @BindView(R.id.et_project_subsidy)
    EditText et_project_subsidy;


    private BottomProjectChooseDialog mChooseDialogYard;
    private BottomListChooseDialog mChooseDialog;
    private BottomListChooseDialog mChooseCarDialog;
    private List<ProjectBean> mList;
    private List<LocationPopBean> mchildList;
    private List<LocationPopBean> mChildCarList;
    private List<ContainerBean> mContainerList;

    private boolean canselect;
    private boolean carCanSelect = false;

    private int orderType;

    private String orderid;

    /**
     * 搜索集装箱
     */
    private SearchFragment searchFragment;
    private List<String> searchList;

    //集装箱信息Layout
    @BindView(R.id.new_order_jizx_layout)
    LinearLayout jizxLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_new_order;
    }

    @Override
    protected void initTitle() {
        setTitle("新建运单");
        setRightText("下一步 ", v -> {
            CheckStepSelectCode();
            isPass();
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        searchList = new ArrayList<>();
//        mSearchList = new ArrayList<>();
//        mSearchAdapter = new SearchPupItenAdapter(this, android.R.layout.simple_list_item_1, mSearchList);
//        et_project_containerNumber1.setAdapter(mSearchAdapter);
//        et_project_containerNumber2.setAdapter(mSearchAdapter);
//        et_project_containerNumber1.setOnFocusChangeListener((v, hasFocus) -> {
//            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) v;
//            if (hasFocus) {
//                autoCompleteTextView.showDropDown();
//            }
//        });
//        et_project_containerNumber2.setOnFocusChangeListener((v, hasFocus) -> {
//            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) v;
//            if (hasFocus) {
//                autoCompleteTextView.showDropDown();
//            }
//        });
//

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mchildList = new ArrayList<>();
        LocationPopBean bean1 = new LocationPopBean();
        LocationPopBean bean2 = new LocationPopBean();
        bean1.setName("接取");
        mchildList.add(bean1);
        bean2.setName("送达");
        mchildList.add(bean2);

        getgoodsPlace();


//        et_project_carno.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                XLog.d("s.length=" + s.length()+"  s="+s);
//                if (MyUtils.checkNubmer(s.toString()))
//                    getcarinfo(s.toString());
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    private String sendCargoUnitName;
    private String receivingDepartmentName;
    private String receiveCargoSite;
    private String forwardingSiteName;

    private String sendGoodsId;
    private String receiptGooddsId;
    private int currentposition;

    private int projectType;
    private int transportType;

    @OnClick({R.id.tv_project_code, R.id.tv_project_stepSelectCode, R.id.tv_project_carno, R.id.et_project_containerNumber1, R.id.et_project_containerNumber2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_project_code:
                if (mList.size() == 0) {
                    getgoodsPlace();
                }
                if (mChooseDialogYard == null) {
                    mChooseDialogYard = new BottomProjectChooseDialog(context, mList);
                    mChooseDialogYard.setOnselectListener(position -> {
                                projectId = String.valueOf(mList.get(position).getId());
                                projectCode = mList.get(position).getProjectCode();
                                tv_project_code.setText(projectCode);
                                currentposition = position;
                                /** 联运模式
                                 0 汽运
                                 1 接取
                                 2 送达
                                 3 火运
                                 4 接取+火运
                                 5 火运+送达
                                 6 联运
                                 7 接取+送达*/
                                canselect = false;
                                carCanSelect = true;
                                transportType = mList.get(position).getTransportType();

                                sendCargoUnitName = mList.get(position).getSendCargoUnitName();
                                receivingDepartmentName = mList.get(position).getReceivingDepartmentName();
                                receiveCargoSite = mList.get(position).getReceiveCargoSite();
                                forwardingSiteName = mList.get(position).getForwardingSiteName();
//                                Log.e(TAG, "onViewClicked: "+sendCargoUnitName );
//                                Log.e(TAG, "onViewClicked: "+receivingDepartmentName );
//                                Log.e(TAG, "onViewClicked: "+receiveCargoSite );
//                                Log.e(TAG, "onViewClicked: "+forwardingSiteName );
                                switch (transportType) {
                                    case 0:
                                        tv_project_stepSelectCode.setText("汽运");
                                        setMode1();
                                        sendGoodsId = mList.get(position).getSendCargoUnitId();
                                        receiptGooddsId = mList.get(position).getReceivingDepartmentId();
                                        setGoodsMessage(sendCargoUnitName, receivingDepartmentName);
                                        break;
                                    case 1:
                                        tv_project_stepSelectCode.setText("接取");
                                        setMode2();
                                        sendGoodsId = mList.get(position).getSendCargoUnitId();
                                        receiptGooddsId = mList.get(position).getReceiveCargoSiteId();
                                        setGoodsMessage(sendCargoUnitName, mList.get(position).getReceiveCargoSite());
                                        break;
                                    case 2:
                                        tv_project_stepSelectCode.setText("送达");
                                        setMode3();
                                        sendGoodsId = mList.get(position).getForwardingSiteId();
                                        receiptGooddsId = mList.get(position).getReceivingDepartmentId();
                                        setGoodsMessage(mList.get(position).getForwardingSiteName(), receivingDepartmentName);
                                        break;
                                    case 3:
                                        tv_project_stepSelectCode.setText("火运");
                                        break;
                                    case 4:
                                        tv_project_stepSelectCode.setText("接取");
                                        setMode2();
                                        sendGoodsId = mList.get(position).getSendCargoUnitId();
                                        receiptGooddsId = mList.get(position).getReceiveCargoSiteId();
                                        setGoodsMessage(sendCargoUnitName, mList.get(position).getBeginSiteName());
                                        break;
                                    case 5:
                                        tv_project_stepSelectCode.setText("送达");
                                        setMode3();
                                        sendGoodsId = mList.get(position).getForwardingSiteId();
                                        receiptGooddsId = mList.get(position).getReceivingDepartmentId();
                                        setGoodsMessage(mList.get(position).getEndSiteName(), receivingDepartmentName);
//                                        canselect = true;
                                        break;
                                    case 6:
                                        tv_project_stepSelectCode.setText("");
                                        canselect = true;
                                        break;
                                    case 7:
                                        tv_project_stepSelectCode.setText("");
                                        canselect = true;
                                        break;
                                    default:
                                        break;
                                }
                                if (canselect) {
                                    tv_project_stepSelectCode.setText("");
                                } else {
                                    stepSelectCode = tv_project_stepSelectCode.getText().toString();
                                }

                                /** 项目类型
                                 0 集装箱
                                 1 散装*/
                                projectType = mList.get(position).getProjectType();
                                switch (projectType) {
                                    case 0:
                                        if (transportType == 0) {  //汽运没有集装箱，特例
                                            orderType = NEW_ORDER_SAND;
                                            tv_project_type.setText("散装");
                                            jizxLayout.setVisibility(View.GONE);
                                        } else {
                                            orderType = NEW_ORDER_JIZX;
                                            tv_project_type.setText("集装箱");
                                            jizxLayout.setVisibility(View.VISIBLE);
                                        }
                                        break;
                                    case 1:
                                        orderType = NEW_ORDER_SAND;
                                        tv_project_type.setText("散装");
                                        jizxLayout.setVisibility(View.GONE);
                                        break;
                                    default:
                                        break;
                                }

                                branchGroupName = mList.get(position).getBranchGroupName();
                                tv_project_branchGroupName.setText(branchGroupName);
//                                tv_project_sendpeople.setText(mList.get(position).getSendCargoCompanyName());
//                                tv_project_acceptpeople.setText(mList.get(position).getReceiveCargoCompanyName());
                                /*0 吨1 位*/
                                int valuationUnitName = mList.get(position).getValuationUnitName();

                                switch (valuationUnitName) {
                                    case 0:
                                        tv_project_gettype.setText("吨");
                                        break;
                                    case 1:
                                        tv_project_gettype.setText("件");
                                        break;
                                    default:
                                        break;
                                }

                                arrivePlaceAddress = mList.get(position).getArrivePlaceAddress();
                                cargoName = mList.get(position).getCargoName();

                            }
                    );
                }
                mChooseDialogYard.show();
                break;
            case R.id.tv_project_stepSelectCode:
                if (!canselect) {
                    return;
                }
                if (mChooseDialog == null) {
                    mChooseDialog = new BottomListChooseDialog(context, mchildList);
                    mChooseDialog.setOnselectListener(position -> {
                                clearCarMessage();
                                tv_project_stepSelectCode.setText(mchildList.get(position).getName());
                                stepSelectCode = tv_project_stepSelectCode.getText().toString();
                                if (stepSelectCode.equals("接取")) {
                                    setMode2();
                                    sendGoodsId = mList.get(currentposition).getSendCargoUnitId();
                                    receiptGooddsId = mList.get(currentposition).getReceiveCargoSiteId();
                                    Log.e(TAG, "onViewClicked: " +mList.get(currentposition).getBeginSiteName());
                                    setGoodsMessage(sendCargoUnitName, mList.get(currentposition).getBeginSiteName());
                                } else {
                                    setMode3();
                                    sendGoodsId = mList.get(currentposition).getForwardingSiteId();
                                    receiptGooddsId = mList.get(currentposition).getReceivingDepartmentId();
                                    setGoodsMessage(mList.get(currentposition).getEndSiteName(), receivingDepartmentName);
                                }
                            }
                    );

                }
                mChooseDialog.show();
                break;

            case R.id.tv_project_carno:
                if (!carCanSelect) {
                    return;
                }
                getcarinfo();
                break;

            case R.id.et_project_containerNumber1:
                searchFragment = SearchFragment.newInstance();
                searchFragment.setOnTextWatcherListener(s -> {
                    loadContainerNumberData(s);
                });
                searchFragment.setOnSearchClickListener(key -> {
                    if (searchList.contains(key)) {
                        et_project_containerNumber1.setText(key);
                    } else {
                        IToast.showShort("请选择存在的集装箱");
                    }
                });
                searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
                loadContainerNumberData("");
                break;
            case R.id.et_project_containerNumber2:
                searchFragment = SearchFragment.newInstance();
                searchFragment.setOnTextWatcherListener(s -> {
                    loadContainerNumberData(s);
                });
                searchFragment.setOnSearchClickListener(key -> {
                    if (searchList.contains(key)) {
                        et_project_containerNumber2.setText(key);
                    } else {
                        IToast.showShort("请选择存在的集装箱");
                    }
                });
                searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
                loadContainerNumberData("");
                break;

            default:
                break;
        }
    }

    private void setMode1() {
        mTvProjectSendGoods.setText("发货单位:");
        mTvProjectArriveGoods.setText("收货单位:");
    }

    private void setMode2() {
        mTvProjectSendGoods.setText("发货单位:");
        mTvProjectArriveGoods.setText("收货站点:");
    }

    private void setMode3() {
        mTvProjectSendGoods.setText("发货站点:");
        mTvProjectArriveGoods.setText("收货单位:");
    }

    private void setGoodsMessage(String sendAddress, String receiveAddress) {
        tv_project_sendpeople.setText(sendAddress);
        tv_project_acceptpeople.setText(receiveAddress);
    }

    private void clearCarMessage() {
        carType = "";
        tv_project_cartype.setText("");
        driverName = "";
        tv_project_cardriver.setText("");
        driverPhone = "";
        deductionPrice = "";
        carrierVehicleName = "";
        carPlateNumber = "";
        tv_project_carno.setText("");
        shortBargeCost = "";
        tv_project_shortBargeCost.setText("");
    }

    private void CheckStepSelectCode() {
        //0 接取1 送达2 汽运
        if (stepSelectCode.equals("接取")) {
            stepSelectCodeInt = "0";
        } else if (stepSelectCode.equals("送达")) {
            stepSelectCodeInt = "1";
        } else if (stepSelectCode.equals("汽运")) {
            stepSelectCodeInt = "2";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {

            submitData(data.getStringExtra("takeCargoPlaceId"), data.getStringExtra("takeCargoSiteId"));
        }
    }

    /**
     * 新建运单提交 *请求接口*
     */
    private void submitData(String takeCargoPlaceId, String takeCargoSiteId) {
        if (TextUtils.isEmpty(tv_project_code.getText().toString())) {
            IToast.showShort("请先选择项目");
            return;
        }
        if (TextUtils.isEmpty(carPlateNumber)) {
            IToast.showShort("请输入承运车辆");
            return;
        }
        if (TextUtils.isEmpty(carPlateNumber)) {
            IToast.showShort("请输入承运车辆");
            return;
        }
//        shortBargeCost = et_project_shortBargeCost.getText().toString();
        subsidy = et_project_subsidy.getText().toString();
        if (orderType == NEW_ORDER_JIZX) {
            containerNumber1 = et_project_containerNumber1.getText().toString();
            containerNumber2 = et_project_containerNumber2.getText().toString();
        }

        CheckStepSelectCode();
        Observable observable = HttpManager.getInstance().getOrderService()
                .newMotorOrder(ApiRetrofit.getInstance().newMotorOrder(transportType,projectId, projectCode,
                        stepSelectCodeInt, branchGroupName, arrivePlaceAddress, cargoName,
                        carTeamId, carPlateNumber, carrierVehicleId, carrierVehicleName, carType,
                        driverName, driverPhone, shortBargeCost, deductionPrice, subsidy,
                        containerNumber1, containerNumber2, String.valueOf(orderType),
                        takeCargoPlaceId, takeCargoSiteId)
                );

        HttpObserver observer = new HttpObserver<RequestResults>(this,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("新增运单成功");
                    finish();

                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 获取项目列表信息
     */
    private void getgoodsPlace() {
        Observable<RequestResultsList<ProjectBean>> observable = HttpManager.getInstance().getOrderService()
                .getProjectList(ApiRetrofit.getInstance().getProjectList("1"));

        HttpObserver<RequestResultsList<ProjectBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    mList = data.getObj();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 获取所有车辆信息
     */
    private void getcarinfo() {
        if (TextUtils.isEmpty(stepSelectCode)) {
            IToast.showShort("请先选择阶段");
            return;
        }
        CheckStepSelectCode();
        Observable<RequestResultsList<DriverInfoBean>> observable = HttpManager.getInstance().getOrderService()
                .getListCarinfo(ApiRetrofit.getInstance().getListCarinfo(projectId, stepSelectCodeInt));

        HttpObserver<RequestResultsList<DriverInfoBean>> observer = new HttpObserver<>(context, DialogUtil.createLoading(AddNewOrderActivity.this),
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    List<DriverInfoBean> driverInfoBeanList = data.getObj();
                    if (mChildCarList == null) {
                        mChildCarList = new ArrayList<>();
                    }
                    for (int i = 0; i < driverInfoBeanList.size(); i++) {
                        LocationPopBean bean = new LocationPopBean();
                        bean.setName(driverInfoBeanList.get(i).getPlateNumber());
                        mChildCarList.add(bean);
                    }
                    if (mChooseCarDialog == null) {
                        mChooseCarDialog = new BottomListChooseDialog(context, mChildCarList);
                        mChooseCarDialog.setOnselectListener(position -> {
                                    if (mChildCarList.isEmpty()) {
                                        return;
                                    }
                                    DriverInfoBean driverInfo = driverInfoBeanList.get(position);
                                    carrierVehicleId = String.valueOf(driverInfo.getDriverId());
                                    carTeamId = driverInfo.getCarTeamId();
                                    carType = driverInfo.getMotorcycleType();
                                    tv_project_cartype.setText(carType);
                                    driverName = driverInfo.getDriverName();
                                    tv_project_cardriver.setText(driverName);
                                    driverPhone = driverInfo.getDriverPhone();
                                    deductionPrice = driverInfo.getDeductionPrice();
                                    carrierVehicleName = driverInfo.getDriverName();
                                    carPlateNumber = driverInfo.getPlateNumber();
                                    tv_project_carno.setText(carPlateNumber);
                                    shortBargeCost = driverInfo.getTransportPrice();
                                    tv_project_shortBargeCost.setText(shortBargeCost);
                                }
                        );

                    }
                    mChooseCarDialog.show();

                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    /**
     * 获取可以使用的集装箱信息
     */
    private void loadContainerNumberData(String text) {
        CheckStepSelectCode();
        Observable<RequestResultsList<ContainerBean>> observable = HttpManager.getInstance().getOrderService()
                .getOrderContainerNum(ApiRetrofit.getInstance().getOrderContainerNum(text,projectId,stepSelectCodeInt, receiptGooddsId, sendGoodsId));

        HttpObserver<RequestResultsList<ContainerBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    mContainerList = data.getObj();
                    searchList.clear();
                    for (int i = 0; i < mContainerList.size(); i++) {
                        searchList.add(mContainerList.get(i).getText());
                    }
                    searchFragment.setData(searchList);
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 根据车牌号获取对应信息
     */
    private void getcarinfo(String mplateNumber) {
//        //测试数据   皖H78392
//        mplateNumber = "皖H78394";
        carPlateNumber = mplateNumber;
        Observable<RequestResults<DriverInfoBean>> observable = HttpManager.getInstance().getOrderService()
                .getCarinfo(ApiRetrofit.getInstance().getCarinfo(carPlateNumber));

        HttpObserver<RequestResults<DriverInfoBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    DriverInfoBean driverInfo = data.getObj();

                    carrierVehicleId = String.valueOf(driverInfo.getDriverId());
                    carTeamId = driverInfo.getCarTeamId();
                    carType = driverInfo.getMotorcycleType();
                    tv_project_cartype.setText(carType);
                    driverName = driverInfo.getDriverName();
                    tv_project_cardriver.setText(driverName);
                    driverPhone = driverInfo.getDriverPhone();
                    deductionPrice = driverInfo.getDeductionPrice();
                    carrierVehicleName = driverInfo.getDriverName();

                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 判断订单能否新建
     */
    private void isPass() {
        CheckStepSelectCode();
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .checkProject(ApiRetrofit.getInstance().checkProject(projectId, stepSelectCodeInt));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context, DialogUtil.createLoading(context),
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    startActivityForResult(new Intent(context, NewOrderActivity.class).putExtra("stepSelectCode", stepSelectCodeInt).
                            putExtra("sendCompanyId", sendGoodsId), 101);
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

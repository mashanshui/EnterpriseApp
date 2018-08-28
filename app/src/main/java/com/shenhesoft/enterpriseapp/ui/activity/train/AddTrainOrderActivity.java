package com.shenhesoft.enterpriseapp.ui.activity.train;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.DriverInfoBean;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.bean.TrainProjectBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.confirmarrive.GuideAllocationActivity;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;
import com.shenhesoft.enterpriseapp.widget.BottomProjectChooseDialog;
import com.shenhesoft.enterpriseapp.widget.BottomProjectChooseDialog2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.log.XLog;
import io.reactivex.Observable;

/**
 * 作者：Tornado
 * 创作日期：2017/12/26.
 * 描述：火运干线》添加新运单
 */

public class AddTrainOrderActivity extends BaseTitleActivity {

    public final static int NEW_ORDER_JIZX = 1; //集装箱运单
    public final static int NEW_ORDER_SAND = 2; //散堆运单

    private String projectId; //项目id
    private String projectCode;// 项目编号
    private String stepSelectCode; // 阶段选择
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
    @BindView(R.id.tv_project_stepSelectCode)//货物品名
            TextView tv_project_stepSelectCode;
    @BindView(R.id.tv_project_cargotype)//货物规格
            TextView tv_project_cargotype;
    @BindView(R.id.tv_project_startstation)//始发站点
            TextView tv_project_startstation;
    @BindView(R.id.tv_project_arrivestation)//到达站点
            TextView tv_project_arrivestation;
    @BindView(R.id.tv_project_sendpeople)
    TextView tv_project_sendpeople;
    @BindView(R.id.tv_project_acceptpeople)
    TextView tv_project_acceptpeople;
    @BindView(R.id.et_project_carno)//请车数
            EditText et_project_carno;
    @BindView(R.id.tv_project_cartype)//请车类型
            TextView tv_project_cartype;
    @BindView(R.id.et_project_cardriver) //预计载重
            EditText et_project_cardriver;
    //    @BindView(R.id.tv_project_gettype)
//    TextView tv_project_gettype;
    @BindView(R.id.et_project_site)//货场
            TextView tv_project_site;
    @BindView(R.id.et_project_location)//货位
            TextView tv_project_location;
    @BindView(R.id.et_project_less)//库存
            TextView tv_project_less;
    @BindView(R.id.tv_project_account)//预付款账户
            TextView tv_project_account;
    @BindView(R.id.tv_project_balance)//预付款账户余额
            TextView tv_project_balance;
    @BindView(R.id.tv_project_money)//预计支出金额
            TextView tv_project_money;

    private String freightSum; //每辆车价格
    private String receiveAccountId;  //预付款账户id
    private String receiveAccountName; //预付款账户name
    private String depositAmount;  //预付款账户余额


    private BottomProjectChooseDialog2 mChooseDialogYard;
    private BottomListChooseDialog mChooseDialog;
    private List<TrainProjectBean> mList;
    private List<LocationPopBean> mchildList;

    private String pleaseCarTypeId;

    private int orderType;

    //集装箱信息Layout
    @BindView(R.id.new_order_jizx_layout)
    LinearLayout jizxLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_train_order;
    }

    @Override
    protected void initTitle() {
        setTitle("新建运单");
        setRightText("完成 ", v -> {
//            startActivityForResult(new Intent(context, GuideAllocationActivity.class), 101);

            submitData(et_project_carno.getText().toString(), pleaseCarTypeId
                    , et_project_cardriver.getText().toString(), tv_project_money.getText().toString());
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mchildList = new ArrayList<>();
        mList = new ArrayList<>();
        getTrainTypeList();
        getGoodsPlace();

        et_project_cardriver.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(freightSum) && isValidInt(freightSum)) {

                    if (!TextUtils.isEmpty(s)) {
                        try {
                            tv_project_money.setText(Integer.valueOf(s.toString()) * Integer.valueOf(freightSum) + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                            IToast.showShort("请不要捣乱哦！");
                        }

                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.tv_project_code, R.id.tv_project_cartype})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_project_code: //项目列表选择
                if (mList.size() == 0) {
                    getGoodsPlace();
                    return;
                }
                if (mChooseDialogYard == null) {
                    mChooseDialogYard = new BottomProjectChooseDialog2(context, mList);
                    mChooseDialogYard.setOnselectListener(position -> {

                                bindText(mList.get(position));
                            }
                    );
                }
                mChooseDialogYard.show();
                break;
            case R.id.tv_project_cartype:
                if (mchildList.size() == 0) {
                    getTrainTypeList();
                    return;
                }
                if (mChooseDialog == null) {
                    mChooseDialog = new BottomListChooseDialog(context, mchildList);
                    mChooseDialog.setOnselectListener(position -> {
                                pleaseCarTypeId = String.valueOf(mchildList.get(position).getId());
                                tv_project_cartype.setText(mchildList.get(position).getName());
                            }
                    );

                }
                mChooseDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /***
     * 绑定信息
     */
    private void bindText(TrainProjectBean bean) {
        projectId = String.valueOf(bean.getId());
        projectCode = bean.getProjectCode();
        tv_project_code.setText(projectCode);
        tv_project_stepSelectCode.setText(bean.getCargoName());
        tv_project_cargotype.setText(bean.getCargoSpecifications());
        tv_project_startstation.setText(bean.getBeginSiteName());
        tv_project_arrivestation.setText(bean.getEndSiteName());
        tv_project_account.setText(bean.getAdvanceCharge().getReceiveAccountName());
        tv_project_balance.setText(bean.getAdvanceCharge().getDepositAmount());
        branchGroupName = bean.getBranchGroupName();
        tv_project_branchGroupName.setText(branchGroupName);
        tv_project_sendpeople.setText(bean.getSendCargoCompanyName());
        tv_project_acceptpeople.setText(bean.getReceiveCargoCompanyName());
        arrivePlaceAddress = bean.getArrivePlaceAddress();
        cargoName = bean.getCargoName();

        tv_project_site.setText(bean.getFreightYardName());
        tv_project_location.setText(bean.getCargoLocationName());
        tv_project_less.setText(bean.getCurrentQty());
        freightSum = bean.getFreightSum();
        receiveAccountId = bean.getAdvanceCharge().getReceiveAccountId();
        receiveAccountName = bean.getAdvanceCharge().getReceiveAccountName();
        depositAmount = bean.getAdvanceCharge().getDepositAmount();

//        /** 项目类型
//         0 集装箱
//         1 散装*/
//        int projectType = bean.getProjectType();
//        switch (projectType) {
//            case 0:
//                orderType = NEW_ORDER_JIZX;
//                tv_project_type.setText("集装箱");
//                jizxLayout.setVisibility(View.VISIBLE);
//                break;
//            case 1:
//                orderType = NEW_ORDER_SAND;
//                tv_project_type.setText("散装");
//                jizxLayout.setVisibility(View.GONE);
//                break;
//            default:
//                break;
//        }

        //修改------集装箱和散装都有库存信息
        int projectType = bean.getProjectType();
        switch (projectType) {
            case 0:
                orderType = NEW_ORDER_JIZX;
                tv_project_type.setText("集装箱");
                jizxLayout.setVisibility(View.VISIBLE);
                break;
            case 1:
                orderType = NEW_ORDER_SAND;
                tv_project_type.setText("散装");
                jizxLayout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    /**
     * 新建运单提交 *请求接口*
     */
    private void submitData(String pleaseTrainNum,
                            String pleaseCarTypeId, String estimateWeight, String estimateCost) {

        if (TextUtils.isEmpty(tv_project_code.getText().toString())) {
            IToast.showShort("请先选择项目");
            return;
        }
        if (TextUtils.isEmpty(receiveAccountId)) {
            IToast.showShort("该项目未在中心站点账户存入");
            return;
        }
        double money1 = Double.valueOf(tv_project_money.getText().toString());
        double money2 = Double.valueOf(depositAmount);
        if (money1 > money2) {
            IToast.showShort("支出金额不能大于余额");
            return;
        }

        Observable observable = HttpManager.getInstance().getOrderService()
                .saveTrainOrder(ApiRetrofit.getInstance().newTrainOrder(projectId
                        , pleaseTrainNum, pleaseCarTypeId, estimateWeight, estimateCost,receiveAccountId,receiveAccountName,depositAmount));

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
    private void getGoodsPlace() {
        Observable<RequestResultsList<TrainProjectBean>> observable = HttpManager.getInstance().getOrderService()
                .newTrainOrder(ApiRetrofit.getInstance().getProjectList("1"));

        HttpObserver<RequestResultsList<TrainProjectBean>> observer = new HttpObserver<>(context,
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
     * 获取车型信息
     */
    private void getTrainTypeList() {
        Observable<RequestResultsList<LocationPopBean>> observable = HttpManager.getInstance().getUserService()
                .getTrainTypeList(ApiRetrofit.getInstance().getTrainTypelist());

        HttpObserver<RequestResultsList<LocationPopBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    mchildList = data.getObj();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    public static boolean isValidInt(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}

package com.shenhesoft.enterpriseapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.MyApplication;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseFragment;
import com.shenhesoft.enterpriseapp.bean.BusinessinfoBean;
import com.shenhesoft.enterpriseapp.bean.CustomerCheckBean;
import com.shenhesoft.enterpriseapp.bean.DriverCheckBean;
import com.shenhesoft.enterpriseapp.bean.FeePayBean;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.auditing.AuditingActivity;
import com.shenhesoft.enterpriseapp.utils.AppUtil;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.PieChartView;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;

/**
 * @author zmx
 * @date 2018/2/2
 * @desc 业务模块
 */

public class BusinessFragment extends BaseFragment {
    @BindView(R.id.my_cricleview)
    PieChartView circleMotor;

    @BindView(R.id.nums_waitdispatch)
    TextView nums_waitdispatch;

    @BindView(R.id.nums_waitsend)
    TextView nums_waitsend;

    @BindView(R.id.nums_onway)
    TextView nums_onway;

    @BindView(R.id.nums_guide)
    TextView nums_guide;

    @BindView(R.id.nums_waitcallback)
    TextView nums_waitcallback;

    @BindView(R.id.nums_waitpay)
    TextView nums_waitpay;

    @BindView(R.id.tv_wait_verify)
    TextView wait_verify;

    @BindView(R.id.nums_train_requestcar)
    TextView nums_train_requestcar;

    @BindView(R.id.nums_train_onload)
    TextView nums_train_onload;

    @BindView(R.id.nums_train_waitsend)
    TextView nums_train_waitsend;

    @BindView(R.id.nums_train_onway)
    TextView nums_train_onway;

    @BindView(R.id.nums_train_waitunload)
    TextView nums_train_waitunload;

    @BindView(R.id.nums_train_waitcall)
    TextView nums_train_waitcall;

    private int waitVerifyCount = 0;

    @BindView(R.id.my_cricleview_train)
    PieChartView circleTrain;
    int[] nums = {0, 0, 0, 0, 0, 0};
    int[] numtrain = {0, 0, 0, 0, 0, 0};
    int[] colors = {R.color.business_1, R.color.business_2, R.color.business_3,
            R.color.business_4, R.color.business_5, R.color.business_6};

    private static int CHECK;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mybusiness;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        CHECK = SharedPref.getInstance(context).getInt(AppConstant.CHECK, 0);
        getBusinessinfo();
        getCount1();
    }


    /**
     * 获取业务信息
     */
    private void getBusinessinfo() {
        String sysOrgCode = SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.SysOrgCode, "");
        Observable<RequestResults<BusinessinfoBean>> observable = HttpManager.getInstance().getOrderService()
                .getBusinessinfo("curMonth", AppUtil.getUserinfo().getId() + "", sysOrgCode);

        HttpObserver<RequestResults<BusinessinfoBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 200) {
                        IToast.showShort(data.getMsg());
                        return;
                    }

                    for (int i = 0; i < data.getObj().getListBulkOrderStatus().size(); i++) {
                        nums[i] = data.getObj().getListBulkOrderStatus().get(i).getValue();
                    }
                    nums_waitdispatch.setText(nums[0] + "");
                    nums_waitsend.setText(nums[1] + "");
                    nums_onway.setText(nums[2] + "");
                    nums_guide.setText(nums[3] + "");
                    nums_waitcallback.setText(nums[4] + "");
                    nums_waitpay.setText(nums[5] + "");

                    for (int i = 0; i < data.getObj().getListTrainOrderStatus().size(); i++) {
                        numtrain[i] = data.getObj().getListTrainOrderStatus().get(i).getValue();
                    }
                    nums_train_requestcar.setText(numtrain[0] + "");
                    nums_train_onload.setText(numtrain[1] + "");
                    nums_train_waitsend.setText(numtrain[2] + "");
                    nums_train_onway.setText(numtrain[3] + "");
                    nums_train_waitunload.setText(numtrain[4] + "");
                    nums_train_waitcall.setText(numtrain[5] + "");

                    circleMotor.setPieChartData(nums, colors);
                    circleTrain.setPieChartData(numtrain, colors);
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    @OnClick({R.id.btn_tocheck})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_tocheck:
                if (CHECK == 1) {
                    Router.newIntent(context).to(AuditingActivity.class).launch();
                } else {
                    IToast.showShort("您没有权限操作此项");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 运费支出列表
     */
    private void getCount1() {
        JSONObject condition = new JSONObject();
        try {
            condition.put("userId", AppUtil.getUserinfo().getId() + "");
            condition.put("queryFrom", "app");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("start", "0");
        params.put("length", "1");
        params.put("condition", condition.toString());
        params.put("sysOrgCode", SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.SysOrgCode, ""));
        XLog.d("", params.toString());

        Observable<RequestResults<RequestResultsList<FeePayBean>>> observable = HttpManager.getInstance().getOrderService()
                .getFeepayinfo(params);

        HttpObserver<RequestResults<RequestResultsList<FeePayBean>>> observer = new HttpObserver<>(context,
                data -> {
                    if (!(data.getState() >= 200 && data.getState() < 300)) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    waitVerifyCount = Integer.parseInt(data.getObj().getUndefine());
                    getCount2();
                });
        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 运费支出列表
     */
    private void getCount2() {
        JSONObject condition = new JSONObject();
        try {
            condition.put("userId", AppUtil.getUserinfo().getId() + "");
            condition.put("queryFrom", "app");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("start", "0");
        params.put("length", "1");
        params.put("condition", condition.toString());
        params.put("sysOrgCode", SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.SysOrgCode, ""));
        XLog.d("", params.toString());

        Observable<RequestResults<RequestResultsList<CustomerCheckBean>>> observable = HttpManager.getInstance().getOrderService()
                .getCustomerinfo(params);

        HttpObserver<RequestResults<RequestResultsList<CustomerCheckBean>>> observer = new HttpObserver<>(context,
                data -> {
                    if (!(data.getState() >= 200 && data.getState() < 300)) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    waitVerifyCount = waitVerifyCount + Integer.parseInt(data.getObj().getUndefine());
                    getCount3();
                });
        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 司机对账列表
     */
    private void getCount3() {
        JSONObject condition = new JSONObject();
        try {
            condition.put("userId", AppUtil.getUserinfo().getId() + "");
            condition.put("queryFrom", "app");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("start", "0");
        params.put("length", "1");
        params.put("condition", condition.toString());
        params.put("sysOrgCode", SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.SysOrgCode, ""));
        XLog.d("" + params.toString());

        Observable<RequestResults<RequestResultsList<DriverCheckBean>>> observable = HttpManager.getInstance().getOrderService()
                .getDrivecheckinfo(params);

        HttpObserver<RequestResults<RequestResultsList<DriverCheckBean>>> observer = new HttpObserver<>(context,
                data -> {
                    if (!(data.getState() >= 200 && data.getState() < 300)) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    waitVerifyCount = waitVerifyCount + Integer.parseInt(data.getObj().getUndefine());
                    wait_verify.setText(String.valueOf(waitVerifyCount));
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

}

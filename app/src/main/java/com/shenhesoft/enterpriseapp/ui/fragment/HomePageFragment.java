package com.shenhesoft.enterpriseapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseEvent;
import com.shenhesoft.enterpriseapp.base.BaseFragment;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.bean.TaskDetailsBean;
import com.shenhesoft.enterpriseapp.bean.UserinfoBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.UnusualWaybillActivity;
import com.shenhesoft.enterpriseapp.ui.activity.confirmarrive.ConfirmArriveActivity;
import com.shenhesoft.enterpriseapp.ui.activity.feecheck.WaitConfirmChargingActivity;
import com.shenhesoft.enterpriseapp.ui.activity.motor.MotorActivity;
import com.shenhesoft.enterpriseapp.ui.activity.task.AllotTaskActivity;
import com.shenhesoft.enterpriseapp.ui.activity.train.TrainActivity;
import com.shenhesoft.enterpriseapp.ui.activity.waitdispatch.WaitDispatchActivity;
import com.shenhesoft.enterpriseapp.utils.AppUtil;
import com.shenhesoft.enterpriseapp.utils.IToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;

/**
 * @author 张继淮
 * @date 2017/9/29
 * @description 主页fragment
 */

public class HomePageFragment extends BaseFragment {
    private static final String TAG = "HomePageFragment";

    private static int TASK_DISTRIBUTION;
    private static int WAIT_DISPATCH;
    private static int CONFIRM_ARRIVE;
    private static int CONFIRM_CHARGING;
    private static int UNUSUAL;
    private static int WAYBILL;
    private static int TRAIN;
    //    @BindView(R.id.waveView)
//    WaveView waveView;
    //    private WaveViewHelper waveViewHelper;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.statusBar)
    View mStatusBar;
    @BindView(R.id.ll_confirm_arrive)
    LinearLayout mLlConfirmArrive;
    @BindView(R.id.ll_confirm_charging)
    LinearLayout mLlConfirmCharging;
    @BindView(R.id.ll_wait_dispatch)
    LinearLayout mLlWaitDispatch;
    @BindView(R.id.ll_waybill)
    LinearLayout mLlWaybill;
    @BindView(R.id.ll_unusual)
    LinearLayout mLlUnusual;
    @BindView(R.id.et_home_search)
    EditText mEtHomeSearch;
    @BindView(R.id.iv_right1)
    ImageView imgEmail;
    @BindView(R.id.ll_wait_task)
    LinearLayout mLlWaitTask;

    Unbinder unbinder;
    @BindView(R.id.tv_day_completeOrder)
    TextView tvDayCompleteOrder;
    @BindView(R.id.tv_day_transitOrder)
    TextView tvDayTransitOrder;
    @BindView(R.id.tv_month_order)
    TextView tvMonthOrder;

    private BGABadgeLinearLayout mBGAWaitTask;
    private BGABadgeLinearLayout mBGAWaitDispatch;
    private BGABadgeLinearLayout mBGAConfirmArrive;
    private BGABadgeLinearLayout mBGAConfirmCharging;

//    BadgeView waitDispatchBadge;
//    BadgeView waitTaskBadge;
//    BadgeView confirmArriveBadge;
//    BadgeView confirmChargingBadge;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        loadOrderCount(1);
        loadOrderCount(2);
        loadOrderCount(3);

        TASK_DISTRIBUTION = SharedPref.getInstance(context).getInt(AppConstant.TASK_DISTRIBUTION, 0);
        WAIT_DISPATCH=SharedPref.getInstance(context).getInt(AppConstant.WAIT_DISPATCH, 0);
        CONFIRM_ARRIVE=SharedPref.getInstance(context).getInt(AppConstant.CONFIRM_ARRIVE, 0);
        CONFIRM_CHARGING=SharedPref.getInstance(context).getInt(AppConstant.CONFIRM_CHARGING, 0);
        UNUSUAL=SharedPref.getInstance(context).getInt(AppConstant.UNUSUAL, 0);
        WAYBILL=SharedPref.getInstance(context).getInt(AppConstant.WAYBILL, 0);
        TRAIN=SharedPref.getInstance(context).getInt(AppConstant.TRAIN, 0);

        //图标右上角显示未读消息数量，舍弃BadgeView使用BGABadgeLinearLayout
//        waitDispatchBadge = new BadgeView(getActivity());
//        waitDispatchBadge.setTargetView(mLlWaitDispatch);
//        badge.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
//        badge.setBadgeCount(42);

//        waitTaskBadge = new BadgeView(getActivity());
//        waitTaskBadge.setTargetView(mLlWaitTask);
//
//        confirmArriveBadge = new BadgeView(getActivity());
//        confirmArriveBadge.setTargetView(mLlConfirmArrive);
//
//        confirmChargingBadge = new BadgeView(getActivity());
//        confirmChargingBadge.setTargetView(mLlConfirmCharging);

//        waveViewHelper = new WaveViewHelper(waveView);
//        waveViewHelper.start();
    }


    @Override
    public boolean bindEventBus() {
        return true;
    }

    @Override
    protected void getBusEvent(BaseEvent msg) {
        switch (msg.tag) {
            case AppConstant.LOGIN:
                XLog.d("HomePageFragment", "登陆成功");
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWaitTaskListData();
        loadWaitDispatchListData();
        loadConfirmArriveListData();
        loadConfirmChargingListData();
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Optional
    @OnClick({R.id.iv_left, R.id.ll_wait_dispatch, R.id.ll_confirm_charging, R.id.ll_confirm_arrive,
            R.id.ll_waybill, R.id.ll_unusual, R.id.ll_train, R.id.home_root, R.id.iv_right1, R.id.ll_wait_task})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                postEvent(new BaseEvent(AppConstant.SHOW_MENU));
                break;
            case R.id.ll_wait_dispatch:
                if (WAIT_DISPATCH == 1) {
                    Router.newIntent(context).to(WaitDispatchActivity.class).launch();
                } else {
                  showToast();
                }
                break;
            case R.id.ll_confirm_charging:
                if (CONFIRM_CHARGING == 1) {
                    Router.newIntent(context).to(WaitConfirmChargingActivity.class).launch();
                } else {
                    showToast();
                }
                break;
            case R.id.ll_confirm_arrive:
                if (CONFIRM_ARRIVE == 1) {
                    Router.newIntent(context).to(ConfirmArriveActivity.class).launch();
                } else {
                    showToast();
                }
                break;
            case R.id.ll_waybill:
                if (WAYBILL == 1) {
                    Router.newIntent(context).to(MotorActivity.class).launch();
                } else {
                    showToast();
                }
                break;
            case R.id.ll_unusual:
                if (UNUSUAL == 1) {
                    Router.newIntent(context).to(UnusualWaybillActivity.class).launch();
                } else {
                    showToast();
                }
                break;
            case R.id.ll_train:
                if (TRAIN == 1) {
                    Router.newIntent(context).to(TrainActivity.class).launch();
                } else {
                    showToast();
                }
                break;
            case R.id.home_root:
                break;
            case R.id.ll_wait_task:
                if (TASK_DISTRIBUTION == 1) {
                    Router.newIntent(context).to(AllotTaskActivity.class).launch();
                } else {
                    showToast();
                }
                break;
            case R.id.iv_right1:
//                Router.newIntent(context).to(AllotTaskActivity.class).launch();
//                RongIM.getInstance().startPrivateChat(context, "Q10", "我是标题");
                break;
            default:
                break;
        }

    }

    private void showToast() {
        IToast.showShort("您没有权限操作此项");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        mBGAWaitTask = (BGABadgeLinearLayout) rootView.findViewById(R.id.BGA_wait_task);
        mBGAConfirmArrive = (BGABadgeLinearLayout) rootView.findViewById(R.id.BGA_confirm_arrive);
        mBGAWaitDispatch = (BGABadgeLinearLayout) rootView.findViewById(R.id.BGA_wait_dispatch);
        mBGAConfirmCharging = (BGABadgeLinearLayout) rootView.findViewById(R.id.BGA_confirm_charging);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 获取任务分配消息数量
     */
    private void loadWaitTaskListData() {
        Observable observable = HttpManager.getInstance().getOrderService()
                .getTaskListData(ApiRetrofit.getInstance().getTaskParams(1 + ""));

        HttpObserver observer = new HttpObserver<RequestResults<ListALLResults<TaskDetailsBean>>>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    int waitTaskCount = data.getObj().getTotalCount();
                    if (waitTaskCount > 99) {
                        mBGAWaitTask.showTextBadge("99+");
                    } else if (waitTaskCount == 0) {
                        mBGAWaitTask.hiddenBadge();
                    } else {
                        mBGAWaitTask.showTextBadge(String.valueOf(waitTaskCount));
                    }
                });
                HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 获取调度审核消息数量
     */
    private void loadWaitDispatchListData() {
        Observable<RequestResults<ListALLResults<MotorDetailsBean>>> observable = HttpManager.getInstance().getUserService()
                .getWaitDispatchList(ApiRetrofit.getInstance().waitdisptach("1"));

        HttpObserver<RequestResults<ListALLResults<MotorDetailsBean>>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }

                    int waitDispatchCount = data.getObj().getTotalCount();
                    if (waitDispatchCount > 99) {
                        mBGAWaitDispatch.showTextBadge("99+");
                    } else if (waitDispatchCount == 0) {
                        mBGAWaitDispatch.hiddenBadge();
                    } else {
                        mBGAWaitDispatch.showTextBadge(String.valueOf(waitDispatchCount));
                    }
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 获取到货确认消息数量
     */
    private void loadConfirmArriveListData() {
        Observable<RequestResults<ListALLResults<MotorDetailsBean>>> observable = HttpManager.getInstance().getUserService()
                .waitUnloading(ApiRetrofit.getInstance().waitUnloading("1", "4"));

        HttpObserver<RequestResults<ListALLResults<MotorDetailsBean>>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    int confirmArriveCount = data.getObj().getTotalCount();
                    if (confirmArriveCount > 99) {
                        mBGAConfirmArrive.showTextBadge("99+");
                    } else if (confirmArriveCount == 0) {
                        mBGAConfirmArrive.hiddenBadge();
                    } else {
                        mBGAConfirmArrive.showTextBadge(String.valueOf(confirmArriveCount));
                    }

                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    /**
     * 获取计费确认消息数量
     */
    private void loadConfirmChargingListData() {
        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .getChargeOrderCount(ApiRetrofit.getInstance().chargeOrderCount());

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    double dcount = (Double) data.getObj();
                    int ConfirmChargingCount = (int) dcount;
                    if (ConfirmChargingCount > 99) {
                        mBGAConfirmCharging.showTextBadge("99+");
                    } else if (ConfirmChargingCount == 0) {
                        mBGAConfirmCharging.hiddenBadge();
                    } else {
                        mBGAConfirmCharging.showTextBadge(String.valueOf(ConfirmChargingCount));
                    }
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 获取气泡数据
     */
    private void loadOrderCount(int type) {
        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .getOrderCount(ApiRetrofit.getInstance().getOrderCount(String.valueOf(type)));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        return;
                    }
                    double dcount = (Double) data.getObj();
                    int count = (int) dcount;
                    if (type==1) {
                        tvDayCompleteOrder.setText(String.valueOf(count));
                    } else if (type == 2) {
                        tvMonthOrder.setText(String.valueOf(count));
                    } else if (type == 3) {
                        tvDayTransitOrder.setText(String.valueOf(count));
                    }
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }
}

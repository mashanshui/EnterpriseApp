package com.shenhesoft.enterpriseapp.ui.activity.auditing;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.MyApplication;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.FeePayBean;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.utils.AppUtil;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.log.XLog;
import io.reactivex.Observable;

/**
 * @author 张继淮
 * @date 2018/2/3
 * @desc 运费支出
 */

public class FeepayActivity extends BaseTitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private static final String TAG = "FeepayActivity";
    @BindView(R.id.lv_auditing)
    ListView mListView;
    @BindView(R.id.myrefreshLayout)
    BGARefreshLayout mMyrefreshLayout;

    private FeePayAdapter mFeePayAdapter;
    private List<FeePayBean> mFeePayBeans;
    private List<FeePayBean> refreshList;
    private ConfirmDialog mConfirmDialog;
    private boolean isLoadMore = false;
    private int start = 0;
    private int length = 10;

    @Override
    public int getLayoutId() {
        return R.layout.activity_auditing_common;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mFeePayBeans = new ArrayList<>();
        mFeePayAdapter = new FeePayAdapter(context);
        mListView.setAdapter(mFeePayAdapter);
        mFeePayAdapter.setOnClicklistener((position, type) ->
        {
            if (mFeePayBeans.get(position).getFinanceStatusNodeName().equals("计费确认")) {
                ShowConfirmDialog(mFeePayBeans.get(position).getShOrderFinId(), 0);
            } else {
                ShowConfirmDialog(mFeePayBeans.get(position).getShOrderFinId(), 1);
            }
        });
        mMyrefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder meiTuanRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        mMyrefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
        getBusinessinfo();
    }

    @Override
    protected void initTitle() {
        setTitle("运费支出");
    }


    /***
     * 审核通过Dialog
     * @param shOrderFinId  主键
     * @param type  类型 0 计费确认 1 财务审核
     */
    private void ShowConfirmDialog(String shOrderFinId, int type) {
        if (mConfirmDialog == null) {
            mConfirmDialog = new ConfirmDialog(context);
            mConfirmDialog.setTitle1("确认审核通过");
            mConfirmDialog.setCancelListener(v -> mConfirmDialog.dismiss());
        }
        mConfirmDialog.setConfirmListener(v -> {
            if (type == 0) {
                checkFeego(shOrderFinId);
            } else {
                checkMoneygo(shOrderFinId);
            }
            mConfirmDialog.dismiss();
        });
        mConfirmDialog.show();
    }


    /**
     * 运费支出列表
     */
    private void getBusinessinfo() {
        JSONObject condition = new JSONObject();
        try {
            condition.put("userId", AppUtil.getUserinfo().getId() + "");
            condition.put("queryFrom", "app");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
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

                    refreshList = data.getObj().getObj();
                    /**
                     * 下拉刷新时数据是从头开始，因此需要setData
                     * 上拉加载时数据是往后叠加的，所以需要addData
                     * 如果上拉加载时没有加载到数据，pageNo页面数需要保持不变，减1
                     */
                    //上拉加载后有数据
                    if (isLoadMore && !refreshList.isEmpty()) {
                        mFeePayAdapter.addData(refreshList);
                        mFeePayBeans.addAll(refreshList);
                        isLoadMore = false;
                        mFeePayAdapter.notifyDataSetChanged();
                    }
                    //上拉加载后没有数据
                    else if (isLoadMore && refreshList.isEmpty()) {
                        start = start - length;
                        isLoadMore = false;
                    }
                    //下拉刷新后有数据
                    else if (!isLoadMore && !refreshList.isEmpty()) {
                        mFeePayAdapter.setData(refreshList);
                        if (!mFeePayBeans.isEmpty()) {
                            mFeePayBeans.clear();
                        }
                        mFeePayBeans.addAll(refreshList);
                        mFeePayAdapter.notifyDataSetChanged();
                    }
                    //下拉刷新后没有数据了
                    else if (!isLoadMore && refreshList.isEmpty()) {
                        mFeePayAdapter.setData(refreshList);
                        if (!mFeePayBeans.isEmpty()) {
                            mFeePayBeans.clear();
                        }
                        mFeePayAdapter.notifyDataSetChanged();
                    }
                });
        observer.setRefreshLayout(mMyrefreshLayout);
        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    /**
     * 计费审核通过
     */
    private void checkFeego(String shOrderFinId) {
//
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", AppUtil.getUserinfo().getId() + "");
        params.put("name", AppUtil.getUserinfo().getName());
        params.put("sysOrgCode", SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.SysOrgCode, ""));
        XLog.d("", params.toString());
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .checkFeepaygo(shOrderFinId, params);

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (!(data.getState() >= 200 && data.getState() < 300)) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("计费审核通过");
                    getBusinessinfo();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    /**
     * 财务审核通过
     */
    private void checkMoneygo(String shOrderFinId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", AppUtil.getUserinfo().getId() + "");
        params.put("name", AppUtil.getUserinfo().getName());
        params.put("sysOrgCode", SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.SysOrgCode, ""));
        XLog.d("", params.toString());
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .checkMoneygo(shOrderFinId, params);
        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (!(data.getState() >= 200 && data.getState() < 300)) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("财务审核通过");
                    getBusinessinfo();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoadMore = false;
        start = 0;
        getBusinessinfo();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        start = start + length;
        getBusinessinfo();
        return true;
    }

}

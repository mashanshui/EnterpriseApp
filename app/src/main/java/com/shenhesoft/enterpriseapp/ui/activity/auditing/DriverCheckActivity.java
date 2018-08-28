package com.shenhesoft.enterpriseapp.ui.activity.auditing;

import android.os.Bundle;
import android.widget.ListView;

import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.MyApplication;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.DriverCheckBean;
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
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.log.XLog;
import io.reactivex.Observable;

/**
 * @author 张继淮
 * @date 2018/2/3
 * @desc 司机对账
 */

public class DriverCheckActivity extends BaseTitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.lv_auditing)
    ListView mListView;
    @BindView(R.id.myrefreshLayout)
    BGARefreshLayout mMyrefreshLayout;

    private DriverCheckAdapter mFeePayAdapter;
    private List<DriverCheckBean> mFeePayBeans;
    private List<DriverCheckBean> refreshList;
    private boolean isLoadMore = false;
    private int start = 0;
    private int length = 10;

    private ConfirmDialog mConfirmDialog;

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
        mFeePayAdapter = new DriverCheckAdapter(context);
        mListView.setAdapter(mFeePayAdapter);
        mFeePayAdapter.setOnClicklistener((position, type) -> {
            ShowConfirmDialog(mFeePayBeans.get(position).getShPackId());
        });
        mMyrefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder meiTuanRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        mMyrefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
        getBusinessinfo();
    }

    @Override
    protected void initTitle() {
        setTitle("司机对账");
    }


    /***
     * 审核通过Dialog
     * @param shPackId  主键
     */
    private void ShowConfirmDialog(String shPackId) {
        if (mConfirmDialog == null) {
            mConfirmDialog = new ConfirmDialog(context);
            mConfirmDialog.setTitle1("确认审核通过");
            mConfirmDialog.setCancelListener(v -> mConfirmDialog.dismiss());
        }
        mConfirmDialog.setConfirmListener(v -> {
            checkFeego(shPackId);
            mConfirmDialog.dismiss();
        });
        mConfirmDialog.show();
    }


    /**
     * 司机对账列表
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
        XLog.d("" + params.toString());

        Observable<RequestResults<RequestResultsList<DriverCheckBean>>> observable = HttpManager.getInstance().getOrderService()
                .getDrivecheckinfo(params);

        HttpObserver<RequestResults<RequestResultsList<DriverCheckBean>>> observer = new HttpObserver<>(context,
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
     * 司机对账通过
     */
    private void checkFeego(String shPackId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", AppUtil.getUserinfo().getId() + "");
        params.put("name", AppUtil.getUserinfo().getName());
        params.put("sysOrgCode", SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.SysOrgCode, ""));
        XLog.d("",params.toString());
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .checkDriver(shPackId,params);

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (!(data.getState() >= 200 && data.getState() < 300)) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("司机对账通过");
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

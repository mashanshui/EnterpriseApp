package com.shenhesoft.enterpriseapp.ui.activity.auditing;

import android.os.Bundle;
import android.widget.ListView;

import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.MyApplication;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.CustomerCheckBean;
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
 * @desc 客户对账
 */

public class CustomerCheckActivity extends BaseTitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.lv_auditing)
    ListView mListView;
    @BindView(R.id.myrefreshLayout)
    BGARefreshLayout mMyrefreshLayout;

    private CustomerCheckAdapter mFeePayAdapter;
    private List<CustomerCheckBean> mFeePayBeans;
    private List<CustomerCheckBean> refreshList;
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

    /***
     * 审核通过Dialog
     * @param custPackId  主键
     * @param packType
     */
    private void ShowConfirmDialog(String custPackId, String packType) {
        if (mConfirmDialog == null) {
            mConfirmDialog = new ConfirmDialog(context);
            mConfirmDialog.setTitle1("确认审核通过");
            mConfirmDialog.setCancelListener(v -> mConfirmDialog.dismiss());
        }
        mConfirmDialog.setConfirmListener(v -> {

            checkFeego(custPackId, packType);
            mConfirmDialog.dismiss();
        });
        mConfirmDialog.show();
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mFeePayBeans = new ArrayList<>();
        mFeePayAdapter = new CustomerCheckAdapter(context);
        mListView.setAdapter(mFeePayAdapter);
        mFeePayAdapter.setOnClicklistener((position, type) -> {
            ShowConfirmDialog(mFeePayBeans.get(position).getCheckId(), mFeePayBeans.get(position).getPackType());
        });
        mMyrefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder meiTuanRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        mMyrefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
        getBusinessinfo();
    }

    @Override
    protected void initTitle() {
        setTitle("客户对账");
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
        params.put("start",start);
        params.put("length", length);
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
     * 客户对账审核通过
     */
    private void checkFeego(String custPackId, String packType) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", AppUtil.getUserinfo().getId() + "");
        params.put("name", AppUtil.getUserinfo().getName());
        params.put("sysOrgCode", SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.SysOrgCode, ""));
        XLog.d("",params.toString());
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .checkCustomergo(custPackId, packType,params);

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (!(data.getState() >= 200 && data.getState() < 300)) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("客户对账审核通过");
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

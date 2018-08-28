package com.shenhesoft.enterpriseapp.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseFragment;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.feecheck.FeeUnusualAdapter;
import com.shenhesoft.enterpriseapp.ui.activity.motor.MotorDetailsActivity;
import com.shenhesoft.enterpriseapp.utils.AppUtil;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;

/**
 * @author zmx
 * @date 2017/12/5
 * @desc 计费确认fragment
 */


public class FeeCheckFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private static final String TAG = "FeeCheckFragment";
    @BindView(R.id.lv_common)
    ListView mListView;
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @BindView(R.id.myrefreshLayout)
    BGARefreshLayout mMyrefreshLayout;
    Unbinder unbinder;
    private FeeUnusualAdapter mAdapter;
    private ConfirmDialog mConfirmDialog;
    private List<MotorDetailsBean> list;
    private List<MotorDetailsBean> refreshList;
    private int pageNo = 1;
    private boolean isLoadMore = false;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        list = new ArrayList<>();
        mAdapter = new FeeUnusualAdapter(context);
        mListView.setAdapter(mAdapter);

        mAdapter.setOnRejectListener(position -> ShowDialog(list.get(position).getShOrderFinId()));
        mListView.setOnItemClickListener((parent, view, position, id) ->
                Router.newIntent(context).to(MotorDetailsActivity.class).
                        putSerializable("motorbean", (Serializable) list.get(position)).launch());
        mMyrefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder meiTuanRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        mMyrefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    @Override
    public void onResume() {
        super.onResume();
        submit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wait_dispatch;
    }

    @Optional
    @OnClick({R.id.btn_confirm})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:

                break;
            default:
                break;
        }
    }

    private void ShowDialog(String shOrderFinId) {
        if (mConfirmDialog == null) {
            mConfirmDialog = new ConfirmDialog(context);
            mConfirmDialog.setTitle1("确认进入计费状态");
            mConfirmDialog.setCancelListener(v -> mConfirmDialog.dismiss());
        }
        mConfirmDialog.setConfirmListener(v ->
                {
                    checkFeego(shOrderFinId);
                    mConfirmDialog.dismiss();
                }
        );
        mConfirmDialog.show();
    }

    /**
     * 提交
     */
    private void submit() {
        Observable<RequestResults<ListALLResults<MotorDetailsBean>>> observable = HttpManager.getInstance().getUserService()
                .waitUnloading(ApiRetrofit.getInstance().waitUnloading(pageNo+"", "5"));

        HttpObserver<RequestResults<ListALLResults<MotorDetailsBean>>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    refreshList = data.getObj().getRows();
                    Iterator<MotorDetailsBean> iterator = refreshList.iterator();
                    while (iterator.hasNext()) {
                        MotorDetailsBean bean = iterator.next();
                        if (TextUtils.isEmpty(bean.getReceipterDate())) {
                            iterator.remove();
                        }
                    }
                    /**
                     * 下拉刷新时数据是从头开始，因此需要setData
                     * 上拉加载时数据是往后叠加的，所以需要addData
                     * 如果上拉加载时没有加载到数据，pageNo页面数需要保持不变，减1
                     */
                    //上拉加载后有数据
                    if (isLoadMore && !refreshList.isEmpty()) {
                        mAdapter.addData(refreshList);
                        list.addAll(refreshList);
                        isLoadMore = false;
                        mAdapter.notifyDataSetChanged();
                    }
                    //上拉加载后没有数据
                    else if (isLoadMore && refreshList.isEmpty()) {
                        pageNo--;
                        isLoadMore = false;
                    }
                    //下拉刷新后有数据
                    else if (!isLoadMore && !refreshList.isEmpty()) {
                        mAdapter.setData(refreshList);
                        if (!list.isEmpty()) {
                            list.clear();
                        }
                        list.addAll(refreshList);
                        mAdapter.notifyDataSetChanged();
                    }
                    //下拉刷新后没有数据了
                    else if (!isLoadMore && refreshList.isEmpty()) {
                        mAdapter.setData(refreshList);
                        if (!list.isEmpty()) {
                            list.clear();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
        observer.setRefreshLayout(mMyrefreshLayout);
        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 计费审核通过
     */
    private void checkFeego(String shOrderFinId) {
        Log.e(TAG, "checkFeego: "+shOrderFinId );
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", AppUtil.getUserinfo().getId() + "");
        params.put("name", AppUtil.getUserinfo().getName());
        XLog.d(params.toString());
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .checkFeepaygo(shOrderFinId, params);

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (!(data.getState() >= 200 && data.getState() < 300)) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("计费审核通过");
                    submit();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoadMore = false;
        /**
         * 下拉刷新时页面从头开始加载
         */
        pageNo = 1;
        submit();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        /**
         * 上拉加载时每次加载页面加1
         */
        pageNo++;
        submit();
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
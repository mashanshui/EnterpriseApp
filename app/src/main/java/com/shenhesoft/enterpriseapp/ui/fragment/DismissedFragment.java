package com.shenhesoft.enterpriseapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseEvent;
import com.shenhesoft.enterpriseapp.base.BaseFragment;
import com.shenhesoft.enterpriseapp.bean.WaitDispatchBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.adapter.DismissedAdapter;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import io.reactivex.Observable;

import static com.shenhesoft.enterpriseapp.AppConstant.REJECT_ORDER;

/**
 * @author 张继淮
 * @date 2017/12/5
 * @desc 已驳回fragment
 */


public class DismissedFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.lv_common)
    ListView mListView;
    @BindView(R.id.myrefreshLayout)
    BGARefreshLayout mMyrefreshLayout;
    Unbinder unbinder;
    private DismissedAdapter mAdapter;
    private ConfirmDialog mConfirmDialog;
    //重新加载数据
    private boolean isreload = true;
    private int pageNo = 1;
    private boolean isLoadMore = false;
    private List<WaitDispatchBean> list;
    private List<WaitDispatchBean> refreshList;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mAdapter = new DismissedAdapter(context);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnRejectListener(position -> postEvent(new BaseEvent(REJECT_ORDER, position)));

        list = new ArrayList<>();
        mMyrefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder meiTuanRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        mMyrefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_common_viewpager;
    }

    @Override
    public boolean bindEventBus() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        submit();
//        if (isreload) {
//            submit();
//            isreload = false;
//        }
    }

    /**
     * 提交
     */
    private void submit() {
        Observable<RequestResults<ListALLResults<WaitDispatchBean>>> observable = HttpManager.getInstance().getUserService()
                .getRejectDispatchList(ApiRetrofit.getInstance().rejectDisptach(pageNo+""));

        HttpObserver<RequestResults<ListALLResults<WaitDispatchBean>>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }

                    refreshList = data.getObj().getRows();
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

    @Override
    protected void getBusEvent(BaseEvent msg) {
        super.getBusEvent(msg);
        switch (msg.getTag()) {
            case REJECT_ORDER:
                isreload = true;
                break;
            default:
                break;
        }
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
}

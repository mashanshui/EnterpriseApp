package com.shenhesoft.enterpriseapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseFragment;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.motor.MotorDetailsActivity;
import com.shenhesoft.enterpriseapp.ui.adapter.UnusualAdapter;
import com.shenhesoft.enterpriseapp.utils.IToast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/4/4
 * @desc TODO
 */
public class CheckUnusualFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.lv_common)
    ListView mListView;
    @BindView(R.id.myrefreshLayout)
    BGARefreshLayout mMyrefreshLayout;
    Unbinder unbinder;
    private UnusualAdapter mAdapter;
    private List<MotorDetailsBean> list;
    private List<MotorDetailsBean> refreshList;
    private int pageNo = 1;
    private boolean isLoadMore = false;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        list = new ArrayList<>();
        mAdapter = new UnusualAdapter(context);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener((parent, view, position, id) ->
                Router.newIntent(context).to(MotorDetailsActivity.class).
                        putSerializable("motorbean", (Serializable) list.get(position)).launch());
        mMyrefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder meiTuanRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        mMyrefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_common_viewpager;
    }


    @Override
    public void onResume() {
        super.onResume();
        submit();
    }

    /**
     * 提交
     */
    private void submit() {
        Observable<RequestResults<ListALLResults<MotorDetailsBean>>> observable = HttpManager.getInstance().getUserService()
                .waitUnloadingunsual(ApiRetrofit.getInstance().waitUnloadingunusual(pageNo + "", "5"));

        HttpObserver<RequestResults<ListALLResults<MotorDetailsBean>>> observer = new HttpObserver<>(context,
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

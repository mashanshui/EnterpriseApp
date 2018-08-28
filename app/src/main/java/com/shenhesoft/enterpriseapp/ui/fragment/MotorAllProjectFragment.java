package com.shenhesoft.enterpriseapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import android.widget.AutoCompleteTextView;
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
import com.shenhesoft.enterpriseapp.ui.adapter.MotorAllAdapter;
import com.shenhesoft.enterpriseapp.utils.IToast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;

/**
 * @author 张继淮
 * @date 2017/12/7
 * @desc 全部短驳项目fragment
 */

public class MotorAllProjectFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private static final String TAG = "MotorAllProjectFragment";
    private static final String KEY_MOTOR_STATUS = "key_status";

    @BindView(R.id.lv_common)
    ListView mLvCommon;
    private MotorAllAdapter mAdapter;
    private String requestStatus;
    private int pageNo = 1;
    private List<MotorDetailsBean> list;
    private boolean isLoadMore;
    private List<MotorDetailsBean> refreshList;

    @BindView(R.id.myrefreshLayout)
    BGARefreshLayout mRefreshLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_common_viewpager;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestStatus = getArguments().getString(KEY_MOTOR_STATUS);
        list = new ArrayList<>();
    }


    protected void processLogic() {
        mRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder meiTuanRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        mRefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadListData();
    }

    /**
     * 创建一个MotorAllProjectFragment实例
     *
     * @param status 加载不同列表数据的标识值
     * @return
     */
    public static MotorAllProjectFragment newInstance(String status) {
        MotorAllProjectFragment mf = new MotorAllProjectFragment();
        Bundle b = new Bundle();
        b.putString(KEY_MOTOR_STATUS, status);
        mf.setArguments(b);
        return mf;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mAdapter = new MotorAllAdapter(context);
        mLvCommon.setAdapter(mAdapter);
        mAdapter.setRefreshListener(new MotorAllAdapter.RefreshListener() {
            @Override
            public void refresh() {
                loadListData();
            }
        });
        mLvCommon.setOnItemClickListener((parent, view, position, id) -> {
            Router.newIntent(context).to(MotorDetailsActivity.class).putSerializable("motorbean", (Serializable) list.get(position)).launch();
        });
        processLogic();

//        loadListData();

    }

    /**
     * 获取列表数据 *请求接口*
     */
    private void loadListData() {
        if (requestStatus == null) {
            IToast.showShort("界面异常，请返回重新进入！");
            return;
        }
        Observable observable = HttpManager.getInstance().getOrderService()
                .getMotorShortListData(ApiRetrofit.getInstance().motorShortParams(pageNo + "", requestStatus));

        HttpObserver observer = new HttpObserver<RequestResults<ListALLResults<MotorDetailsBean>>>(getContext(),
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    refreshList = data.getObj().getRows();
                    if ("5".equals(requestStatus)) {
                        Iterator<MotorDetailsBean> iterator = refreshList.iterator();
                        while (iterator.hasNext()) {
                            MotorDetailsBean bean = iterator.next();
                            if (!TextUtils.isEmpty(bean.getReceipterDate())) {
                                iterator.remove();
                            }
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
//                    if (!refreshList.isEmpty()) {
//                        if (isLoadMore) {
//                            mAdapter.addData(refreshList);
//                            list.addAll(refreshList);
//                        } else {
//                            mAdapter.setData(refreshList);
//                            if (!list.isEmpty()) {
//                                list.clear();
//                            }
//                            list.addAll(refreshList);
//                        }
//                        mAdapter.notifyDataSetChanged();
//                    } else {
//                        pageNo--;
////                        IToast.showShort("没有更多数据");
//                    }
                });
        observer.setRefreshLayout(mRefreshLayout);
        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoadMore = false;
        /**
         * 下拉刷新时页面从头开始加载
         */
        pageNo = 1;
        loadListData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        /**
         * 上拉加载时每次加载页面加1
         */
        pageNo++;
        loadListData();
        return true;
    }

}

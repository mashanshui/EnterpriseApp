package com.shenhesoft.enterpriseapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseFragment;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.bean.TrainBean;
import com.shenhesoft.enterpriseapp.bean.TrainDetailsBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.train.TrainDetailsActivity;
import com.shenhesoft.enterpriseapp.ui.adapter.TrainCommonAdapter;
import com.shenhesoft.enterpriseapp.utils.IToast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;

/**
 * @author 张继淮
 * @date 2017/12/7
 * @desc 火运干线 除了全部以外的通用fragment
 */

public class TrainCommonFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    public final static String KEY_TRAIN_STATUS = "train_status";

    private String trainStatus;

    private int pageNo = 1;

    @BindView(R.id.lv_common)
    ListView mLvCommon;
    private TrainCommonAdapter mAdapter;
    private List<TrainBean> list;
    @BindView(R.id.myrefreshLayout)
    BGARefreshLayout mRefreshLayout;
    private boolean isLoadMore;
    private List<TrainBean> refreshList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trainStatus = getArguments().getString(KEY_TRAIN_STATUS);
        list = new ArrayList<>();
    }

    protected void processLogic() {
        mRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder meiTuanRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        mRefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
//        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadListDataByStatus();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_common_viewpager;
    }

    /**
     * 创建一个MotorAllProjectFragment实例
     *
     * @param status 加载不同状态列表数据的标识值
     * @return
     */
    public static TrainCommonFragment newInstance(String status) {
        TrainCommonFragment tf = new TrainCommonFragment();
        Bundle b = new Bundle();
        b.putString(KEY_TRAIN_STATUS, status);
        tf.setArguments(b);
        return tf;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        processLogic();
        mAdapter = new TrainCommonAdapter(context);
        mLvCommon.setAdapter(mAdapter);

//        loadListDataByStatus();
        mLvCommon.setOnItemClickListener((parent, view, position, id)
                -> Router.newIntent(context).to(TrainDetailsActivity.class)
                .putSerializable("trainbean", (Serializable) list.get(position)).launch());
    }


    /***
     * 根据状态加载数据
     */
    private void loadListDataByStatus() {
        if (trainStatus == null) {
            IToast.showShort("界面异常，请返回重新进入");
            return;
        }

        Observable<RequestResults<ListALLResults<TrainBean>>> observable = HttpManager.getInstance().getOrderService()
                .getTrainListData(ApiRetrofit.getInstance().trainListParams(pageNo + "", trainStatus));

        HttpObserver observer = new HttpObserver<RequestResults<ListALLResults<TrainBean>>>(context,
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
        loadListDataByStatus();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        /**
         * 上拉加载时每次加载页面加1
         */
        pageNo++;
        loadListDataByStatus();
        return true;
    }
}

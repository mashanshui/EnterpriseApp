package com.shenhesoft.enterpriseapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseFragment;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.ProjectDetailsActivity;
import com.shenhesoft.enterpriseapp.ui.activity.confirmarrive.GuideOrderActivity;
import com.shenhesoft.enterpriseapp.ui.adapter.WaitUnloadingAdapter;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;

/**
 * @author 张继淮>zhangmx
 * @date 2017/12/5
 * @desc 等待卸货fragment
 */


public class WaitUnloadingFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
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
    private WaitUnloadingAdapter mAdapter;
    private ConfirmDialog mConfirmDialog;
    private List<MotorDetailsBean> list;
    private List<MotorDetailsBean> refreshList;
    private int pageNo = 1;
    private boolean isLoadMore = false;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        list = new ArrayList<>();
        mAdapter = new WaitUnloadingAdapter(context);
        mListView.setAdapter(mAdapter);

        mAdapter.setOnRejectListener(position ->
                Router.newIntent(context).putString("orderId", String.valueOf(list.get(position).getOrderid())).to(GuideOrderActivity.class).launch()
        );
        mListView.setOnItemClickListener((parent, view, position, id) ->
                Router.newIntent(context).putSerializable("motorDetailsBean", list.get(position)).to(ProjectDetailsActivity.class).launch()
        );

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
                if (mConfirmDialog == null) {
                    mConfirmDialog = new ConfirmDialog(context);
                    mConfirmDialog.setTitle1(getString(R.string.confirm_action));
                    mConfirmDialog.setTitle2(getString(R.string.ps_cannot_back));
                    mConfirmDialog.setCancelListener(v -> mConfirmDialog.dismiss());
                    mConfirmDialog.setConfirmListener(v -> Toast.makeText(context, "核准完成", Toast.LENGTH_SHORT));
                }
                mConfirmDialog.show();
                break;
            default:
                break;
        }
    }

    /**
     * 提交
     */
    private void submit() {
        Observable<RequestResults<ListALLResults<MotorDetailsBean>>> observable = HttpManager.getInstance().getUserService()
                .waitUnloading(ApiRetrofit.getInstance().waitUnloading(pageNo + "", "4"));

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


//    order/listAllTbOrderDifferentStatus.do
}

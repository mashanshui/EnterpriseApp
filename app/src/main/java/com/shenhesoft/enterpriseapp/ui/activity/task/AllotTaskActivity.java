package com.shenhesoft.enterpriseapp.ui.activity.task;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.MotorAllBean;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.bean.TaskBean;
import com.shenhesoft.enterpriseapp.bean.TaskDetailsBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.motor.MotorDetailsActivity;
import com.shenhesoft.enterpriseapp.utils.DensityUtils;
import com.shenhesoft.enterpriseapp.utils.IToast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;

/**
 * 作者：
 * 创作日期：2018/1/5.
 * 描述：分配任务
 */

public class AllotTaskActivity extends BaseTitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{

    @BindView(R.id.lv_common)
    ListView mListview;
    private int pageNo = 1;
    private boolean isLoadMore;
    private Dialog allotDialog;
    private AllotTaskAdapter adapter;
    private List<TaskDetailsBean> mlist;
    private List<TaskDetailsBean> refreshList;
    private int mPosition;
    @BindView(R.id.myrefreshLayout)
    BGARefreshLayout mRefreshLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_common_viewpager;
    }

    @Override
    protected void initTitle() {
        setTitle("分配任务");

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mlist = new ArrayList<>();
        processLogic();
        adapter = new AllotTaskAdapter(context);
        loadListData();
        adapter.setBottonTxtEven(new AllotTaskAdapter.OnBottonTxtEven() {
            @Override
            public void allotClick(int position) {
                mPosition = position;
                showAddMoreDialog();
            }

            @Override
            public void startClick(int position) {
                mPosition = position;
                startTbProjectDistribution(String.valueOf(mlist.get(mPosition).getId()), mlist.get(mPosition).getType() + "");
            }

            @Override
            public void stopClick(int position) {
                mPosition = position;
                stopTbProjectDistribution(String.valueOf(mlist.get(mPosition).getId()), mlist.get(mPosition).getType() + "");

            }
        });

        mListview.setAdapter(adapter);

        mListview.setOnItemClickListener((parent, view, position, id) -> {
            Router.newIntent(context).to(TaskDetailsActivity.class)
                    .putSerializable("taskbean", (Serializable) mlist.get(position)).launch();
        });
    }

    protected void processLogic() {
        mRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder meiTuanRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        mRefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }


    private void showAddMoreDialog() {
        if (allotDialog == null) {
            allotDialog = new Dialog(context, R.style.train_wait_addzcyd_dialog_style);
            allotDialog.setContentView(R.layout.dialog_allot_task);

            WindowManager.LayoutParams lp = allotDialog.getWindow().getAttributes();
            lp.width = (int) (DensityUtils.getScreenWidthPixels(context) * 0.7);//设置宽度
            lp.gravity = Gravity.CENTER; //设置居中

            initallotDialogViews();

        } else {
            allotDialog.show();
        }
    }

    private void initallotDialogViews() {
        TextView tvClean = (TextView) allotDialog.findViewById(R.id.dialog_wait_add_tvclean);
        tvClean.setOnClickListener(v -> allotDialog.cancel());
        EditText etNumber = (EditText) allotDialog.findViewById(R.id.et_task_number);

        TextView tvSave = (TextView) allotDialog.findViewById(R.id.dialog_wait_add_tvsave);
        tvSave.setOnClickListener(v -> {
            XLog.d("mposition===" + mPosition);
            saveTbProjectDistribution(String.valueOf(mlist.get(mPosition).getId()),
                    etNumber.getText().toString(), mlist.get(mPosition).getType() + "");
            allotDialog.dismiss();

        });
        allotDialog.show();

        //....
    }

    /**
     * 获取列表数据 *请求接口*
     */
    private void loadListData() {
        Observable observable = HttpManager.getInstance().getOrderService()
                .getTaskListData(ApiRetrofit.getInstance().getTaskParams(pageNo+""));

        HttpObserver observer = new HttpObserver<RequestResults<ListALLResults<TaskDetailsBean>>>(this,
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
                        adapter.addData(refreshList);
                        mlist.addAll(refreshList);
                        isLoadMore = false;
                        adapter.notifyDataSetChanged();
                    }
                    //上拉加载后没有数据
                    else if (isLoadMore && refreshList.isEmpty()) {
                        pageNo--;
                        isLoadMore = false;
                    }
                    //下拉刷新后有数据
                    else if (!isLoadMore && !refreshList.isEmpty()) {
                        adapter.setData(refreshList);
                        if (!mlist.isEmpty()) {
                            mlist.clear();
                        }
                        mlist.addAll(refreshList);
                        adapter.notifyDataSetChanged();
                    }
                    //下拉刷新后没有数据了
                    else if (!isLoadMore && refreshList.isEmpty()) {
                        adapter.setData(refreshList);
                        if (!mlist.isEmpty()) {
                            mlist.clear();
                        }
                        adapter.notifyDataSetChanged();
                    }

                });
        observer.setRefreshLayout(mRefreshLayout);
        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    /**
     * 保存分配信息
     */
    private void saveTbProjectDistribution(String projectId, String distributionNum, String taskType) {
        Observable observable = HttpManager.getInstance().getOrderService()
                .saveTask(ApiRetrofit.getInstance().saveTaskParams(projectId, distributionNum, taskType));

        HttpObserver observer = new HttpObserver<RequestResults>(this,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("保存分配信息成功");
                    loadListData();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    /**
     * 暂停分配
     */
    private void stopTbProjectDistribution(String projectIds, String projectStages) {
        Observable observable = HttpManager.getInstance().getOrderService()
                .stopTask(ApiRetrofit.getInstance().stopTaskParams(projectIds, projectStages));

        HttpObserver observer = new HttpObserver<RequestResults>(this,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    loadListData();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    /**
     * 开始分配
     */
    private void startTbProjectDistribution(String projectIds, String projectStages) {
        Observable observable = HttpManager.getInstance().getOrderService()
                .startTask(ApiRetrofit.getInstance().startTaskParams(projectIds, projectStages));

        HttpObserver observer = new HttpObserver<RequestResults>(this,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("项目开始分配");
                    loadListData();
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

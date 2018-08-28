package com.shenhesoft.enterpriseapp.ui.activity.task;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListView;

import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.bean.PDetailsRootItem;
import com.shenhesoft.enterpriseapp.bean.TaskDetailsBean;
import com.shenhesoft.enterpriseapp.bean.TrainDetailsBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.adapter.PDetailsAdapter;
import com.shenhesoft.enterpriseapp.utils.DensityUtils;
import com.shenhesoft.enterpriseapp.utils.IToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.log.XLog;
import io.reactivex.Observable;


/**
 * 作者：Tornado
 * 创作日期：2018/1/5.
 * 描述：
 */

public class TaskDetailsActivity extends BaseTitleActivity {

    @BindView(R.id.details_ex_listview)
    ExpandableListView exListview;
    @BindView(R.id.task_tv_allot)
    TextView tvAllot;          //分配
    @BindView(R.id.task_tv_start)
    TextView tvStart;          //开始
    @BindView(R.id.task_tv_pause)
    TextView tvPause;          //暂停
    TaskDetailsBean mTaskDetailsBean;
    private Dialog allotDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_task_details;
    }

    @Override
    protected void initTitle() {
        setTitle("任务详情");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mTaskDetailsBean = (TaskDetailsBean) getIntent().getSerializableExtra("taskbean");
        PDetailsAdapter adapter = new PDetailsAdapter(createListData(mTaskDetailsBean), context);
        exListview.setGroupIndicator(null);
        exListview.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            exListview.expandGroup(i);
        }

        exListview.setOnGroupClickListener((parent, v, groupPosition, id) -> true);


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
            saveTbProjectDistribution(String.valueOf(mTaskDetailsBean.getId()),
                    etNumber.getText().toString(), mTaskDetailsBean.getType() + "");
            allotDialog.dismiss();

        });
        allotDialog.show();

        //....
    }


    private List<PDetailsRootItem> createListData(TaskDetailsBean bean) {
        List<PDetailsRootItem> rootItems = new ArrayList<>();


        if (bean.getProjectType().equals("0")) {
            bean.setProjectType("集装箱");
        } else if (bean.getProjectType().equals("1")) {
            bean.setProjectType("散装");
        }

        PDetailsRootItem item = new PDetailsRootItem("分配信息", R.drawable.icon_jizxxx);
        item.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"已领任务：", "待领任务：", "完成任务："},
                new String[]{bean.getObtainTask(), bean.getWaitObtainTask(), bean.getFulfillTask()}
        ));
        rootItems.add(item);

        PDetailsRootItem item1 = new PDetailsRootItem("项目信息", R.drawable.icon_xiangmxx);
        item1.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"项目编号：", "项目类型：", "发货人：", "收货人：", "分支机构："},
                new String[]{bean.getProjectCode(), bean.getProjectType(), bean.getShipper(),
                        bean.getConsignee(), bean.getBranchOrgan()}
        ));
        rootItems.add(item1);

        PDetailsRootItem item2 = new PDetailsRootItem("货物信息", R.drawable.icon_jizxxx);
        item2.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"货物名称：", "货物规格：", "化验指标："},
                new String[]{bean.getCargoName(), bean.getCargoSpecification(), bean.getAssayIndex()}
        ));
        rootItems.add(item2);


        PDetailsRootItem item4 = new PDetailsRootItem("收发货信息", R.drawable.icon_jizxxx);
        item4.setChildItems(PDetailsRootItem.createChilds(
                new String[]{"发货单位：", "取货地址：", "收货单位：", "运抵地址："},
                new String[]{bean.getSendCompany(), bean.getSendAddress(), bean.getReceiptCompany(),
                        bean.getReceiptAddress()}
        ));
        rootItems.add(item4);

        return rootItems;
    }

    @OnClick({R.id.task_tv_allot, R.id.task_tv_start, R.id.task_tv_pause})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.task_tv_allot:
                showAddMoreDialog();
                break;

            case R.id.task_tv_start:
                startTbProjectDistribution(String.valueOf(mTaskDetailsBean.getId()), mTaskDetailsBean.getType() + "");
                break;

            case R.id.task_tv_pause:
                stopTbProjectDistribution(String.valueOf(mTaskDetailsBean.getId()), mTaskDetailsBean.getType() + "");

                break;
            default:
                break;
        }
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
                    IToast.showShort("项目已暂停分配");

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
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

}

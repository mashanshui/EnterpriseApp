package com.shenhesoft.enterpriseapp.ui.activity.task;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.TaskBean;
import com.shenhesoft.enterpriseapp.bean.TaskDetailsBean;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * 作者：
 * 创作日期：2018/1/5.
 * 描述：分配任务 列表展示adapter
 */

public class AllotTaskAdapter extends SimpleListAdapter<TaskDetailsBean, AllotTaskAdapter.ViewHolder> {


    private OnBottonTxtEven bottonTxtEven;

    public interface OnBottonTxtEven {
        void allotClick(int position);

        void startClick(int position);

        void stopClick(int position);
    }

    public AllotTaskAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_lv_task;
    }

    public void setBottonTxtEven(OnBottonTxtEven bottonTxtEven) {
        this.bottonTxtEven = bottonTxtEven;
    }

    @Override
    protected void convert(ViewHolder holder, TaskDetailsBean item, int position) {

        //0 未分配 1 分配中 2 暂停分配
        //阶段(1-接取 2-送达3 汽运)  type
        String orderstate = "";
        String type = "";
        if (item.getOrderStatus() == 0) {
            orderstate = "未分配 ";
        } else if (item.getOrderStatus() == 1) {
            orderstate = "分配中";
        } else if (item.getOrderStatus() == 2) {
            orderstate = "暂停分配";
        }

        if (item.getType() == 1) {
            type = "接取";
        } else if (item.getType() == 2) {
            type = "送达";
        } else if (item.getType() == 3) {
            type = "汽运";
        }


        holder.tvProjectCode.setText(item.getProjectCode());
        holder.tvZhuangt.setText(orderstate);
        holder.tvJied.setText(type);
        holder.tvFahqy.setText(item.getSendCompany());
        holder.tvShouhqy.setText(item.getReceiptCompany());
        holder.tvHuowmc.setText(item.getCargoName());
        holder.tvYilrw.setText(item.getObtainTask());
        holder.tvDailrw.setText(item.getWaitObtainTask());
        holder.tvWancrw.setText(item.getFulfillTask());

        holder.tvAllot.setOnClickListener(new BottomTvOnClick(0, position));
        holder.tvStart.setOnClickListener(new BottomTvOnClick(1, position));
        holder.tvPause.setOnClickListener(new BottomTvOnClick(2, position));
    }

    class BottomTvOnClick implements View.OnClickListener {

        //type  0保存，1开始，2暂停
        private int type;
        private int postion;

        public BottomTvOnClick(int type, int postion) {
            this.postion = postion;
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            if (bottonTxtEven != null) {
                if (type == 0) {
                    bottonTxtEven.allotClick(postion);
                } else if (type == 1) {
                    bottonTxtEven.startClick(postion);
                } else {
                    bottonTxtEven.stopClick(postion);
                }
            }
        }
    }

    class ViewHolder {
        @BindView(R.id.tv_project_name)
        TextView tvProjectCode;         //项目编号
        @BindView(R.id.task_item_tv_zhuangt)
        TextView tvZhuangt;             //状态
        @BindView(R.id.task_item_tv_jied)
        TextView tvJied;             //阶段
        @BindView(R.id.task_item_tv_fahqy)
        TextView tvFahqy;             //发货企业
        @BindView(R.id.task_item_tv_shouhqy)
        TextView tvShouhqy;             //收货企业
        @BindView(R.id.task_item_tv_huomwc)
        TextView tvHuowmc;             //货物名称
        @BindView(R.id.task_item_tv_yilrw)
        TextView tvYilrw;             //已领任务
        @BindView(R.id.task_item_tv_dailrw)
        TextView tvDailrw;             //待领任务
        @BindView(R.id.task_item_tv_wancru)
        TextView tvWancrw;             //完成任务

        @BindView(R.id.task_item_tv_allot)
        TextView tvAllot;             //分配
        @BindView(R.id.task_item_tv_start)
        TextView tvStart;             //开始
        @BindView(R.id.task_item_tv_pause)
        TextView tvPause;             //暂停


        ViewHolder(View view) {
            KnifeKit.bind(this, view);
        }
    }
}

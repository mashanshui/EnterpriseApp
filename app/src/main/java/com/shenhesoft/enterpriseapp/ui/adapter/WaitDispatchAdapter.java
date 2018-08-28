package com.shenhesoft.enterpriseapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.bean.WaitDispatchBean;
import com.shenhesoft.enterpriseapp.ui.activity.task.AllotTaskAdapter;
import com.shenhesoft.enterpriseapp.utils.MyUtils;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import io.rong.imkit.RongIM;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 等待调度适配器
 */

public class WaitDispatchAdapter extends SimpleListAdapter<MotorDetailsBean, WaitDispatchAdapter.ViewHolder> {

    private OnCheckChangeListener mChangeListener;

    private OnRejectListener mOnRejectListener;

    private AllotTaskAdapter.OnBottonTxtEven bottonTxtEven;

    public interface OnBottonTxtEven {
        void allotClick(int position);

        void startClick(int position);

        void stopClick(int position);
    }

    public void setBottonTxtEven(AllotTaskAdapter.OnBottonTxtEven bottonTxtEven) {
        this.bottonTxtEven = bottonTxtEven;
    }


    public WaitDispatchAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_wait_dispatch;
    }

    @Override
    protected void convert(ViewHolder holder, MotorDetailsBean item, int position) {
        holder.mCheckBox.setOnClickListener(v -> {
            getDataSource().get(position).setChecked(holder.mCheckBox.isChecked());
            if (mChangeListener != null)
                mChangeListener.OnChange();
        });
        holder.mCheckBox.setChecked(item.isChecked());

        holder.mTvReject.setOnClickListener(v -> {
            if (mOnRejectListener != null)
                mOnRejectListener.OnReject(position);
        });

        holder.mTvProjectName.setText(item.getProjectCode());

        holder.mTvCarNumber.setText(item.getCarPlateNumber());

        holder.mTvCarvehicleName.setText(item.getCarType());

        holder.mTvTotal.setText(item.getHistoryTbOrderNumDriverId());

        holder.mTvTCarTeam.setText(item.getCarPlateNumber() + "");

        holder.mTvPhone.setOnClickListener(v -> MyUtils.call(context, item.getContactTel()));

        holder.mTvConfirm.setOnClickListener(v -> {
            bottonTxtEven.allotClick(position);
        });
        holder.mTvMessage.setOnClickListener(v -> {
            /**
             * 启动单聊界面。
             *
             * @param context      应用上下文。
             * @param targetUserId 要与之聊天的用户 Id。
             * @param title        聊天的标题，开发者需要在聊天界面通过 intent.getData().getQueryParameter("title")
             *                     获取该值, 再手动设置为聊天界面的标题。
             */
            RongIM.getInstance().startPrivateChat(context, "S" + item.getDriverId(), "");
        });


    }

    class ViewHolder {
        @BindView(R.id.checkbox)
        CheckBox mCheckBox;
        @BindView(R.id.tv_project_name)
        TextView mTvProjectName;
        @BindView(R.id.ll_project_name)
        LinearLayout mLlProjectName;
        @BindView(R.id.tv_confirm)
        TextView mTvConfirm;
        @BindView(R.id.tv_reject)
        TextView mTvReject;
        @BindView(R.id.tv_message)
        TextView mTvMessage;
        @BindView(R.id.tv_phone)
        TextView mTvPhone;
        @BindView(R.id.tv_car_number)
        TextView mTvCarNumber;
        @BindView(R.id.tv_car_vehicleName)
        TextView mTvCarvehicleName;
        @BindView(R.id.tv_total)
        TextView mTvTotal;
        @BindView(R.id.tv_car_team_name)
        TextView mTvTCarTeam;

        private ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }

    public void setChangeListener(OnCheckChangeListener changeListener) {
        mChangeListener = changeListener;
    }

    public void setOnRejectListener(OnRejectListener onRejectListener) {
        mOnRejectListener = onRejectListener;
    }

    public interface OnCheckChangeListener {
        void OnChange();
    }

    public interface OnRejectListener {
        void OnReject(int position);
    }

    public void setCheckAll(boolean isChecked) {
        for (MotorDetailsBean bean : getDataSource()) {
            bean.setChecked(isChecked);
        }
        notifyDataSetChanged();
    }
}

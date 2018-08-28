package com.shenhesoft.enterpriseapp.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.MotorAllBean;
import com.shenhesoft.enterpriseapp.utils.MyUtils;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 异常适配器
 */

public class TrainAllAdapter extends SimpleListAdapter<MotorAllBean, TrainAllAdapter.ViewHolder> {


    public TrainAllAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_train_list;
    }

    @Override
    protected void convert(ViewHolder holder, MotorAllBean item, int position) {
        Drawable rightDrawable;
        switch (item.getType()) {
            case 0:
                holder.mTvType.setText("等待承认");
                holder.mTvState.setText("等待承认");
                rightDrawable = context.getResources().getDrawable(R.drawable.wait_admit);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.mTvState.setCompoundDrawables(rightDrawable, null, null, null);
                break;
            case 1:
                holder.mTvType.setText("等待装车");
                holder.mTvState.setText("等待装车");
                rightDrawable = context.getResources().getDrawable(R.drawable.ic_wait_entrucking);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.mTvState.setCompoundDrawables(rightDrawable, null, null, null);
                break;
            case 2:
                holder.mTvType.setText("等待发运");
                holder.mTvState.setText("等待发运");
                rightDrawable = context.getResources().getDrawable(R.drawable.ic_wait_shipment);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.mTvState.setCompoundDrawables(rightDrawable, null, null, null);
                break;
            case 3:
                holder.mTvType.setText("在途运载");
                holder.mTvState.setText("在途运载");
                rightDrawable = context.getResources().getDrawable(R.drawable.ic_loading);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.mTvState.setCompoundDrawables(rightDrawable, null, null, null);
                break;
            case 4:
                holder.mTvType.setText("等待卸货");
                holder.mTvState.setText("等待卸货");
                rightDrawable = context.getResources().getDrawable(R.drawable.ic_wait_unloading);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.mTvState.setCompoundDrawables(rightDrawable, null, null, null);
                break;
            case 6:
                holder.mTvType.setText("等待回单");
                holder.mTvState.setText("等待回单");
                rightDrawable = context.getResources().getDrawable(R.drawable.ic_wait_bill);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.mTvState.setCompoundDrawables(rightDrawable, null, null, null);
                break;
        }

        holder.mTvPhone.setOnClickListener(v -> {
            MyUtils.call(context, "123456");

        });
    }

    class ViewHolder {
        @BindView(R.id.tv_project_name)
        TextView mTvProjectName;
        @BindView(R.id.ll_project_name)
        LinearLayout mLlProjectName;
        @BindView(R.id.tv_restore)
        TextView mTvState;
        @BindView(R.id.tv_message)
        TextView mTvMessage;
        @BindView(R.id.tv_phone)
        TextView mTvPhone;
        @BindView(R.id.tv_type)
        TextView mTvType;

        ViewHolder(View view) {
            KnifeKit.bind(this, view);
        }
    }
}

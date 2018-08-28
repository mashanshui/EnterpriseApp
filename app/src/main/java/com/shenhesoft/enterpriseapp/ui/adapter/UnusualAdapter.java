package com.shenhesoft.enterpriseapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.bean.WaitDispatchBean;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 异常适配器
 */

public class UnusualAdapter extends SimpleListAdapter<MotorDetailsBean, UnusualAdapter.ViewHolder> {

    public UnusualAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_unusual;
    }

    @Override
    protected void convert(ViewHolder holder, MotorDetailsBean item, int position) {

        holder.mTvarrivelocation.setText(item.getReceiptCompany());
        holder.mTvcarname.setText(item.getCarPlateNumber());
        holder.mTvgoodsname.setText(item.getCargoName());
//        holder.mTvid.setText(item.getDriverId());
        holder.mTvstartlocation.setText(item.getShipCompany());
        holder.mTvreason.setText(item.getRejectCause());
        holder.mTvProjectName.setText(item.getProjectCode());

    }

    class ViewHolder {
        @BindView(R.id.tv_project_name)
        TextView mTvProjectName;
        @BindView(R.id.tv_wait_unloading_arrivelocation)
        TextView mTvarrivelocation;
        @BindView(R.id.tv_wait_unloading_carname)
        TextView mTvcarname;
        @BindView(R.id.tv_wait_unloading_goodsname)
        TextView mTvgoodsname;
        //        @BindView(R.id.tv_wait_unloading_id)
//        TextView mTvid;
        @BindView(R.id.tv_wait_unloading_startlocation)
        TextView mTvstartlocation;
        @BindView(R.id.tv_wait_unloading_reason)
        TextView mTvreason;


        private ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }
}

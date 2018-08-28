package com.shenhesoft.enterpriseapp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.MotorAllBean;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.bean.WaitDispatchBean;
import com.shenhesoft.enterpriseapp.ui.activity.confirmarrive.ArriveUnusualActivity;
import com.shenhesoft.enterpriseapp.ui.activity.confirmarrive.GuideAllocationActivity;
import com.shenhesoft.enterpriseapp.utils.MyUtils;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import cn.droidlover.xdroidmvp.router.Router;
import io.rong.imkit.RongIM;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 等待引导适配器
 */

public class WaitUnloadingAdapter extends SimpleListAdapter<MotorDetailsBean, WaitUnloadingAdapter.ViewHolder> {

    private OnRejectListener mOnRejectListener;

    public WaitUnloadingAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_wait_unloading;
    }

    @Override
    protected void convert(ViewHolder holder, MotorDetailsBean item, int position) {
//        holder.mCheckBox.setOnClickListener(v -> {
//            getDataSource().get(position).setChecked(holder.mCheckBox.isChecked());
//            if (mChangeListener != null)
//                mChangeListener.OnChange();
//        });
        holder.mCheckBox.setVisibility(View.GONE);
        holder.mCheckBox.setChecked(item.isChecked());
        holder.mTvReject.setOnClickListener(v -> {
            if (mOnRejectListener != null)
                mOnRejectListener.OnReject(position);
        });
        holder.mTvarrivelocation.setText(item.getReceiptCompany());
        holder.mTvcarname.setText(item.getCarPlateNumber());
        holder.mTvgoodsname.setText(item.getCargoName());
//        holder.mTvid.setText(item.getDriverId());
        holder.mTvstartlocation.setText(item.getShipCompany());
//        holder.mTvsuttle.setText(item.getCarType());
        holder.mTvProjectName.setText(item.getProjectCode());
        holder.mTvOrderCode.setText(item.getOrderCode());
        holder.mTvPhone.setOnClickListener(v -> MyUtils.call(context, item.getContactTel()));
        holder.mTvMessage.setOnClickListener(v ->
                {  /**
                 * 启动单聊界面。
                 *
                 * @param context      应用上下文。
                 * @param targetUserId 要与之聊天的用户 Id。
                 * @param title        聊天的标题，开发者需要在聊天界面通过 intent.getData().getQueryParameter("title")
                 *                     获取该值, 再手动设置为聊天界面的标题。
                 */
                    RongIM.getInstance().startPrivateChat(context, "S" + item.getDriverId(), "");
                }
        );

        holder.mTvConfirm.setOnClickListener(v -> {
            Router.newIntent((Activity) context).putString("orderId", String.valueOf(item.getOrderid())).to(ArriveUnusualActivity.class).launch();
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

        @BindView(R.id.tv_order_code)
        TextView mTvOrderCode;

        @BindView(R.id.tv_wait_unloading_arrivelocation)
        TextView mTvarrivelocation;
        @BindView(R.id.tv_wait_unloading_carname)
        TextView mTvcarname;
        @BindView(R.id.tv_wait_unloading_goodsname)
        TextView mTvgoodsname;

        @BindView(R.id.tv_wait_unloading_startlocation)
        TextView mTvstartlocation;
//        @BindView(R.id.tv_wait_unloading_suttle)
//        TextView mTvsuttle;


        private ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
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

//    public void setCheckAll(boolean isChecked) {
//        for (WaitDispatchBean bean : getDataSource()) {
//            bean.setChecked(isChecked);
//        }
//        notifyDataSetChanged();
//    }
}

package com.shenhesoft.enterpriseapp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.UnusualBean;
import com.shenhesoft.enterpriseapp.bean.WaitDispatchBean;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 异常适配器
 */

public class UnusualWaybillAdapter extends SimpleListAdapter<UnusualBean, UnusualWaybillAdapter.ViewHolder> {

    public UnusualWaybillAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_unusual_wayill;
    }

    @Override
    protected void convert(ViewHolder holder, UnusualBean item, int position) {

        holder.mTvProjectCode.setText(item.getProjectCode());
        holder.mTvOrderCode.setText(item.getOrderCode());
        holder.mTvcarrierVehicleName.setText(item.getCarrierVehicleName());
        holder.mTvcargoName.setText(item.getCargoName());
        holder.mTvsendCompany.setText(item.getSendCompany());
        holder.mTvreceiptCompany.setText(item.getReceiptCompany());
//        holder.mTvreasone.setText(item.getReason());
        String reason = "";
        if (!TextUtils.isEmpty(item.getExceptionReason())) {
            reason = item.getExceptionReason();
        } else if (TextUtils.isEmpty(item.getExceptionReasonDetail())) {
            reason = item.getExceptionReasonDetail();
        }
        holder.mTvreasone.setText(reason);
        holder.mTvusername.setText(item.getUsername());

    }

    class ViewHolder {
        @BindView(R.id.tv_project_code)
        TextView mTvProjectCode;
        @BindView(R.id.tv_order_code)
        TextView mTvOrderCode;
        @BindView(R.id.tv_project_carrierVehicleName)
        TextView mTvcarrierVehicleName;
        @BindView(R.id.tv_project_cargoName)
        TextView mTvcargoName;
        @BindView(R.id.tv_project_sendCompany)
        TextView mTvsendCompany;
        @BindView(R.id.tv_project_receiptCompany)
        TextView mTvreceiptCompany;
        @BindView(R.id.tv_project_reason)
        TextView mTvreasone;
        @BindView(R.id.tv_project_username)
        TextView mTvusername;


        ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }

}

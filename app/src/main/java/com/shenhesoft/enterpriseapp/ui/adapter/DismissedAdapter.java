package com.shenhesoft.enterpriseapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.WaitDispatchBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.utils.MyUtils;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import io.reactivex.Observable;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 已驳回适配器
 */

public class DismissedAdapter extends SimpleListAdapter<WaitDispatchBean, DismissedAdapter.ViewHolder> {
    private ConfirmDialog mConfirmDialog;
    private OnRejectListener mOnRejectListener;

    public DismissedAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_dismissed;
    }

    @Override
    protected void convert(ViewHolder holder, WaitDispatchBean item, int position) {
        holder.mTvRestore.setOnClickListener(v -> {
            if (mConfirmDialog == null) {
                mConfirmDialog = new ConfirmDialog(context);
                mConfirmDialog.setTitle1("确认还原此项目");
                mConfirmDialog.setConfirmText("还原");
            }
            mConfirmDialog.setConfirmListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dispatch(String.valueOf(item.getId()), position);
                }
            });
            mConfirmDialog.show();
        });

        holder.mTvProjectName.setText(item.getProjectCode());

        holder.mTvCarNumber.setText(item.getCarPlateNumber());

        holder.mTvCarvehicleName.setText(item.getCarrierVehicleName());

        holder.mTvTotal.setText(item.getDriverId());

        holder.mTvTCarTeam.setText(item.getId() + "");

        holder.mTvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.call(context, item.getPhone());
            }
        });
    }


    class ViewHolder {
        @BindView(R.id.tv_project_name)
        TextView mTvProjectName;
        @BindView(R.id.ll_project_name)
        LinearLayout mLlProjectName;
        @BindView(R.id.tv_wait_bill)
        TextView mTvRestore;
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


        ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }


    private void Dispatch(String orderid, int posotion) {
        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .restoreOrder(ApiRetrofit.getInstance().restoreOrder(orderid));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("项目已经还原");
                    if (mConfirmDialog != null) {
                        mConfirmDialog.dismiss();
                        getDataSource().remove(posotion);
                        notifyDataSetChanged();
                        mOnRejectListener.OnReject(posotion);
                    }

                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    public void setOnRejectListener(OnRejectListener onRejectListener) {
        mOnRejectListener = onRejectListener;
    }

    public interface OnRejectListener {
        void OnReject(int position);
    }
}

package com.shenhesoft.enterpriseapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.motor.AddArrivexxActivity;
import com.shenhesoft.enterpriseapp.ui.activity.motor.AddFayxxActivity;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.utils.MyUtils;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;

import java.io.Serializable;
import java.util.Map;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import io.reactivex.Observable;
import io.rong.imkit.RongIM;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 异常适配器
 */

public class MotorAllAdapter extends SimpleListAdapter<MotorDetailsBean, MotorAllAdapter.ViewHolder> {
    private RefreshListener refreshListener;

    public MotorAllAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_motor_all;
    }

    @Override
    protected void convert(ViewHolder holder, MotorDetailsBean item, int position) {
        switch (item.getOrderStatus()) {
            /** 1:等待调度
             2:等待发运
             3:在途运载
             4:货位引导
             5:等待回单
             6:等待确认
             7:已完成*/
            case "2":
                holder.mLlBottom.setVisibility(View.VISIBLE);
                holder.mLine.setVisibility(View.VISIBLE);
                holder.mRlBottom.setVisibility(View.GONE);
                holder.mTvType.setText("等待发运");
                holder.mTvPhone2.setOnClickListener(v -> {
                    MyUtils.call(context, item.getContactTel());
                });
                holder.mTvWaitDispatch.setOnClickListener(new WaitShipmentsTxtClick(Integer.valueOf(item.getOrderStatus()), position, item.getOrderid()));
                break;
            case "3":
                holder.mLlBottom.setVisibility(View.GONE);
                holder.mLine.setVisibility(View.VISIBLE);
                holder.mRlBottom.setVisibility(View.VISIBLE);
                holder.mTvType.setText("在途运载");
                holder.mTvRestore.setText("到货确认");
                holder.mTvRestore.setOnClickListener(new WaitShipmentsTxtClick(Integer.valueOf(item.getOrderStatus()), position, item.getOrderid()));
                break;
            case "5":
                holder.mLlBottom.setVisibility(View.GONE);
                holder.mLine.setVisibility(View.VISIBLE);
                holder.mRlBottom.setVisibility(View.VISIBLE);
                holder.mTvType.setText("等待回单");
                holder.mTvRestore.setOnClickListener(new WaitShipmentsTxtClick(Integer.valueOf(item.getOrderStatus()), position,item.getOrderid()));
                break;
            case "6":
                holder.mLlBottom.setVisibility(View.GONE);
                holder.mLine.setVisibility(View.VISIBLE);
                holder.mRlBottom.setVisibility(View.VISIBLE);
                holder.mTvType.setText("等待确认");
                holder.mTvRestore.setOnClickListener(new WaitShipmentsTxtClick(Integer.valueOf(item.getOrderStatus()), position, item.getOrderid()));
                break;
            case "7":
                holder.mLlBottom.setVisibility(View.GONE);
                holder.mLine.setVisibility(View.VISIBLE);
                holder.mRlBottom.setVisibility(View.VISIBLE);
                holder.mTvType.setText("已完成");
                break;
            default:
                break;
        }

        holder.mTvCancelDispatch.setOnClickListener(v -> {
            ConfirmDialog mConfirmDialog = new ConfirmDialog(context);
            mConfirmDialog.setTitle1("确认取消");
            mConfirmDialog.setCancelListener(v1 -> mConfirmDialog.dismiss());
            mConfirmDialog.setConfirmListener(v1 -> {
                SumbitData(String.valueOf(item.getOrderid()),String.valueOf(item.getProjectType()));
                mConfirmDialog.dismiss();
            });
            mConfirmDialog.show();
        });

        holder.mTvRestore.setOnClickListener(new WaitShipmentsTxtClick(Integer.valueOf(item.getOrderStatus()), position, item.getOrderid()));
        holder.mTvProjectName.setText(item.getProjectCode());
        holder.tvYundbh.setText(item.getOrderCode());
        holder.tvChengycl.setText(item.getCarPlateNumber());
        holder.tvHuowmc.setText(item.getCargoName());
        holder.tvFahdw.setText(item.getShipCompany());
        holder.tvShowhdw.setText(item.getReceiptCompany());
        holder.mTvPhone.setOnClickListener(v -> {
            MyUtils.call(context, item.getContactTel());
        });

        holder.mTvMessage2.setOnClickListener(v -> {
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

        holder.mTvMessage.setOnClickListener(v -> {

            /**
             * 启动单聊界面。
             *
             * @param context      应用上下文。
             * @param targetUserId 要与之聊天的用户 Id。
             * @param title        聊天的标题，开发者需要在聊天界面通过 intent.getData().getQueryParameter("title")
             *                     获取该值, 再手动设置为聊天界面的标题。
             */
            if (!TextUtils.isEmpty(item.getDriverId())) {
                RongIM.getInstance().startPrivateChat(context, "S" + item.getDriverId(), "");
            }
        });
    }

    class ViewHolder {
        @BindView(R.id.tv_project_name)
        TextView mTvProjectName;
        @BindView(R.id.ll_project_name)
        LinearLayout mLlProjectName;
        @BindView(R.id.line)
        View mLine;
        @BindView(R.id.tv_restore)
        TextView mTvRestore;
        @BindView(R.id.tv_message)
        TextView mTvMessage;
        @BindView(R.id.tv_phone)
        TextView mTvPhone;
        @BindView(R.id.rl_bottom)
        RelativeLayout mRlBottom;
        @BindView(R.id.tv_wait_dispatch)
        TextView mTvWaitDispatch;
        @BindView(R.id.tv_cancel_dispatch)
        TextView mTvCancelDispatch;
        @BindView(R.id.tv_message2)
        TextView mTvMessage2;
        @BindView(R.id.tv_phone2)
        TextView mTvPhone2;
        @BindView(R.id.ll_bottom)
        LinearLayout mLlBottom;
        @BindView(R.id.ll_motor_all)
        LinearLayout mLlMotorAll;

        @BindView(R.id.tv_type)
        TextView mTvType;                       //运单状态 textView

        @BindView(R.id.motor_item_tv_yundbh)
        TextView tvYundbh;                      //运单编号 textView

        @BindView(R.id.motor_item_tv_chengycl)
        TextView tvChengycl;                    //承运车辆 textView

        @BindView(R.id.motor_item_tv_huowmc)
        TextView tvHuowmc;                      //货物名称 textView

        @BindView(R.id.motor_item_tv_fahdw)
        TextView tvFahdw;                       //发货单位 textView

        //收货单位 textView
        @BindView(R.id.motor_item_tv_shouhdw)
        TextView tvShowhdw;


        ViewHolder(View view) {
            KnifeKit.bind(this, view);
        }
    }

    private class WaitShipmentsTxtClick implements View.OnClickListener {
        private int position;
        private int type;
        private int orderid;

        public WaitShipmentsTxtClick(int type, int position, int orderid) {
            this.position = position;
            this.type = type;
            this.orderid = orderid;
        }

        @Override
        public void onClick(View v) {
            switch (type) {
                case 2:
                    Intent intent = new Intent(context, AddFayxxActivity.class);
                    Bundle data = new Bundle();
                    data.putSerializable("MotorDetails", (Serializable) getDataSource().get(position));
                    intent.putExtras(data);
//                    intent.putExtra("orderid", String.valueOf(getDataSource().get(position).getOrderid()));
                    context.startActivity(intent);
                    break;
                case 3:
                    ConfirmDialog mConfirmDialog = new ConfirmDialog(context);
                    mConfirmDialog.setTitle1("确认到货");
                    mConfirmDialog.setCancelListener(v1 -> mConfirmDialog.dismiss());
                    mConfirmDialog.setConfirmListener(v1 -> {
                        commintData(String.valueOf(orderid));
                        mConfirmDialog.dismiss();
                    });
                    mConfirmDialog.show();
                    break;
                case 5:
                    Intent intent1 = new Intent(context, AddArrivexxActivity.class);
                    Bundle data1 = new Bundle();
                    data1.putSerializable("MotorDetails", (Serializable) getDataSource().get(position));
                    intent1.putExtras(data1);
//                    intent1.putExtra("orderid", String.valueOf(getDataSource().get(position).getOrderid()));
                    context.startActivity(intent1);
                    break;
                default:
                    break;
            }

        }
    }

    private void commintData(String orderid) {

        Map<String, Object> params = ApiRetrofit.getInstance().updateMotorStatus(orderid,"4");

        Observable<RequestResults> observable = HttpManager.getInstance()
                .getOrderService().updateMotorStatus(params);

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("保存到货通知信息成功！");
                    refreshListener.refresh();
                });
        HttpManager.getInstance().statrPostTask(observable, observer);

    }

    /**
     * 提交信息
     */
    private void SumbitData(String orderId,String projectType) {
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .cancelMotorOrder(ApiRetrofit.getInstance().cancelMotorOrder(orderId,projectType));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("发运已取消");
                    refreshListener.refresh();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    public interface RefreshListener {
        void refresh();
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }
}

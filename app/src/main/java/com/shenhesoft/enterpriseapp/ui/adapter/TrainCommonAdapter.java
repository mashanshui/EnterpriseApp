package com.shenhesoft.enterpriseapp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.TrainBean;
import com.shenhesoft.enterpriseapp.ui.activity.train.AllowActivity;
import com.shenhesoft.enterpriseapp.ui.activity.train.EntruckingActivity;
import com.shenhesoft.enterpriseapp.ui.activity.train.TrackinthewayActivity;
import com.shenhesoft.enterpriseapp.ui.activity.train.UnloadingActivity;
import com.shenhesoft.enterpriseapp.ui.activity.train.WaitCallbackActivity;
import com.shenhesoft.enterpriseapp.ui.activity.train.WaitSendActivity;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 异常适配器
 */

public class TrainCommonAdapter extends SimpleListAdapter<TrainBean, TrainCommonAdapter.ViewHolder> {


    public TrainCommonAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_train_common;
    }

    @Override
    protected void convert(ViewHolder holder, TrainBean item, int position) {
        Drawable rightDrawable;
        switch (item.getOrderStatus()) {
            case 1:
                holder.mLlTrainNum.setVisibility(View.VISIBLE);
                holder.mLlGoodsNum.setVisibility(View.GONE);
                holder.mTvRequsetOrAllow.setText("请车数：");
                holder.tv_request_num.setText(item.getInviteTrainNum());
                holder.mTvState.setText("等待承认");
                rightDrawable = context.getResources().getDrawable(R.drawable.wait_admit);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.mTvState.setCompoundDrawables(rightDrawable, null, null, null);
                break;
            case 2:
                holder.mLlTrainNum.setVisibility(View.VISIBLE);
                holder.mLlGoodsNum.setVisibility(View.GONE);
                holder.mTvRequsetOrAllow.setText("承认车数：");
                holder.tv_request_num.setText(item.getAdmitTrainNum());
                holder.mTvState.setText("等待装车");
                rightDrawable = context.getResources().getDrawable(R.drawable.ic_wait_entrucking);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.mTvState.setCompoundDrawables(rightDrawable, null, null, null);
                break;
            case 3:
                holder.mLlTrainNum.setVisibility(View.VISIBLE);
                holder.mLlGoodsNum.setVisibility(View.VISIBLE);
                holder.mTvRequsetOrAllow.setText("装车数：");
                holder.tv_request_num.setText(item.getAdmitTrainNum());
                holder.mTvState.setText("等待发运");
                rightDrawable = context.getResources().getDrawable(R.drawable.ic_wait_shipment);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.mTvState.setCompoundDrawables(rightDrawable, null, null, null);
                break;
            case 4:
                holder.mLlTrainNum.setVisibility(View.VISIBLE);
                holder.mLlGoodsNum.setVisibility(View.VISIBLE);
                holder.mTvRequsetOrAllow.setText("装车数：");
                holder.tv_request_num.setText(item.getAdmitTrainNum());
                holder.mTvState.setText("在途运载");
                rightDrawable = context.getResources().getDrawable(R.drawable.ic_loading);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.mTvState.setCompoundDrawables(rightDrawable, null, null, null);
                break;
            case 5:
                holder.mLlTrainNum.setVisibility(View.VISIBLE);
                holder.mLlGoodsNum.setVisibility(View.VISIBLE);
                holder.mTvRequsetOrAllow.setText("装车数：");
                holder.tv_request_num.setText(item.getAdmitTrainNum());
                holder.mTvState.setText("等待卸货");
                rightDrawable = context.getResources().getDrawable(R.drawable.ic_wait_unloading);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.mTvState.setCompoundDrawables(rightDrawable, null, null, null);
                break;
            case 6:
                holder.mLlTrainNum.setVisibility(View.GONE);
                holder.mLlGoodsNum.setVisibility(View.GONE);
                holder.mTvState.setText("等待回单");
                rightDrawable = context.getResources().getDrawable(R.drawable.ic_wait_bill);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                holder.mTvState.setCompoundDrawables(rightDrawable, null, null, null);
                break;
            case 7:
                holder.mRlBottom.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        holder.mTvProjectName.setText(item.getProjectCode());
        holder.tvQingcdh.setText(item.getPleaseTrainNumber());
        holder.tvFahdw.setText(item.getBeginPlace());
        holder.tvShouhdw.setText(item.getEndPlace());
        holder.tvHuowmc.setText(item.getCargoName());
        holder.tvShifzd.setText(item.getShipStation());
        holder.tvDaodzd.setText(item.getReceiptStation());
        holder.tv_goods_num.setText(item.getEntruckWeight());

        holder.mTvState.setOnClickListener(v -> {
            if (item.getOrderStatus() == 1) {
                Router.newIntent((Activity) context).to(AllowActivity.class).putSerializable("trainbean", item).putString("orderId", String.valueOf(item.getId())).launch();
            } else if (item.getOrderStatus() == 2) {
                Router.newIntent((Activity) context).to(EntruckingActivity.class).putSerializable("trainbean", item).putString("orderId", String.valueOf(item.getId())).launch();
            } else if (item.getOrderStatus() == 3) {
                Router.newIntent((Activity) context).to(WaitSendActivity.class).putSerializable("trainbean", item).putString("orderId", String.valueOf(item.getId())).launch();
            } else if (item.getOrderStatus() == 4) {
                Router.newIntent((Activity) context).to(TrackinthewayActivity.class).putSerializable("trainbean", item).putString("orderId", String.valueOf(item.getId())).launch();
            } else if (item.getOrderStatus() == 5) {
                Router.newIntent((Activity) context).to(UnloadingActivity.class).putSerializable("trainbean", item).putString("orderId", String.valueOf(item.getId())).launch();
            } else if (item.getOrderStatus() == 6) {
                Router.newIntent((Activity) context).to(WaitCallbackActivity.class).putSerializable("trainbean", item).putString("orderId", String.valueOf(item.getId())).launch();
            }
//            Router.newIntent((Activity) context).to(AllowActivity.class).putString("orderId", String.valueOf(item.getId())).launch();
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
        @BindView(R.id.tv_request_or_allow)
        TextView mTvRequsetOrAllow;
        @BindView(R.id.tv_phone)
        TextView mTvPhone;
        @BindView(R.id.ll_goods_num)
        LinearLayout mLlGoodsNum;
        @BindView(R.id.ll_request_train)
        LinearLayout mLlTrainNum;

        //请车数
        @BindView(R.id.tv_request_num)
        TextView tv_request_num;
        //请车单号
        @BindView(R.id.train_item_tv_qingcdh)
        TextView tvQingcdh;
        //货物名称
        @BindView(R.id.train_item_tv_huowmc)
        TextView tvHuowmc;
        //发货单位
        @BindView(R.id.train_item_tv_fahdw)
        TextView tvFahdw;
        //始发站点
        @BindView(R.id.train_item_tv_shifzd)
        TextView tvShifzd;
        //收货单位
        @BindView(R.id.train_item_tv_shouhdw)
        TextView tvShouhdw;
        //到达站点
        @BindView(R.id.train_item_tv_daodzd)
        TextView tvDaodzd;
        //装载吨位
        @BindView(R.id.tv_goods_num)
        TextView tv_goods_num;

        @BindView(R.id.rl_bottom)
        LinearLayout mRlBottom;

        ViewHolder(View view) {
            KnifeKit.bind(this, view);
        }
    }


//    /**
//     * 提交信息
//     */
//    private void SumbitData() {
//        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
//                .saveTbTrainorderCarNum(ApiRetrofit.getInstance().saveTbTrainorderCarNum(orderId, MEt_allow_number.getText().toString()));
//
//        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
//                data -> {
//                    if (data.getState() != 1) {
//                        IToast.showShort(data.getMsg());
//                        return;
//                    }
//                    IToast.showShort("发运已取消");
//                });
//
//        HttpManager.getInstance().statrPostTask(observable, observer);
//    }


}

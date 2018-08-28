package com.shenhesoft.enterpriseapp.ui.activity.auditing;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.FeePayBean;
import com.shenhesoft.enterpriseapp.bean.MotorAllBean;
import com.shenhesoft.enterpriseapp.ui.adapter.WaitDispatchAdapter;
import com.shenhesoft.enterpriseapp.ui.adapter.iml.MyOnClicklistener;
import com.shenhesoft.enterpriseapp.utils.MyUtils;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 审核费用支出
 */

public class FeePayAdapter extends SimpleListAdapter<FeePayBean, FeePayAdapter.ViewHolder> {

    private MyOnClicklistener myOnClicklistener;

    public FeePayAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_feepay;
    }

    @Override
    protected void convert(ViewHolder holder, FeePayBean item, int position) {

        holder.tv_projectcode.setText(item.getOrderCode());
        holder.tv_time.setText(item.getReceipterDate());
        holder.tv_car_number.setText(item.getCarPlateNumber());
        holder.tv_add_fee.setText(item.getSubsidy());
        holder.tv_need_fee.setText(item.getShouldPayFigure());
        holder.tv_tag.setText(item.getFinanceStatusNodeName());
        holder.btn_sumit.setOnClickListener(v -> {
            if (myOnClicklistener != null) {
                myOnClicklistener.onclick(position, 0);
            }
        });

    }

    public void setOnClicklistener(MyOnClicklistener myOnClicklistener) {
        this.myOnClicklistener = myOnClicklistener;
    }

    class ViewHolder {
        @BindView(R.id.tv_projectcode)
        TextView tv_projectcode;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_car_number)
        TextView tv_car_number;
        @BindView(R.id.tv_add_fee)
        TextView tv_add_fee;
        @BindView(R.id.tv_need_fee)
        TextView tv_need_fee;
        @BindView(R.id.btn_sumit)
        TextView btn_sumit;
        @BindView(R.id.tv_tag)
        TextView tv_tag;

        ViewHolder(View view) {
            KnifeKit.bind(this, view);
        }
    }
}

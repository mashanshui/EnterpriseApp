package com.shenhesoft.enterpriseapp.ui.activity.auditing;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.DriverCheckBean;
import com.shenhesoft.enterpriseapp.bean.FeePayBean;
import com.shenhesoft.enterpriseapp.ui.adapter.iml.MyOnClicklistener;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 审核费用支出
 */

public class DriverCheckAdapter extends SimpleListAdapter<DriverCheckBean, DriverCheckAdapter.ViewHolder> {

    private MyOnClicklistener myOnClicklistener;

    public DriverCheckAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_drivercheck;
    }

    @Override
    protected void convert(ViewHolder holder, DriverCheckBean item, int position) {

        holder.tv_projectcode.setText(item.getShPackId());
        holder.tv_time.setText(item.getCreateDate());
        holder.tv_car_number.setText(item.getPaymentName());
        holder.tv_add_fee.setText(item.getPayRatio());
        holder.tv_need_fee.setText(item.getFreightChargeAmount());
        holder.tv_need_gasoline.setText(item.getSuppliesAmount());
        holder.tv_person.setText(item.getDriverName());
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
        @BindView(R.id.tv_need_gasoline)
        TextView tv_need_gasoline;
        @BindView(R.id.tv_person)
        TextView tv_person;

        ViewHolder(View view) {
            KnifeKit.bind(this, view);
        }
    }
}

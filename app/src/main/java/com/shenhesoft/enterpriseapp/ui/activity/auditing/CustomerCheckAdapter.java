package com.shenhesoft.enterpriseapp.ui.activity.auditing;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.CustomerCheckBean;
import com.shenhesoft.enterpriseapp.bean.DriverCheckBean;
import com.shenhesoft.enterpriseapp.ui.adapter.iml.MyOnClicklistener;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 审核客户对账
 */

public class CustomerCheckAdapter extends SimpleListAdapter<CustomerCheckBean, CustomerCheckAdapter.ViewHolder> {

    private MyOnClicklistener myOnClicklistener;

    public CustomerCheckAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_customercheck;
    }

    @Override
    protected void convert(ViewHolder holder, CustomerCheckBean item, int position) {

        holder.tv_projectcode.setText(item.getCheckId());
        holder.tv_time.setText(item.getModifiyDate());
        holder.tv_car_number.setText(item.getProduceMoney());
        holder.tv_add_fee.setText(item.getTaxMoney());
        holder.tv_need_fee.setText(item.getCheckDate());
        holder.tv_need_gasoline.setText(item.getOrderCount());

//        packType 0 汽运 1 接取 2 送达 3 火运 9对账明细
        String type = "";
        if (item.getPackType().equals("0")) {
            type = "汽运";
        } else if (item.getPackType().equals("1")) {
            type = "接取";
        } else if (item.getPackType().equals("2")) {
            type = "送达";
        } else if (item.getPackType().equals("3")) {
            type = "火运";
        } else if (item.getPackType().equals("9")) {
            type = "对账明细";
        }


        holder.tv_tag.setText(type);
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


        ViewHolder(View view) {
            KnifeKit.bind(this, view);
        }
    }
}

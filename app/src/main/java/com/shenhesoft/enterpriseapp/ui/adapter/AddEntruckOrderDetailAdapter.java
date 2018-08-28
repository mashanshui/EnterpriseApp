package com.shenhesoft.enterpriseapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderDetailBean;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 异常适配器
 */

public class AddEntruckOrderDetailAdapter extends SimpleListAdapter<EntruckOrderDetailBean, AddEntruckOrderDetailAdapter.ViewHolder> {

    public AddEntruckOrderDetailAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_addentruck_orderdetail;
    }

    @Override
    protected void convert(ViewHolder holder, EntruckOrderDetailBean item, int position) {
        holder.mTvCarType.setText(item.getCarType() + "  " + item.getCarNumber());
        holder.mTvName.setText("详单" + (position + 1));

    }

    class ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_car_type)
        TextView mTvCarType;


        private ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }
}

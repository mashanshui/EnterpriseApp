package com.shenhesoft.enterpriseapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderBean;
import com.shenhesoft.enterpriseapp.bean.WaitDispatchBean;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 异常适配器
 */

public class EntruckListAdapter extends SimpleListAdapter<EntruckOrderBean, EntruckListAdapter.ViewHolder> {

    public EntruckListAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_entruck_list;
    }

    @Override
    protected void convert(ViewHolder holder, EntruckOrderBean item, int position) {

        holder.tv_yard.setText(item.getCargoPlaceName());
        holder.tv_location.setText(item.getCargoSiteName());
        holder.tvZhuangcxd.setText("(已填写" + item.getOrderDetail().size() + ")");

    }

    class ViewHolder {
        @BindView(R.id.tv_loading_train_common_yard)
        TextView tv_yard;

        @BindView(R.id.tv_loading_train_common_location)
        TextView tv_location;

        @BindView(R.id.tv_loading_train_zhuangcxd)
        TextView tvZhuangcxd;

        private ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }
}

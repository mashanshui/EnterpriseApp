package com.shenhesoft.enterpriseapp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderDetailBean;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author zmx
 * @date 2017/12/4
 * @desc 异常适配器
 */

public class UnloadOrderAdapter extends SimpleListAdapter<EntruckOrderDetailBean, UnloadOrderAdapter.ViewHolder> {

    private OnCheckChangeListener mChangeListener;
    private int type;

    public UnloadOrderAdapter(Context context, int type) {
        super(context);
        this.type = type;
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_unload_order;
    }

    @Override
    protected void convert(ViewHolder holder, EntruckOrderDetailBean item, int position) {
        holder.mTvCarType.setText(item.getCarType() + "  " + item.getCarNumber());
        holder.mTvName.setText("详单" + (position + 1));
        holder.mTvPlace.setText(item.getGoodsPlace());
        holder.mTvSite.setText(item.getGoodsSite());
        if (!TextUtils.isEmpty(item.getGoodsPlace()) && !TextUtils.isEmpty(item.getGoodsSite())) {
            holder.img_unload_info.setVisibility(View.VISIBLE);
        } else {
            holder.img_unload_info.setVisibility(View.GONE);
        }

        if (type == 0) {
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.mCheckBox.setOnClickListener(v -> {
                getDataSource().get(position).setChecked(holder.mCheckBox.isChecked());
                if (mChangeListener != null)
                    mChangeListener.OnChange();
            });
            holder.mCheckBox.setChecked(item.isChecked());
        } else {
            holder.mCheckBox.setVisibility(View.GONE);
        }
    }

    class ViewHolder {
        @BindView(R.id.checkbox)
        CheckBox mCheckBox;

        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_car_type)
        TextView mTvCarType;

        @BindView(R.id.tv_goods_place)
        TextView mTvPlace;
        @BindView(R.id.tv_goods_site)
        TextView mTvSite;

        @BindView(R.id.img_unload_info)
        ImageView img_unload_info;


        private ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }

    }

    public void setChangeListener(OnCheckChangeListener changeListener) {
        mChangeListener = changeListener;
    }


    public interface OnCheckChangeListener {
        void OnChange();
    }
}

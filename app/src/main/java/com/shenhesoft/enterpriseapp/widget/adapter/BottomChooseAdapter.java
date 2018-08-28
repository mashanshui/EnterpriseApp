package com.shenhesoft.enterpriseapp.widget.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/6
 * @desc TODO
 */

public class BottomChooseAdapter extends SimpleListAdapter<LocationPopBean, BottomChooseAdapter.ViewHolder> {

    private int mposition;

    public BottomChooseAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_reject_reason;
    }

    @Override
    protected void convert(ViewHolder holder, LocationPopBean item, int position) {
        if (position == mposition)
            holder.mCheckbox.setChecked(true);
        else
            holder.mCheckbox.setChecked(false);
        holder.mTvReason.setText(item.getName());
    }

    public void SetPosition(int position) {
        this.mposition = position;
        notifyDataSetChanged();
    }

    class ViewHolder {
        @BindView(R.id.tv_reason)
        TextView mTvReason;
        @BindView(R.id.checkbox)
        CheckBox mCheckbox;

        private ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }
}

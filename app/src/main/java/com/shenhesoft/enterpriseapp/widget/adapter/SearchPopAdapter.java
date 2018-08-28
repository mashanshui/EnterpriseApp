package com.shenhesoft.enterpriseapp.widget.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.LocationBean;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc TODO
 */

public class SearchPopAdapter extends SimpleListAdapter<LocationBean, SearchPopAdapter.ViewHolder> {
    public SearchPopAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_location_pop;
    }

    @Override
    protected void convert(ViewHolder holder, LocationBean item, int position) {
        holder.mTvName.setText(item.getCarNo());
    }


    public static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;

        private ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }
}

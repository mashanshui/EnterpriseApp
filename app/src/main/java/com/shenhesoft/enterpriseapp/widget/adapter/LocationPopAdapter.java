package com.shenhesoft.enterpriseapp.widget.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.LocationBean;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.bean.ProjectBean;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc TODO
 */

public class LocationPopAdapter extends SimpleListAdapter<ProjectBean, LocationPopAdapter.ViewHolder> {
    public LocationPopAdapter(Context context) {
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
    protected void convert(ViewHolder holder, ProjectBean item, int position) {
        holder.mTvName.setText(item.getProjectCode());
    }


    public static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;

        private ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }
}

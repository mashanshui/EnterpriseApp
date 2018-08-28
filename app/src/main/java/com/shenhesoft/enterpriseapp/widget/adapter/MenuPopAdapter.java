package com.shenhesoft.enterpriseapp.widget.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.MenuPopBean;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc TODO
 */

public class MenuPopAdapter extends SimpleListAdapter<MenuPopBean, MenuPopAdapter.ViewHolder> {
    public MenuPopAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_pop_menu;
    }

    @Override
    protected void convert(ViewHolder holder, MenuPopBean item, int position) {
        holder.mTvTitle.setText(item.getTitle());
    }


    public static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        private ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }
}

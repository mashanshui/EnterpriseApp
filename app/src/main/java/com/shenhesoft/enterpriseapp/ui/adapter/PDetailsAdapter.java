package com.shenhesoft.enterpriseapp.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.PDetailsChildItem;
import com.shenhesoft.enterpriseapp.bean.PDetailsRootItem;

import java.util.List;

/**
 * 作者：
 * 创作日期：2018/1/2.
 * 描述：项目详情 适配器
 */

public class PDetailsAdapter extends BaseExpandableListAdapter {

    private List<PDetailsRootItem> data;
    private LayoutInflater mInflater;
    private Context context;

    public PDetailsAdapter(List<PDetailsRootItem> data, Context context) {
        this.data = data;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getChildItems().size();
    }

    @Override
    public PDetailsRootItem getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public PDetailsChildItem getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getChildItems().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 绑定父级项视图
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        RootViewHolder rHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_exlv_pdetails_root, null);
            rHolder = new RootViewHolder(convertView);
            convertView.setTag(rHolder);
        } else {
            rHolder = (RootViewHolder) convertView.getTag();
        }

        rHolder.imgIcon.setImageResource(data.get(groupPosition).getLeftIconResId());
        rHolder.tvRName.setText(data.get(groupPosition).getrName());
        if (data.get(groupPosition).getStatus() == 1) {
            //异常状态 文字黄色
            rHolder.tvRName.setTextColor(Color.parseColor("#FFB515"));
        } else {
            //正常状态 还原
            rHolder.tvRName.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    /**
     *  绑定子项视图
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder cHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_exlv_pdetails_child, null);
            cHolder = new ChildViewHolder(convertView);
            convertView.setTag(cHolder);
        } else {
            cHolder = (ChildViewHolder) convertView.getTag();
        }
        cHolder.tvCName.setText(data.get(groupPosition).getChildItems().get(childPosition).getcName());
        cHolder.tvCValue.setText(data.get(groupPosition).getChildItems().get(childPosition).getcValue());

        if (childPosition == data.get(groupPosition).getChildItems().size() - 1) {
            //当前父项内最后一条子项后显示 一条粗粗的分割线
            cHolder.vline.setVisibility(View.VISIBLE);
        } else {
            cHolder.vline.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class RootViewHolder {
        ImageView imgIcon;
        TextView tvRName;

        public RootViewHolder(View view) {
            imgIcon = (ImageView) view.findViewById(R.id.pdetails_root_icon);
            tvRName = (TextView) view.findViewById(R.id.pdetails_root_tv_name);
        }
    }

    class ChildViewHolder {
        TextView tvCName;
        TextView tvCValue;
        View vline;

        public ChildViewHolder(View view) {
            tvCName = (TextView) view.findViewById(R.id.pdetails_child_tv_name);
            tvCValue = (TextView) view.findViewById(R.id.pdetails_child_tv_value);
            vline = view.findViewById(R.id.pdetails_child_line);
        }
    }
}

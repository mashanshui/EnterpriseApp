package com.shenhesoft.enterpriseapp.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.LocationBean;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.bean.ProjectBean;
import com.shenhesoft.enterpriseapp.widget.adapter.LocationPopAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 定位搜索pop
 */

public class LocationPop extends PopupWindow implements AdapterView.OnItemClickListener {
    private Context mContext;
    private ListView mListView;
    private LocationPopAdapter mAdapter;
    private onSelectListener listener;


    public LocationPop(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_location_pop, null);
        setContentView(contentView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(0));
        mListView = (ListView) contentView.findViewById(R.id.lv_location_pop);
        mAdapter = new LocationPopAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    public void setData(List<ProjectBean> list) {
        if (mAdapter != null) {
            mAdapter.setData(list);
        }
    }

    public boolean hasdata() {
        if (mAdapter != null && mAdapter.getDataSource().size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listener != null) {
            listener.getposition(position);
        }
    }

    public interface onSelectListener {
        void getposition(int position);
    }

    public void setOnselectListener(onSelectListener onselectListener) {
        this.listener = onselectListener;
    }
}

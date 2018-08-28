package com.shenhesoft.enterpriseapp.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.LocationBean;
import com.shenhesoft.enterpriseapp.widget.adapter.LocationPopAdapter;

import java.util.List;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 定位搜索pop
 */

public class SearchCarPop extends PopupWindow {
    private Context mContext;
    private ListView mListView;
    private LocationPopAdapter mAdapter;

    public SearchCarPop(Context context) {
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
    }

//    public void setData(List<LocationBean> list) {
//        if (mAdapter != null) {
//            mAdapter.setData(list);
//        }
//    }


}

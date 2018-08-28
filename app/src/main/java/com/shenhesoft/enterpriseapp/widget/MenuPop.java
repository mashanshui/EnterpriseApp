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
import com.shenhesoft.enterpriseapp.bean.MenuPopBean;
import com.shenhesoft.enterpriseapp.widget.adapter.MenuPopAdapter;

import java.util.List;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc menu pop
 */

public class MenuPop extends PopupWindow implements AdapterView.OnItemClickListener {
    private Context mContext;
    private ListView mListView;
    private MenuPopAdapter mAdapter;
    private List<MenuPopBean> mTitleList;
    private OnItemClickListener onItemClickListener;

    public MenuPop(Context context, List<MenuPopBean> titleList) {
        super(context);
        mContext = context;
        mTitleList = titleList;
        initView();
    }

    private void initView() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_menu, null);
        setContentView(contentView);
        setBackgroundDrawable(new ColorDrawable(0));
        mListView = (ListView) contentView.findViewById(R.id.listView);
        mAdapter = new MenuPopAdapter(mContext);
        mAdapter.setData(mTitleList);
        mListView.setAdapter(mAdapter);
        mListView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);//popwindow中ListView宽度不能自适应，必须要measure
        setWidth(mListView.getMeasuredWidth());
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        mListView.setOnItemClickListener(this);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onItemClickListener.OnitemClick(position);
    }

    public interface OnItemClickListener {
        void OnitemClick(int position);
    }

}

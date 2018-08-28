package com.shenhesoft.enterpriseapp.ui.fragment;

import android.os.Bundle;
import android.widget.ListView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseFragment;
import com.shenhesoft.enterpriseapp.bean.UnusualMessageBean;
import com.shenhesoft.enterpriseapp.ui.adapter.UnusualMessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 张继淮
 * @date 2017/12/5
 * @desc 异常消息fragment
 */

public class UnusualMessageFragment extends BaseFragment {
    @BindView(R.id.lv_message)
    ListView mListView;
    private UnusualMessageAdapter mAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mAdapter = new UnusualMessageAdapter(context);
        mListView.setAdapter(mAdapter);
        List<UnusualMessageBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UnusualMessageBean bean = new UnusualMessageBean();
            bean.setName("司机" + i);
            list.add(bean);
        }
        mAdapter.setData(list);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_unusual_message;
    }
}

package com.shenhesoft.enterpriseapp.ui.activity.waitdispatch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ExpandableListView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.ui.fragment.DismissedFragment;
import com.shenhesoft.enterpriseapp.ui.fragment.WaitDispatchFragment;
import com.shenhesoft.enterpriseapp.widget.MyTableLayout;
import com.shenhesoft.enterpriseapp.widget.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author 张继淮
 * @date 2017/12/5
 * @desc 等待调度界面
 */

public class WaitDispatchActivity extends BaseTitleActivity  {
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    MyTableLayout mTabLayout;
    private WaitDispatchFragment mWaitDispatchFragment;
    private DismissedFragment mDissmissedFragment;
    private ViewPagerAdapter adapter;
    private List<Fragment> fragments;

    @Override
    protected void initTitle() {
        setTitle(getString(R.string.wait_dispatch));
        setRight1Img(R.drawable.search_black, v -> {
            // TODO: 2017/12/5 搜索
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_tablayout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mWaitDispatchFragment = new WaitDispatchFragment();
        mDissmissedFragment = new DismissedFragment();
        fragments = new ArrayList<>();
        fragments.add(mWaitDispatchFragment);
        fragments.add(mDissmissedFragment);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setTitles(new String[]{"等待调度", "已驳回"});
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment ff = fragments.get(position);
                ff.onResume();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void setListener() {
    }

//    @Optional
//    @OnClick({R.id.rb_dismissed, R.id.rb_wait_dispatch})
//    public void OnClick(View view) {

//    }


}

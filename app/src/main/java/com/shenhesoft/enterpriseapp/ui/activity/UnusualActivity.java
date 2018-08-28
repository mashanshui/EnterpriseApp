package com.shenhesoft.enterpriseapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.ui.fragment.UnusualMessageFragment;
import com.shenhesoft.enterpriseapp.widget.MyTableLayout;
import com.shenhesoft.enterpriseapp.widget.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 异常消息activity
 */

public class UnusualActivity extends BaseTitleActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    MyTableLayout mTabLayout;
    private UnusualMessageFragment mAllFragment;
    private UnusualMessageFragment mUnreadFragment;
    private ViewPagerAdapter adapter;
    private List<Fragment> fragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_tablayout;
    }

    @Override
    protected void initTitle() {
        setTitle("异常提醒");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mAllFragment = new UnusualMessageFragment();
        mUnreadFragment = new UnusualMessageFragment();
        fragments = new ArrayList<>();
        fragments.add(mAllFragment);
        fragments.add(mUnreadFragment);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setTitles(new String[]{"全部","未读"});
        mTabLayout.setupWithViewPager(mViewPager);
    }

}

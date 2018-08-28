package com.shenhesoft.enterpriseapp.ui.activity.confirmarrive;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.ui.fragment.ConfirmUnusualFragment;
import com.shenhesoft.enterpriseapp.ui.fragment.UnusualFragment;
import com.shenhesoft.enterpriseapp.ui.fragment.WaitUnloadingFragment;
import com.shenhesoft.enterpriseapp.widget.MyTableLayout;
import com.shenhesoft.enterpriseapp.widget.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zmx
 * @date 2017/12/5
 * @desc 到货确认界面
 */

public class ConfirmArriveActivity extends BaseTitleActivity {
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    MyTableLayout mTabLayout;
    private WaitUnloadingFragment mWaitUnloadingFragment;
    private ConfirmUnusualFragment mUnusualFragment;
    private ViewPagerAdapter adapter;
    private List<Fragment> fragments;

    @Override
    protected void initTitle() {
        setTitle("到货确认");
//        setRight1Img(R.drawable.search_black,v -> {
//            // TODO: 2017/12/5 搜索
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_tablayout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mWaitUnloadingFragment = new WaitUnloadingFragment();
        mUnusualFragment = new ConfirmUnusualFragment();
        fragments = new ArrayList<>();
        fragments.add(mWaitUnloadingFragment);
        fragments.add(mUnusualFragment);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setTitles(new String[]{"等待卸货","异常运单"});
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void setListener() {
    }

//    @Optional
//    @OnClick({R.id.rb_dismissed, R.id.rb_wait_dispatch})
//    public void OnClick(View view) {

//    }
}

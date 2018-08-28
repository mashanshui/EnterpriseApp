package com.shenhesoft.enterpriseapp.ui.activity.feecheck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.ui.fragment.CheckUnusualFragment;
import com.shenhesoft.enterpriseapp.ui.fragment.FeeCheckFragment;
import com.shenhesoft.enterpriseapp.ui.fragment.UnusualFragment;
import com.shenhesoft.enterpriseapp.widget.MyTableLayout;
import com.shenhesoft.enterpriseapp.widget.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 张继淮
 * @date 2017/12/7
 * @desc 计费确认
 */

public class WaitConfirmChargingActivity extends BaseTitleActivity {
    @BindView(R.id.tab_layout)
    MyTableLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private CheckUnusualFragment mUnusualFragment2;
    private List<Fragment> mFragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_tablayout;
    }

    @Override
    protected void initTitle() {
        setTitle("计费确认");
//        setRight1Img(R.drawable.search_black, v -> {
//        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTabLayout.setTitles(new String[]{"等待确认", "异常运单"});
        mFragments = new ArrayList<>();
        mUnusualFragment2 = new CheckUnusualFragment();
        mFragments.add(new FeeCheckFragment());
        mFragments.add(mUnusualFragment2);
        mViewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));
        mTabLayout.setupWithViewPager(mViewpager);
    }

}

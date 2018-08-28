package com.shenhesoft.enterpriseapp.ui.activity.motor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.MenuPopBean;
import com.shenhesoft.enterpriseapp.ui.fragment.MotorAllProjectFragment;
import com.shenhesoft.enterpriseapp.widget.MenuPop;
import com.shenhesoft.enterpriseapp.widget.MyTableLayout;
import com.shenhesoft.enterpriseapp.widget.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.log.XLog;

/**
 * @author 张继淮
 * @date 2017/12/7
 * @desc 汽运短驳
 */

public class MotorActivity extends BaseTitleActivity {

    @BindView(R.id.tab_layout)
    MyTableLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.dark_view)
    View mDarkView;

    private List<Fragment> mFragments;
    private MotorAllProjectFragment motorAllProjectFragment;
    private MotorAllProjectFragment motorAllProjectFragment1;
    private MotorAllProjectFragment motorAllProjectFragment2;
    private MotorAllProjectFragment motorAllProjectFragment3;
    private MenuPop mMenuPop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_tablayout;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void initTitle() {
        setTitle("汽运短驳");

        setRight1Img(R.drawable.add, v ->
        {
            Intent intent = new Intent(this, AddNewOrderActivity.class);
            startActivity(intent);
        });
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        motorAllProjectFragment = MotorAllProjectFragment.newInstance("2,3,5");
        motorAllProjectFragment1 = MotorAllProjectFragment.newInstance("2");
        motorAllProjectFragment2 = MotorAllProjectFragment.newInstance("3");
        motorAllProjectFragment3 = MotorAllProjectFragment.newInstance("5");
        mFragments = new ArrayList<>();
        mFragments.add(motorAllProjectFragment);
        mFragments.add(motorAllProjectFragment1);
        mFragments.add(motorAllProjectFragment2);
        mFragments.add(motorAllProjectFragment3);

        mViewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));
        mTabLayout.setTitles(new String[]{"全部", "等待发运", "在途运载", "等待回单"});
        mTabLayout.setupWithViewPager(mViewpager);
//        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


    }

}

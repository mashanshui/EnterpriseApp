package com.shenhesoft.enterpriseapp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.shenhesoft.enterpriseapp.base.BaseFragment;

import java.util.List;

/**
 * Viewpager+Fragment的适配器
 */
public class MyFragmentVpAdapter<T extends BaseFragment> extends FragmentPagerAdapter {

    private List<T> fragments;

    public MyFragmentVpAdapter(FragmentManager fm, List<T> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

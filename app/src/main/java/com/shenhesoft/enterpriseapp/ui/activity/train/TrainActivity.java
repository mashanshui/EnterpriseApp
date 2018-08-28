package com.shenhesoft.enterpriseapp.ui.activity.train;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.MenuPopBean;
import com.shenhesoft.enterpriseapp.ui.adapter.TraininthewayAdapter;
import com.shenhesoft.enterpriseapp.ui.fragment.TrainCommonFragment;
import com.shenhesoft.enterpriseapp.widget.MenuPop;
import com.shenhesoft.enterpriseapp.widget.MyTableLayout;
import com.shenhesoft.enterpriseapp.widget.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.log.XLog;

/**
 * @author 张明星
 * @date 2017/12/23
 * @desc 火运干线
 */

public class TrainActivity extends BaseTitleActivity {

    //===============订单状态===============

    public final static String TRAIN_STATUS_ALL = "1,2,3,4,5,6"; //全部
    public final static String TRAIN_STATUS_DENGDCR = "1"; //等待承认
    public final static String TRAIN_STATUS_DENGDZC = "2"; //等待装载
    public final static String TRAIN_STATUS_DENGDFY = "3"; //等待发运
    public final static String TRAIN_STATUS_ZAITYZ = "4";  //在途运载
    public final static String TRAIN_STATUS_DENGDXH = "5"; //等待卸货
    public final static String TRAIN_STATUS_DENGDHD = "6"; //等待回单
    public final static String TRAIN_STATUS_COMPLETE = "7"; //已完成

    @BindView(R.id.tab_layout)
    MyTableLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.dark_view)
    View mDarkView;
    private List<Fragment> mFragments;
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
        setTitle("火运干线");
        setRight1Img(R.drawable.add, v -> {
            Intent intent = new Intent(this, AddTrainOrderActivity.class);
            startActivity(intent);
        });

    }

    private void showMenu() {
        if (mMenuPop == null) {
            List<MenuPopBean> list = new ArrayList<>();
            MenuPopBean bean1 = new MenuPopBean();
            bean1.setTitle("新建集装箱运单");
            MenuPopBean bean2 = new MenuPopBean();
            bean2.setTitle("新建散堆装运单");
            list.add(bean1);
            list.add(bean2);
            mMenuPop = new MenuPop(this, list);
            mMenuPop.setOnDismissListener(() -> showDarkView(false));
            mMenuPop.setOnItemClickListener(position -> {
                XLog.d(list.get(position).getTitle());
            });
        }

        if (!mMenuPop.isShowing()) {
            mMenuPop.showAsDropDown(mLlIv2);
            showDarkView(true);
        }
    }

    public void showDarkView(boolean show) {
        if (show) {
            mDarkView.setVisibility(View.VISIBLE);
            mDarkView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dark_fade_in));
        } else {
            mDarkView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dark_fade_out));
            mDarkView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mFragments.add(TrainCommonFragment.newInstance(TRAIN_STATUS_ALL));
        mFragments.add(TrainCommonFragment.newInstance(TRAIN_STATUS_DENGDCR));
        mFragments.add(TrainCommonFragment.newInstance(TRAIN_STATUS_DENGDZC));
        mFragments.add(TrainCommonFragment.newInstance(TRAIN_STATUS_DENGDFY));
        mFragments.add(TrainCommonFragment.newInstance(TRAIN_STATUS_ZAITYZ));
        mFragments.add(TrainCommonFragment.newInstance(TRAIN_STATUS_DENGDXH));
        mFragments.add(TrainCommonFragment.newInstance(TRAIN_STATUS_DENGDHD));
        mFragments.add(TrainCommonFragment.newInstance(TRAIN_STATUS_COMPLETE));

        mViewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));
        mTabLayout.setTitles(new String[]{"全部", "等待承认", "等待装车", "等待发运", "在途运载", "等待卸货", "等待回单","已完成"});
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

}

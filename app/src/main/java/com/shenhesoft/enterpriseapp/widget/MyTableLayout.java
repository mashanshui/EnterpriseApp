package com.shenhesoft.enterpriseapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import static io.rong.imkit.utilities.RongUtils.dip2px;
import static io.rong.imkit.utilities.RongUtils.getScreenWidth;
import static io.rong.imkit.utilities.RongUtils.px2dip;

/**
 * @author 张继淮
 * @date 2017/12/6
 * @desc setTitle
 */

public class MyTableLayout extends TabLayout {
    private String[] titles;

    public MyTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //在setupWithViewPager之前操作
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public MyTableLayout(Context context) {
        super(context);
    }

    //viewpager要先setAdapter
    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        for (int i = 0; i < getTabCount(); i++) {
            getTabAt(i).setText(titles[i]);
        }
//        tt();

    }

    private void tt() {
        try {
            int pxvalue = dip2px(0);
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) this.getChildAt(0);
//            mTabLayout.setMinimumWidth(50);
            int dp10 = px2dip((getScreenWidth() / titles.length - pxvalue) / 2);

            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);

                //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                mTextViewField.setAccessible(true);

                TextView mTextView = (TextView) mTextViewField.get(tabView);

                tabView.setPadding(0, 0, 0, 0);

                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                int width = 0;
                width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }

                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width;
                params.leftMargin = dp10;
                params.rightMargin = dp10;
                tabView.setLayoutParams(params);

                tabView.invalidate();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

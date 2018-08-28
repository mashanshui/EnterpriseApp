package com.shenhesoft.enterpriseapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * @author 马山水
 * @date 2018/3/27
 * @desc 普通的ExpandableListView外层签嵌套了ScrollView后会显示不出子层数据，这里解决了这个问题
 * 具体使用在火运卸货的页面
 */

public class NestedExpandaleListView extends ExpandableListView {
    public NestedExpandaleListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public NestedExpandaleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public NestedExpandaleListView(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        //将重新计算的高度传递回去
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

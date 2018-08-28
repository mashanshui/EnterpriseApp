package com.shenhesoft.enterpriseapp.ui.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.UnusualMessageBean;

import butterknife.BindView;
import cn.carbs.android.expandabletextview.library.ExpandableTextView;
import cn.droidlover.xdroidmvp.base.SimpleListAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 异常消息适配器
 */

public class UnusualMessageAdapter extends SimpleListAdapter<UnusualMessageBean, UnusualMessageAdapter.ViewHolder> implements ExpandableTextView.OnExpandListener {
    private SparseArray<Integer> mPositionsAndStates;
    private int etvWidth;

    public UnusualMessageAdapter(Context context) {
        super(context);
        mPositionsAndStates = new SparseArray<>();
    }

    @Override
    protected ViewHolder newViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_unusual_message;
    }

    @Override
    protected void convert(ViewHolder holder, UnusualMessageBean item, int position) {
        holder.mTvName.setText(item.getName());
        holder.mTextView.setExpandListener(this);
        //todo lib有bug：有时候部分view宽度会显示不全
        holder.ll_content.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY);
        if (etvWidth == 0) {
            holder.mTextView.post(() -> etvWidth = holder.mTextView.getWidth());
        }
        Integer state = mPositionsAndStates.get(position);
        holder.mTextView.updateForRecyclerView("djlasjdajdadakdakdakdkapdkakdpadss" +
                "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss", etvWidth, state == null ? 0 : state);
    }

    @Override
    public void onExpand(ExpandableTextView view) {
        Object obj = view.getTag();
        if (obj != null && obj instanceof Integer) {
            mPositionsAndStates.put((Integer) obj, view.getExpandState());
        }
    }

    @Override
    public void onShrink(ExpandableTextView view) {
        Object obj = view.getTag();
        if (obj != null && obj instanceof Integer) {
            mPositionsAndStates.put((Integer) obj, view.getExpandState());
        }
    }


    public static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.iv_header)
        ImageView mIvHeader;
        @BindView(R.id.etv)
        ExpandableTextView mTextView;
        @BindView(R.id.ll_content)
        LinearLayout ll_content;

        private ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }
}

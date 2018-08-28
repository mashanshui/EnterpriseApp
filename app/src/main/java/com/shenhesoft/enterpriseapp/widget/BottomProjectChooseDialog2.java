package com.shenhesoft.enterpriseapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.TrainProjectBean;
import com.shenhesoft.enterpriseapp.widget.adapter.BottomProjectChooseAdapter;
import com.shenhesoft.enterpriseapp.widget.adapter.BottomProjectChooseAdapter2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author 张继淮
 * @date 2017/12/6
 * @desc 底部弹出dialog
 */

public class BottomProjectChooseDialog2 extends Dialog implements AdapterView.OnItemClickListener {
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    private BottomProjectChooseAdapter2 mAdapter;
    private Context context;
    private onSelectListener listener;
    private int Mposition;

    private List<TrainProjectBean> mselectlist;

//    public BottomProjectChooseDialog(@NonNull Context context) {
//        super(context, R.style.Dialog);
//        this.context = context;
//        setContentView(R.layout.dialog_choose);
//        KnifeKit.bind(this);
//        Window window = getWindow();
//        //此处可以设置dialog显示的位置
//        window.setGravity(Gravity.BOTTOM);
//        //添加动画
//        window.setWindowAnimations(R.style.DialogEnterStyle);
//        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        initView();
//    }

    public BottomProjectChooseDialog2(@NonNull Context context, List<TrainProjectBean> mlist) {
        super(context, R.style.Dialog);
        this.context = context;
        setContentView(R.layout.dialog_choose);
        KnifeKit.bind(this);
        Window window = getWindow();
        //此处可以设置dialog显示的位置
        window.setGravity(Gravity.BOTTOM);
        //添加动画
        window.setWindowAnimations(R.style.DialogEnterStyle);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        initView(mlist);
    }


    private void initView(List<TrainProjectBean> mlist) {
        mselectlist = mlist;
        mAdapter = new BottomProjectChooseAdapter2(context);
        mAdapter.setData(mselectlist);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }
    

    @OnClick({R.id.tv_cancel, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                if (listener != null && !mselectlist.isEmpty()) {
                    listener.getposition(Mposition);
                }
                dismiss();
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Mposition = position;
        mAdapter.SetPosition(Mposition);
    }

    public interface onSelectListener {
        void getposition(int position);
    }

    public void setOnselectListener(onSelectListener onselectListener) {
        this.listener = onselectListener;
    }
}

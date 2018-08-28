package com.shenhesoft.enterpriseapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;

/**
 * @author 张继淮
 * @date 2017/12/5
 * @desc 确认dialog
 */

public class ConfirmDialog extends Dialog {
    private TextView mTvTitle1, mTvTitle2, mTvConfirm, mTvCancel;

    public ConfirmDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_confirm);
        mTvTitle1 = (TextView) findViewById(R.id.tv_title1);
        mTvTitle2 = (TextView) findViewById(R.id.tv_title2);
        mTvConfirm = (TextView) findViewById(R.id.tv_confirm);
        mTvCancel = (TextView) findViewById(R.id.tv_cancel);
        mTvCancel.setOnClickListener(v -> dismiss());
    }

    public void setCancelListener(View.OnClickListener onClickListener) {
        if (mTvCancel != null)
            mTvCancel.setOnClickListener(onClickListener);
    }

    public void setConfirmListener(View.OnClickListener onClickListener) {
        if (mTvConfirm != null)
            mTvConfirm.setOnClickListener(onClickListener);

    }

    public void setCancelText(String text) {
        if (mTvConfirm != null)
            mTvCancel.setText(text);
    }

    public void setConfirmText(String text) {
        if (mTvConfirm != null)
            mTvConfirm.setText(text);
    }

    public void setTitle1(String title) {
        if (mTvTitle1 != null)
            mTvTitle1.setText(title);
    }

    public void setTitle2(String title) {
        if (mTvTitle2 != null) {
            mTvTitle2.setText(title);
            mTvTitle2.setVisibility(View.VISIBLE);
        }
    }

}

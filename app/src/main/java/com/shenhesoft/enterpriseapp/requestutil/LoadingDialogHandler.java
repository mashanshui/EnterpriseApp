package com.shenhesoft.enterpriseapp.requestutil;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.shenhesoft.enterpriseapp.utils.DialogUtil;


/**
 * 作者：Tornado
 * 创作日期：2017/8/9.
 * 描述：封装的一个LoadingDialog，使用Handler控制显示与关闭的
 */

public class LoadingDialogHandler extends Handler {

    public final static int SHOW_DIALOG = 0;
    public final static int DISMISS_DALOG = 1;

    private Dialog dialog;
    private Context context;
    private DialogCancleListener dialogCancleListener;

    public LoadingDialogHandler(Context context, DialogCancleListener listener) {
        super();
        this.context = context;
        dialogCancleListener = listener;
    }

    private void showDialog() {
        dialog = DialogUtil.createLoading(context);

        //当用户点击返回键 关闭dialog时，即认为用户想取消本次请求
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialogCancleListener.onCancle();
            }
        });

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    private void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_DIALOG:
                showDialog();
                break;
            case DISMISS_DALOG:
                dismissDialog();
                break;
        }
    }


    /**
     * Dialog取消时回调接口
     */
    public interface DialogCancleListener {
        void onCancle();
    }
}

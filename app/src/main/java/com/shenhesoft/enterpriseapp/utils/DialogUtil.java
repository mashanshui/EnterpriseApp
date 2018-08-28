package com.shenhesoft.enterpriseapp.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;


/**
 * 作者：Tornado
 * 创作日期：2017/8/7.
 * 描述：Dialog工具类
 */

public class DialogUtil {

//
//    private static Dialog shareDialog;
//
    /**
     * 创建一个LoadingDialog
     *
     * @param context
     */
    public static Dialog createLoading(Context context) {
        ProgressDialog loadingDialog = new ProgressDialog(context);
        loadingDialog.setMessage("加载中...");
//        loadingDialog.setContentView(R.layout.dialog_loading_layout);
//        // 设置居中
//        loadingDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//        AnimationDrawable anim = (AnimationDrawable) ((ImageView) loadingDialog
//                .findViewById(R.id.load_loading_img)).getDrawable();
//        anim.start();
//        loadingDialog.setCanceledOnTouchOutside(true);

        return loadingDialog;
    }

//    /**
//     * 创建一个分享Dialog
//     *
//     * @param context
//     */
//    public static Dialog showShareDialog(final Context context, final ShareHelper shareHelper) {
//        final Dialog shareDialog = new Dialog(context, R.style.share_dialog_style);
//        shareDialog.setContentView(R.layout.dialog_share);
//        // 设置居中
//        shareDialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
//        WindowManager.LayoutParams lp = shareDialog.getWindow().getAttributes();
//        lp.width = MyApplication.screenWidth; //设置宽度(像素宽度)
//        shareDialog.setCanceledOnTouchOutside(true);
//
//        final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("正在跳转第三方平台...");
//        View.OnClickListener clickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog.show();
//                switch (v.getId()) {
//                    case R.id.share_pengyq:
//                        shareHelper.shareWebLink(SHARE_MEDIA.WEIXIN_CIRCLE);
//                        break;
//                    case R.id.share_weixin:
//                        shareHelper.shareWebLink(SHARE_MEDIA.WEIXIN);
//                        break;
//                    case R.id.share_sina:
//                        shareHelper.shareSinaWeibo();
//                        break;
//                    case R.id.share_qq:
//                        shareHelper.shareWebLink(SHARE_MEDIA.QQ);
//                        break;
//                    case R.id.share_dialog_cancle:
//                        shareDialog.dismiss();
//                        return;
//                }
//            }
//        };
//        shareDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//            }
//        });
//
//        shareDialog.findViewById(R.id.share_pengyq).setOnClickListener(clickListener);
//        shareDialog.findViewById(R.id.share_weixin).setOnClickListener(clickListener);
//        shareDialog.findViewById(R.id.share_sina).setOnClickListener(clickListener);
//        shareDialog.findViewById(R.id.share_qq).setOnClickListener(clickListener);
//        shareDialog.findViewById(R.id.share_dialog_cancle).setOnClickListener(clickListener);
//
//        return shareDialog;
//    }
//
//    /**
//     * 图说 下载提示Dialog
//     *
//     * @param context
//     * @param downClickListener
//     */
//    public static Dialog createDownDialog(Context context, View.OnClickListener downClickListener) {
//        final Dialog dialog = new Dialog(context, R.style.share_dialog_style);
//        dialog.setContentView(R.layout.dialog_download_pic);
//        // 设置居中
//        dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
//        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//        lp.width = MyApplication.screenWidth; //设置宽度(像素宽度)
//        dialog.setCanceledOnTouchOutside(true);
//
//        TextView tvDownload = (TextView) dialog.findViewById(R.id.dialog_txt_download);
//        TextView tvCancle = (TextView) dialog.findViewById(R.id.dialog_txt_cancle);
//
//        tvDownload.setOnClickListener(downClickListener);
//        tvCancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        return dialog;
//    }


}

package com.shenhesoft.enterpriseapp.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseActivity;
import com.shenhesoft.enterpriseapp.base.BaseEvent;
import com.shenhesoft.enterpriseapp.bean.UserinfoBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.user.LoginActivity;
import com.shenhesoft.enterpriseapp.utils.AppUtil;
import com.shenhesoft.enterpriseapp.utils.IToast;

import java.util.List;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "SplashActivity";
    private String userName;
    private String userPassword;
    private int CALL_PHONE_REQUEST_CODE = 1001;
    private ImageView splashIcon;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        splashIcon = (ImageView) findViewById(R.id.splash_icon);
        Glide.with(context).load(R.drawable.splash_icon).into(splashIcon);
        checkPermission();
    }

    private void startLogin() {
        userName = SharedPref.getInstance(context).getString(AppConstant.UserName, "");
        userPassword = SharedPref.getInstance(context).getString(AppConstant.UserPassword, "");
        if (!TextUtils.isEmpty(userName) || !TextUtils.isEmpty(userPassword)) {
            submit();
        } else {
            Router.newIntent(context).to(LoginActivity.class).launch();
            finish();
        }
    }

    /***
     * 检查权限
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            //打电话的权限
            String[] mPermissionList = new String[]{Manifest.permission.CALL_PHONE};
            if (EasyPermissions.hasPermissions(context, mPermissionList)) {
                startLogin();
            } else {
                //未同意过,或者说是拒绝了，再次申请权限
                EasyPermissions.requestPermissions(this, "需要定位权限", CALL_PHONE_REQUEST_CODE, mPermissionList);
            }
        } else {
            //6.0以下，不需要授权
            startLogin();
        }

    }

    /***
     * 提交请求-登陆
     */
    private void submit() {
        Observable<RequestResults<UserinfoBean>> observable = HttpManager.getInstance().getUserService()
                .userLogin(ApiRetrofit.getInstance().login(userName, userPassword));

        HttpObserver<RequestResults<UserinfoBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        Router.newIntent(context).to(LoginActivity.class).launch();
                        finish();
                    }
                    clearUserPermission();
                    SharedPref.getInstance(context).putString(AppConstant.Userinfo, String.valueOf(data.getObj().getId()));
                    SharedPref.getInstance(context).putString(AppConstant.rongCloudToken, String.valueOf(data.getObj().getRongCloudToken()));
                    SharedPref.getInstance(context).putString(AppConstant.SysOrgCode, String.valueOf(data.getObj().getSysOrgCode()));
                    AppUtil.setUserinfo(data.getObj());
                    if (!data.getObj().getMeanList().isEmpty()) {
                        saveUserPermission(data.getObj().getMeanList());
                    }
                    postEvent(new BaseEvent(AppConstant.LOGIN, data.getObj()));
                    Router.newIntent(context).to(MainActivity.class).launch();
                    finish();
                },
                throwable -> {
                    IToast.showShort("登陆失败");
                    Router.newIntent(context).to(LoginActivity.class).launch();
                    finish();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    private void clearUserPermission() {
        SharedPref.getInstance(context).putInt(AppConstant.TASK_DISTRIBUTION, 0);
        SharedPref.getInstance(context).putInt(AppConstant.WAIT_DISPATCH, 0);
        SharedPref.getInstance(context).putInt(AppConstant.CONFIRM_ARRIVE, 0);
        SharedPref.getInstance(context).putInt(AppConstant.CONFIRM_CHARGING, 0);
        SharedPref.getInstance(context).putInt(AppConstant.UNUSUAL, 0);
        SharedPref.getInstance(context).putInt(AppConstant.WAYBILL, 0);
        SharedPref.getInstance(context).putInt(AppConstant.TRAIN, 0);
        SharedPref.getInstance(context).putInt(AppConstant.CHECK, 0);
    }

    private void saveUserPermission(List<UserinfoBean.MeanList> meanList) {
        for (int i = 0; i < meanList.size(); i++) {
            UserinfoBean.MeanList list = meanList.get(i);
            switch (list.getCode()) {
                case "APPdistribution":
                    SharedPref.getInstance(context).putInt(AppConstant.TASK_DISTRIBUTION, 1);
                    break;
                case "APPdisptachCheck":
                    SharedPref.getInstance(context).putInt(AppConstant.WAIT_DISPATCH, 1);
                    break;
                case "APPensureArrive":
                    SharedPref.getInstance(context).putInt(AppConstant.CONFIRM_ARRIVE, 1);
                    break;
                case "APPensureCharing":
                    SharedPref.getInstance(context).putInt(AppConstant.CONFIRM_CHARGING, 1);
                    break;
                case "APPexceptionOrder":
                    SharedPref.getInstance(context).putInt(AppConstant.UNUSUAL, 1);
                    break;
                case "APPshortbargeTrucks":
                    SharedPref.getInstance(context).putInt(AppConstant.WAYBILL, 1);
                    break;
                case "APPtrainLine":
                    SharedPref.getInstance(context).putInt(AppConstant.TRAIN, 1);
                    break;
                case "APPbussinessCheck":
                    SharedPref.getInstance(context).putInt(AppConstant.CHECK, 1);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        startLogin();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        startLogin();
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }
}

package com.shenhesoft.enterpriseapp.ui.activity.user;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseActivity;
import com.shenhesoft.enterpriseapp.base.BaseEvent;
import com.shenhesoft.enterpriseapp.bean.UserinfoBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.MainActivity;
import com.shenhesoft.enterpriseapp.utils.AppUtil;
import com.shenhesoft.enterpriseapp.utils.DialogUtil;
import com.shenhesoft.enterpriseapp.utils.IToast;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author 张继淮
 * @date 2017/11/27
 * @description 登录activity
 */

public class LoginActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_pw)
    EditText mEtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    private String userName;
    private String userPassword;

    private int CALL_PHONE_REQUEST_CODE = 1001;

    @Override
    protected void initView(Bundle savedInstanceState) {
//        requestData(ApiRetrofit.getInstance().login("admin", "123456"));
//        userName = SharedPref.getInstance(context).getString(AppConstant.UserName, "");
//        userPassword = SharedPref.getInstance(context).getString(AppConstant.UserPassword, "");
//        if (!TextUtils.isEmpty(userName) || !TextUtils.isEmpty(userPassword)) {
//            submit();
//        }
    }


    @Override
    public void onRequestSuccess(int requestTag, RequestResults requestResults) {
        super.onRequestSuccess(requestTag, requestResults);
        switch (requestTag) {
            case AppConstant.API_LOGIN:

                break;
            default:
                break;
        }
    }

    @Optional
    @OnClick({R.id.btn_login, R.id.tv_forgot_pw})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                userName = mEtName.getText().toString();
                userPassword = mEtPassword.getText().toString();
                submit();

                break;
            case R.id.tv_forgot_pw:
                Router.newIntent(this).to(ForgotPasswordActivity.class).launch();
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    /***
     * 提交请求-登陆
     */
    private void submit() {
        Observable<RequestResults<UserinfoBean>> observable = HttpManager.getInstance().getUserService()
                .userLogin(ApiRetrofit.getInstance().login(userName,userPassword));

        HttpObserver<RequestResults<UserinfoBean>> observer = new HttpObserver<>(context, DialogUtil.createLoading(context),
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    clearUserPermission();
                    //保存用户输入的用户名和密码，方便下一次自动登陆
                    SharedPref.getInstance(context).putString(AppConstant.UserName, userName);
                    SharedPref.getInstance(context).putString(AppConstant.UserPassword, userPassword);

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

    /***
     * 检查权限
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            //打电话的权限
            String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION};
            if (EasyPermissions.hasPermissions(context, mPermissionList)) {
                //已经同意过
            } else {
                //未同意过,或者说是拒绝了，再次申请权限
                EasyPermissions.requestPermissions(
                        this,  //上下文
                        "需要定位权限", //提示文言
                        CALL_PHONE_REQUEST_CODE, //请求码
                        mPermissionList //权限列表
                );
            }

        } else {
            //6.0以下，不需要授权
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
    }

    //失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
    }


}

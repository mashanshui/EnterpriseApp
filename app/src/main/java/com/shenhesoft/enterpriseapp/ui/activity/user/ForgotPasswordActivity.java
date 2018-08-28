package com.shenhesoft.enterpriseapp.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.UserinfoBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.utils.IToast;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 忘记密码
 */

public class ForgotPasswordActivity extends BaseTitleActivity {
    /**
     * 手机号
     */
    @BindView(R.id.et_phone)
    EditText etPhone;


    /**
     * 验证码
     */
    @BindView(R.id.et_code)
    EditText etCode;

    /**
     * 新密码
     */
    @BindView(R.id.et_new_pwd)
    EditText etPassWord;


    /**
     * 确认新密码
     */
    @BindView(R.id.et_check_pwd)
    EditText etCheckPassWord;


    @Override
    protected void initTitle() {
        setTitle(getString(R.string.forgot_password));
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


    @OnClick({R.id.btn_getcode, R.id.btn_sumit})
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btn_getcode:
                getCode(etPhone.getText().toString());
                break;
            case R.id.btn_sumit:
                submit(etPhone.getText().toString(), etCode.getText().toString(),
                        etPassWord.getText().toString(), etCheckPassWord.getText().toString());
                break;
            default:
                break;
        }

    }


    /***
     * 获取验证码
     */
    private void getCode(String phoneNumber) {
        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .getCode(ApiRetrofit.getInstance().getCode(phoneNumber));

        HttpObserver<RequestResults<UserinfoBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("验证码发送成功");

                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /***
     * 重置密码
     */
    private void submit(String phoneNum, String checkedCode, String newPassword, String newPasswordAgain) {
        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .saveNewPassWord(ApiRetrofit.getInstance().saveNewPassWord(phoneNum, checkedCode, newPassword, newPasswordAgain));

        HttpObserver<RequestResults<RequestResults>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }

                    IToast.showShort("密码重置成功");
                    finish();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgot_password;
    }
}

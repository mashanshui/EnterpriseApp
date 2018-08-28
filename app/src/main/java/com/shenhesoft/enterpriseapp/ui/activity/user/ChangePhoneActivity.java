package com.shenhesoft.enterpriseapp.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseEvent;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.UserinfoBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.MainActivity;
import com.shenhesoft.enterpriseapp.utils.AppUtil;
import com.shenhesoft.enterpriseapp.utils.CheckUtils;
import com.shenhesoft.enterpriseapp.utils.IToast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 修改手机号
 */

public class ChangePhoneActivity extends BaseTitleActivity {

    /**
     * 当前手机号
     */
    @BindView(R.id.et_current_phone)
    EditText currentPhone;

    /**
     * 新手机号
     */
    @BindView(R.id.et_new_phone)
    EditText newPhone;

    /**
     * 验证码
     */
    @BindView(R.id.et_code)
    EditText code;

    /**
     * 获取验证码
     */
    @BindView(R.id.btn_getcode)
    Button getCode;


    /**
     * 确认修改
     */
    @BindView(R.id.btn_sumit)
    Button submit;


    @Override
    protected void initTitle() {
        setTitle("修改手机号");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @OnClick({R.id.btn_getcode, R.id.btn_sumit})
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btn_getcode:
                getCode(currentPhone.getText().toString());
                break;
            case R.id.btn_sumit:
                submit(code.getText().toString(),currentPhone.getText().toString(),newPhone.getText().toString());
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
     * 修改手机号
     */
    private void submit(String checkedCode, String oldPhoneNum, String newPhoneNum) {
        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .saveNewPhone(ApiRetrofit.getInstance().saveNewPhone(checkedCode, oldPhoneNum, newPhoneNum));

        HttpObserver<RequestResults<RequestResults>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }

                    IToast.showShort("手机号修改成功");
                    finish();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


}

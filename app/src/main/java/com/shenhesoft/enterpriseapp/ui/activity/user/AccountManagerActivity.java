package com.shenhesoft.enterpriseapp.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.ui.activity.user.ChangePhoneActivity;
import com.shenhesoft.enterpriseapp.ui.activity.user.ForgotPasswordActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author 张继淮
 * @date 2017/12/7
 * @desc TODO账号管理
 */

public class AccountManagerActivity extends BaseTitleActivity {
    @BindView(R.id.ll_change_phone)
    LinearLayout mLlChangePhone;
    @BindView(R.id.ll_change_password)
    LinearLayout mLlChangePassword;

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_manager;
    }

    @Override
    protected void initTitle() {
        setTitle("账号管理");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @OnClick({R.id.ll_change_phone, R.id.ll_change_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_change_phone:
                Router.newIntent(this).to(ChangePhoneActivity.class).launch();
                break;
            case R.id.ll_change_password:
                Router.newIntent(this).to(ForgotPasswordActivity.class).launch();
                break;
            default:
                break;
        }
    }
}

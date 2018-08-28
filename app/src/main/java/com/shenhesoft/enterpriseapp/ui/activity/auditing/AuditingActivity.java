package com.shenhesoft.enterpriseapp.ui.activity.auditing;

import android.os.Bundle;
import android.view.View;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author zmx
 * @date 2018/2/3
 * @desc  审核页面
 */

public class AuditingActivity extends BaseTitleActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_auditing;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    @Override
    protected void initTitle() {
        setTitle("审核");
    }


    @OnClick({R.id.tv_feepay, R.id.tv_driverpay, R.id.tv_customerpay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_feepay:
                Router.newIntent(context).to(FeepayActivity.class).launch();
                break;
            case R.id.tv_driverpay:
                Router.newIntent(context).to(DriverCheckActivity.class).launch();
                break;
            case R.id.tv_customerpay:
                Router.newIntent(context).to(CustomerCheckActivity.class).launch();
                break;
            default:
                break;
        }
    }

}

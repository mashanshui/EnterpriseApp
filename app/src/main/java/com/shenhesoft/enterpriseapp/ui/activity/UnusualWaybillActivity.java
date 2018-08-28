package com.shenhesoft.enterpriseapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.TaskBean;
import com.shenhesoft.enterpriseapp.bean.UnusualBean;
import com.shenhesoft.enterpriseapp.bean.WaitDispatchBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.task.AllotTaskAdapter;
import com.shenhesoft.enterpriseapp.ui.adapter.UnusualWaybillAdapter;
import com.shenhesoft.enterpriseapp.ui.fragment.UnusualFragment;
import com.shenhesoft.enterpriseapp.utils.IToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * @author 张继淮
 * @date 2017/12/9
 * @desc 异常运单
 */

public class UnusualWaybillActivity extends BaseTitleActivity {
    @BindView(R.id.fragment_layout)
    FrameLayout fragmentLayout;
//    private UnusualWaybillAdapter mAdapter;
    private UnusualFragment mUnusualFragment;
//    private List<UnusualBean> mlist;

    @Override
    public int getLayoutId() {
        return R.layout.activity_list_common;
    }

    @Override
    protected void initTitle() {
        setTitle("异常运单");
//        setRight1Img(R.drawable.search_black, v -> {
//
//        });
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mUnusualFragment = new UnusualFragment();
        replaceFragment(mUnusualFragment);
//        mAdapter = new UnusualWaybillAdapter(this);
//        mlist = new ArrayList<>();
//
//        loadListData();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.commit();
    }

//    /**
//     * 获取列表数据 *请求接口*
//     */
//    private void loadListData() {
//        Observable observable = HttpManager.getInstance().getOrderService()
//                .getUnusualList(ApiRetrofit.getInstance().getUnusualParams("1"));
//
//        HttpObserver observer = new HttpObserver<RequestResults<ListALLResults<UnusualBean>>>(this,
//                data -> {
//                    if (data.getState() != 1) {
//                        IToast.showShort(data.getMsg());
//                        return;
//                    }
//                    mlist = data.getObj().getRows();
//                    mAdapter.setData(mlist);
//
//                });
//
//        HttpManager.getInstance().statrPostTask(observable, observer);
//    }

}

package com.shenhesoft.enterpriseapp.ui.activity.confirmarrive;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class DispatchOrderActivity extends BaseTitleActivity {

    @BindView(R.id.tv_yard)
    TextView mTvYard;

    @BindView(R.id.ll_yard)
    LinearLayout mLlYard;

    @BindView(R.id.tv_allocation)
    TextView mTvAllocation;

    @BindView(R.id.ll_allocation)
    LinearLayout mLlAllocation;
    private BottomListChooseDialog mChooseDialogYard;
    private BottomListChooseDialog mChooseDialog;
    private List<LocationPopBean> mList;
    private List<LocationPopBean> mchildList;

    //货场主键
    private String takeCargoPlaceId;

    //订单号
    private String orderId;

    //货场名
    private String takeCarogoPlaceName;

    //货位名
    private String takeCargoSiteName;

    //货位id
    private String takeCargoSiteId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm_arrive;
    }

    @Override
    protected void initTitle() {
        setTitle("调度审核");
        setRightText("提交", v -> {
            //不传运单ID  intent返回参数中包含货场货位
            if (TextUtils.isEmpty(orderId)) {
//                if (!TextUtils.isEmpty(takeCargoSiteId) && !TextUtils.isEmpty(takeCargoPlaceId)) {
//                    Intent intent = new Intent();
//                    intent.putExtra("takeCargoPlaceId", takeCargoPlaceId);
//                    intent.putExtra("takeCargoSiteId", takeCargoSiteId);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                }
                IToast.showShort("审核失败，请重新审核");
                finish();
            } else {
                submit();
            }

        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mList = new ArrayList<>();
        mchildList = new ArrayList<>();
        orderId = getIntent().getStringExtra("orderId");
        getgoodsPlace();
    }

    @OnClick({R.id.ll_yard, R.id.ll_allocation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_yard:
                if (mList.size() == 0) {
                    getgoodsPlace();
                }
                if (mChooseDialogYard == null) {
                    mChooseDialogYard = new BottomListChooseDialog(context, mList);
                    mChooseDialogYard.setOnselectListener(position -> {
                                takeCargoPlaceId = String.valueOf(mList.get(position).getId());
                                takeCarogoPlaceName = String.valueOf(mList.get(position).getName());
                                mTvYard.setText(takeCarogoPlaceName);
                                getgoodssite();
                            }
                    );
                }
                mChooseDialogYard.loaddata(mList);
                mChooseDialogYard.show();
                break;
            case R.id.ll_allocation:
                if (mchildList.size() == 0) {
                    getgoodssite();
                }
                if (mChooseDialog == null) {
                    mChooseDialog = new BottomListChooseDialog(context, mchildList);
                    mChooseDialog.setOnselectListener(position -> {
                                takeCargoSiteId = String.valueOf(mchildList.get(position).getId());
                                takeCargoSiteName = String.valueOf(mchildList.get(position).getName());
                                mTvAllocation.setText(takeCargoSiteName);
                            }
                    );

                }
                mChooseDialog.loaddata(mchildList);
                mChooseDialog.show();
                break;
            default:
                break;
        }
    }

    /**
     * 获取货场信息
     */
    private void getgoodsPlace() {
        Observable<RequestResultsList<LocationPopBean>> observable = HttpManager.getInstance().getUserService()
                .getlistGoodsyard(ApiRetrofit.getInstance().getlistGoodsyard(orderId));

        HttpObserver<RequestResultsList<LocationPopBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    mList = data.getObj();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 获取货位信息
     */
    private void getgoodssite() {
        Observable<RequestResultsList<LocationPopBean>> observable = HttpManager.getInstance().getUserService()
                .getlistGoodsyardChlid(ApiRetrofit.getInstance().getlistGoodschild(takeCargoPlaceId));

        HttpObserver<RequestResultsList<LocationPopBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    List<LocationPopBean> list = data.getObj();
                    for (int i = 0; i < list.size(); i++) {
                        LocationPopBean bean = new LocationPopBean();
                        bean.setId(list.get(i).getId());
                        bean.setName(list.get(i).getCargoCode() + " " + list.get(i).getName());
                        mchildList.add(bean);
                    }

                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 提交货场货位信息
     */
    private void submit() {
        Intent intent = new Intent();
        intent.putExtra("takeCarogoPlaceName", takeCarogoPlaceName);
        intent.putExtra("takeCargoSiteName", takeCargoSiteName);
        intent.putExtra("takeCargoPlaceId", takeCargoPlaceId);
        intent.putExtra("takeCargoSiteId", takeCargoSiteId);
        setResult(200,intent);
        finish();
    }

}
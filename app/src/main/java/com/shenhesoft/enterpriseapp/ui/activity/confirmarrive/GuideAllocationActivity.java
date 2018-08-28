package com.shenhesoft.enterpriseapp.ui.activity.confirmarrive;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.bean.WaitDispatchBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author zmx
 * @date 2017/12/6
 * @desc 货位引导
 */

public class GuideAllocationActivity extends BaseTitleActivity {
    private static final String TAG = "GuideAllocationActivity";
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
    private String freightId;

    //订单号
    private String orderId;

    //货场名
    private String distributionCargoPlace;

    //货位名
    private String distributionCargoSite;

    //货位id
    private String siteid;

    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm_arrive;
    }

    @Override
    protected void initTitle() {
        setTitle("货位引导");
        setRightText("提交", v -> {
            //不传运单ID  intent返回参数中包含货场货位
            if (TextUtils.isEmpty(orderId)) {
                if (!TextUtils.isEmpty(siteid) && !TextUtils.isEmpty(freightId)) {
                    Intent intent = new Intent();
                    intent.putExtra("takeCargoPlaceId", freightId);
                    intent.putExtra("takeCargoSiteId", siteid);
                    setResult(RESULT_OK, intent);
                    finish();
                }
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
                                freightId = String.valueOf(mList.get(position).getId());
                                distributionCargoPlace = String.valueOf(mList.get(position).getName());
                                mTvYard.setText(distributionCargoPlace);
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
                                siteid = String.valueOf(mchildList.get(position).getId());
                                distributionCargoSite = String.valueOf(mchildList.get(position).getName());
                                mTvAllocation.setText(distributionCargoSite);
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
                .getTrainYardList(ApiRetrofit.getInstance().getTrainEntrucklist(orderId,"1"));

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
                .getTrainLocationList(ApiRetrofit.getInstance().getTrainEntruckLocationlist(freightId));

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
//        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
//                .submitGoodsyard(ApiRetrofit.getInstance().submitGoodsinfo(orderId, distributionCargoPlace, distributionCargoSite,freightId,siteid));
//
//        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
//                data -> {
//                    if (data.getState() != 1) {
//                        IToast.showShort(data.getMsg());
//                        return;
//                    }
//                    IToast.showShort("货位引导成功！");
//                    Intent intent = new Intent();
//                    intent.putExtra("takeCargoPlaceId", freightId);
//                    intent.putExtra("takeCargoSiteId", siteid);
//                    intent.putExtra("arriveCargoPlaceName", distributionCargoPlace);
//                    intent.putExtra("arriveCargoSiteName", distributionCargoSite);
//                    setResult(RESULT_OK, intent);
////                    setResult(200);
//                    finish();
//                });
//
//        HttpManager.getInstance().statrPostTask(observable, observer);
        Intent intent = new Intent();
        intent.putExtra("takeCargoPlaceId", freightId);
        intent.putExtra("takeCargoSiteId", siteid);
        intent.putExtra("arriveCargoPlaceName", distributionCargoPlace);
        intent.putExtra("arriveCargoSiteName", distributionCargoSite);
        setResult(RESULT_OK, intent);
//                    setResult(200);
        finish();
    }


}

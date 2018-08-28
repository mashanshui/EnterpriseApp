package com.shenhesoft.enterpriseapp.ui.activity.train;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.data.BarEntry;
import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderBean;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderDetailBean;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import io.reactivex.Observable;

/**
 * @author 张明星
 * @date 2018/1/3
 * @desc TODO
 */

public class AddEntruckActivity extends BaseTitleActivity {
    private static final String TAG = "AddEntruckActivity";
    private BottomListChooseDialog mChooseDialog;

    private BottomListChooseDialog mChildChooseDialog;

    private List<LocationPopBean> mList;
    private List<LocationPopBean> mchildList;

    @BindView(R.id.tv_loading_train_common_yard)
    TextView tv_yard;

    @BindView(R.id.tv_loading_train_common_location)
    TextView tv_location;

    @BindView(R.id.tv_loading_train_zhuangcxd)
    TextView tvZhuangcxd;

    //货场主键
    private String freightId;

    //货位主键
    private String locationId;

    //订单号
    private String orderId;

    //货场名
    private String distributionCargoPlace;

    //货位名
    private String distributionCargoSite;

    private String projectType;
    //數據json
    private String submitjson;

    private boolean isedit;

    private List<EntruckOrderDetailBean> listObj;
    private EntruckOrderBean bean;

    /**
     * 承认车数，装车时最大不能超过这个数量
     */
    private int admitNum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_entrucking;
    }

    @Override
    protected void initTitle() {
        cancelBtnLeft();
        setTitle("添加装车信息");
        setRightText("保存", v -> {
            bean = new EntruckOrderBean();
            bean.setCargoPlaceName(distributionCargoPlace);
            bean.setCargoPlaceId(freightId);
            bean.setCargoSiteName(distributionCargoSite);
            bean.setCargoSiteId(locationId);
            bean.setOrderDetail(listObj);

            try {
                JSONArray myarray1 = new JSONArray();

                for (EntruckOrderDetailBean etbean : bean.getOrderDetail()) {
                    JSONObject chobject = new JSONObject();
                    chobject.put("cargoPlaceName", bean.getCargoPlaceName());
                    chobject.put("cargoPlaceId", bean.getCargoPlaceId());
                    chobject.put("cargoSiteName", bean.getCargoSiteName());
                    chobject.put("cargoSiteId", bean.getCargoSiteId());
                    chobject.put("carType", etbean.getCarType());
                    chobject.put("carNumber", etbean.getCarNumber());
                    chobject.put("containerNumber1", etbean.getContainerNumber1());
                    chobject.put("containerNumber2", etbean.getContainerNumber2());
                    chobject.put("sendWeight", etbean.getSendWeight());
                    chobject.put("con_send_weight2", etbean.getConSendWeight2());
                    chobject.put("sendImg", etbean.getImg());
                    myarray1.put(chobject);
                }
//                    object.put("orderDetail", myarray1);
//                    myarray1.put(object);

                Log.d("aaaaaa", myarray1.toString());
                submitjson = myarray1.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            submit(submitjson);
            Intent intent = new Intent().putExtra("entruckOrderBean", (Serializable) bean);
            setResult(RESULT_OK, intent);
            finish();
//            }

        });
    }



    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        listObj = new ArrayList<>();
        bean = (EntruckOrderBean) getIntent().getSerializableExtra("entruckOrderBean");
        if (bean != null) {
            isedit = true;
            distributionCargoPlace = bean.getCargoPlaceName();
            freightId = bean.getCargoPlaceId();
            distributionCargoSite = bean.getCargoSiteName();
            locationId = bean.getCargoSiteId();
            listObj = bean.getOrderDetail();
            tv_location.setText(distributionCargoSite);
            tv_yard.setText(distributionCargoPlace);
            tvZhuangcxd.setText("(已填写" + listObj.size() + ")");
        }
        admitNum = getIntent().getIntExtra("admitNum", 0);
        orderId = getIntent().getStringExtra("orderId");
        projectType = getIntent().getStringExtra("projectType");
        if (TextUtils.isEmpty(orderId)) {
            IToast.showShort("订单号为空");
            finish();
        }
        mList = new ArrayList<>();
        mchildList = new ArrayList<>();
        getgoodsPlace();
    }

    private void AddMore() {

        tvZhuangcxd.setOnClickListener(v -> {
            Intent intent = new Intent(context, DengdzcDetailsActivity.class);
            if (listObj != null && listObj.size() > 0) {
                intent.putExtra("listobj", (Serializable) listObj);
            }
            intent.putExtra("projectType", projectType);
            intent.putExtra("orderId", orderId);
            intent.putExtra("admitNum", admitNum);
            startActivityForResult(intent, 101);
        });

        tv_yard.setOnClickListener(v -> {
            if (mList.size() == 0) {
                IToast.showShort("暂无货场数据");
                getgoodsPlace();
                return;
            }
            if (mChooseDialog == null) {
                mChooseDialog = new BottomListChooseDialog(context, mList);
            }
            mChooseDialog.loaddata(mList);
            mChooseDialog.setOnselectListener(position -> {
                        freightId = String.valueOf(mList.get(position).getId());
                        distributionCargoPlace = String.valueOf(mList.get(position).getName());
                        tv_yard.setText(distributionCargoPlace);
                        getgoodssite();
                    }
            );
            mChooseDialog.show();
        });


        tv_location.setOnClickListener(v -> {
            if (mchildList.size() == 0) {
                getgoodssite();
                return;
            }
            if (mChildChooseDialog == null) {
                mChildChooseDialog = new BottomListChooseDialog(context, mchildList);
            }
            mChildChooseDialog.setOnselectListener(position -> {
                        distributionCargoSite = String.valueOf(mchildList.get(position).getName());
                        locationId = String.valueOf(mchildList.get(position).getId());
                        tv_location.setText(distributionCargoSite);

                    }
            );
            mChildChooseDialog.show();
        });
//        ll_train_loading_parent.addView(parent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            listObj = (ArrayList<EntruckOrderDetailBean>) data.getSerializableExtra("mentrucklist");
            tvZhuangcxd.setText("(已填写" + listObj.size() + ")");
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        AddMore();
    }


    /**
     * 获取货场信息
     */
    private void getgoodsPlace() {
        Observable<RequestResultsList<LocationPopBean>> observable = HttpManager.getInstance().getUserService()
                .getTrainYardList(ApiRetrofit.getInstance().getTrainEntrucklist(orderId, "0"));

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
//                    mchildList = data.getObj();
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

    @Override
    public void onBackPressed() {
        if (mchildList.size() == 0) {
            finish();
        } else {
            IToast.showShort("请保存装车信息");
        }
    }
}

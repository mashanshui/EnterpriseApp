package com.shenhesoft.enterpriseapp.ui.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseEvent;
import com.shenhesoft.enterpriseapp.base.BaseFragment;
import com.shenhesoft.enterpriseapp.bean.DriverInfoBean;
import com.shenhesoft.enterpriseapp.bean.LocationBean;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.bean.ProjectBean;
import com.shenhesoft.enterpriseapp.bean.UserinfoBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.MainActivity;
import com.shenhesoft.enterpriseapp.ui.activity.UnusualActivity;
import com.shenhesoft.enterpriseapp.ui.activity.motor.AddNewOrderActivity;
import com.shenhesoft.enterpriseapp.utils.AppUtil;
import com.shenhesoft.enterpriseapp.utils.DialogUtil;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.utils.MyUtils;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;
import com.shenhesoft.enterpriseapp.widget.LocationPop;

import java.util.ArrayList;
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
 * @date 2017/9/29
 * @description 定位fragment
 */

public class LocationFragment extends BaseFragment implements LocationPop.onSelectListener, AMap.OnMyLocationChangeListener,EasyPermissions.PermissionCallbacks {
    private int CALL_PHONE_REQUEST_CODE = 1001;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.rl_location_title1)
    RelativeLayout mRlTitle1;
    @BindView(R.id.tv_check_all_car)
    TextView mTvCheckAllCar;
    @BindView(R.id.iv_unusual_message)
    ImageView mIvUnusualMessage;
    @BindView(R.id.ll_location_title2)
    LinearLayout mLlTitle2;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.ll_location_title3)
    LinearLayout mLlTitle3;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_plate_number)
    TextView mTvPlateNumber;

    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private LocationPop mLocationPop;
    private List<LocationBean> mLocationBeans;
    private boolean isFirstLocation = true;

    private List<ProjectBean> locationlist;
    private String projectId;


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        locationlist = new ArrayList<>();
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        checkPermission();
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
                startLocation();
            } else {
                //未同意过,或者说是拒绝了，再次申请权限
                EasyPermissions.requestPermissions(this, "需要定位权限", CALL_PHONE_REQUEST_CODE, mPermissionList);
            }

        } else {
            //6.0以下，不需要授权
            startLocation();
        }

    }

    private void startLocation() {
        myLocationStyle = new MyLocationStyle(); //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle); //设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true); //设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setRotateGesturesEnabled(false);
        aMap.setOnMyLocationChangeListener(this);
        aMap.setMyLocationEnabled(true); // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
//        aMap.setMaxZoomLevel(20);
//        aMap.setMinZoomLevel(5);
//        latLngs = new ArrayList<>();
//        latLngs.add(new LatLng(31.84801, 117.26525));
//        latLngs.add(new LatLng(31.850556, 117.266391));
//        latLngs.add(new LatLng(31.846369, 117.274421));
//        latLngs.add(new LatLng(31.871298, 117.304517));
////        //  117.26525,31.84801      117.266391,31.850556   117.274421,31.846369    117.274421,31.846369    117.304517,31.871298
//        LatLng latLng = new LatLng(39.906901,116.397972);
//        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
//        AddMark(latLngs);
        getgoodsPlace();
    }

    private void AddMark(List<LocationBean> mLocationBeans) {
        aMap.clear(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(5));
        for (LocationBean bean : mLocationBeans) {

            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(Double.valueOf(bean.getLat()), Double.valueOf(bean.getLon())));
            markerOption.title("车牌号：" + bean.getCarNo()).snippet("项目编号：" + bean.getProjectCode());

            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.mipmap.location_car)));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果

            aMap.addMarker(markerOption);
        }
    }

    @Optional
    @OnClick({R.id.tv_check_all_car, R.id.iv_unusual_message, R.id.tv_cancel, R.id.iv_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_check_all_car:
                mRlTitle1.setVisibility(View.GONE);
                mLlTitle2.setVisibility(View.VISIBLE);
                if (mLocationPop == null) {
                    mLocationPop = new LocationPop(context);
                    mLocationPop.setOnselectListener(this);
                }
                if (!mLocationPop.hasdata()) {
                    if (locationlist.size() > 0) {
                        mLocationPop.setData(locationlist);
                    }
                }
                mLocationPop.showAsDropDown(mRlTitle1);
                break;
            case R.id.iv_unusual_message:
                Search();
//                Router.newIntent(context).to(UnusualActivity.class).launch();
                break;
            case R.id.tv_cancel:
                mRlTitle1.setVisibility(View.VISIBLE);
                mLlTitle2.setVisibility(View.GONE);
                if (mLocationPop != null) {
                    mLocationPop.dismiss();
                }
                break;
            case R.id.iv_back:
                mRlTitle1.setVisibility(View.VISIBLE);
                mLlTitle3.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }


    /**
     * 获取项目列表信息
     */
    private void getgoodsPlace() {
        Observable<RequestResultsList<ProjectBean>> observable = HttpManager.getInstance().getOrderService()
                .getProjectList(ApiRetrofit.getInstance().getProjectList("1"));

        HttpObserver<RequestResultsList<ProjectBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    locationlist = data.getObj();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_location;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) {
            mMapView.onPause();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.removeAllViews();
            mMapView.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void getposition(int position) {
        String projectCode = locationlist.get(position).getProjectCode();
        projectId = String.valueOf(locationlist.get(position).getId());
        mRlTitle1.setVisibility(View.VISIBLE);
        mLlTitle2.setVisibility(View.GONE);
        mTvCheckAllCar.setText(projectCode);
        if (mLocationPop != null) {
            mLocationPop.dismiss();
        }
        getcarinfo();
    }

    /**
     * 获取所有车辆信息
     */
    private void getcarinfo() {
        Observable<RequestResultsList<DriverInfoBean>> observable = HttpManager.getInstance().getOrderService()
                .getListCarinfo(ApiRetrofit.getInstance().getListCarinfo(projectId, null));

        HttpObserver<RequestResultsList<DriverInfoBean>> observer = new HttpObserver<>(context, DialogUtil.createLoading(context),
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    List<DriverInfoBean> driverInfoBeanList = data.getObj();
                    if (driverInfoBeanList.isEmpty()) {
                        IToast.showShort("没有车辆信息");
                        return;
                    }
                    for (int i = 0; i < driverInfoBeanList.size(); i++) {
                        submit(driverInfoBeanList.get(i).getDriverId());
                    }
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /***
     * 提交请求
     */
    private void submit(int carId) {

        Observable<RequestResultsList<LocationBean>> observable = HttpManager.getInstance().getOrderService()
                .getlocationlist(ApiRetrofit.getInstance().getLocationList(String.valueOf(carId), "", "", ""));

        HttpObserver<RequestResultsList<LocationBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 200) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    List<LocationBean> mLocationBeans = data.getObj();
                    if (mLocationBeans.isEmpty()) {
                        IToast.showShort("找不到车辆位置信息");
                        return;
                    }
                    AddMark(mLocationBeans);
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    private void Search() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("请输入车牌号");    //设置对话框标题
        builder.setIcon(R.mipmap.ico_company);   //设置对话框标题前的图标

        final EditText edit = new EditText(context);
        edit.setTextColor(Color.parseColor("#000000"));
        edit.setGravity(Gravity.CENTER);
        edit.setMaxLines(7);
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "你输入的是: " + edit.getText().toString(), Toast.LENGTH_SHORT).show();
//                if (MyUtils.checkNubmer(edit.getText().toString())) {
//                    submit(edit.getText().toString(), "");
//                } else {
//                    IToast.showShort("车牌号输入错误");
//                }

                getcarinfoByNum(edit.getText().toString());
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    /**
     * 根据车牌号获取对应信息
     */
    private void getcarinfoByNum(String carPlateNumber) {
        Observable<RequestResults<DriverInfoBean>> observable = HttpManager.getInstance().getOrderService()
                .getCarinfo(ApiRetrofit.getInstance().getCarinfo(carPlateNumber));

        HttpObserver<RequestResults<DriverInfoBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    DriverInfoBean driverInfo = data.getObj();
                    submit(driverInfo.getDriverId());
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (isFirstLocation) {
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
            aMap.setMyLocationStyle(myLocationStyle);
            isFirstLocation = false;
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
        startLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        IToast.showShort("不能使用定位功能");
    }
}

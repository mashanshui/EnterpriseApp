package com.shenhesoft.enterpriseapp.ui.activity.train;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jph.takephoto.model.TResult;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderBean;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderDetailBean;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.confirmarrive.GuideAllocationActivity;
import com.shenhesoft.enterpriseapp.ui.adapter.AddEntruckOrderDetailAdapter;
import com.shenhesoft.enterpriseapp.ui.adapter.UnloadOrderAdapter;
import com.shenhesoft.enterpriseapp.utils.Base64Utils;
import com.shenhesoft.enterpriseapp.utils.DensityUtils;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.utils.TakePhotoActivity;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.log.XLog;
import io.reactivex.Observable;

/**
 * 作者：Tornado
 * 创作日期：2017/12/28.
 * 描述：火车干线》等待卸货》卸货详单
 */

public class UnloadDetailsActivity extends TakePhotoActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "UnloadDetailsActivity";
    //录入的装车信息列表
    @BindView(R.id.wait_details_lv)
    ListView lvDetatils;
//    //添加更多Txt
//    @BindView(R.id.wait_tv_add_more_details)
//    TextView tvAdd;

    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    @BindView(R.id.tvselect)
    TextView tvSelect;

    private UnloadOrderAdapter mAddEntruckOrderDetailAdapter;

    private Dialog addDialog;

    private String upimg = null;

    private TextView tvUpimg;

    private List<EntruckOrderDetailBean> mentrucklist;

    private boolean isAdd = true;

    private int mlistposition;

    String orderId;

    private boolean isLocalUpload = false;

    private String projectType;

    private List<EntruckOrderDetailBean> sumittrucklist;

    private String arriveCargoPlaceId;

    private String arriveCargoSiteId;

    private String distributionCargoPlace;

    private String distributionCargoSite;

    @Override
    public int getLayoutId() {
        return R.layout.activity_train_unload;
    }

    @Override
    protected void initTitle() {
        setTitle("装车详单");
        setRightText("保存", v -> {
            for (EntruckOrderDetailBean entruckOrderDetailBean : mentrucklist) {
                if (!TextUtils.isEmpty(entruckOrderDetailBean.getGoodsPlace()) && !TextUtils.isEmpty(entruckOrderDetailBean.getGoodsSite())) {
                } else {
                    IToast.showShort("请先上传分配货位的卸货信息");
                    return;
                }
            }
            finish();
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        upimg = result.getImage().getCompressPath();
        //result.getImage().getOriginalPath()  获取图片真实路径
        tvUpimg.setText("已上传");
        try {
            upimg = "data:image/jpeg;base64," + Base64Utils.encodeFile(upimg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isLocalUpload = true;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (TextUtils.isEmpty(orderId)) {
            IToast.showShort("订单号为空");
            finish();
        }

        mAddEntruckOrderDetailAdapter = new UnloadOrderAdapter(this, 0);

        lvDetatils.setAdapter(mAddEntruckOrderDetailAdapter);
        lvDetatils.setOnItemClickListener(this);
        mAddEntruckOrderDetailAdapter.setChangeListener(() -> notifyConfirmBtn());
        mBtnConfirm.setOnClickListener(v -> startActivityForResult(new Intent(context, GuideAllocationActivity.class)
                .putExtra("orderId",orderId), 101));
        getgoodsPlace();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");
        projectType = getIntent().getStringExtra("projectType");
        mentrucklist = new ArrayList<>();
        sumittrucklist = new ArrayList<>();
        showAddMoreDialog(-1);
    }


    private void notifyConfirmBtn() {
        int checkNum = 0;
        List<EntruckOrderDetailBean> dataSource = mAddEntruckOrderDetailAdapter.getDataSource();
        for (EntruckOrderDetailBean bean : dataSource) {
            if (bean.isChecked())
                checkNum++;
        }
//        if (checkNum != dataSource.size()) {
//            mCheckBox.setChecked(false);
//        } else {
//            mCheckBox.setChecked(true);
//        }
        tvSelect.setText("你已选择" + checkNum + "条数据");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            arriveCargoPlaceId = data.getStringExtra("takeCargoPlaceId");
            arriveCargoSiteId = data.getStringExtra("takeCargoSiteId");
            distributionCargoPlace = data.getStringExtra("arriveCargoPlaceName");
            distributionCargoSite = data.getStringExtra("arriveCargoSiteName");

            JSONArray myarray1 = new JSONArray();
            for (EntruckOrderDetailBean bean : mAddEntruckOrderDetailAdapter.getDataSource()) {
                if (bean.isChecked()) {
                    try {
                        JSONObject chobject = new JSONObject();
                        chobject.put("id", bean.getId());
                        chobject.put("trainOrderId", orderId);
                        chobject.put("containerNumber1", bean.getContainerNumber1());
                        chobject.put("containerNumber2", bean.getContainerNumber2());
                        chobject.put("unloadWeight", bean.getUnloadWeight());
                        chobject.put("conUnloadWeight2", bean.getConUnloadWeight2());
                        chobject.put("arriveCargoPlaceId", arriveCargoPlaceId);
                        chobject.put("arriveCargoSiteId", arriveCargoSiteId);
                        chobject.put("arriveCargoPlaceName", distributionCargoPlace);
                        chobject.put("arriveCargoSiteName", distributionCargoSite);
                        if (isLocalUpload) {
                            chobject.put("unloadImg", bean.getImgcall());
                        } else {
                            chobject.put("unloadImg", null);
                        }
                        myarray1.put(chobject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                if (bean.isChecked()) {
//                    bean.setArriveCargoPlaceId(arriveCargoPlaceId);
//                    bean.setArriveCargoSiteId(arriveCargoSiteId);
//                }
            }
            XLog.d(myarray1.toString());
//            getgoodsPlace();
            submit(myarray1.toString());
        }
    }

    private void showAddMoreDialog(int position) {

        if (addDialog == null) {
            addDialog = new Dialog(context, R.style.train_wait_addzcyd_dialog_style);
            addDialog.setContentView(R.layout.dialog_train_unload_add);

            WindowManager.LayoutParams lp = addDialog.getWindow().getAttributes();
            lp.width = (int) (DensityUtils.getScreenWidthPixels(context) * 0.8);//设置宽度
            lp.gravity = Gravity.CENTER; //设置居中

            initAddDialogViews(position);
        } else {
            initAddDialogViews(position);
            addDialog.show();
        }
    }

    private void initAddDialogViews(int mposition) {
        if (!isAdd) {
            if (mentrucklist.get(mposition).getImgcall() != null) {
                upimg = mentrucklist.get(mposition).getImgcall();
            }
        }

        TextView tvClean = (TextView) addDialog.findViewById(R.id.dialog_wait_add_tvclean);
        TextView tvSave = (TextView) addDialog.findViewById(R.id.dialog_wait_add_tvsave);
        TextView tvCarNumber = (TextView) addDialog.findViewById(R.id.tv_train_wait_carnumber);
        TextView tvcontainerNumber1 = (TextView) addDialog.findViewById(R.id.et_train_wait_number1);
        TextView tvcontainerNumber2 = (TextView) addDialog.findViewById(R.id.et_train_wait_number2);
        TextView tvsendWeight = (TextView) addDialog.findViewById(R.id.et_train_wait_sendWeight);
        EditText etarriveWeight = (EditText) addDialog.findViewById(R.id.et_train_wait_arriveWeight);
        TextView tvsendWeight2 = (TextView) addDialog.findViewById(R.id.et_train_wait_sendWeight2);
        EditText etarriveWeight2 = (EditText) addDialog.findViewById(R.id.et_train_wait_arriveWeight2);

        LinearLayout ll_containerNumber1 = (LinearLayout) addDialog.findViewById(R.id.ll_containerNumber1);
        LinearLayout ll_containerNumber2 = (LinearLayout) addDialog.findViewById(R.id.ll_containerNumber2);
        LinearLayout ll_unloadWeight2 = (LinearLayout) addDialog.findViewById(R.id.ll_unloadWeight2);
        LinearLayout ll_sendWeight2 = (LinearLayout) addDialog.findViewById(R.id.ll_sendWeight2);

        tvUpimg = (TextView) addDialog.findViewById(R.id.tv_train_wait_img);
        TextView tvCarType = (TextView) addDialog.findViewById(R.id.tv_train_wait_add_cartype);

        if (projectType.equals("1")) {
            ll_containerNumber1.setVisibility(View.GONE);
            ll_containerNumber2.setVisibility(View.GONE);
            ll_unloadWeight2.setVisibility(View.GONE);
            ll_sendWeight2.setVisibility(View.GONE);
        } else {
            ll_containerNumber1.setVisibility(View.VISIBLE);
            ll_containerNumber2.setVisibility(View.VISIBLE);
            ll_unloadWeight2.setVisibility(View.VISIBLE);
            ll_sendWeight2.setVisibility(View.VISIBLE);
        }
        if (mposition != -1) {
            tvCarType.setText(mentrucklist.get(mposition).getCarType());
            tvCarNumber.setText(mentrucklist.get(mposition).getCarNumber());
            tvcontainerNumber1.setText(mentrucklist.get(mposition).getContainerNumber1());
            tvcontainerNumber2.setText(mentrucklist.get(mposition).getContainerNumber2());
            tvsendWeight.setText(mentrucklist.get(mposition).getSendWeight());
            tvsendWeight2.setText(mentrucklist.get(mposition).getConSendWeight2());
            etarriveWeight.setText(mentrucklist.get(mposition).getUnloadWeight());
            etarriveWeight2.setText(mentrucklist.get(mposition).getConUnloadWeight2());
            if (!TextUtils.isEmpty(mentrucklist.get(mposition).getImgcall())) {
                tvUpimg.setText("点击重新上传");
            } else {
                tvUpimg.setText("点击上传");
            }
            return;
        }
        tvClean.setOnClickListener(v -> {
            addDialog.cancel();
        });

        tvUpimg.setOnClickListener(v -> {
            ShowPicCheck();
        });


        tvSave.setOnClickListener(v ->
        {
            EntruckOrderDetailBean bean = mAddEntruckOrderDetailAdapter.getDataSource().get(mlistposition);
            bean.setUnloadWeight(etarriveWeight.getText().toString());
            bean.setConUnloadWeight2(etarriveWeight2.getText().toString());
            bean.setImgcall(upimg);
            Log.e(TAG, "initAddDialogViews: "+upimg );
            mAddEntruckOrderDetailAdapter.setData(mentrucklist);
            addDialog.dismiss();
        });

        //....
    }

    /**
     * 提交装车信息
     */
    private void submit(String myjson) {
        //type  0只保存运单信息，不跟新状态  1只更新状态。

        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .submitUnloadinfo(ApiRetrofit.getInstance().submitUnloadinfo(orderId, myjson, "0"));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    refreshUI();
                    IToast.showShort("分配信息提交成功！");
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    private void refreshUI() {
        getgoodsPlace();
    }


    /**
     * 获取运单列表
     */
    private void getgoodsPlace() {
        Observable<RequestResultsList<EntruckOrderDetailBean>> observable = HttpManager.getInstance().getUserService()
                .getOrderDetailList(ApiRetrofit.getInstance().getOrderDetaillist(orderId));

        HttpObserver<RequestResultsList<EntruckOrderDetailBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    mentrucklist = data.getObj();
                    mAddEntruckOrderDetailAdapter.setData(mentrucklist);
                    notifyConfirmBtn();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isAdd = false;
        mlistposition = position;
        showAddMoreDialog(position);
    }

}

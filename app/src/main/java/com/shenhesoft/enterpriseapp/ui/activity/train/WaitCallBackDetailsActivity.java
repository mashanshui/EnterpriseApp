package com.shenhesoft.enterpriseapp.ui.activity.train;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.shenhesoft.enterpriseapp.bean.EntruckOrderDetailBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.confirmarrive.GuideAllocationActivity;
import com.shenhesoft.enterpriseapp.ui.adapter.UnloadOrderAdapter;
import com.shenhesoft.enterpriseapp.utils.Base64Utils;
import com.shenhesoft.enterpriseapp.utils.DensityUtils;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.utils.TakePhotoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class WaitCallBackDetailsActivity extends TakePhotoActivity implements AdapterView.OnItemClickListener {

    //录入的装车信息列表
    @BindView(R.id.wait_details_lv)
    ListView lvDetatils;

    private UnloadOrderAdapter mAddEntruckOrderDetailAdapter;

    private Dialog addDialog;

    private String upimg;

    private String upimgcallback;

    private TextView tvUpimg;

    private TextView tvcallimg;


    private List<EntruckOrderDetailBean> mentrucklist;

    private boolean isAdd = true;

    private int mlistposition;

    String orderId;

    private String projectType;

    private int imgck;

    @Override
    public int getLayoutId() {
        return R.layout.activity_train_callback;
    }

    @Override
    protected void initTitle() {
        setTitle("填写回单");
        setRightText("完成 ", v -> {
            for (EntruckOrderDetailBean entruckOrderDetailBean : mentrucklist) {
                if (!TextUtils.isEmpty(entruckOrderDetailBean.getImg()) && !TextUtils.isEmpty(entruckOrderDetailBean.getImgcall())) {

                } else {
                    IToast.showShort("请上传所有回单信息");
                    return;
                }
            }
            finish();
        });
    }


    @Override
    public void takeSuccess(TResult result) {
        if (imgck == 0) {
            upimg = result.getImage().getCompressPath();
            //result.getImage().getOriginalPath()  获取图片真实路径
            tvUpimg.setText("已上传");
            try {
                upimg = "data:image/jpeg;base64," + Base64Utils.encodeFile(upimg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            upimgcallback = result.getImage().getCompressPath();
            //result.getImage().getOriginalPath()  获取图片真实路径
            tvcallimg.setText("已上传");
            try {
                upimgcallback = "data:image/jpeg;base64," + Base64Utils.encodeFile(upimgcallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        if (TextUtils.isEmpty(orderId)) {
            IToast.showShort("订单号为空");
            finish();
        }

        mAddEntruckOrderDetailAdapter = new UnloadOrderAdapter(this, 1);

        lvDetatils.setAdapter(mAddEntruckOrderDetailAdapter);
        lvDetatils.setOnItemClickListener(this);
        getgoodsPlace();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");
        projectType = getIntent().getStringExtra("projectType");
        mentrucklist = new ArrayList<>();
        showAddMoreDialog(-1);

    }


    private void showAddMoreDialog(int position) {

        if (addDialog == null) {
            addDialog = new Dialog(context, R.style.train_wait_addzcyd_dialog_style);
            addDialog.setContentView(R.layout.dialog_train_wait_callback);

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
            if (mentrucklist.get(mposition).getImg() != null) {
                upimg = mentrucklist.get(mposition).getImg();
            }
        }

        TextView tvClean = (TextView) addDialog.findViewById(R.id.dialog_wait_add_tvclean);
        TextView tvSave = (TextView) addDialog.findViewById(R.id.dialog_wait_add_tvsave);
        TextView tvCarNumber = (TextView) addDialog.findViewById(R.id.tv_train_wait_carnumber);
        TextView tvcontainerNumber1 = (TextView) addDialog.findViewById(R.id.et_train_wait_number1);
        TextView tvcontainerNumber2 = (TextView) addDialog.findViewById(R.id.et_train_wait_number2);
        TextView tvsendWeight = (TextView) addDialog.findViewById(R.id.et_train_wait_sendWeight);
        TextView etarriveWeight = (TextView) addDialog.findViewById(R.id.et_train_wait_arriveWeight);
        TextView tvsendWeight2 = (TextView) addDialog.findViewById(R.id.et_train_wait_sendWeight2);
        TextView etarriveWeight2 = (TextView) addDialog.findViewById(R.id.et_train_wait_arriveWeight2);

        LinearLayout ll_containerNumber1 = (LinearLayout) addDialog.findViewById(R.id.ll_containerNumber1);
        LinearLayout ll_containerNumber2 = (LinearLayout) addDialog.findViewById(R.id.ll_containerNumber2);
        LinearLayout ll_unloadWeight2 = (LinearLayout) addDialog.findViewById(R.id.ll_unloadWeight2);
        LinearLayout ll_sendWeight2 = (LinearLayout) addDialog.findViewById(R.id.ll_sendWeight2);

        tvcallimg = (TextView) addDialog.findViewById(R.id.tv_train_call_img);
        tvUpimg = (TextView) addDialog.findViewById(R.id.tv_train_wait_img);
        TextView tvCarType = (TextView) addDialog.findViewById(R.id.tv_train_wait_add_cartype);

        tvUpimg.setText("点击上传");

        tvcallimg.setText("点击上传");

        upimgcallback = "";
        upimg = "";

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
            if (!TextUtils.isEmpty(mentrucklist.get(mposition).getImg())) {
                tvUpimg.setText("点击重新上传");
//                upimg = mentrucklist.get(mposition).getImg();
            } else {
                tvUpimg.setText("点击上传");
            }

            if (!TextUtils.isEmpty(mentrucklist.get(mposition).getImgcall())) {
                tvcallimg.setText("点击重新上传");
//                upimgcallback = mentrucklist.get(mposition).getImgcall();
            } else {
                tvcallimg.setText("点击上传");
            }
            return;
        }
        tvClean.setOnClickListener(v -> {
            addDialog.cancel();
        });

        tvUpimg.setOnClickListener(v -> {
            imgck = 0;
            ShowPicCheck();
        });

        tvcallimg.setOnClickListener(v -> {
            imgck = 1;
            ShowPicCheck();
        });


        tvSave.setOnClickListener(v ->
        {
            EntruckOrderDetailBean bean = mAddEntruckOrderDetailAdapter.getDataSource().get(mlistposition);
//            bean.setImg(mentrucklist.get(mposition).getImg());
//            bean.setImgcall(mentrucklist.get(mposition).getImgcall());
            mAddEntruckOrderDetailAdapter.setData(mentrucklist);
            submit(String.valueOf(bean.getId()), upimg, upimgcallback);
            addDialog.dismiss();
        });

        //....
    }

    /**
     * 火运-等待回单信息提交
     * <p>
     * trainOrderId 火运运单id
     * trainOrderCargoPalceId 车辆火运中间表主键
     * deliverGoodsImg 图片 发货运单
     * arrivalImg 图片 到货运单
     *
     * @return
     */
    private void submit(String trainOrderCargoPalceId, String deliverGoodsImg, String arrivalImg) {
        //type  0只保存运单信息，不跟新状态  1只更新状态。

        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .submitWaitCall(ApiRetrofit.getInstance().submitWaitCallinfo(orderId, trainOrderCargoPalceId, deliverGoodsImg, arrivalImg, 0));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    getgoodsPlace();
                    IToast.showShort("分配信息提交成功！");
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
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
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isAdd = false;
        mlistposition = position;
        showAddMoreDialog(position);
    }

    //在途运载
    private void sendTrain() {
        Observable<RequestResults> observable = HttpManager.getInstance().getOrderService()
                .sendTrain(ApiRetrofit.getInstance().updataTrainParams(orderId, 7));

        HttpObserver observer = new HttpObserver<RequestResults>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("在途运载完成！");
                    finish();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);

    }
}

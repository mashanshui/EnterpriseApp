package com.shenhesoft.enterpriseapp.ui.activity.motor;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jph.takephoto.model.TResult;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.utils.Base64Utils;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.utils.TakePhotoActivity;

import java.math.BigDecimal;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * 作者：Tornado
 * 创作日期：2017/12/27.
 * 描述：汽车短驳》》添加发运信息
 */

public class AddFayxxActivity extends TakePhotoActivity {

    @BindView(R.id.fayxx_et_fahpz)
    EditText etFahpz;             //发货皮重

    @BindView(R.id.fayxx_et_fahmz)
    EditText etFahmz;             //发货毛重

    @BindView(R.id.fayxx_et_fahjz1)
    EditText etFahjz1;             //集装箱发货净重1

    @BindView(R.id.fayxx_et_fahjz2)
    EditText etFahjz2;             //集装箱发货净重2

    @BindView(R.id.fayxx_et_fahjz)
    EditText etFahjz;      //散堆装发货净重

    @BindView(R.id.fayxx_et_huayzb)
    EditText etHuayzb;             //化验指标

    @BindView(R.id.fayxx_tv_yundsc)
    TextView tvYundsc;             //运单上传
    @BindView(R.id.containerNum1)
    TextView mContainerNum1;
    @BindView(R.id.containerNum2)
    TextView mContainerNum2;

    @BindView(R.id.ll_packing)
    LinearLayout mLlPacking;
    @BindView(R.id.ll_container1)
    LinearLayout mLlContainer1;
    @BindView(R.id.ll_container2)
    LinearLayout mLlContainer2;

    private String imgUrl;//图片地址

    String orderid;//订单id

    private String projectType;

    private MotorDetailsBean mMotorDetailsBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_fayxx;
    }

    @Override
    protected void initTitle() {
        setTitle("发运信息");
        setRightText("完成 ", v -> {
            commintData();
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        imgUrl = result.getImage().getCompressPath();
        //result.getImage().getOriginalPath()  获取图片真实路径
        tvYundsc.setText("已上传");
    }

    @OnClick({R.id.fayxx_tv_yundsc})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.fayxx_tv_yundsc:
                ShowPicCheck();
                break;
            default:
                break;
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mMotorDetailsBean = (MotorDetailsBean) getIntent().getSerializableExtra("MotorDetails");
//        orderid = getIntent().getStringExtra("orderid");
        orderid = String.valueOf(mMotorDetailsBean.getOrderid());
        projectType = mMotorDetailsBean.getProjectType();
        if (TextUtils.isEmpty(orderid)) {
            IToast.showShort("当前订单号为空");
            finish();
        }
        setText();
    }

    private void setText() {
        if ("0".equals(projectType)) {
            //集装箱
            mLlContainer1.setVisibility(View.VISIBLE);
            mLlContainer2.setVisibility(View.VISIBLE);
            mLlPacking.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(mMotorDetailsBean.getBoxNumber1())) {
                mContainerNum1.setText(mMotorDetailsBean.getBoxNumber1());
            }
            if (!TextUtils.isEmpty(mMotorDetailsBean.getBoxNumber2())) {
                mContainerNum2.setText(mMotorDetailsBean.getBoxNumber2());
            }
        } else {
            //散堆装
            mLlContainer1.setVisibility(View.GONE);
            mLlContainer2.setVisibility(View.GONE);
            mLlPacking.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    /**
     * 提交数据 请求接口
     */

    private void commintData() {
        String fhpz = etFahpz.getText().toString().trim();
        String fhmz = etFahmz.getText().toString().trim();
        String fhjz = etFahjz.getText().toString().trim();
        String fhjz1 = etFahjz1.getText().toString().trim();
        String fhjz2 = etFahjz2.getText().toString().trim();
        String huayzb = etHuayzb.getText().toString().trim();
        String imgSubmit = "";
        try {
            imgSubmit = "data:image/jpeg;base64," + Base64Utils.encodeFile(imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (!inputverification(fhpz, fhmz,fhjz, fhjz1, fhjz2, huayzb, imgSubmit)) {
            return;
        }
        if ("0" .equals(projectType)) {
            projectType = "1";
        } else {
            projectType = "2";
        }
        Map<String, Object> params = ApiRetrofit.getInstance().submitMotorFayxxParams(orderid, txtCastDecimal(fhpz)
                , txtCastDecimal(fhmz),txtCastDecimal(fhjz), txtCastDecimal(fhjz1), txtCastDecimal(fhjz2), huayzb, imgSubmit, projectType);


        Observable<RequestResults> observable = HttpManager.getInstance()
                .getOrderService().addMotorFayxxForm(params);

        HttpObserver<RequestResults> observer = new HttpObserver<RequestResults>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("发运信息提交成功");
                    finish();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);

    }

    //输入验证...
    private boolean inputverification(String fhpz, String fhmz,String fhjz,
                                      String fhjz1, String fhjz2, String huayzb, String imgUrl) {
        if (fhpz.isEmpty()) {
            IToast.showShort("请填入发货皮重");
            return false;
        }
        if (fhmz.isEmpty()) {
            IToast.showShort("请填入发货毛重");
            return false;
        }
        if (huayzb.isEmpty()) {
            IToast.showShort("请填入化验指标");
            return false;
        }
        if (imgUrl.isEmpty()) {
            IToast.showShort("请上传运单照片");
            return false;
        }
        /**
         * 根据集装箱和散堆装判断数据是否正确
         */
        BigDecimal fhmzNum = new BigDecimal(fhmz);
        BigDecimal fhpzNmu = new BigDecimal(fhpz);

        if (projectType.equals("1")) {
            if (fhjz.isEmpty()) {
                IToast.showShort("请填入发货净重");
                return false;
            }
            double fhjzNum = txtCastDecimal(fhjz);
            if (fhmzNum.subtract(fhpzNmu).doubleValue() != fhjzNum) {
                IToast.showShort("重量填写错误");
                return false;
            }
        } else {
            if (fhjz1.isEmpty()) {
                IToast.showShort("请填入第1个集装箱发货净重");
                return false;
            }
            if (fhjz2.isEmpty()&& !TextUtils.isEmpty(mContainerNum2.getText().toString())) {
                IToast.showShort("请填入第2个集装箱发货净重");
                return false;
            }
            BigDecimal fhjz1Num = new BigDecimal(fhjz1);
            BigDecimal fhjz2Num = new BigDecimal(fhjz2);
            if (fhmzNum.subtract(fhpzNmu).doubleValue() != fhjz1Num.add(fhjz2Num).doubleValue()) {
                IToast.showShort("重量填写错误");
                return false;
            }
        }
        return true;
    }

    private double txtCastDecimal(String txt) {
        try {
            return Double.parseDouble(txt);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

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
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jph.takephoto.model.TResult;
import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.bean.ContainerBean;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderDetailBean;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.adapter.AddEntruckOrderDetailAdapter;
import com.shenhesoft.enterpriseapp.utils.Base64Utils;
import com.shenhesoft.enterpriseapp.utils.DensityUtils;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.utils.SearchPupItenAdapter;
import com.shenhesoft.enterpriseapp.utils.TakePhotoActivity;
import com.shenhesoft.enterpriseapp.widget.BottomListChooseDialog;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;
import com.shenhesoft.enterpriseapp.widget.LocationPop;
import com.shenhesoft.enterpriseapp.widget.SearchFragment;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.kit.Codec;
import io.reactivex.Observable;

/**
 * 作者：Tornado
 * 创作日期：2017/12/28.
 * 描述：火车干线》等待装车》装车详单
 */

public class DengdzcDetailsActivity extends TakePhotoActivity implements View.OnClickListener, AdapterView.OnItemClickListener ,AdapterView.OnItemLongClickListener{
    private static final String TAG = "DengdzcDetailsActivity";
    //录入的装车信息列表
    @BindView(R.id.wait_details_lv)
    ListView lvDetatils;
    //添加更多Txt
    @BindView(R.id.wait_tv_add_more_details)
    TextView tvAdd;

    private AddEntruckOrderDetailAdapter mAddEntruckOrderDetailAdapter;

    private Dialog addDialog;

    private ConfirmDialog deleteDialog;

    private List<LocationPopBean> mList;
    private BottomListChooseDialog mChooseDialog;

    private String upimg;

    private TextView tvUpimg;

    private List<EntruckOrderDetailBean> mentrucklist;

    private boolean isAdd = true;

    private int mlistposition;

    private String projectType="";

    private String orderId;

    private List<ContainerBean> mContainerList;

    private SearchFragment searchFragment;
    private List<String> mSearchList;

    /**
     * 承认车数，装车时最大不能超过这个数量
     */
    private int admitNum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_train_wait_details;
    }

    @Override
    protected void initTitle() {
        cancelBtnLeft();
        setTitle("装车详单");
        setRightText("保存 ", v -> {
            Intent intent = new Intent().putExtra("mentrucklist", (Serializable) mentrucklist);
            setResult(RESULT_OK, intent);
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
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (getIntent().getSerializableExtra("listobj") != null) {
            mentrucklist = (ArrayList<EntruckOrderDetailBean>) getIntent().getSerializableExtra("listobj");
        }
        mAddEntruckOrderDetailAdapter = new AddEntruckOrderDetailAdapter(this);
        mAddEntruckOrderDetailAdapter.setData(mentrucklist);
        lvDetatils.setAdapter(mAddEntruckOrderDetailAdapter);
        lvDetatils.setOnItemClickListener(this);
        lvDetatils.setOnItemLongClickListener(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        projectType = getIntent().getStringExtra("projectType");
        orderId = getIntent().getStringExtra("orderId");
        admitNum = getIntent().getIntExtra("admitNum", 0);
        tvAdd.setOnClickListener(this);
        mList = new ArrayList<>();
        mentrucklist = new ArrayList<>();
        mSearchList = new ArrayList<>();
        showAddMoreDialog(-1);
    }

    private void showAddMoreDialog(int position) {
        if (mList.size() == 0) {
            getTrainTypeList();
        }

        if (addDialog == null) {
            addDialog = new Dialog(context, R.style.train_wait_addzcyd_dialog_style);
            addDialog.setContentView(R.layout.dialog_train_wait_add);

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
        EditText etCarNumber = (EditText) addDialog.findViewById(R.id.et_train_wait_carnumber);
        TextView etcontainerNumber1 = (TextView) addDialog.findViewById(R.id.et_train_wait_number1);
        TextView etcontainerNumber2 = (TextView) addDialog.findViewById(R.id.et_train_wait_number2);

        EditText etsendWeight = (EditText) addDialog.findViewById(R.id.et_train_wait_sendWeight);
        EditText etsendWeight2 = (EditText) addDialog.findViewById(R.id.et_train_wait_sendWeight2);
        tvUpimg = (TextView) addDialog.findViewById(R.id.tv_train_wait_img);
        TextView tvCarType = (TextView) addDialog.findViewById(R.id.tv_train_wait_add_cartype);

        LinearLayout ll_containerNumber1 = (LinearLayout) addDialog.findViewById(R.id.ll_containerNumber1);
        LinearLayout ll_containerNumber2 = (LinearLayout) addDialog.findViewById(R.id.ll_containerNumber2);
        LinearLayout ll_sendWeight = (LinearLayout) addDialog.findViewById(R.id.ll_sendWeight);
        LinearLayout ll_sendWeight2 = (LinearLayout) addDialog.findViewById(R.id.ll_sendWeight2);

        //清空数据
        tvCarType.setText("");
        etCarNumber.setText("");
        etcontainerNumber1.setText("");
        etcontainerNumber2.setText("");
        etsendWeight.setText("");
        etsendWeight2.setText("");
        tvUpimg.setText("点击上传");

        if (projectType.equals("1")) {
            ll_containerNumber1.setVisibility(View.GONE);
            ll_containerNumber2.setVisibility(View.GONE);
            ll_sendWeight.setVisibility(View.VISIBLE);
            ll_sendWeight2.setVisibility(View.GONE);
        } else {
            ll_containerNumber1.setVisibility(View.VISIBLE);
            ll_containerNumber2.setVisibility(View.VISIBLE);
            ll_sendWeight.setVisibility(View.VISIBLE);
            ll_sendWeight2.setVisibility(View.VISIBLE);
        }
        if (mposition != -1) {
            tvCarType.setText(mentrucklist.get(mposition).getCarType());
            etCarNumber.setText(mentrucklist.get(mposition).getCarNumber());
            etcontainerNumber1.setText(mentrucklist.get(mposition).getContainerNumber1());
            etcontainerNumber2.setText(mentrucklist.get(mposition).getContainerNumber2());
            etsendWeight.setText(mentrucklist.get(mposition).getSendWeight());
            etsendWeight2.setText(mentrucklist.get(mposition).getConSendWeight2());
            if (!TextUtils.isEmpty(mentrucklist.get(mposition).getImg())) {
                tvUpimg.setText("点击重新上传");
            } else {
                tvUpimg.setText("点击上传");
            }
            return;
        }
        etcontainerNumber1.setOnClickListener(v -> {
            searchFragment = SearchFragment.newInstance();
            searchFragment.setOnTextWatcherListener(new SearchFragment.IOnTextWatcherListener() {
                @Override
                public void textChange(String s) {
                    loadContainerNumberData(s);
                }
            });
            searchFragment.setOnSearchClickListener(key -> {
                if (mSearchList.contains(key)) {
                    etcontainerNumber1.setText(key);
                } else {
                    IToast.showShort("请选择存在的集装箱");
                }
            });
            searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
            loadContainerNumberData("");
        });
        etcontainerNumber2.setOnClickListener(v -> {
            searchFragment = SearchFragment.newInstance();
            searchFragment.setOnTextWatcherListener(s -> {
                loadContainerNumberData(s);
            });
            searchFragment.setOnSearchClickListener(key -> {
                if (mSearchList.contains(key)) {
                    etcontainerNumber2.setText(key);
                } else {
                    IToast.showShort("请选择存在的集装箱");
                }
            });
            searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
            loadContainerNumberData("");
        });

        tvClean.setOnClickListener(v -> {
            addDialog.cancel();
        });

        tvUpimg.setOnClickListener(v -> {
            ShowPicCheck();
        });

        tvCarType.setOnClickListener(v -> {
            if (mList.size() == 0) {
                getTrainTypeList();
                return;
            }
            if (mChooseDialog == null) {
                mChooseDialog = new BottomListChooseDialog(context, mList);
            }
            mChooseDialog.setOnselectListener(position -> {
                tvCarType.setText(mList.get(position).getName());
                    }
            );
            mChooseDialog.show();

        });

        tvSave.setOnClickListener(v ->
        {

            EntruckOrderDetailBean bean = new EntruckOrderDetailBean(tvCarType.getText().toString(),
                    etCarNumber.getText().toString(),
                    etcontainerNumber1.getText().toString(),
                    etcontainerNumber2.getText().toString(),
                    etsendWeight.getText().toString(),
                    etsendWeight2.getText().toString(),
                    upimg);
            if (isAdd) {
                int num = SharedPref.getInstance(context).getInt(AppConstant.AdmitNum, 0);
                SharedPref.getInstance(context).putInt(AppConstant.AdmitNum, num+1);
                mentrucklist.add(bean);
            } else {
                mentrucklist.set(mlistposition, bean);
            }
            mAddEntruckOrderDetailAdapter.setData(mentrucklist);
            addDialog.dismiss();
        });


        //....
    }


    private void showDeleteDialog(int position) {
        if (deleteDialog == null) {
            deleteDialog = new ConfirmDialog(context);
            deleteDialog.setTitle1("确认删除");
            deleteDialog.setCancelListener(v -> deleteDialog.dismiss());
        }
        deleteDialog.setConfirmListener(v -> {
            mentrucklist.remove(position);
            int num = SharedPref.getInstance(context).getInt(AppConstant.AdmitNum, 0);
            SharedPref.getInstance(context).putInt(AppConstant.AdmitNum, num-1);
            deleteDialog.dismiss();
            mAddEntruckOrderDetailAdapter.setData(mentrucklist);
        });
        deleteDialog.show();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wait_tv_add_more_details:
                int num = SharedPref.getInstance(context).getInt(AppConstant.AdmitNum, 0);
                if (num >= admitNum) {
                    IToast.showShort("装车数量不能超过承认车数");
                } else {
                    isAdd = true;
                    showAddMoreDialog(-1);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 获取车型信息
     */
    private void getTrainTypeList() {
        Observable<RequestResultsList<LocationPopBean>> observable = HttpManager.getInstance().getUserService()
                .getTrainTypeList(ApiRetrofit.getInstance().getTrainTypelist());

        HttpObserver<RequestResultsList<LocationPopBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    mList = data.getObj();
                    for (int i = 0; i < mList.size(); i++) {
                        LocationPopBean locationPopBean = mList.get(i);
                        locationPopBean.setName(locationPopBean.getTrainKindCode() + locationPopBean.getTrainTypeCode());
                        mList.set(i, locationPopBean);
                    }
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 获取可以使用的集装箱信息
     */
    private void loadContainerNumberData(String text) {
        Observable<RequestResultsList<ContainerBean>> observable = HttpManager.getInstance().getOrderService()
                .getTrainContainerNum(ApiRetrofit.getInstance().getTrainContainerNum(orderId, text));

        HttpObserver<RequestResultsList<ContainerBean>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    mContainerList = data.getObj();
                    mSearchList.clear();
                    for (int i = 0; i < mContainerList.size(); i++) {
                        mSearchList.add(mContainerList.get(i).getText());
                    }
                    searchFragment.setData(mSearchList);
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isAdd = false;
        mlistposition = position;
        showAddMoreDialog(position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showDeleteDialog(position);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mentrucklist.size() == 0) {
            finish();
        } else {
            IToast.showShort("请保存装车信息");
        }
    }
}

package com.shenhesoft.enterpriseapp.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseEvent;
import com.shenhesoft.enterpriseapp.base.BaseFragment;
import com.shenhesoft.enterpriseapp.bean.ContainerBean;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.activity.confirmarrive.DispatchOrderActivity;
import com.shenhesoft.enterpriseapp.ui.activity.motor.MotorDetailsActivity;
import com.shenhesoft.enterpriseapp.ui.activity.task.AllotTaskAdapter;
import com.shenhesoft.enterpriseapp.ui.activity.waitdispatch.RejectActivity;
import com.shenhesoft.enterpriseapp.ui.adapter.WaitDispatchAdapter;
import com.shenhesoft.enterpriseapp.utils.DensityUtils;
import com.shenhesoft.enterpriseapp.utils.IToast;
import com.shenhesoft.enterpriseapp.widget.ConfirmDialog;
import com.shenhesoft.enterpriseapp.widget.SearchFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;
import okhttp3.Dispatcher;

import static com.shenhesoft.enterpriseapp.AppConstant.REJECT_ORDER;

/**
 * @author 张继淮
 * @date 2017/12/5
 * @desc 等待调度fragment
 */


public class WaitDispatchFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private static final String TAG = "WaitDispatchFragment";
    @BindView(R.id.lv_common)
    ListView mListView;
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @BindView(R.id.myrefreshLayout)
    BGARefreshLayout mMyrefreshLayout;
    Unbinder unbinder;
    private WaitDispatchAdapter mAdapter;
    private ConfirmDialog mConfirmDialog;
    private List<MotorDetailsBean> list;
    private List<MotorDetailsBean> refreshList;
    //重新加载数据
    private boolean isreload = true;
    private int pageNo = 1;
    private boolean isLoadMore = false;
    private Dialog allotDialog;

    private int Mposition;
    private SearchFragment searchFragment;
    private List<String> mSearchList;
    private List<ContainerBean> mContainerList;
    private String takeCarogoPlaceName;
    private String takeCargoSiteName;
    private String takeCargoPlaceId;
    private String takeCargoSiteId;

    //获取集装箱号
    private String projectId;
    private String stepSelectCodeInt;
    private String receiptGooddsId;
    private String sendGoodsId;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        processLogic();
        mSearchList = new ArrayList<>();
        list = new ArrayList<>();
        mAdapter = new WaitDispatchAdapter(context);
        mListView.setAdapter(mAdapter);
        mCheckBox.setOnClickListener(v -> {
            mAdapter.setCheckAll(mCheckBox.isChecked());
            notifyConfirmBtn();
        });
        mAdapter.setBottonTxtEven(new AllotTaskAdapter.OnBottonTxtEven() {
            @Override
            public void allotClick(int position) {
                Mposition = position;
                Intent intent = new Intent(context, DispatchOrderActivity.class);
                intent.putExtra("orderId", String.valueOf(list.get(position).getOrderid()));
                startActivityForResult(intent, 101);
            }

            @Override
            public void startClick(int position) {

            }

            @Override
            public void stopClick(int position) {

            }
        });
        mAdapter.setChangeListener(() -> notifyConfirmBtn());
        mAdapter.setOnRejectListener(position -> Router.newIntent(context).to(RejectActivity.class).putInt("position", position)
                .putString("orderid", String.valueOf(mAdapter.getDataSource().get(position).getOrderid())).launch());
        mListView.setOnItemClickListener((parent, view, position, id) ->
                Router.newIntent(context).to(MotorDetailsActivity.class).
                        putSerializable("motorbean", (Serializable) list.get(position)).launch());

    }

    protected void processLogic() {
        mMyrefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder meiTuanRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        mMyrefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }


    private void notifyConfirmBtn() {
        int checkNum = 0;
        List<MotorDetailsBean> dataSource = mAdapter.getDataSource();
        for (MotorDetailsBean bean : dataSource) {
            if (bean.isChecked()) {
                checkNum++;
            }
        }
        if (checkNum != dataSource.size()) {
            mCheckBox.setChecked(false);
        } else {
            mCheckBox.setChecked(true);
        }
        mBtnConfirm.setText("确认调度(" + checkNum + ")");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 200) {
            takeCarogoPlaceName = data.getStringExtra("takeCarogoPlaceName");
            takeCargoPlaceId = data.getStringExtra("takeCargoPlaceId");
            takeCargoSiteName = data.getStringExtra("takeCargoSiteName");
            takeCargoSiteId = data.getStringExtra("takeCargoSiteId");
            if (list.get(Mposition).getProjectType().equals("0")) {
                showAddMoreDialog();
            } else {
                submitPlace(String.valueOf(list.get(Mposition).getOrderid()), "", "");
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isreload) {
            submit();
            isreload = false;
        }
    }

    private void showAddMoreDialog() {
        if (allotDialog == null) {
            allotDialog = new Dialog(context, R.style.train_wait_addzcyd_dialog_style);
            allotDialog.setContentView(R.layout.dialog_wait_check);

            WindowManager.LayoutParams lp = allotDialog.getWindow().getAttributes();
            lp.width = (int) (DensityUtils.getScreenWidthPixels(context) * 0.7);//设置宽度
            lp.gravity = Gravity.CENTER; //设置居中

            initallotDialogViews();

        } else {
            allotDialog.show();
        }
    }

    private void initallotDialogViews() {
        TextView tvClean = (TextView) allotDialog.findViewById(R.id.dialog_wait_add_tvclean);
        tvClean.setOnClickListener(v -> allotDialog.cancel());
        TextView etNumber1 = (TextView) allotDialog.findViewById(R.id.et_number1);
        TextView etNumber2 = (TextView) allotDialog.findViewById(R.id.et_number2);
        projectId = list.get(Mposition).getProjectId();
        stepSelectCodeInt = list.get(Mposition).getStepSelectCode();
        receiptGooddsId = list.get(Mposition).getReceiptCompanyId();
        sendGoodsId = list.get(Mposition).getSendCompanyId();

        etNumber1.setOnClickListener(v -> {
            searchFragment = SearchFragment.newInstance();
            searchFragment.setOnTextWatcherListener(s -> {
                loadContainerNumberData(s);
            });
            searchFragment.setOnSearchClickListener(key -> {
                if (mSearchList.contains(key)) {
                    etNumber1.setText(key);
                } else {
                    IToast.showShort("请选择存在的集装箱");
                }
            });
            searchFragment.show(getFragmentManager(), SearchFragment.TAG);
            loadContainerNumberData("");
        });
        etNumber2.setOnClickListener(v -> {
            searchFragment = SearchFragment.newInstance();
            searchFragment.setOnTextWatcherListener(s -> {
                loadContainerNumberData(s);
            });
            searchFragment.setOnSearchClickListener(key -> {
                if (mSearchList.contains(key)) {
                    etNumber2.setText(key);
                } else {
                    IToast.showShort("请选择存在的集装箱");
                }
            });
            searchFragment.show(getFragmentManager(), SearchFragment.TAG);
            loadContainerNumberData("");
        });

        TextView tvSave = (TextView) allotDialog.findViewById(R.id.dialog_wait_add_tvsave);
        tvSave.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(etNumber1.getText().toString())) {
                submitPlace(String.valueOf(list.get(Mposition).getOrderid()), etNumber1.getText().toString(), etNumber2.getText().toString());
            } else {
                IToast.showShort("请选择集装箱");
                return;
            }
            allotDialog.dismiss();

        });
        allotDialog.show();

        //....
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wait_dispatch;
    }

    @Optional
    @OnClick({R.id.btn_confirm})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (mConfirmDialog == null) {
                    mConfirmDialog = new ConfirmDialog(context);
                    mConfirmDialog.setTitle1(getString(R.string.confirm_action));
                    mConfirmDialog.setTitle2(getString(R.string.ps_cannot_back));
                    mConfirmDialog.setCancelListener(v -> mConfirmDialog.dismiss());
                    mConfirmDialog.setConfirmListener(v -> {
                        Toast.makeText(context, "核准完成", Toast.LENGTH_SHORT).show();
                        mConfirmDialog.dismiss();
                    });
                }
                mConfirmDialog.show();
                break;
            default:
                break;
        }
    }


    /**
     * 提交
     */
    private void submit() {
        Observable<RequestResults<ListALLResults<MotorDetailsBean>>> observable = HttpManager.getInstance().getUserService()
                .getWaitDispatchList(ApiRetrofit.getInstance().waitdisptach(pageNo + ""));

        HttpObserver<RequestResults<ListALLResults<MotorDetailsBean>>> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }

                    refreshList = data.getObj().getRows();
                    /**
                     * 下拉刷新时数据是从头开始，因此需要setData
                     * 上拉加载时数据是往后叠加的，所以需要addData
                     * 如果上拉加载时没有加载到数据，pageNo页面数需要保持不变，减1
                     */
                    //上拉加载后有数据
                    if (isLoadMore && !refreshList.isEmpty()) {
                        mAdapter.addData(refreshList);
                        list.addAll(refreshList);
                        isLoadMore = false;
                        mAdapter.notifyDataSetChanged();
                    }
                    //上拉加载后没有数据
                    else if (isLoadMore && refreshList.isEmpty()) {
                        pageNo--;
                        isLoadMore = false;
                    }
                    //下拉刷新后有数据
                    else if (!isLoadMore && !refreshList.isEmpty()) {
                        mAdapter.setData(refreshList);
                        if (!list.isEmpty()) {
                            list.clear();
                        }
                        list.addAll(refreshList);
                        mAdapter.notifyDataSetChanged();
                    }
                    //下拉刷新后没有数据了
                    else if (!isLoadMore && refreshList.isEmpty()) {
                        mAdapter.setData(refreshList);
                        if (!list.isEmpty()) {
                            list.clear();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
        observer.setRefreshLayout(mMyrefreshLayout);
        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    /**
     * 获取可以使用的集装箱信息
     */
    private void loadContainerNumberData(String text) {
        Observable<RequestResultsList<ContainerBean>> observable = HttpManager.getInstance().getOrderService()
                .getOrderContainerNum(ApiRetrofit.getInstance().getOrderContainerNum(text,projectId, stepSelectCodeInt, receiptGooddsId, sendGoodsId));

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


    /**
     * 提交货场货位信息
     */
    private void submitPlace(String orderid, String containerNumber1, String containerNumber2) {

        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .submitDispatchGoodsyard(ApiRetrofit.getInstance().submitDispatchGoodsyard(orderid, takeCarogoPlaceName, takeCargoSiteName, takeCargoPlaceId, takeCargoSiteId));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    Dispatch(orderid, containerNumber1, containerNumber2);
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    private void Dispatch(String orderid, String containerNumber1, String containerNumber2) {
        Observable<RequestResults> observable = HttpManager.getInstance().getUserService()
                .checkDispatch(ApiRetrofit.getInstance().checkDispatch(orderid, containerNumber1, containerNumber2));

        HttpObserver<RequestResults> observer = new HttpObserver<>(context,
                data -> {
                    if (data.getState() != 1) {
                        IToast.showShort(data.getMsg());
                        return;
                    }
                    IToast.showShort("核准完成");
                    submit();
                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }

    @Override
    public boolean bindEventBus() {
        return true;
    }

    @Override
    protected void getBusEvent(BaseEvent msg) {
        super.getBusEvent(msg);
        switch (msg.getTag()) {
            case REJECT_ORDER:
                isreload = true;
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoadMore = false;
        /**
         * 下拉刷新时页面从头开始加载
         */
        pageNo = 1;
        submit();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        /**
         * 上拉加载时每次加载页面加1
         */
        pageNo++;
        submit();
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

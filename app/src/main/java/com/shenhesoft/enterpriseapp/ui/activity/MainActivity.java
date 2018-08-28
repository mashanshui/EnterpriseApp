package com.shenhesoft.enterpriseapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.ui.activity.user.AccountManagerActivity;
import com.shenhesoft.enterpriseapp.ui.activity.user.LoginActivity;
import com.shenhesoft.enterpriseapp.MyApplication;
import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseActivity;
import com.shenhesoft.enterpriseapp.base.BaseEvent;
import com.shenhesoft.enterpriseapp.bean.UserinfoBean;
import com.shenhesoft.enterpriseapp.net.ApiRetrofit;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.requestutil.HttpManager;
import com.shenhesoft.enterpriseapp.requestutil.HttpObserver;
import com.shenhesoft.enterpriseapp.ui.adapter.MyFragmentVpAdapter;
import com.shenhesoft.enterpriseapp.ui.fragment.BusinessFragment;
import com.shenhesoft.enterpriseapp.ui.fragment.HomePageFragment;
import com.shenhesoft.enterpriseapp.ui.fragment.LocationFragment;
import com.shenhesoft.enterpriseapp.ui.fragment.UnusualFragment;
import com.shenhesoft.enterpriseapp.utils.AppUtil;
import com.shenhesoft.enterpriseapp.widget.NoScrollViewPager;
import com.shenhesoft.enterpriseapp.widget.adapter.ViewPagerAdapter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description MainActivity
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.rb_home)
    RadioButton rb_home;
    @BindView(R.id.rb_location)
    RadioButton rb_location;
    @BindView(R.id.rb_message)
    RadioButton rb_message;
    @BindView(R.id.rb_business)
    RadioButton rb_business;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.ll_account_manager)
    LinearLayout mLlAccoutManager;
    @BindView(R.id.ll_main_layout)
    LinearLayout mLlMainLayout;
    @BindView(R.id.tv_username)
    TextView mTvUsername;

    private ViewPagerAdapter adapter;
    private List<Fragment> fragments;
    //    String imToken = "utsDK2geOFq4JPUSjkpUODxxUmxmMzynL4xrH5irEqPka2+QANDEyPhj7X75sGGREtLBrmOjNFM=";
//    String imToken = "oGnIQrvafekwGbn915mEJDxxUmxmMzynL4xrH5irEqPka2+QANDEyEsq/rt/K0ir5otEmuhGLYg=";
    private ArrayList<Object> msgList;
    private ConversationListFragment mConversationListFragment = null;
    private boolean isDebug;
    private Conversation.ConversationType[] mConversationsTypes = null;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        String rongtoken = SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.rongCloudToken, "");

//        rongtoken = "YZcRxSYaWHfd9druIhD5UDxxUmxmMzynL4xrH5irEqNKYlY9j1k7up5O62/5sNFQEtLBrmOjNFM=";
        if (!TextUtils.isEmpty(rongtoken)) {
            connectIm(rongtoken);
        }
        initViewpager();
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mLlMainLayout.setTranslationX(slideOffset * drawerView.getWidth());

            }
        });
//        submit();
        XLog.d(sHA1(this));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (TextUtils.isEmpty(SharedPref.getInstance(context).getString(AppConstant.Userinfo, ""))) {
            Router.newIntent(this).to(LoginActivity.class).launch();
        }
        mTvUsername.setText(SharedPref.getInstance(context).getString(AppConstant.UserName, ""));
    }


    private void submit() {
        Observable<RequestResults<UserinfoBean>> observable = HttpManager.getInstance().getUserService()
                .userLogin(ApiRetrofit.getInstance().login("admin", "123456"));

        HttpObserver<RequestResults<UserinfoBean>> observer = new HttpObserver<>(context,
                data -> {

                });

        HttpManager.getInstance().statrPostTask(observable, observer);
    }


    @Override
    public void onRequestSuccess(int requestTag, RequestResults requestResults) {
        super.onRequestSuccess(requestTag, requestResults);
        switch (requestTag) {
            case AppConstant.API_LOGIN:

                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestError(int requestTag, NetError error) {
        super.onRequestError(requestTag, error);
        switch (requestTag) {
            case AppConstant.API_LOGIN:

                break;
            default:
                break;
        }
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean bindEventBus() {
        return true;
    }

    private void initViewpager() {
        fragments = new ArrayList<>();
        fragments.add(new HomePageFragment());
        fragments.add(new LocationFragment());
        fragments.add(initConversationList());
        fragments.add(new BusinessFragment());
//        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        MyFragmentVpAdapter myadapter = new MyFragmentVpAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(myadapter);
        viewpager.setOffscreenPageLimit(4);
        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {

            }

            @Override
            public void onError(RongIMClient.ErrorCode e) {

            }
        }, mConversationsTypes);
    }

    private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            mConversationListFragment = new ConversationListFragment();
            mConversationListFragment.setAdapter(new ConversationListAdapter(RongContext.getInstance()));
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "fals") //设置私聊会话是否聚合显示
//                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                    .build();
            mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                    Conversation.ConversationType.GROUP
            };
            mConversationListFragment.setUri(uri);
            return mConversationListFragment;
        } else {
            return mConversationListFragment;
        }
    }

    /**
     * 连接服务器，在整个应用程序全局，只需要调用一次，需在 {init} 之后调用。
     * 如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。
     *
     * @return RongIMClient  客户端核心类的实例。
     */
    private void connectIm(String token) {
        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.d("LoginActivity", "Token 错误");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess" + userid);
//                    startConversation();

                    //        //监听接收到的消息
                    if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext())))
                        RongIM.setOnReceiveMessageListener((message, i) -> {
                            return false;
                        });

//                    UserInfo userInfo = new UserInfo("Q" + AppUtil.getUserinfo().getId(), AppUtil.getUserinfo().getName(), Uri.parse("http://d.hiphotos.baidu.com/baike/pic/item/1f178a82b9014a902df55272a1773912b21bee32.jpg"));
                    UserInfo userInfo = new UserInfo("Q" + AppUtil.getUserinfo().getId(), AppUtil.getUserinfo().getName(),null);
                    /**
                     * 设置当前用户信息，
                     *
                     * @param userInfo 当前用户信息
                     */
                    RongIM.getInstance().setCurrentUserInfo(userInfo);
                    /**
                     * 设置消息体内是否携带用户信息。
                     *
                     * @param state 是否携带用户信息，true 携带，false 不携带。
                     */
                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("LoginActivity", "--onError" + errorCode);
//                    connectIm();
                }
            });
        }

    }


    @OnClick({R.id.rb_home, R.id.rb_location, R.id.rb_message, R.id.rb_business, R.id.ll_account_manager, R.id.btn_logout})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                rb_home.setChecked(true);
                viewpager.setCurrentItem(0);
                break;
            case R.id.rb_location:
                rb_location.setChecked(true);
                viewpager.setCurrentItem(1);
                break;
            case R.id.rb_message:
                rb_message.setChecked(true);
                viewpager.setCurrentItem(2);
                break;
            case R.id.rb_business:
                rb_business.setChecked(true);
                viewpager.setCurrentItem(3);
                break;
            case R.id.ll_account_manager:
                Router.newIntent(this).to(AccountManagerActivity.class).launch();
                break;
            case R.id.btn_logout:
                //清除用户数据
                SharedPref.getInstance(context).putString(AppConstant.UserName, "");
                SharedPref.getInstance(context).putString(AppConstant.UserPassword, "");
                Router.newIntent(this).to(LoginActivity.class).launch();
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void getBusEvent(BaseEvent msg) {
        switch (msg.tag) {
            case AppConstant.SHOW_MENU:
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
            case AppConstant.LOGIN:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }

                String rongtoken = SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.rongCloudToken, "");
                connectIm(rongtoken);
                XLog.d("MainActivity" + "登陆成功");
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongIM.getInstance().logout();
    }
}

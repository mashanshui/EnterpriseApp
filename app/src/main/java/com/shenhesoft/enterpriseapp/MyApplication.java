package com.shenhesoft.enterpriseapp;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import cn.droidlover.xdroidmvp.net.XApi;
import io.rong.imkit.RongIM;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description MyApplication
 */

public class MyApplication extends MultiDexApplication {
    public static final boolean IS_DEBUG = true;
    public static final String BUGLY_APPID = "fbc78fc3a9";
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //注册Im
        initRongCloud();
        //注册网络请求
        initNetProvider();
        //debug下检测漏洞
//        if (IS_DEBUG) {
//            LeakCanary.install(this);
//        } else {
//            //Bugly
//            CrashReport.initCrashReport(getApplicationContext(), BUGLY_APPID, false);
//        }
    }

    private void initNetProvider() {
        ((Runnable) () -> XApi.registerProvider(new NetProvider() {
            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public boolean dispatchProgressEnable() {
                return false;
            }
        })).run();
    }

    private void initRongCloud() {
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            RongIM.init(this);
        }

//        try {
//            RongIMClient.registerMessageType(DeleteContactMessage.class);
//        } catch (AnnotationNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    @Nullable
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}

package com.shenhesoft.enterpriseapp.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

import com.shenhesoft.enterpriseapp.MyApplication;
import com.shenhesoft.enterpriseapp.bean.TaskBean;
import com.shenhesoft.enterpriseapp.bean.UserinfoBean;

/**
 * Created by Luojingjing on 2017/11/8.
 */

public class AppUtil {
    private static SaveObjectUtils mSaveObjectUtils;

    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";

    @TargetApi(14)
    public static int getNavigationBarHeight(Activity context) {
        Resources res = context.getResources();
        boolean mInPortrait = (res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavBar(context)) {
                String key;
                if (mInPortrait) {
                    key = NAV_BAR_HEIGHT_RES_NAME;
                } else {
                    key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME;
                }
                return getInternalDimensionSize(res, key);
            }
        }
        return result;
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 调用拨号界面
     *
     * @param phone   电话号码
     * @param context 上下文
     */
    public static void call(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /*private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";
    private static String sNavBarOverride;
    static {
        // Android allows a system property to override the presence of the navigation bar.
        // Used by the emulator.
        // See https://github.com/android/platform_frameworks_base/blob/master/policy/src/com/android/internal/policy/impl/PhoneWindowManager.java#L1076
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
                sNavBarOverride = null;
            }
        }
    }*/

    /**
     * 判断手机是否有底部导航栏--没有实体键的手机有虚拟按键
     *
     * @param context
     * @return
     */
    public static boolean hasNavBar(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = context.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }

    }

    public static SaveObjectUtils getAppSPObj() {
        if (mSaveObjectUtils == null) {
            mSaveObjectUtils = new SaveObjectUtils(MyApplication.getContext(), "APPINFO");
        }
        return mSaveObjectUtils;
    }

    public static UserinfoBean getUserinfo() {
        return getAppSPObj().getObject("user", UserinfoBean.class);
    }

    public static TaskBean getMyTask() {
        return getAppSPObj().getObject("task", TaskBean.class);
    }

    public static void setUserinfo(Object obj) {
        getAppSPObj().setObject("user", obj);
    }

    public static void setMyTask(Object obj) {
        getAppSPObj().setObject("task", obj);
    }


}

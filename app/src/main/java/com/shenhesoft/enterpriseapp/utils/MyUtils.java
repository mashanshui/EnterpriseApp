package com.shenhesoft.enterpriseapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 张明星
 * @date 2017/12/23
 * @desc 我的工具类
 */

public class MyUtils {

    public static Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5|WJ]{1}[A-Z0-9]{6}$");

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


    public static boolean checkNubmer(String CarNumber) {
        Matcher matcher = pattern.matcher(CarNumber);
        if (!matcher.matches())
            return false;
        else
            return true;


    }

    /**
     * 获取当前月份
     *
     * @return 当前月份
     */
    public static String getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int month = cal.get(Calendar.MONTH) + 1;
        return month + "";
    }

}

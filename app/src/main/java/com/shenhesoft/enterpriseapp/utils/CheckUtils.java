package com.shenhesoft.enterpriseapp.utils;

import cn.droidlover.xdroidmvp.kit.Kits;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description CheckUtils工具类
 */

public class CheckUtils {
    /**
     * 判断数据是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        return Kits.Empty.check(obj);
    }

    /**
     * 判断数据是否为不空
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return !Kits.Empty.check(obj);
    }

}

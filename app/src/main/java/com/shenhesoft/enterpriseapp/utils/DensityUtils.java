/**
 * Copyright (c) 2015, Spencer , ChinaSunHZ (www.spencer-dev.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shenhesoft.enterpriseapp.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;


/**
 * Pixel operations tool
 * 像素、密度、屏幕操作工具类
 */
public class DensityUtils {

    private DensityUtils() {
    }

    /**
     * dp converter to px
     *
     * @param mContext Context
     * @param dpValue  before computing dp
     * @return after computing px
     */
    public static int dip2px(Context mContext, float dpValue) {
        float scale = mContext.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px converter to dp
     *
     * @param mContext Context
     * @param pxValue  before computing px
     * @return after computing dp
     */
    public static int px2dip(Context mContext, float pxValue) {
        float scale = mContext.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px converter to sp, To ensure that the same text size
     *
     * @param mContext Context
     * @param pxValue  before computing px
     * @return after computing sp
     */
    public static int px2sp(Context mContext, float pxValue) {
        float fontScale = mContext.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp converter to px, To ensure that the same text size
     *
     * @param mContext Context
     * @param spValue  before computing sp
     * @return after computing px
     */
    public static int sp2px(Context mContext, float spValue) {
        float fontScale = mContext.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * Gets the screen width and height, the unit is px
     * 获得屏幕的宽和高 单位像素
     *
     * @param mContext Context
     * @return point.x : width ,point.y : height
     */
    public static Point getScreenMetrics(Context mContext) {
        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);

    }

    /**
     * Gets the screen width  the unit is px
     * 获得屏幕的宽 单位像素
     *
     * @param mContext Context
     * @return point.x : width ,point.y : height
     */
    public static int getScreenWidthPixels(Context mContext) {
        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        return w_screen;

    }

    /**
     * Gets the screen  height, the unit is px
     * 获得屏幕的高 单位像素
     *
     * @param mContext Context
     * @return point.x : width ,point.y : height
     */
    public static int getScreenHeightPixels(Context mContext) {
        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        int w_screen = dm.heightPixels;
        return w_screen;

    }


    /**
     * Get screen aspect ratio
     *
     * @param mContext Context
     * @return screen aspect ratio
     */
    public static float getScreenRate(Context mContext) {
        Point P = getScreenMetrics(mContext.getApplicationContext());
        float H = P.y;
        float W = P.x;
        return (H / W);
    }

}

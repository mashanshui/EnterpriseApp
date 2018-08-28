package com.shenhesoft.enterpriseapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.shenhesoft.enterpriseapp.R;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description ImageUtils工具类
 */

public class ImageUtils {
    /**
     * 图片加载
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .crossFade()
                .into(imageView);
//        ILFactory.getLoader().loadNet(imageView,url,new ILoader.Options(R.drawable.back,R.drawable.back));
    }

    /**
     * 带占位图的图片加载
     *
     * @param context
     * @param url
     * @param imageView
     * @param resourceId
     */
    public static void load(Context context, Object url, ImageView imageView, int resourceId) {
        RequestManager manager = Glide.with(context);
        if (resourceId != 0) {
            manager.load(url)
                    .placeholder(resourceId)
                    .fallback(resourceId)
                    .error(resourceId)
                    .crossFade()
                    .into(imageView);
        } else {
            manager.load(url)
                    .placeholder(R.mipmap.ic_launcher)
                    .fallback(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(imageView);
        }
    }
}

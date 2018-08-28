package com.shenhesoft.enterpriseapp.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：
 * 创作日期：2018/1/2.
 * 描述：项目详情的 父级item
 */

public class PDetailsRootItem {

    private String rName;      //item名称
    private int leftIconResId; //左侧图标资源ID
    private int status; //运单状态 默认0 正常  1异常（变黄）
    private List<PDetailsChildItem> childItems;

    public PDetailsRootItem(String rName, int leftIconResId) {
        this.rName = rName;
        this.leftIconResId = leftIconResId;
    }

    public static List<PDetailsChildItem> createChilds(String[] names, String[] values) {
        List<PDetailsChildItem> items = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            items.add(new PDetailsChildItem(names[i], values[i]));
        }
        return items;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public int getLeftIconResId() {
        return leftIconResId;
    }

    public void setLeftIconResId(int leftIconResId) {
        this.leftIconResId = leftIconResId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<PDetailsChildItem> getChildItems() {
        return childItems;
    }

    public void setChildItems(List<PDetailsChildItem> childItems) {
        this.childItems = childItems;
    }
}

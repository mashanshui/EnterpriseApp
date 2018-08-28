package com.shenhesoft.enterpriseapp.bean;

import java.util.List;

/**
 * 作者：
 * 创作日期：2018/1/2.
 * 描述：项目详情 子级Item
 */

public class PDetailsChildItem {

    private String cName;  //item名称
    private String cValue; //item值

    public PDetailsChildItem() {
    }

    public PDetailsChildItem(String cName, String cValue) {
        this.cName = cName;
        this.cValue = cValue;
    }


    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcValue() {
        return cValue;
    }

    public void setcValue(String cValue) {
        this.cValue = cValue;
    }
}

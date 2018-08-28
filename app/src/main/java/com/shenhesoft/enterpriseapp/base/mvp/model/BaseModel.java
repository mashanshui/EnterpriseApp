package com.shenhesoft.enterpriseapp.base.mvp.model;


import cn.droidlover.xdroidmvp.net.IModel;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description BaseModel
 */

public class BaseModel implements IModel {

    protected boolean error;

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return error;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}

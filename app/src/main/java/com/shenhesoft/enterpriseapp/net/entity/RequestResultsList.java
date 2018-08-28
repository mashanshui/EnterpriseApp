package com.shenhesoft.enterpriseapp.net.entity;

import com.google.gson.annotations.SerializedName;
import com.shenhesoft.enterpriseapp.base.mvp.model.BaseModel;

import java.util.List;

/**
 * @author 张继淮
 * @date 2017/9/28
 * @description 网络请求返回类型类
 */

public class RequestResultsList<T> extends BaseModel {
    @SerializedName(value = "state", alternate = {"code"})
    private int state;

    @SerializedName(value = "msg", alternate = {"message"})
    private String msg;

    @SerializedName(value = "recordsTotal")
    private String undefine;

    @SerializedName(value = "obj", alternate = {"data"})
    private List<T> obj;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUndefine() {
        return undefine;
    }

    public void setUndefine(String undefine) {
        this.undefine = undefine;
    }

    public List<T> getObj() {
        return obj;
    }

    public void setObj(List<T> obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Response{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                ", obj='" + obj + '\'' +
                '}';
    }
}

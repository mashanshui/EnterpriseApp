package com.shenhesoft.enterpriseapp.net.entity;

import com.shenhesoft.enterpriseapp.base.mvp.model.BaseModel;

import java.util.List;

/**
 * @author 张明星
 * @date 2017/12/24
 * @description 网络请求返回类型类List
 */

public class ListALLResults<T> extends BaseModel {
    private String msg;
    private List<T> rows;
    private int totalCount;
    private int limit;
    private int totalPage;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}

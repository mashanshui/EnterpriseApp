package com.shenhesoft.enterpriseapp.bean;

import java.io.Serializable;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 定位界面查询项目实体类
 */

public class TrainLocationBean implements Serializable {
    private String time;
    private String location;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

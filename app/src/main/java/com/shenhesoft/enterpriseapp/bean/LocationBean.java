package com.shenhesoft.enterpriseapp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 定位界面查询项目实体类
 */

public class LocationBean implements Serializable {

    /**
     * projectCode : QL00001
     * orderId :
     * carNo : c04
     * lon : 116.398258
     * remark :
     * id : 4
     * position :
     * projectId : 1
     * lat : 39.9146
     * carId :
     */

    private String projectCode;
    private String orderId;
    private String carNo;
    private String lon;
    private String remark;
    @SerializedName("id")
    private String id;
    private String position;
    private int projectId;
    private String lat;
    private String carId;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}

package com.shenhesoft.enterpriseapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author 张继淮
 * @date 2017/12/7
 * @desc 汽车短驳的 item
 */

public class MotorAllBean {

    @SerializedName("id")
    private int orderid; //订单编号
    @SerializedName("projectCode")
    private String projectCode; //项目编号
    @SerializedName("orderCode")
    private String orderCode;  //运单号
    @SerializedName("carPlateNumber")
    private String plateNumber; //运载车牌号
    @SerializedName("sendCompany")
    private String sendCompany; //发货单位
    @SerializedName("receiptCompany")
    private String receiptCompany;//收货单位
    @SerializedName("cargoName")
    private String cargoName; //货物名称

    @SerializedName("status")
    private int type; //运单状态


    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getSendCompany() {
        return sendCompany;
    }

    public void setSendCompany(String sendCompany) {
        this.sendCompany = sendCompany;
    }

    public String getReceiptCompany() {
        return receiptCompany;
    }

    public void setReceiptCompany(String receiptCompany) {
        this.receiptCompany = receiptCompany;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

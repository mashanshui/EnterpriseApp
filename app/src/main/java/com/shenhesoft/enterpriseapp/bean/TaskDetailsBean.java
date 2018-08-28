package com.shenhesoft.enterpriseapp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 作者：
 * 创作日期：2018/1/5.
 * 描述：分配任务 详情 bean
 */

public class TaskDetailsBean implements Serializable {


    @SerializedName("id")
    private int id;  //id

    @SerializedName("alreadyRecNum")
    private String obtainTask;  //已领任务

    @SerializedName("waitRecNum")
    private String waitObtainTask; //待领任务

    @SerializedName("completeTodayNum")
    private String fulfillTask;    //完成任务

    @SerializedName("projectCode")
    private String projectCode; //项目编号

    @SerializedName("projectType")
    private String projectType; //项目类型

    @SerializedName("sendCargoCompanyName")
    private String shipper;     //发货人

    @SerializedName("receiveCargoCompanyName")
    private String consignee;   //收货人

    @SerializedName("branchGroupName")
    private String branchOrgan; //分支机构

    @SerializedName("ee")
    private String yardman;     //调度员

    @SerializedName("xaaaaxx")
    private String createDate;  //创建时间

    @SerializedName("status")
    private int orderStatus; //运单状态

    @SerializedName("taskType")
    private int type;      //阶段

    @SerializedName("xx2222x")
    private String updateDate;  //更新时间

    @SerializedName("cargoName")
    private String cargoName;   //货物名称

    @SerializedName("cargoSpecifications")
    private String cargoSpecification; //货物规格

    @SerializedName("cc")
    private String assayIndex;   //化验指标

    @SerializedName("aaaaa")
    private String boxNumber1; //集装箱号

    @SerializedName("aa")
    private String boxNumber2;

    @SerializedName("sendCompanyName")
    private String sendCompany; //发货单位

    @SerializedName("receiptCompanyName")
    private String receiptCompany;//收货单位

    @SerializedName("sendCompanyNameAddress")
    private String sendAddress; //发货地址

    @SerializedName("receiptCompanyAddress")
    private String receiptAddress;//收货地址

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObtainTask() {
        return obtainTask;
    }

    public void setObtainTask(String obtainTask) {
        this.obtainTask = obtainTask;
    }

    public String getWaitObtainTask() {
        return waitObtainTask;
    }

    public void setWaitObtainTask(String waitObtainTask) {
        this.waitObtainTask = waitObtainTask;
    }

    public String getFulfillTask() {
        return fulfillTask;
    }

    public void setFulfillTask(String fulfillTask) {
        this.fulfillTask = fulfillTask;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getBranchOrgan() {
        return branchOrgan;
    }

    public void setBranchOrgan(String branchOrgan) {
        this.branchOrgan = branchOrgan;
    }

    public String getYardman() {
        return yardman;
    }

    public void setYardman(String yardman) {
        this.yardman = yardman;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getCargoSpecification() {
        return cargoSpecification;
    }

    public void setCargoSpecification(String cargoSpecification) {
        this.cargoSpecification = cargoSpecification;
    }

    public String getAssayIndex() {
        return assayIndex;
    }

    public void setAssayIndex(String assayIndex) {
        this.assayIndex = assayIndex;
    }

    public String getBoxNumber1() {
        return boxNumber1;
    }

    public void setBoxNumber1(String boxNumber1) {
        this.boxNumber1 = boxNumber1;
    }

    public String getBoxNumber2() {
        return boxNumber2;
    }

    public void setBoxNumber2(String boxNumber2) {
        this.boxNumber2 = boxNumber2;
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

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getReceiptAddress() {
        return receiptAddress;
    }

    public void setReceiptAddress(String receiptAddress) {
        this.receiptAddress = receiptAddress;
    }
}

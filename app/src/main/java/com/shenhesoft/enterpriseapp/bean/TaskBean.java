package com.shenhesoft.enterpriseapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：
 * 创作日期：2018/1/5.
 * 描述：分配任务的列表展示bean
 */

public class TaskBean {

    @SerializedName("id")
    private String id; //项目id

    @SerializedName("projectCode")
    private String projectCode; //项目编号
    @SerializedName("aa")
    private String status;      //状态
    @SerializedName("taskType")
    private String phases;      //阶段
    @SerializedName("sendCompanyName")
    private String sendCompany; //发货单位
    @SerializedName("receiptCompanyName")
    private String receiptCompany;//收货单位
    @SerializedName("cargoName")
    private String cargoName;   //货物名称
    @SerializedName("alreadyRecNum")
    private String obtainTask;  //已领任务
    @SerializedName("waitRecNum")
    private String waitObtainTask; //待领任务
    @SerializedName("ff")
    private String fulfillTask;    //完成任务

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhases() {
        return phases;
    }

    public void setPhases(String phases) {
        this.phases = phases;
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
}

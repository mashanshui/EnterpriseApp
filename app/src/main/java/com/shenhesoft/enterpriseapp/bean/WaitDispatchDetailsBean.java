package com.shenhesoft.enterpriseapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：
 * 创作日期：2018/1/2.
 * 描述：等待调度 项目详情
 */

public class WaitDispatchDetailsBean {

    @SerializedName("xxx")
    private String carPlateNumber; //车牌号
    @SerializedName("xxx")
    private String carMasterName;  //车主姓名
    @SerializedName("xxx")
    private String driverName;     //司机姓名
    @SerializedName("xxx")
    private String driversLicense; //驾驶证号
    @SerializedName("xxx")
    private String carBrand;       //车辆品牌
    @SerializedName("xxx")
    private String carModelNumber; //车辆型号
    @SerializedName("xxx")
    private String carType;        //车辆类型

    private String motorcade;      //车队
    @SerializedName("xxx")
    private String changeInfo;     //变更信息
    @SerializedName("xxx")
    private String subCompany1;    //挂靠公司1
    @SerializedName("xxx")
    private String subCompany2;    //挂靠公司2
    @SerializedName("xxx")
    private String hisTransport;   //历史运输
    @SerializedName("xxx")
    private String badnessRecord;  //不良记录

    @SerializedName("xxx")
    private String rejectCause;    //驳回原因
    @SerializedName("xxx")
    private String rejectDate;     //驳回时间
    @SerializedName("xxx")
    private String rejecter;       //驳回人


    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getCarMasterName() {
        return carMasterName;
    }

    public void setCarMasterName(String carMasterName) {
        this.carMasterName = carMasterName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriversLicense() {
        return driversLicense;
    }

    public void setDriversLicense(String driversLicense) {
        this.driversLicense = driversLicense;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModelNumber() {
        return carModelNumber;
    }

    public void setCarModelNumber(String carModelNumber) {
        this.carModelNumber = carModelNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getMotorcade() {
        return motorcade;
    }

    public void setMotorcade(String motorcade) {
        this.motorcade = motorcade;
    }

    public String getChangeInfo() {
        return changeInfo;
    }

    public void setChangeInfo(String changeInfo) {
        this.changeInfo = changeInfo;
    }

    public String getSubCompany1() {
        return subCompany1;
    }

    public void setSubCompany1(String subCompany1) {
        this.subCompany1 = subCompany1;
    }

    public String getSubCompany2() {
        return subCompany2;
    }

    public void setSubCompany2(String subCompany2) {
        this.subCompany2 = subCompany2;
    }

    public String getHisTransport() {
        return hisTransport;
    }

    public void setHisTransport(String hisTransport) {
        this.hisTransport = hisTransport;
    }

    public String getBadnessRecord() {
        return badnessRecord;
    }

    public void setBadnessRecord(String badnessRecord) {
        this.badnessRecord = badnessRecord;
    }

    public String getRejectCause() {
        return rejectCause;
    }

    public void setRejectCause(String rejectCause) {
        this.rejectCause = rejectCause;
    }

    public String getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(String rejectDate) {
        this.rejectDate = rejectDate;
    }

    public String getRejecter() {
        return rejecter;
    }

    public void setRejecter(String rejecter) {
        this.rejecter = rejecter;
    }
}

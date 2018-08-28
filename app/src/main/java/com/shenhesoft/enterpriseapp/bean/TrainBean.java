package com.shenhesoft.enterpriseapp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：
 * 创作日期：2017/12/28.
 * 描述：火车干线 列表数据bean
 */

public class TrainBean implements Serializable {

    @SerializedName("id")
    private String id; //id
    @SerializedName("projectCode")
    private String projectCode; //项目编号
    @SerializedName("orderCode")
    private String orderCode;  //运单号

    @SerializedName("projectType")
    private String projectType; //项目类型
    @SerializedName("sendCompany")
    private String sendCompany;     //起运中心站
    @SerializedName("receiptCompany")
    private String receiptCompany;   //到达中心站
    @SerializedName("branchName")
    private String branchOrgan; //分支机构
    @SerializedName("sendOperatorId")
    private String yardman;     //调度员
    @SerializedName("createDate")
    private String createDate;  //创建时间
    @SerializedName("status")
    private int orderStatus; //运单状态
    @SerializedName("updateDate")
    private String updateDate;  //更新时间
    @SerializedName("cargoName")
    private String cargoName;   //货物名称
    @SerializedName("cargoSpecifications")
    private String cargoSpecification; //货物规格
    @SerializedName("testIndicators")
    private String assayIndex;   //化验指标

    @SerializedName("pleaseTrainNumber")
    private String pleaseTrainNumber;  //请车单号

    @SerializedName("carPlateNumber")
    private String plateNumber; //运载车牌号
    @SerializedName("pleaseCarNum")
    private String inviteTrainNum;      //请车数
    @SerializedName("pleaseCarTypeId")
    private String inviteTrainType;     //请车类型
    @SerializedName("sureCarNum")
    private String admitTrainNum;        //承认车数
    @SerializedName("loseCarNum")
    private String realityTrainNum;     //落车数
    @SerializedName("estimateWeight")
    private String predictWeight;       //预计载重
    @SerializedName("beginSite")
    private String shipStation;    //始发站点
    @SerializedName("aa")
    private String shipGoodsYard;  //发货货场
    @SerializedName("bb")
    private String shipGoodsLocation; //发货货位
    @SerializedName("endSite")
    private String receiptStation;  //到达站点
    //    @SerializedName("aaaaa")
//    private String inviteTime;      //请车时间
    @SerializedName("entruckDate")
    private String loadingTime;     //装车时间
    @SerializedName("pp")
    private String departTime;      //发车时间
    @SerializedName("advanceChargeAccount")
    private String advanceAccount;  //预付款账户
    @SerializedName("dd")
    private String freightCharge;   //运输费用
    @SerializedName("endPlace")
    private String endPlace;    //收货企业
    @SerializedName("beginPlace")
    private String beginPlace;    //发货企业
    @SerializedName("entruckNumbe")
    private String entruckNumbe;   //装车数

    @SerializedName("entruckWeight")
    private String entruckWeight;   //装载吨位

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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
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

    public String getPleaseTrainNumber() {
        return pleaseTrainNumber;
    }

    public void setPleaseTrainNumber(String pleaseTrainNumber) {
        this.pleaseTrainNumber = pleaseTrainNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getInviteTrainNum() {
        return inviteTrainNum;
    }

    public void setInviteTrainNum(String inviteTrainNum) {
        this.inviteTrainNum = inviteTrainNum;
    }

    public String getInviteTrainType() {
        return inviteTrainType;
    }

    public void setInviteTrainType(String inviteTrainType) {
        this.inviteTrainType = inviteTrainType;
    }

    public String getAdmitTrainNum() {
        return admitTrainNum;
    }

    public void setAdmitTrainNum(String admitTrainNum) {
        this.admitTrainNum = admitTrainNum;
    }

    public String getRealityTrainNum() {
        return realityTrainNum;
    }

    public void setRealityTrainNum(String realityTrainNum) {
        this.realityTrainNum = realityTrainNum;
    }

    public String getPredictWeight() {
        return predictWeight;
    }

    public void setPredictWeight(String predictWeight) {
        this.predictWeight = predictWeight;
    }

    public String getShipStation() {
        return shipStation;
    }

    public void setShipStation(String shipStation) {
        this.shipStation = shipStation;
    }

    public String getShipGoodsYard() {
        return shipGoodsYard;
    }

    public void setShipGoodsYard(String shipGoodsYard) {
        this.shipGoodsYard = shipGoodsYard;
    }

    public String getShipGoodsLocation() {
        return shipGoodsLocation;
    }

    public void setShipGoodsLocation(String shipGoodsLocation) {
        this.shipGoodsLocation = shipGoodsLocation;
    }

    public String getReceiptStation() {
        return receiptStation;
    }

    public void setReceiptStation(String receiptStation) {
        this.receiptStation = receiptStation;
    }

    public String getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(String loadingTime) {
        this.loadingTime = loadingTime;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getAdvanceAccount() {
        return advanceAccount;
    }

    public void setAdvanceAccount(String advanceAccount) {
        this.advanceAccount = advanceAccount;
    }

    public String getFreightCharge() {
        return freightCharge;
    }

    public void setFreightCharge(String freightCharge) {
        this.freightCharge = freightCharge;
    }

    public String getEntruckNumbe() {
        return entruckNumbe;
    }

    public void setEntruckNumbe(String entruckNumbe) {
        this.entruckNumbe = entruckNumbe;
    }

    public String getEntruckWeight() {
        return entruckWeight;
    }

    public void setEntruckWeight(String entruckWeight) {
        this.entruckWeight = entruckWeight;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public String getBeginPlace() {
        return beginPlace;
    }

    public void setBeginPlace(String beginPlace) {
        this.beginPlace = beginPlace;
    }
}

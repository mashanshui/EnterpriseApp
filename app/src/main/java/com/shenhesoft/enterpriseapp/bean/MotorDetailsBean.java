package com.shenhesoft.enterpriseapp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 作者：Tornado
 * 创作日期：2018/1/3.
 * 描述：汽车短驳 运单详情
 */

public class MotorDetailsBean implements Serializable {
    private boolean checked;
    @SerializedName("id")
    private int orderid; //订单号
    @SerializedName("orderCode")
    private String orderCode;  //运单号
    @SerializedName("projectCode")
    private String projectCode; //项目编号
    @SerializedName("projectId")
    private String projectId; //项目id
    @SerializedName("projectType")
    private String projectType; //项目类型

    //    @SerializedName("xxx")
//    private String shipper;     //发货人
//    @SerializedName("xxx")
//    private String consignee;   //收货人
    @SerializedName("branchGroupName")
    private String branchOrgan; //分支机构
    @SerializedName("userDispatchName")
    private String yardman;     //调度员
    @SerializedName("createDate")
    private String createDate;  //创建时间
    @SerializedName("status")
    private String orderStatus; //运单状态
    @SerializedName("updateDate")
    private String updateDate;  //更新时间
    @SerializedName("cargoName")
    private String cargoName;   //货物名称
    @SerializedName("specifications")
    private String cargoSpecification; //货物规格
    @SerializedName("testIndicators")
    private String assayIndex;   //化验指标

    @SerializedName("historyTbOrderNumDriverId")
    private String historyTbOrderNumDriverId; //历史运输

    @SerializedName("carPlateNumber")
    private String carPlateNumber; //承运车牌号
    @SerializedName("carType")
    private String carType;        //车辆类型
    @SerializedName("driverName")
    private String driverName;     //司机姓名
    @SerializedName("driverId")
    private String driverId;   //化验指标
    @SerializedName("driverPhone")
    private String contactTel;     //联系方式
    @SerializedName("containerNumber1")
    private String boxNumber1;     //箱号1
    @SerializedName("containerNumber2")
    private String boxNumber2;     //箱号2
    @SerializedName("sendCompanyId")
    private String sendCompanyId;    //发货单位id
    @SerializedName("sendCompany")
    private String shipCompany;    //发货单位
    @SerializedName("pickupPlace")
    private String claimAddress;   //取货地址
    @SerializedName("sendGross")
    private String shipWeightM;    //发货毛重
    @SerializedName("sendTare")
    private String shipWeightP;    //发货皮重
    @SerializedName("containerOneSendNet")
    private String shipWeightJ1;   //发货净重1
    @SerializedName("containerTwoSendNet")
    private String shipWeightJ2;   //发货净重2

    @SerializedName("receiptCompanyId")
    private String receiptCompanyId;  //收货单位id
    @SerializedName("receiptCompany")
    private String receiptCompany;  //收货单位
    @SerializedName("arrivePlace")
    private String receiptAddress; //运抵地址
    @SerializedName("arriveFreightYrad")
    private String goodsYard;         //货场
    @SerializedName("arriveFreightSite")
    private String goodsLocation;     //货位
    @SerializedName("receiptGross")
    private String deliveryWeightM;   //到货毛重
    @SerializedName("receiptTare")
    private String deliveryWeightP;   //到货皮重
    @SerializedName("containerOneReceiptNet")
    private String deliveryWeightJ1;  //到货净重1
    @SerializedName("containerTwoReceiptNet")
    private String deliveryWeightJ2;  //到货毛重2
    @SerializedName("spoilageWeight")
    private String spoilageWeight;    //损耗质量

    @SerializedName("exceptionReoportReason")
    private String rejectCause;    //异常原因
    @SerializedName("exceptionTime")
    private String submitDate;     //提交时间
    @SerializedName("exceptionReoportName")
    private String rejecter;       //提报者
    @SerializedName("affirmStatus")
    private String disposeResult;  //处理结果
    @SerializedName("affirmDate")
    private String disposeDate;    //处理时间
    @SerializedName("affirmUserxxx")
    private String handler;       //操作人
    @SerializedName("stepSelectCode")
    private String stepSelectCode;      //阶段选择
    @SerializedName("carItemName")
    private String carItemName;   //所属车队

    @SerializedName("receipterDate")
    private String receipterDate;

    @SerializedName("deleteTime")
    private String deleteTime;       //操作人

    @SerializedName("deleteName")
    private String deleteName;       //操作人

    @SerializedName("deleteReason")
    private String deleteReason;       //操作人

    private String arriveredImg;

    public String getArriveredImg() {
        return arriveredImg;
    }

    public void setArriveredImg(String arriveredImg) {
        this.arriveredImg = arriveredImg;
    }

    public String getStepSelectCode() {
        return stepSelectCode;
    }

    public void setStepSelectCode(String stepSelectCode) {
        this.stepSelectCode = stepSelectCode;
    }

    private String shOrderFinId;

    public String getCarItemName() {
        return carItemName;
    }

    public void setCarItemName(String carItemName) {
        this.carItemName = carItemName;
    }

    public String getShOrderFinId() {
        return shOrderFinId;
    }

    public void setShOrderFinId(String shOrderFinId) {
        this.shOrderFinId = shOrderFinId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getHistoryTbOrderNumDriverId() {
        return historyTbOrderNumDriverId;
    }

    public void setHistoryTbOrderNumDriverId(String historyTbOrderNumDriverId) {
        this.historyTbOrderNumDriverId = historyTbOrderNumDriverId;
    }

    public String getReceipterDate() {
        return receipterDate;
    }

    public void setReceipterDate(String receipterDate) {
        this.receipterDate = receipterDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeleteName() {
        return deleteName;
    }

    public void setDeleteName(String deleteName) {
        this.deleteName = deleteName;
    }

    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

//    @SerializedName("status")
//    private int type;


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

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

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
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

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
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

    public String getShipCompany() {
        return shipCompany;
    }

    public void setShipCompany(String shipCompany) {
        this.shipCompany = shipCompany;
    }

    public String getClaimAddress() {
        return claimAddress;
    }

    public void setClaimAddress(String claimAddress) {
        this.claimAddress = claimAddress;
    }

    public String getShipWeightM() {
        return shipWeightM;
    }

    public void setShipWeightM(String shipWeightM) {
        this.shipWeightM = shipWeightM;
    }

    public String getShipWeightP() {
        return shipWeightP;
    }

    public void setShipWeightP(String shipWeightP) {
        this.shipWeightP = shipWeightP;
    }

    public String getShipWeightJ1() {
        return shipWeightJ1;
    }

    public void setShipWeightJ1(String shipWeightJ1) {
        this.shipWeightJ1 = shipWeightJ1;
    }

    public String getShipWeightJ2() {
        return shipWeightJ2;
    }

    public void setShipWeightJ2(String shipWeightJ2) {
        this.shipWeightJ2 = shipWeightJ2;
    }

    public String getReceiptCompany() {
        return receiptCompany;
    }

    public void setReceiptCompany(String receiptCompany) {
        this.receiptCompany = receiptCompany;
    }

    public String getReceiptAddress() {
        return receiptAddress;
    }

    public void setReceiptAddress(String receiptAddress) {
        this.receiptAddress = receiptAddress;
    }

    public String getGoodsYard() {
        return goodsYard;
    }

    public void setGoodsYard(String goodsYard) {
        this.goodsYard = goodsYard;
    }

    public String getGoodsLocation() {
        return goodsLocation;
    }

    public void setGoodsLocation(String goodsLocation) {
        this.goodsLocation = goodsLocation;
    }

    public String getDeliveryWeightM() {
        return deliveryWeightM;
    }

    public void setDeliveryWeightM(String deliveryWeightM) {
        this.deliveryWeightM = deliveryWeightM;
    }

    public String getDeliveryWeightP() {
        return deliveryWeightP;
    }

    public void setDeliveryWeightP(String deliveryWeightP) {
        this.deliveryWeightP = deliveryWeightP;
    }

    public String getDeliveryWeightJ1() {
        return deliveryWeightJ1;
    }

    public void setDeliveryWeightJ1(String deliveryWeightJ1) {
        this.deliveryWeightJ1 = deliveryWeightJ1;
    }

    public String getDeliveryWeightJ2() {
        return deliveryWeightJ2;
    }

    public void setDeliveryWeightJ2(String deliveryWeightJ2) {
        this.deliveryWeightJ2 = deliveryWeightJ2;
    }

    public String getSendCompanyId() {
        return sendCompanyId;
    }

    public void setSendCompanyId(String sendCompanyId) {
        this.sendCompanyId = sendCompanyId;
    }

    public String getReceiptCompanyId() {
        return receiptCompanyId;
    }

    public void setReceiptCompanyId(String receiptCompanyId) {
        this.receiptCompanyId = receiptCompanyId;
    }

    public String getSpoilageWeight() {
        return spoilageWeight;
    }

    public void setSpoilageWeight(String spoilageWeight) {
        this.spoilageWeight = spoilageWeight;
    }

    public String getRejectCause() {
        return rejectCause;
    }

    public void setRejectCause(String rejectCause) {
        this.rejectCause = rejectCause;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getRejecter() {
        return rejecter;
    }

    public void setRejecter(String rejecter) {
        this.rejecter = rejecter;
    }

    public String getDisposeResult() {
        return disposeResult;
    }

    public void setDisposeResult(String disposeResult) {
        this.disposeResult = disposeResult;
    }

    public String getDisposeDate() {
        return disposeDate;
    }

    public void setDisposeDate(String disposeDate) {
        this.disposeDate = disposeDate;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    @Override
    public String toString() {
        return "MotorDetailsBean{" +
                "checked=" + checked +
                ", orderid=" + orderid +
                ", orderCode='" + orderCode + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", projectType='" + projectType + '\'' +
                ", branchOrgan='" + branchOrgan + '\'' +
                ", yardman='" + yardman + '\'' +
                ", createDate='" + createDate + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", cargoName='" + cargoName + '\'' +
                ", cargoSpecification='" + cargoSpecification + '\'' +
                ", assayIndex='" + assayIndex + '\'' +
                ", historyTbOrderNumDriverId='" + historyTbOrderNumDriverId + '\'' +
                ", carPlateNumber='" + carPlateNumber + '\'' +
                ", carType='" + carType + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverId='" + driverId + '\'' +
                ", contactTel='" + contactTel + '\'' +
                ", boxNumber1='" + boxNumber1 + '\'' +
                ", boxNumber2='" + boxNumber2 + '\'' +
                ", shipCompany='" + shipCompany + '\'' +
                ", claimAddress='" + claimAddress + '\'' +
                ", shipWeightM='" + shipWeightM + '\'' +
                ", shipWeightP='" + shipWeightP + '\'' +
                ", shipWeightJ1='" + shipWeightJ1 + '\'' +
                ", shipWeightJ2='" + shipWeightJ2 + '\'' +
                ", receiptCompany='" + receiptCompany + '\'' +
                ", receiptAddress='" + receiptAddress + '\'' +
                ", goodsYard='" + goodsYard + '\'' +
                ", goodsLocation='" + goodsLocation + '\'' +
                ", deliveryWeightM='" + deliveryWeightM + '\'' +
                ", deliveryWeightP='" + deliveryWeightP + '\'' +
                ", deliveryWeightJ1='" + deliveryWeightJ1 + '\'' +
                ", deliveryWeightJ2='" + deliveryWeightJ2 + '\'' +
                ", spoilageWeight='" + spoilageWeight + '\'' +
                ", rejectCause='" + rejectCause + '\'' +
                ", submitDate='" + submitDate + '\'' +
                ", rejecter='" + rejecter + '\'' +
                ", disposeResult='" + disposeResult + '\'' +
                ", disposeDate='" + disposeDate + '\'' +
                ", handler='" + handler + '\'' +
                ", carItemName='" + carItemName + '\'' +
                ", deleteTime='" + deleteTime + '\'' +
                ", deleteName='" + deleteName + '\'' +
                ", deleteReason='" + deleteReason + '\'' +
                ", shOrderFinId='" + shOrderFinId + '\'' +
                '}';
    }
}

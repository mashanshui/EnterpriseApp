package com.shenhesoft.enterpriseapp.bean;

import java.io.Serializable;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 定位界面查询项目实体类
 */

public class TrainProjectBean implements Serializable {

    /**
     * id : 46
     * projectCode : QL20171226173812
     * branchGroupId : 1
     * branchGroupName : 合肥分支中心
     * projectType : 0
     * transportType : 6
     * cargoId : 1
     * cargoName : 煤
     * cargoSpecifications : 吨
     * cargoPrice :
     * valuationUnitName : 0
     * sendCargoCompanyId : 1
     * sendCargoCompanyName : 深合软件
     * receiveCargoCompanyId : 1
     * receiveCargoCompanyName : 深合软件
     * sendCargoUnitId : 1
     * sendCargoUnitName : 深合软件
     * receivingDepartmentId : 1
     * receivingDepartmentName : 深合软件
     * receiveCargoSiteId : 5
     * receiveCargoSite : CCCCC
     * shortBargeCarrierMode : 0
     * shortBargeCarrierId :
     * shortBargeCarrierName : 平台;平台;
     * sendShortBargeCarrierMode : 0
     * beginCenterSiteId : 2
     * beginCenterSiteName : BBBBB
     * beginSiteId : 5
     * beginSiteName : CCCCC
     * beginAddress : 123
     * endCenterSiteId : 2
     * endCenterSiteName : BBBBB
     * endSiteId : 5
     * endSiteName : CCCCC,123
     * endAddress :
     * freight :
     * materialCost :
     * tarpaulinCost :
     * beginStevedoringCost :
     * endStevedoringCost :
     * freightSum :
     * forwardingSiteId : 5
     * forwardingSiteName : CCCCC
     * forwardingUnitId :
     * forwardingUnitName :
     * takePlace :
     * takePlaceDetail :
     * arrivePlace :
     * arrivePlaceAddress :
     * pickUpPrice :
     * trainPrice :
     * arrivePrice :
     * transportPrice :
     * remark :
     * createDate : 2017.12.26 17:38
     * editDate :
     * status : 1
     * creatorId : 1
     * principal : 0
     * isDistribution : 1
     * deleteFlag : 0
     * finishDate :
     * sendShortBargeCarrierName :
     * sendCargoCompany :
     * receiveCargoCompany :
     * sendCargoUnit :
     * receivingDepartment :
     * receiveTrainStation :
     * sendTrainStation :
     * shortBarges :
     * operationLogs :
     */

    private int id;

    private String projectCode;

    private int transportType;

    private int projectType;

    private String branchGroupName;

    private String sendCargoCompanyName;

    private String receiveCargoCompanyName;

    private int valuationUnitName;//0吨1位

    private String cargoSpecifications;

    private String beginSiteName;

    private String endSiteName;

    private String freightSum;

    private String bankAccount;

    private String bankLastAmount;

    private String freightYardName;

    private String cargoLocationName;

    private String currentQty;

    public String getFreightYardName() {
        return freightYardName;
    }

    public void setFreightYardName(String freightYardName) {
        this.freightYardName = freightYardName;
    }

    public String getCargoLocationName() {
        return cargoLocationName;
    }

    public void setCargoLocationName(String cargoLocationName) {
        this.cargoLocationName = cargoLocationName;
    }

    public String getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(String currentQty) {
        this.currentQty = currentQty;
    }

    public String getFreightSum() {
        return freightSum;
    }

    public void setFreightSum(String freightSum) {
        this.freightSum = freightSum;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankLastAmount() {
        return bankLastAmount;
    }

    public void setBankLastAmount(String bankLastAmount) {
        this.bankLastAmount = bankLastAmount;
    }

    public String getCargoSpecifications() {
        return cargoSpecifications;
    }

    public void setCargoSpecifications(String cargoSpecifications) {
        this.cargoSpecifications = cargoSpecifications;
    }

    public String getBeginSiteName() {
        return beginSiteName;
    }

    public void setBeginSiteName(String beginSiteName) {
        this.beginSiteName = beginSiteName;
    }

    public String getEndSiteName() {
        return endSiteName;
    }

    public void setEndSiteName(String endSiteName) {
        this.endSiteName = endSiteName;
    }

    public int getProjectType() {
        return projectType;
    }

    public void setProjectType(int projectType) {
        this.projectType = projectType;
    }

    public String getBranchGroupName() {
        return branchGroupName;
    }

    public void setBranchGroupName(String branchGroupName) {
        this.branchGroupName = branchGroupName;
    }

    public String getSendCargoCompanyName() {
        return sendCargoCompanyName;
    }

    public void setSendCargoCompanyName(String sendCargoCompanyName) {
        this.sendCargoCompanyName = sendCargoCompanyName;
    }

    public String getReceiveCargoCompanyName() {
        return receiveCargoCompanyName;
    }

    public void setReceiveCargoCompanyName(String receiveCargoCompanyName) {
        this.receiveCargoCompanyName = receiveCargoCompanyName;
    }

    public int getValuationUnitName() {
        return valuationUnitName;
    }

    public void setValuationUnitName(int valuationUnitName) {
        this.valuationUnitName = valuationUnitName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public int getTransportType() {
        return transportType;
    }

    public void setTransportType(int transportType) {
        this.transportType = transportType;
    }

    //    private int branchGroupId;

    private String forwardingSiteId;
    private String forwardingSiteName;
    private String sendCargoUnitId;
    private String sendCargoUnitName;
    private String receivingDepartmentId;
    private String receivingDepartmentName;
    private String receiveCargoSiteId;
    private String receiveCargoSite;

    private String cargoName;

    public String getForwardingSiteId() {
        return forwardingSiteId;
    }

    public void setForwardingSiteId(String forwardingSiteId) {
        this.forwardingSiteId = forwardingSiteId;
    }

    public String getForwardingSiteName() {
        return forwardingSiteName;
    }

    public void setForwardingSiteName(String forwardingSiteName) {
        this.forwardingSiteName = forwardingSiteName;
    }

    public String getSendCargoUnitId() {
        return sendCargoUnitId;
    }

    public void setSendCargoUnitId(String sendCargoUnitId) {
        this.sendCargoUnitId = sendCargoUnitId;
    }

    public String getSendCargoUnitName() {
        return sendCargoUnitName;
    }

    public void setSendCargoUnitName(String sendCargoUnitName) {
        this.sendCargoUnitName = sendCargoUnitName;
    }

    public String getReceivingDepartmentId() {
        return receivingDepartmentId;
    }

    public void setReceivingDepartmentId(String receivingDepartmentId) {
        this.receivingDepartmentId = receivingDepartmentId;
    }

    public String getReceivingDepartmentName() {
        return receivingDepartmentName;
    }

    public void setReceivingDepartmentName(String receivingDepartmentName) {
        this.receivingDepartmentName = receivingDepartmentName;
    }

    public String getReceiveCargoSiteId() {
        return receiveCargoSiteId;
    }

    public void setReceiveCargoSiteId(String receiveCargoSiteId) {
        this.receiveCargoSiteId = receiveCargoSiteId;
    }

    public String getReceiveCargoSite() {
        return receiveCargoSite;
    }

    public void setReceiveCargoSite(String receiveCargoSite) {
        this.receiveCargoSite = receiveCargoSite;
    }

    private String arrivePlaceAddress;

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getArrivePlaceAddress() {
        return arrivePlaceAddress;
    }

    public void setArrivePlaceAddress(String arrivePlaceAddress) {
        this.arrivePlaceAddress = arrivePlaceAddress;
    }

    private AdvanceCharge advanceCharge;

    public AdvanceCharge getAdvanceCharge() {
        return advanceCharge;
    }

    public void setAdvanceCharge(AdvanceCharge advanceCharge) {
        this.advanceCharge = advanceCharge;
    }


    public static class AdvanceCharge implements Serializable {
        private String depositAmount;
        private String receiveAccountId;
        private String receiveAccountName;

        public String getDepositAmount() {
            return depositAmount;
        }

        public void setDepositAmount(String depositAmount) {
            this.depositAmount = depositAmount;
        }

        public String getReceiveAccountId() {
            return receiveAccountId;
        }

        public void setReceiveAccountId(String receiveAccountId) {
            this.receiveAccountId = receiveAccountId;
        }

        public String getReceiveAccountName() {
            return receiveAccountName==null?"":receiveAccountName;
        }

        public void setReceiveAccountName(String receiveAccountName) {
            this.receiveAccountName = receiveAccountName;
        }
    }
}

package com.shenhesoft.enterpriseapp.bean;

/**
 * @author 张继淮
 * @date 2018/1/8
 * @desc TODO
 */

public class UnusualBean {


    /**
     * exceptionId : 3
     * username : admin
     * projectCode : QL00001
     * status : 3
     * orderCode : 100012
     * exceptionSource : 1
     * carrierVehicleName : 1
     * receiptCompany : 合肥市深合软件
     * projectId : 1
     * order_id : 12
     * cargoName : 煤
     * sendCompany : 新疆秦龙矿业有限公司
     */

    private int exceptionId;
    private String username;
    private String projectCode;
    private int status;
    private String orderCode;
    private int exceptionSource;
    private String carrierVehicleName;
    private String receiptCompany;
    private int projectId;
    private int order_id;
    private String cargoName;
    private String sendCompany;
    private String exceptionReason;
    private String exceptionReasonDetail;

    public String getExceptionReason() {
        return exceptionReason;
    }

    public void setExceptionReason(String exceptionReason) {
        this.exceptionReason = exceptionReason;
    }

    public String getExceptionReasonDetail() {
        return exceptionReasonDetail;
    }

    public void setExceptionReasonDetail(String exceptionReasonDetail) {
        this.exceptionReasonDetail = exceptionReasonDetail;
    }

    public int getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(int exceptionId) {
        this.exceptionId = exceptionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getExceptionSource() {
        return exceptionSource;
    }

    public void setExceptionSource(int exceptionSource) {
        this.exceptionSource = exceptionSource;
    }

    public String getCarrierVehicleName() {
        return carrierVehicleName;
    }

    public void setCarrierVehicleName(String carrierVehicleName) {
        this.carrierVehicleName = carrierVehicleName;
    }

    public String getReceiptCompany() {
        return receiptCompany;
    }

    public void setReceiptCompany(String receiptCompany) {
        this.receiptCompany = receiptCompany;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getSendCompany() {
        return sendCompany;
    }

    public void setSendCompany(String sendCompany) {
        this.sendCompany = sendCompany;
    }
}

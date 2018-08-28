package com.shenhesoft.enterpriseapp.bean;

import java.io.Serializable;

/**
 * @author 张继淮
 * @date 2018/2/3
 * @desc TODO
 */

public class DriverCheckBean implements Serializable {
    /***
     * 对账单号
     */
    private String shPackId;

    /***
     * 日期
     */
    private String createDate;

    /***
     * 支付模式
     */
    private String paymentName;

    /***
     * 支付比例
     */
    private String payRatio;


    /***
     * 应付运费
     */
    private String freightChargeAmount;

    /***
     * 应付油气
     */
    private String suppliesAmount;

    /***
     * 领取人
     */
    private String driverName;


    public String getShPackId() {
        return shPackId;
    }

    public void setShPackId(String shPackId) {
        this.shPackId = shPackId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getPayRatio() {
        return payRatio;
    }

    public void setPayRatio(String payRatio) {
        this.payRatio = payRatio;
    }

    public String getFreightChargeAmount() {
        return freightChargeAmount;
    }

    public void setFreightChargeAmount(String freightChargeAmount) {
        this.freightChargeAmount = freightChargeAmount;
    }

    public String getSuppliesAmount() {
        return suppliesAmount;
    }

    public void setSuppliesAmount(String suppliesAmount) {
        this.suppliesAmount = suppliesAmount;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}

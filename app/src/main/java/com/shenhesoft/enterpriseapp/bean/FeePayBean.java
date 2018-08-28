package com.shenhesoft.enterpriseapp.bean;

import java.io.Serializable;

/**
 * @author 张继淮
 * @date 2018/2/3
 * @desc TODO
 */

public class FeePayBean implements Serializable {
    /***
     * 运单编号
     */
    private String orderCode;

    /***
     * 日期
     */
    private String receipterDate;

    /***
     * 承运车辆
     */
    private String carPlateNumber;

    /***
     * 补加运费
     */
    private String subsidy;


    /***
     * 应付运费
     */
    private String shouldPayFigure;

    /***
     * 计费确认
     */
    private String financeStatusNodeName;

    /***
     * 财务状态
     */
    private String financeStatusName;

    /***
     * 主键
     */
    private String shOrderFinId;


    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getReceipterDate() {
        return receipterDate;
    }

    public void setReceipterDate(String receipterDate) {
        this.receipterDate = receipterDate;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(String subsidy) {
        this.subsidy = subsidy;
    }

    public String getShouldPayFigure() {
        return shouldPayFigure;
    }

    public void setShouldPayFigure(String shouldPayFigure) {
        this.shouldPayFigure = shouldPayFigure;
    }

    public String getFinanceStatusNodeName() {
        return financeStatusNodeName;
    }

    public void setFinanceStatusNodeName(String financeStatusNodeName) {
        this.financeStatusNodeName = financeStatusNodeName;
    }

    public String getFinanceStatusName() {
        return financeStatusName;
    }

    public void setFinanceStatusName(String financeStatusName) {
        this.financeStatusName = financeStatusName;
    }

    public String getShOrderFinId() {
        return shOrderFinId;
    }

    public void setShOrderFinId(String shOrderFinId) {
        this.shOrderFinId = shOrderFinId;
    }
}

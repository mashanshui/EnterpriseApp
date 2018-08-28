package com.shenhesoft.enterpriseapp.bean;

import java.io.Serializable;

/**
 * @author 张继淮
 * @date 2018/2/3
 * @desc TODO
 */

public class CustomerCheckBean implements Serializable {
    /***
     * 对账单号
     */
    private String checkId;

    /***
     * 日期
     */
    private String modifiyDate;

    /***
     * 对账金额
     */
    private String produceMoney;

    /***
     * 应开税额
     */
    private String taxMoney;


    /***
     * 对账时间
     */
    private String checkDate;

    /***
     * 汽运对账
     */
    private String packType;

    /***
     * 运单总数
     */
    private String orderCount;

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getModifiyDate() {
        return modifiyDate;
    }

    public void setModifiyDate(String modifiyDate) {
        this.modifiyDate = modifiyDate;
    }

    public String getProduceMoney() {
        return produceMoney;
    }

    public void setProduceMoney(String produceMoney) {
        this.produceMoney = produceMoney;
    }

    public String getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(String taxMoney) {
        this.taxMoney = taxMoney;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }
}

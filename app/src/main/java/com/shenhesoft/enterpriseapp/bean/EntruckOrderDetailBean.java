package com.shenhesoft.enterpriseapp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author 张继淮
 * @date 2018/1/3
 * @desc TODO
 */

public class EntruckOrderDetailBean implements Serializable {

    public EntruckOrderDetailBean(String carType, String carNumber, String containerNumber1, String containerNumber2, String sendWeight, String conSendWeight2, String img) {
        this.carType = carType;
        this.carNumber = carNumber;
        this.containerNumber1 = containerNumber1;
        this.containerNumber2 = containerNumber2;
        this.sendWeight = sendWeight;
        this.conSendWeight2 = conSendWeight2;
        this.img = img;
    }

    public EntruckOrderDetailBean(String carType, String carNumber, String containerNumber1, String containerNumber2, String sendWeight,String conSendWeight2, String img, String unloadWeight) {
        this.carType = carType;
        this.carNumber = carNumber;
        this.containerNumber1 = containerNumber1;
        this.containerNumber2 = containerNumber2;
        this.sendWeight = sendWeight;
        this.conSendWeight2 = conSendWeight2;
        this.img = img;
        this.unloadWeight = unloadWeight;
    }

    public EntruckOrderDetailBean(String carType, String carNumber, String containerNumber1, String containerNumber2, String sendWeight, String img, String unloadWeight, String goodsPlace, String goodsSite) {
        this.carType = carType;
        this.carNumber = carNumber;
        this.containerNumber1 = containerNumber1;
        this.containerNumber2 = containerNumber2;
        this.sendWeight = sendWeight;
        this.img = img;
        this.unloadWeight = unloadWeight;
        this.goodsPlace = goodsPlace;
        this.goodsSite = goodsSite;
    }

    private int id;

    private boolean checked;

    private String carType;

    private String carNumber;

    private String containerNumber1;

    private String containerNumber2;

    private String sendWeight;

    /**
     * 由于后期业务需要，为每个集装箱都添加一个发货载重，为了不影响前面的代码，在这里添加一个sendWeight2代表第二个集装箱的发货载重
     */
    private String conSendWeight2;

    @SerializedName("sendImg")
    private String img;

    @SerializedName("unloadImg")
    private String imgcall;

    private String unloadWeight;

    /**
     * 由于后期业务需要，为每个集装箱都添加一个卸货载重，为了不影响前面的代码，在这里添加一个con_unload_weight2代表第二个集装箱的卸货载重
     */
    private String conUnloadWeight2;

    private String arriveCargoPlaceId;

    private String arriveCargoSiteId;

    @SerializedName("arriveCargoPlaceName")
    private String goodsPlace;

    @SerializedName("arriveCargoSiteName")
    private String goodsSite;

    public String getImgcall() {
        return imgcall;
    }

    public void setImgcall(String imgcall) {
        this.imgcall = imgcall;
    }

    public String getGoodsPlace() {
        return goodsPlace;
    }

    public void setGoodsPlace(String goodsPlace) {
        this.goodsPlace = goodsPlace;
    }

    public String getGoodsSite() {
        return goodsSite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGoodsSite(String goodsSite) {
        this.goodsSite = goodsSite;

    }

    public String getConSendWeight2() {
        return conSendWeight2;
    }

    public void setConSendWeight2(String conSendWeight2) {
        this.conSendWeight2 = conSendWeight2;
    }

    public String getConUnloadWeight2() {
        return conUnloadWeight2;
    }

    public void setConUnloadWeight2(String conUnloadWeight2) {
        this.conUnloadWeight2 = conUnloadWeight2;
    }

    public String getArriveCargoPlaceId() {
        return arriveCargoPlaceId;
    }

    public void setArriveCargoPlaceId(String arriveCargoPlaceId) {
        this.arriveCargoPlaceId = arriveCargoPlaceId;
    }

    public String getArriveCargoSiteId() {
        return arriveCargoSiteId;
    }

    public void setArriveCargoSiteId(String arriveCargoSiteId) {
        this.arriveCargoSiteId = arriveCargoSiteId;
    }

    public String getUnloadWeight() {
        return unloadWeight;
    }

    public void setUnloadWeight(String unloadWeight) {
        this.unloadWeight = unloadWeight;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getContainerNumber1() {
        return containerNumber1;
    }

    public void setContainerNumber1(String containerNumber1) {
        this.containerNumber1 = containerNumber1;
    }

    public String getContainerNumber2() {
        return containerNumber2;
    }

    public void setContainerNumber2(String containerNumber2) {
        this.containerNumber2 = containerNumber2;
    }

    public String getSendWeight() {
        return sendWeight;
    }

    public void setSendWeight(String sendWeight) {
        this.sendWeight = sendWeight;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

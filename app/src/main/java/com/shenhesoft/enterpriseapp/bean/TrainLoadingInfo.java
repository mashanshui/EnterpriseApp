package com.shenhesoft.enterpriseapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：
 * 创作日期：2018/1/4.
 * 描述：火车干线 运单详情 装车信息
 */

public class TrainLoadingInfo {

    @SerializedName("carType")
    private String trainType; //车型
    @SerializedName("carNumber")
    private String trainNumber; //车号
    @SerializedName("containerNumber1")
    private String boxNumber1; //集装箱号
    @SerializedName("containerNumber2")
    private String boxNumber2;
    @SerializedName("sendWeight")
    private String weight;     //发货载重

    /**
     * 新增需求每个集装箱都有一个发货载重，第二个集装箱的发货载重用conSendWeight2表示
     */
    @SerializedName("conSendWeight2")
    private String conSendWeight2;  //发货载重

    @SerializedName("unloadWeight")
    private String unloadWeight; //到货载重

    @SerializedName("conUnloadWeight2")
    private String conUnloadWeight2; //到货载重

    public TrainLoadingInfo() {
    }

    public TrainLoadingInfo(String trainType, String trainNumber, String boxNumber1, String boxNumber2, String weight) {
        this.trainType = trainType;
        this.trainNumber = trainNumber;
        this.boxNumber1 = boxNumber1;
        this.boxNumber2 = boxNumber2;
        this.weight = weight;
    }

    public String getTrainType() {
        return trainType;
    }

    public String getConSendWeight2() {
        return conSendWeight2;
    }

    public void setConSendWeight2(String conSendWeight2) {
        this.conSendWeight2 = conSendWeight2;
    }

    public String getUnloadWeight() {
        return unloadWeight;
    }

    public void setUnloadWeight(String unloadWeight) {
        this.unloadWeight = unloadWeight;
    }

    public String getConUnloadWeight2() {
        return conUnloadWeight2;
    }

    public void setConUnloadWeight2(String conUnloadWeight2) {
        this.conUnloadWeight2 = conUnloadWeight2;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}

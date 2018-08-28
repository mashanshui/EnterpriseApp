package com.shenhesoft.enterpriseapp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author 张继淮
 * @date 2017/12/4
 * @desc 定位界面查询项目实体类
 */

public class LocationPopBean implements Serializable {


    @SerializedName(value = "id", alternate = {"freightId", "projectId", "cargoId"})
    private int id;
    @SerializedName(value = "name", alternate = {"freightName", "orderCode", "trainKind", "cargoName"})
    private String name;

    @SerializedName("trainKindCode")
    private String trainKindCode;

    @SerializedName("trainTypeCode")
    private String trainTypeCode;

    @SerializedName(value = "cargoCode", alternate = {"code"})
    private String cargoCode;

    public String getTrainKindCode() {
        return trainKindCode;
    }

    public void setTrainKindCode(String trainKindCode) {
        this.trainKindCode = trainKindCode;
    }

    public String getTrainTypeCode() {
        return trainTypeCode;
    }

    public void setTrainTypeCode(String trainTypeCode) {
        this.trainTypeCode = trainTypeCode;
    }

    public LocationPopBean(int id, String name, String cargoCode) {
        this.id = id;
        this.name = name;
        this.cargoCode = cargoCode;
    }

    public LocationPopBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public LocationPopBean() {

    }

    public String getCargoCode() {
        return cargoCode;
    }

    public void setCargoCode(String cargoCode) {
        this.cargoCode = cargoCode;
    }

    public LocationPopBean(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

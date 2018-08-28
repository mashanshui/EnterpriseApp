package com.shenhesoft.enterpriseapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author 张继淮
 * @date 2017/12/5
 * @desc 等待调度实体类
 */

public class WaitDispatchBean {

    @SerializedName("id")
    private int id;

    @SerializedName("orderCode")
    private String orderCode;

    private boolean checked;
    @SerializedName("projectCode")
    private String projectCode;
    @SerializedName("carPlateNumber")
    private String carPlateNumber;
    @SerializedName("carrierVehicleName")
    private String carrierVehicleName;
    @SerializedName("driverId")
    private String driverId;
    @SerializedName("cargoName")
    private String cargoName;

    @SerializedName("stepSelect")
    private String phone;

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    //    @SerializedName("stepSelect")
//    private String phone;
//
//    @SerializedName("stepSelect")
//    private String phone;
//


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getCarrierVehicleName() {
        return carrierVehicleName;
    }

    public void setCarrierVehicleName(String carrierVehicleName) {
        this.carrierVehicleName = carrierVehicleName;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

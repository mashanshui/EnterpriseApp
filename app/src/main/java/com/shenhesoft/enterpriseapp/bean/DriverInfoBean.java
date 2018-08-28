package com.shenhesoft.enterpriseapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author 张继淮
 * @date 2017/12/29
 * @desc TODO
 */

public class DriverInfoBean {

    /**
     * driverId : 5079
     * informationId : 58
     * driverName : 阿狗
     * motorcycleType : 一汽解放J9L大卡
     * driverPhone : 15355131788
     * plateNumber : 皖H78394
     * transportPrice :
     * deductionRate : 0
     * deductionPrice :
     * changerName :
     * teamName :
     */

    private int driverId;
    private int informationId;
    private String driverName;
    private String motorcycleType;
    private String driverPhone;
    private String plateNumber;
    private String transportPrice;
    private String deductionRate;
    private String deductionPrice;
    private String changerName;
    private String teamName;

    @SerializedName("teamId")
    private String carTeamId;

    public String getCarTeamId() {
        return carTeamId;
    }

    public void setCarTeamId(String carTeamId) {
        this.carTeamId = carTeamId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getInformationId() {
        return informationId;
    }

    public void setInformationId(int informationId) {
        this.informationId = informationId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getMotorcycleType() {
        return motorcycleType;
    }

    public void setMotorcycleType(String motorcycleType) {
        this.motorcycleType = motorcycleType;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getTransportPrice() {
        return transportPrice;
    }

    public void setTransportPrice(String transportPrice) {
        this.transportPrice = transportPrice;
    }

    public String getDeductionRate() {
        return deductionRate;
    }

    public void setDeductionRate(String deductionRate) {
        this.deductionRate = deductionRate;
    }

    public String getDeductionPrice() {
        return deductionPrice;
    }

    public void setDeductionPrice(String deductionPrice) {
        this.deductionPrice = deductionPrice;
    }

    public String getChangerName() {
        return changerName;
    }

    public void setChangerName(String changerName) {
        this.changerName = changerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}

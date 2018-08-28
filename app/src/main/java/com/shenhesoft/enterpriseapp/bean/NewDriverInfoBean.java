package com.shenhesoft.enterpriseapp.bean;

import java.util.List;

/**
 * @author 马山水
 * @date 2018/3/16
 * @desc TODO
 */

public class NewDriverInfoBean {

    /**
     * driverId : 997994
     * informationId : 992369
     * driverName : 拓瞻雨
     * motorcycleType : 牵引拖挂车
     * driverPhone : 18199900001
     * plateNumber : 测A00001
     * transportPrice :
     * deductionRate : 0
     * deductionPrice :
     * changerName :
     * teamName :
     * teamId :
     * ownerCarName : 拓瞻雨
     * brand : 解放
     * model : A001
     * distributionId :
     * historyTbOrderNumDriverId :
     * tbAnchoredCompanyList : [{"id":1,"name":"新疆秦龙矿业有限公司","phone":"18715083549","status":"","createDate":"","address":"","linkman":""}]
     */

    private int driverId;
    private int informationId;
    private String driverName;
    private String motorcycleType;
    private String driverPhone;
    private String plateNumber;
    private String transportPrice;
    private int deductionRate;
    private String deductionPrice;
    private String changerName;
    private String teamName;
    private String teamId;
    private String ownerCarName;
    private String brand;
    private String model;
    private String distributionId;
    private String historyTbOrderNumDriverId;
    private List<TbAnchoredCompanyListBean> tbAnchoredCompanyList;

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

    public int getDeductionRate() {
        return deductionRate;
    }

    public void setDeductionRate(int deductionRate) {
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

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getOwnerCarName() {
        return ownerCarName;
    }

    public void setOwnerCarName(String ownerCarName) {
        this.ownerCarName = ownerCarName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(String distributionId) {
        this.distributionId = distributionId;
    }

    public String getHistoryTbOrderNumDriverId() {
        return historyTbOrderNumDriverId;
    }

    public void setHistoryTbOrderNumDriverId(String historyTbOrderNumDriverId) {
        this.historyTbOrderNumDriverId = historyTbOrderNumDriverId;
    }

    public List<TbAnchoredCompanyListBean> getTbAnchoredCompanyList() {
        return tbAnchoredCompanyList;
    }

    public void setTbAnchoredCompanyList(List<TbAnchoredCompanyListBean> tbAnchoredCompanyList) {
        this.tbAnchoredCompanyList = tbAnchoredCompanyList;
    }

    public static class TbAnchoredCompanyListBean {
        /**
         * id : 1
         * name : 新疆秦龙矿业有限公司
         * phone : 18715083549
         * status :
         * createDate :
         * address :
         * linkman :
         */

        private int id;
        private String name;
        private String phone;
        private String status;
        private String createDate;
        private String address;
        private String linkman;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }
    }
}

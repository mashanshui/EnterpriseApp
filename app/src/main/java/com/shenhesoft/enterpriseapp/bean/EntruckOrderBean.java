package com.shenhesoft.enterpriseapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张继淮
 * @date 2018/1/3
 * @desc TODO
 */

public class EntruckOrderBean implements Serializable {

    //货场名称
    private String cargoPlaceName;

    //货场id
    private String cargoPlaceId;

    //货场名称
    private String cargoSiteName;

    //货位id
    private String cargoSiteId;

    //装车详单集合
    private List<EntruckOrderDetailBean> orderDetail;

    public String getCargoPlaceName() {
        return cargoPlaceName;
    }

    public void setCargoPlaceName(String cargoPlaceName) {
        this.cargoPlaceName = cargoPlaceName;
    }

    public String getCargoPlaceId() {
        return cargoPlaceId;
    }

    public void setCargoPlaceId(String cargoPlaceId) {
        this.cargoPlaceId = cargoPlaceId;
    }

    public String getCargoSiteId() {
        return cargoSiteId;
    }

    public void setCargoSiteId(String cargoSiteId) {
        this.cargoSiteId = cargoSiteId;
    }

    public String getCargoSiteName() {
        return cargoSiteName;
    }

    public void setCargoSiteName(String cargoSiteName) {
        this.cargoSiteName = cargoSiteName;
    }

    public List<EntruckOrderDetailBean> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<EntruckOrderDetailBean> orderDetail) {
        this.orderDetail = orderDetail;
    }
}

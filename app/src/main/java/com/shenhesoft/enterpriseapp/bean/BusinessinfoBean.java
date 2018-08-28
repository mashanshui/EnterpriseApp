package com.shenhesoft.enterpriseapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张继淮
 * @date 2018/2/2
 * @desc TODO
 */

public class BusinessinfoBean implements Serializable {

    /**
     * listBulkOrderStatus : [{"name":"等待调度","value":2},{"name":"等待发运","value":6},{"name":"在途运载","value":4},{"name":"货位引导","value":1},{"name":"等待回单","value":2},{"name":"计费确认","value":15}]
     * sumTrainOrderStatus : 0
     * listTrainOrderStatus : [{"name":"等待承认","value":0},{"name":"等待装车","value":0},{"name":"等待发运","value":0},{"name":"在途运载","value":0},{"name":"等待卸货","value":0},{"name":"等待回单","value":0}]
     * sumBulkOrderStatus : 30
     */

    private int sumTrainOrderStatus;
    private int sumBulkOrderStatus;
    private List<ListBulkOrderStatusBean> listBulkOrderStatus;
    private List<ListTrainOrderStatusBean> listTrainOrderStatus;

    public int getSumTrainOrderStatus() {
        return sumTrainOrderStatus;
    }

    public void setSumTrainOrderStatus(int sumTrainOrderStatus) {
        this.sumTrainOrderStatus = sumTrainOrderStatus;
    }

    public int getSumBulkOrderStatus() {
        return sumBulkOrderStatus;
    }

    public void setSumBulkOrderStatus(int sumBulkOrderStatus) {
        this.sumBulkOrderStatus = sumBulkOrderStatus;
    }

    public List<ListBulkOrderStatusBean> getListBulkOrderStatus() {
        return listBulkOrderStatus;
    }

    public void setListBulkOrderStatus(List<ListBulkOrderStatusBean> listBulkOrderStatus) {
        this.listBulkOrderStatus = listBulkOrderStatus;
    }

    public List<ListTrainOrderStatusBean> getListTrainOrderStatus() {
        return listTrainOrderStatus;
    }

    public void setListTrainOrderStatus(List<ListTrainOrderStatusBean> listTrainOrderStatus) {
        this.listTrainOrderStatus = listTrainOrderStatus;
    }

    public static class ListBulkOrderStatusBean {
        /**
         * name : 等待调度
         * value : 2
         */

        private String name;
        private int value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class ListTrainOrderStatusBean {
        /**
         * name : 等待承认
         * value : 0
         */

        private String name;
        private int value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}

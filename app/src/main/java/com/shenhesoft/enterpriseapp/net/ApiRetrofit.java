package com.shenhesoft.enterpriseapp.net;


import android.util.Log;

import com.shenhesoft.enterpriseapp.AppConstant;
import com.shenhesoft.enterpriseapp.MyApplication;
import com.shenhesoft.enterpriseapp.utils.AppUtil;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.log.XPrinter;

/**
 * @author 张继淮
 * @date 2017/11/27
 * @description Api管理
 */

public class ApiRetrofit {


    private static ApiRetrofit mInstance;

    private ApiRetrofit() {
    }

    public static ApiRetrofit getInstance() {
        if (mInstance == null) {
            synchronized (ApiRetrofit.class) {
                mInstance = new ApiRetrofit();
            }
        }
        return mInstance;
    }


    /**
     * 位置查询
     *
     * @return
     */
    public Map<String, Object> getLocationList(String carId, String carNo, String projectId, String projectCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("carId", carId);
        params.put("carNo", carNo);
        params.put("projectId", projectId);
        params.put("projectCode", projectCode);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 到货确认-异常列表
     *
     * @param pageNo 页号
     * @return
     */
    public Map<String, Object> getProjectList(String pageNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("page", pageNo);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 判断订单能否新建
     *
     * @param
     * @return
     */
    public Map<String, Object> checkProject(String projectId, String stepSelectCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("stepSelectCode", stepSelectCode);
        params.put("projectId", projectId);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 登陆
     *
     * @param name
     * @param password
     * @return
     */
    public Map<String, Object> login(String name, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userName", name);
        params.put("password", password);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 获取验证码
     *
     * @param phoneNum
     * @return
     */
    public Map<String, Object> getCode(String phoneNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("phoneNum", phoneNum);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 保存新手机号
     *
     * @param checkedCode      验证码
     * @param oldPhoneNum      原手机号码
     * @param newPasswordAgain 新号码
     * @return
     */
    public Map<String, Object> saveNewPhone(String checkedCode, String oldPhoneNum, String newPasswordAgain) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("checkedCode", checkedCode);
        params.put("oldPhoneNum", oldPhoneNum);
        params.put("newPhoneNum", newPasswordAgain);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 保存新密码
     *
     * @param phoneNum         手机号码
     * @param checkedCode      验证码
     * @param newPassword      新密码
     * @param newPasswordAgain 新重复密码
     * @return
     */
    public Map<String, Object> saveNewPassWord(String phoneNum, String checkedCode, String newPassword, String newPasswordAgain) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("phoneNum", phoneNum);
        params.put("checkedCode", checkedCode);
        params.put("newPassword", newPassword);
        params.put("newPasswordAgain", newPasswordAgain);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 计费确认-异常提交
     *
     * @return
     */
    public Map<String, Object> FeeunusalSubmit(String orderId, String exceptionReason, String exceptionReasonDetail) {
        Map<String, Object> params = new HashMap<>();
        params.put("submitUserId", getUserID());
        params.put("orderId", orderId);
        params.put("exceptionReason", exceptionReason);
        params.put("exceptionReasonDetail", exceptionReasonDetail);
        params.put("nodeType", "计费确认");
        XLog.d(params.toString());
        return params;
    }

    /**
     * 到货确认-异常提交
     *
     * @return
     */
    public Map<String, Object> ArriveunusalSubmit(String orderId, String exceptionReason, String exceptionReasonDetail) {
        Map<String, Object> params = new HashMap<>();
        params.put("submitUserId", getUserID());
        params.put("orderId", orderId);
        params.put("exceptionReason", exceptionReason);
        params.put("exceptionReasonDetail", exceptionReasonDetail);
        params.put("nodeType", "到货确认");
        XLog.d(params.toString());
        return params;
    }

    /**
     * 调度审核列表
     *
     * @return
     */
    public Map<String, Object> waitdisptach(String page) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("page", page);
        params.put("status", "1");
        XLog.d(params.toString());
        return params;
    }

    /**
     * 调度审核-驳回信息列表
     *
     * @return
     */
    public Map<String, Object> rejectDisptach(String page) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("page", page);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 调度审核-驳回信息列表-还原
     *
     * @return
     */
    public Map<String, Object> restoreOrder(String orderid) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("orderId", orderid);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 调度审核-驳回提交
     *
     * @return
     */
    public Map<String, Object> rejectSubmit(String orderId, String remark) {
        Map<String, Object> params = new HashMap<>();
        params.put("SysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("orderId", orderId);
        params.put("remark", remark);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 到货确认-异常提交
     *
     * @return
     */
    public Map<String, Object> unusalSubmit(String orderId, String exceptionReason, String exceptionReasonDetail) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("orderId", orderId);
        params.put("exceptionReason", exceptionReason);
        params.put("exceptionReasonDetail", exceptionReasonDetail);
        params.put("shortTrainFlag", "0");
//shortTrainFlag 短驳还是火运 0-短驳 1-火运
        XLog.d(params.toString());
        Log.e("info", "unusalSubmit: "+params.toString() );
        return params;
    }

    /**
     * 等待调度-核准
     *
     * @return
     */
    public Map<String, Object> checkDispatch(String orderid, String containerNumber1, String containerNumber2) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("orderId", orderid);
        params.put("containerNumber1", containerNumber1);
        params.put("containerNumber2", containerNumber2);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 等待调度-驳回原因列表
     *
     * @return
     */
    public Map<String, Object> rejectOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * 到货确认-等待卸货
     *
     * @param pageNo 页号
     * @param status "4"
     * @return
     */
    public Map<String, Object> waitUnloading(String pageNo, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("page", pageNo);
        params.put("status", status);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 主页-计费确认消息数量
     *
     * @return
     */
    public Map<String, Object> chargeOrderCount() {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * 首页-获取订单数量
     *
     * @return
     */
    public Map<String, Object> getOrderCount(String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("type", type);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 到货确认-计费确认-异常列表
     *
     * @param pageNo 页号
     * @param status 到货确认status=3 计费确认status=5
     * @return
     */
    public Map<String, Object> waitUnloadingunusual(String pageNo,String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("page", pageNo);
        params.put("status", status);
        XLog.d(params.toString());
        return params;
    }
    /**
     * 全部异常列表
     *
     * @param pageNo 页号
     * @return
     */
    public Map<String, Object> allunusual(String pageNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("page", pageNo);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 到货确认-确认-货场列表
     *
     * @return
     */
    public Map<String, Object> getlistGoodsyard(String orderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("orderId", orderId);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 到货确认-确认-货场列表
     *
     * @return
     */
    public Map<String, Object> getArrivalListGoodsyard(String orderid) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("orderId", orderid);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 新建运单-货场列表
     *
     * @return
     */
    public Map<String, Object> getNewOrderListGoodsyard(String stepSelectCode, String sendCompanyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("stepSelectCode", stepSelectCode);
        params.put("sendCompanyId", sendCompanyId);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 汽运-取消发运
     *
     * @return
     */
    public Map<String, Object> cancelMotorOrder(String OrderId,String projectType) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("orderId", OrderId);
        params.put("type", projectType);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 火运-承认车
     *
     * @return
     */
    public Map<String, Object> saveTbTrainorderCarNum(String trainOrderId, String num) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", getUserID());
        params.put("sysOrgCode", getSysOrgCode());
        params.put("trainOrderId", trainOrderId);
        params.put("sureNum", num);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 火运-装车/卸货-详单
     *
     * @return
     */
    public Map<String, Object> getOrderDetaillist(String trainOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("trainOrderId", trainOrderId);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 火运-装车-货场列表
     *
     * @return
     */
    public Map<String, Object> getTrainTypelist() {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * 火运-卸车根据车型获取-车号列表
     *
     * @return
     */
    public Map<String, Object> getTrainUnloadlist(String trainOrderId, String carTypeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("trainOrderId", trainOrderId);
        params.put("carTypeId", carTypeId);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 火运-装车-货场列表
     *
     * @return
     */
    public Map<String, Object> getTrainEntrucklist(String trainOrderId, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", getUserID());
        params.put("sysOrgCode", getSysOrgCode());
        params.put("trainOrderId", trainOrderId);
        params.put("type", type);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 火运-装车-货位列表
     *
     * @return
     */
    public Map<String, Object> getTrainEntruckLocationlist(String yardId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("yardId", yardId);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 火运-装车-装车信息提交
     *
     * @return
     */
    public Map<String, Object> submitEntruckinfo(String trainOrderId, String entruckInfoJson, String entruckNumbe, String containerNums, String entruckWeight) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("trainOrderId", trainOrderId);
        params.put("entruckInfoJson", entruckInfoJson);
        params.put("entruckNumbe", entruckNumbe);
        params.put("containerNums", containerNums);
        params.put("entruckWeight", entruckWeight);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 火运-卸车-卸车信息提交
     *
     * @return
     */
    public Map<String, Object> submitUnloadinfo(String trainOrderId, String arrivalInfoJson, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("trainOrderId", trainOrderId);
        params.put("arrivalInfoJson", arrivalInfoJson);
        params.put("type", type);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 火运-等待回单信息提交
     * <p>
     * userId 当前登录人主键
     * trainOrderId 火运运单id
     * trainOrderCargoPalceId 车辆火运中间表主键
     * deliverGoodsImg 图片 发货运单
     * arrivalImg 图片 到货运单
     * type  0只保存运单信息，不跟新状态  1只更新状态。
     *
     * @return
     */
    public Map<String, Object> submitWaitCallinfo(String trainOrderId, String trainOrderCargoPalceId, String deliverGoodsImg, String arrivalImg, int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("trainOrderId", trainOrderId);
        params.put("trainOrderCargoPalceId", trainOrderCargoPalceId);
        params.put("deliverGoodsImg", deliverGoodsImg);
        params.put("arrivalImg", arrivalImg);
        params.put("type", type);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 到货确认-确认-货位列表
     *
     * @return
     */
    public Map<String, Object> getlistGoodschild(String freightId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("freightId", freightId);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 到货确认-提交货场货位信息
     *
     * @return
     */
    public Map<String, Object> submitGoodsinfo(String orderId, String distributionCargoPlace, String distributionCargoSite,String distributionCargoPlaceId,String distributionCargoSiteId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", getUserID());
        params.put("orderId", orderId);
        params.put("distributionCargoPlace", distributionCargoPlace);
        params.put("distributionCargoSite", distributionCargoSite);
        params.put("sysOrgCode", getSysOrgCode());
        params.put("distributionCargoPlaceId", distributionCargoPlaceId);
        params.put("distributionCargoSiteId", distributionCargoSiteId);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 调度审核-提交货场货位信息
     *
     * @return
     */
    public Map<String, Object> submitDispatchGoodsyard(String orderId, String distributionCargoPlace, String distributionCargoSite,String distributionCargoPlaceId,String distributionCargoSiteId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("orderId", orderId);
        params.put("takeCarogoPlaceName", distributionCargoPlace);
        params.put("takeCargoSiteName", distributionCargoSite);

        params.put("takeCargoPlaceId", distributionCargoPlaceId);
        params.put("takeCargoSiteId", distributionCargoSiteId);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 到货确认-确认-货位列表
     *
     * @return
     */
    public Map<String, Object> getListCarinfo(String projectId,String shortType) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("projectId", projectId);
        params.put("shortType", shortType);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 根据车牌号获取车辆信息
     *
     * @return
     */
    public Map<String, Object> getCarinfo(String plateNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("plateNumber", plateNumber);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 根据driverId获取车辆信息
     *
     * @return
     */
    public Map<String, Object> getCarinfoByDriverId(String projectId,String driverId,String shortType) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
//        params.put("projectId", projectId);
        params.put("driverId", driverId);
//        params.put("shortType", shortType);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 到货确认-确认-货位列表
     *
     * @return
     */
    public Map<String, Object> getOrderContainerNum(String text,String projectId,String stepSelectCode, String receiptCompanyId, String sendCompanyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("projectId", projectId);
        params.put("text", text);
        params.put("stepSelectCode", stepSelectCode);
        if (stepSelectCode.equals("0")) {
            params.put("receiptCompanyId", receiptCompanyId);  //接取 收货站点
        } else {
            params.put("sendCompanyId", sendCompanyId);   //送达发货站点
        }
        XLog.d(params.toString());
        return params;
    }

    /**
     * 等待装车-填写装车详单获取集装箱号
     *
     * @return
     */
    public Map<String, Object> getTrainContainerNum(String trainOrderId,String text) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("trainOrderId", trainOrderId);
        params.put("text", text);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 创建汽车短驳-新建运单的 Map请求参数
     *
     * @param projectId          项目id
     * @param projectCode        项目编号
     * @param stepSelectCode     阶段选择
     * @param branchGroupName    分支机构
     * @param arrivePlaceAddress 运抵地址
     * @param cargoName          货物品名
     * @param carTeamId          车队id
     * @param carPlateNumber     承运车辆车牌号
     * @param carrierVehicleId   承运车辆id
     * @param carrierVehicleName 承运车辆名称
     * @param carType            车辆类型
     * @param driverName         驾驶员
     * @param driverPhone        联系方式
     * @param shortBargeCost     短驳运费
     * @param deductionPrice     折损单价
     * @param subsidy            补贴
     * @param containerNumber1Id 集装箱1号id
     * @param containeNumber2Id  集装箱2号id
     * @param containeNumber2Id  集装箱2号id
     * @return
     */
    public Map<String, Object> newMotorOrder(int transportType,String projectId, String projectCode,
                                             String stepSelectCode, String branchGroupName, String arrivePlaceAddress, String cargoName,
                                             String carTeamId, String carPlateNumber, String carrierVehicleId, String carrierVehicleName,
                                             String carType, String driverName, String driverPhone, String shortBargeCost, String deductionPrice,
                                             String subsidy, String containerNumber1Id, String containeNumber2Id, String type,
                                             String takeCargoPlaceId, String takeCargoSiteId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", getUserID());
        params.put("projectId", projectId);
        params.put("projectCode", projectCode);
        params.put("transportType", String.valueOf(transportType));
        params.put("stepSelectCode", stepSelectCode);
        params.put("branchGroupName", branchGroupName);
        params.put("arrivePlaceAddress", arrivePlaceAddress);
        params.put("cargoName", cargoName);
        params.put("carTeamId", carTeamId);
        params.put("carPlateNumber", carPlateNumber);
        params.put("carrierVehicleId", carrierVehicleId);
        params.put("carrierVehicleName", carrierVehicleName);
        params.put("carType", carType);
        params.put("driverName", driverName);
        params.put("driverPhone", driverPhone);
        params.put("shortBargeCost", shortBargeCost);
        params.put("deductionPrice", deductionPrice);
        params.put("subsidy", subsidy);
        if (type.equals("1")) {
            params.put("containerNumber1", containerNumber1Id);
            params.put("containerNumber2", containeNumber2Id);
        }
        params.put("type", type);
        params.put("takeCargoPlaceId", takeCargoPlaceId);
        params.put("takeCargoSiteId", takeCargoSiteId);
        params.put("sysOrgCode", getSysOrgCode());
        XLog.d(params.toString());
        return params;
    }


    /**
     * 创建汽车短驳-新建运单的 Map请求参数
     * <p>
     * projectId 项目主键
     * pleaseTrainNum 请车数
     * pleaseCarTypeId 请车类型id
     * estimateWeight 预计载重
     * estimateCost  预计支出金额
     *
     * @return
     */

    public Map<String, Object> newTrainOrder(String projectId, String pleaseTrainNum,
                                             String pleaseCarTypeId, String estimateWeight, String estimateCost, String receiveAccountId, String receiveAccountName, String depositAmount) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("projectId", projectId);
        params.put("pleaseTrainNum", pleaseTrainNum);
        params.put("pleaseCarTypeId", pleaseCarTypeId);
        params.put("estimateWeight", estimateWeight);
        params.put("estimateCost", estimateCost);
        params.put("receiveAccountId", receiveAccountId);
        params.put("receiveAccountName", receiveAccountName);
        params.put("depositAmount", depositAmount);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 异常列表 Map请求参数
     *
     * @param page 页号
     * @return
     */
    public Map<String, Object> getUnusualParams(String page) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("page", page);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 创建任务分配列表 Map请求参数
     *
     * @param page 页号
     * @return
     */
    public Map<String, Object> getTaskParams(String page) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("page", page);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 创建任务分配-保存分配的 Map请求参数
     *
     * @param projectId 項目id
     * @return
     */
    public Map<String, Object> saveTaskParams(String projectId, String distributionNum, String taskType) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("projectId", projectId);
        params.put("distributionNum", distributionNum);
        params.put("taskType", taskType);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 创建任务分配-开始分配的 Map请求参数
     *
     * @param projectId 項目id
     * @return
     */
    public Map<String, Object> startTaskParams(String projectId, String projectStages) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("projectId", projectId);
        params.put("projectStages", projectStages);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 创建任务分配-暂停分配的 Map请求参数
     *
     * @param projectId projectId 項目id
     * @return
     */
    public Map<String, Object> stopTaskParams(String projectId, String projectStages) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("projectId", projectId);
        params.put("projectStages", projectStages);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 创建汽车短驳的 Map请求参数
     *
     * @param pageNo 页号
     * @param status 全部:"2,3,5" /等待发运 "2" /在途"3" /回单"5"
     * @return
     */
    public Map<String, Object> motorShortParams(String pageNo, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("page", pageNo);
        params.put("status", status);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 汽车短驳 提交等待发运 发运信息Map参数
     *
     * @return
     */
    public Map<String, Object> submitMotorFayxxParams(String orderId, double pz, double mz, double jz, double jz1
            , double jz2, String huayzb, String img, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("orderId", orderId);
        params.put("sendTare", pz);
        params.put("sendGross", mz);
        if (type.equals("1")) {
            params.put("containerOneSendNet", jz1);
            params.put("containerTwoSendNet", jz2);
        } else {
            params.put("containerOneSendNet", jz);
//            params.put("containerTwoSendNet", 0);
        }
        params.put("testIndicators", huayzb);
        params.put("img", img);
        params.put("type", type);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 汽车短驳 提交等待发运 发运信息Map参数
     *
     * @return
     */
    public Map<String, Object> submitMotorArrivexxParams(String orderId, double pz, double mz, double jz, double jz1
            , double jz2, String huayzb, String img, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("orderId", orderId);
        params.put("receiptTare", pz);
        params.put("receiptGross", mz);
        if (type.equals("1")) {
            params.put("containerOneReceiptNet", jz1);
            params.put("containerTwoReceiptNet", jz2);
        } else {
            params.put("containerOneReceiptNet", jz);
//            params.put("containerTwoSendNet", 0);
        }
//        params.put("testIndicators", huayzb);
        params.put("img", img);
        params.put("type", type);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 汽车短驳 更新运单状态
     * 3在途运载
     * 4货位引导
     * 5等待回单
     * @return
     */
    public Map<String, Object> updateMotorStatus(String orderId,String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("orderId", orderId);
        params.put("status", status);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 火车干线 列表数据 请求Map参数
     *
     * @param pageNo
     * @param status
     * @return
     */
    public Map<String, Object> trainListParams(String pageNo, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("page", pageNo);
        params.put("status", status);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 火车干线 列表数据 请求Map参数
     *
     * @param trainOrderId
     * @return
     */
    public Map<String, Object> trainDetailParams(String trainOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("trainOrderId", trainOrderId);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 火车干线 列表数据 请求Map参数
     *
     * @param trainOrderId 火运运单id
     * @param location     位置
     * @return
     */
    public Map<String, Object> savetrainLocationParams(String trainOrderId, String location) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("trainOrderId", trainOrderId);
        params.put("location", location);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 火车干线 列表数据 请求Map参数
     * 4 等待发运 5在途运载  6 等待卸货   7等待回单
     *
     * @param trainOrderId
     * @return
     */
    public Map<String, Object> updataTrainParams(String trainOrderId, int status) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysOrgCode", getSysOrgCode());
        params.put("userId", getUserID());
        params.put("trainOrderId", trainOrderId);
        params.put("status", status);
        XLog.d(params.toString());
        return params;
    }


    /**
     * 获取用户ID
     *
     * @return
     */
    private String getUserID() {
        return SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.Userinfo, "");
    }

    /**
     * 获取SysOrgCode
     * @return
     */
    public String getSysOrgCode() {
        return SharedPref.getInstance(MyApplication.getContext()).getString(AppConstant.SysOrgCode, "");
    }
}

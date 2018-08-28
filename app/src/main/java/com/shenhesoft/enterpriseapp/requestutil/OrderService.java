package com.shenhesoft.enterpriseapp.requestutil;

import com.shenhesoft.enterpriseapp.HttpURL;
import com.shenhesoft.enterpriseapp.bean.BusinessinfoBean;
import com.shenhesoft.enterpriseapp.bean.ContainerBean;
import com.shenhesoft.enterpriseapp.bean.CustomerCheckBean;
import com.shenhesoft.enterpriseapp.bean.DriverCheckBean;
import com.shenhesoft.enterpriseapp.bean.DriverInfoBean;
import com.shenhesoft.enterpriseapp.bean.FeePayBean;
import com.shenhesoft.enterpriseapp.bean.LocationBean;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.bean.NewDriverInfoBean;
import com.shenhesoft.enterpriseapp.bean.ProjectBean;
import com.shenhesoft.enterpriseapp.bean.TaskDetailsBean;
import com.shenhesoft.enterpriseapp.bean.TrainBean;
import com.shenhesoft.enterpriseapp.bean.TrainDetailsBean;
import com.shenhesoft.enterpriseapp.bean.TrainLocationBean;
import com.shenhesoft.enterpriseapp.bean.TrainProjectBean;
import com.shenhesoft.enterpriseapp.bean.UnusualBean;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 作者：Tornado
 * 创作日期：2017/12/22.
 * 描述：业务相关的请求接口
 */

public interface OrderService {

    /***
     * 获取业务模块信息
     */
    @GET(HttpURL.IP + "/api/orderStatuss")
    Observable<RequestResults<BusinessinfoBean>> getBusinessinfo(@Query("curMonthFlag") String curMonth, @Query("userId") String userId, @Query("sysOrgCode") String sysOrgCode);


    /***
     * 获取运费支出列表
     */
    @POST(HttpURL.IP + "/api/link/shortOrderFinances")
    Observable<RequestResults<RequestResultsList<FeePayBean>>> getFeepayinfo(@Body Map<String, Object> parmas);


    /***
     * 获取运费支出列表
     */
    @POST(HttpURL.IP + "/api/link/shortOrderFinances")
    Observable<RequestResults<RequestResultsList<MotorDetailsBean>>> getFeeCheckinfo(@Body Map<String, Object> parmas);

    /***
     * 获取司机对账列表
     */
    @POST(HttpURL.IP + "/api/link/shortPacks")
    Observable<RequestResults<RequestResultsList<DriverCheckBean>>> getDrivecheckinfo(@Body Map<String, Object> parmas);


    /***
     * 获取客户对账列表
     */
    @POST(HttpURL.IP + "/api/link/customerCheckingApp")
    Observable<RequestResults<RequestResultsList<CustomerCheckBean>>> getCustomerinfo(@Body Map<String, Object> parmas);

    /***
     * 计费确认通过
     */
    @POST(HttpURL.IP + "/api/link/shortOrderFinance/billingVerify/{shOrderFinId}")
    Observable<RequestResults> checkFeepaygo(@Path("shOrderFinId") String shOrderFinId, @Body Map<String, Object> parmas);

    /***
     * 司机对账财务审核通过
     */
//    @PUT(HttpURL.IP + "/api/shortPacks/financeAudit/{shPackId}")
    @PUT(HttpURL.IP + "/api/link/shortPacksApp/financeAuditApp/{shPackIds}")
    Observable<RequestResults> checkDriver(@Path("shPackIds") String shPackIds, @Body Map<String, Object> parmas);

    /***
     * 财务审核通过
     */
//    @PUT(HttpURL.IP + "/api/shortOrderFinance/financeAudit/{shOrderFinId}")
    @PUT(HttpURL.IP + "/api/link/shortOrderFinanceApp/financeAuditApp/{shOrderFinIds}")
    Observable<RequestResults> checkMoneygo(@Path("shOrderFinIds") String shOrderFinIds, @Body Map<String, Object> parmas);

    /***
     * 客户对账通过
     */
    @PUT(HttpURL.IP + "/api/link/customerCheckingConf/financeAudit/{checkId}/{packType}")
    Observable<RequestResults> checkCustomergo(@Path("checkId") String checkId, @Path("packType") String packType,@Body Map<String, Object> parmas);

    /***
     * 位置信息获取
     */
    @POST(HttpURL.IP + "/api/mapPoints")
    Observable<RequestResultsList<LocationBean>> getlocationlist(@Body Map<String, Object> parmas);
//    /***
//     * 位置信息获取
//     */
//    @GET(HttpURL.IP + "/api/mapPoints")
//    Observable<RequestResultsList<LocationBean>> getlocationlist(@QueryMap Map<String, Object> parmas);

    /**
     * 异常列表数据
     */
    @POST("order/listShortExceptionInfoByUserIdApp.do")
    Observable<RequestResults<ListALLResults<UnusualBean>>> getUnusualList(@Body Map<String, Object> parmas);

    /**
     * 任务分配-保存分配
     */
    @POST("project/saveTbProjectDistributionApp.do")
    Observable<RequestResults> saveTask(@Body Map<String, Object> parmas);

    /**
     * 任务分配-开始分配
     */
    @POST("project/beginTbProjectDistributionApp.do")
    Observable<RequestResults> startTask(@Body Map<String, Object> parmas);

    /**
     * 任务分配-暂停分配
     */
    @POST("project/stopTbProjectDistributionApp.do")
    Observable<RequestResults> stopTask(@Body Map<String, Object> parmas);

    /**
     * 获取分配任务列表数据
     */
    @POST("project/listTbProjectForDistributionApp.do")
    Observable<RequestResults<ListALLResults<TaskDetailsBean>>> getTaskListData(@Body Map<String, Object> parmas);

    /**
     * 等待调度-驳回原因列表
     *
     * @return
     */
    @POST("order/listTurndownReasonApp.do")
    Observable<RequestResultsList<String>> rejectOrder(@Body Map<String, Object> params);


    /**
     * 到货确认-异常原因列表
     *
     * @return
     */
    @POST("order/listOrderExceptionReasonApp.do")
    Observable<RequestResultsList<String>> ArriveUnusualOrder(@Body Map<String, Object> params);

    /**
     * 汽运短驳-新建集装箱运单
     */
    @POST("order/saveTbOrderApp.do")
    Observable<RequestResults> newMotorOrder(@Body Map<String, Object> parmas);


    /**
     * 火运-新建运单
     */
    @POST("trainOrder/saveTbTrainOrderApp.do")
    Observable<RequestResults> saveTrainOrder(@Body Map<String, Object> parmas);

    /**
     * 火运干线-项目列表
     */
    @POST("trainOrder/trainListTbProjectApp.do")
    Observable<RequestResultsList<TrainProjectBean>> newTrainOrder(@Body Map<String, Object> parmas);


    /**
     * 获取汽车短驳列表数据
     */
    @POST("order/listAllTbOrderDifferentStatus.do")
    Observable<RequestResults<ListALLResults<MotorDetailsBean>>> getMotorShortListData(@Body Map<String, Object> parmas);


    /**
     * 添加汽车短驳》发运信息
     *
     * @param params 相关参数
     * @return
     */
    @POST("order/saveTbOrderForwardInfo.do")
    Observable<RequestResults> addMotorFayxxForm(@Body Map<String, Object> params);


    /**
     * 添加汽车短驳》到货信息
     *
     * @param params 相关参数
     * @return
     */
    @POST("order/receipterTbOrderApp.do")
    Observable<RequestResults> addMotorArriveForm(@Body Map<String, Object> params);

    /**
     * 汽车短驳-新建运单获取集装箱号
     *
     * @param params 相关参数
     * @return
     */
    @POST("order/getContainerNum.do")
    Observable<RequestResultsList<ContainerBean>> getOrderContainerNum(@Body Map<String, Object> params);

    /**
     * 等待装车-填写装车详单获取集装箱号
     *
     * @param params 相关参数
     * @return
     */
    @POST("trainOrder/trainAppContainerList.do")
    Observable<RequestResultsList<ContainerBean>> getTrainContainerNum(@Body Map<String, Object> params);

    /**
     * 获取项目列表
     *
     * @param params 相关参数
     * @return
     */
    @POST("order/bulkListTbProjectApp.do")
    Observable<RequestResultsList<ProjectBean>> getProjectList(@Body Map<String, Object> params);

    /**
     * 判断订单能否新建
     *
     * @param params 相关参数
     * @return
     */
    @POST("order/appAddOrderIsPublish.do")
    Observable<RequestResults> checkProject(@Body Map<String, Object> params);

    /**
     * 根据车牌号获取车辆信息
     *
     * @param params 相关参数
     * @return
     */
    @POST("order/viewTbOrderCarDetailByPlateNumber.do")
    Observable<RequestResults<DriverInfoBean>> getCarinfo(@Body Map<String, Object> params);


    /**
     * 根据driverId获取车辆信息
     *
     * @param params 相关参数
     * @return
     */
    @POST("order/viewCarInfoByDriverIdApp.do")
    Observable<RequestResults<NewDriverInfoBean>> getCarinfoByDriverId(@Body Map<String, Object> params);

    /**
     * 获取所有车辆信息
     *
     * @return
     */
    @POST("order/listCarByPorjectIdApp.do")
    Observable<RequestResultsList<DriverInfoBean>> getListCarinfo(@Body Map<String, Object> params);

    /**
     * 获取火车干线的列表数据
     *
     * @param params
     * @return
     */
    @POST("trainOrder/listAllTbTrainOrderDifferentStatus.do")
    Observable<RequestResults<ListALLResults<TrainBean>>> getTrainListData(@Body Map<String, Object> params);


    /**
     * 获取火车干线的列表数据
     *
     * @param params
     * @return
     */
    @POST("trainOrder/viewTbTrainOrderById.do")
    Observable<RequestResults<TrainDetailsBean>> getTrainDetail(@Body Map<String, Object> params);

    /**
     * 获取火车干线-在途位置的列表数据
     *
     * @param params
     * @return
     */
    @POST("trainOrder/listHistoryLocationTbTrainOrder.do")
    Observable<RequestResultsList<TrainLocationBean>> getTrainLocation(@Body Map<String, Object> params);


    /**
     * 获取火车干线-在途位置保存
     *
     * @param params
     * @return
     */
    @POST("trainOrder/saveLocationTbTrainOrderApp.do")
    Observable<RequestResults> saveTrainLocation(@Body Map<String, Object> params);


    /**
     * 火运-状态更改
     *
     * @param params
     * @return
     */
    @POST("trainOrder/updateTrainOrderStatusByParamApp.do")
    Observable<RequestResults> sendTrain(@Body Map<String, Object> params);

    /**
     * 火运-承运车信息提交
     *
     * @param params
     * @return
     */
    @POST("trainOrder/saveTbTrainOrderAdmitCarNum.do")
    Observable<RequestResults> saveTbTrainorderCarNum(@Body Map<String, Object> params);


    /**
     * 汽运-取消发运
     *
     * @param params
     * @return
     */
    @POST("order/cancelTbOrderForward.do")
    Observable<RequestResults> cancelMotorOrder(@Body Map<String, Object> params);

    /**
     * 汽车短驳-更新运单状态
     *
     * @param params 相关参数
     * @return
     */
    @POST("order/appUpdateOrderStatus.do")
    Observable<RequestResults> updateMotorStatus(@Body Map<String, Object> params);


//    /**
//     * 火运-承运车信息提交
//     *
//     * @param params
//     * @return
//     */
//    @POST("trainOrder/saveTbTrainOrderAdmitCarNum.do")
//    Observable<RequestResults> saveTbTrainorderCarNum(@Body Map<String, Object> params);

}


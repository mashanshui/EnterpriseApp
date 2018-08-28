package com.shenhesoft.enterpriseapp.requestutil;


import com.shenhesoft.enterpriseapp.HttpURL;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderBean;
import com.shenhesoft.enterpriseapp.bean.EntruckOrderDetailBean;
import com.shenhesoft.enterpriseapp.bean.LocationPopBean;
import com.shenhesoft.enterpriseapp.bean.MotorDetailsBean;
import com.shenhesoft.enterpriseapp.bean.UserinfoBean;
import com.shenhesoft.enterpriseapp.bean.WaitDispatchBean;
import com.shenhesoft.enterpriseapp.net.entity.ListALLResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResults;
import com.shenhesoft.enterpriseapp.net.entity.RequestResultsList;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者：张明星
 * 创作日期：2017/10/27
 * 描述：用户相关的请求接口
 */

public interface UserService {


    /**
     * 用户登录
     *
     * @return
     */
    @POST(HttpURL.LOGIN)
    Observable<RequestResults<UserinfoBean>> userLogin(@Body Map<String, Object> params);


    /**
     * 获取验证码
     *
     * @return
     */
    @POST("login/viewVerificationByregisterPhoneApp.do")
    Observable<RequestResults> getCode(@Body Map<String, Object> params);


    /**
     * 保存新手机号
     *
     * @return
     */
    @POST("login/changeTbSystmUserPhoneApp.do")
    Observable<RequestResults> saveNewPhone(@Body Map<String, Object> params);


    /**
     * 保存新密码
     *
     * @return
     */
    @POST("login/changeTbSystemUserPasswordApp.do")
    Observable<RequestResults> saveNewPassWord(@Body Map<String, Object> params);


    /**
     * 等待调度-等待调度列表
     *
     * @return
     */
    @POST(HttpURL.WAITDISPATCH)
    Observable<RequestResults<ListALLResults<MotorDetailsBean>>> getWaitDispatchList(@Body Map<String, Object> params);

    /**
     * 等待调度-驳回列表
     *
     * @return
     */
    @POST(HttpURL.REJECT_DISPATCH)
    Observable<RequestResults<ListALLResults<WaitDispatchBean>>> getRejectDispatchList(@Body Map<String, Object> params);


    /**
     * 等待调度-核准（确认调度）
     *
     * @return
     */
    @POST(HttpURL.CHECK_ORDER)
    Observable<RequestResults> checkDispatch(@Body Map<String, Object> params);

    /**
     * 等待调度-驳回提交
     *
     * @return
     */
    @POST(HttpURL.REJECT_SUBMIT)
    Observable<RequestResults> rejectSubmit(@Body Map<String, Object> params);

    /**
     * 到货确认-异常原因提交
     *
     * @return
     */
    @POST("order/saveTbExceptionMsgApp.do")
    Observable<RequestResults> ArriveReasonSubmit(@Body Map<String, Object> params);


    /**
     * 计费确认-异常原因提交
     *
     * @return
     */
    @POST(HttpURL.IP + "/api/exceptionMsg")
    Observable<RequestResults> FeecheckUnusualSubmit(@Body Map<String, Object> params);

    /**
     * 等待调度-驳回列表-还原
     *
     * @return
     */
    @POST(HttpURL.RESTORE_ORDER)
    Observable<RequestResults> restoreOrder(@Body Map<String, Object> params);


    /**
     * 到货确认-等待卸货列表
     *
     * @return
     */
    @POST(HttpURL.WAIT_UNLOADING)
    Observable<RequestResults<ListALLResults<MotorDetailsBean>>> waitUnloading(@Body Map<String, Object> params);

    /**
     * 主页-获取气泡数据
     *
     * @return
     */
    @POST("order/appOrderCounts.do")
    Observable<RequestResults> getOrderCount(@Body Map<String, Object> params);

    /**
     * 主页-获取计费确认数量
     *
     * @return
     */
    @POST("order/appWaitBillentCounts.do")
    Observable<RequestResults> getChargeOrderCount(@Body Map<String, Object> params);

    /**
     * 到货确认-计费确认-异常列表
     *
     * @return
     */
    @POST(HttpURL.WAIT_UNLOADING_UNSUAL)
    Observable<RequestResults<ListALLResults<MotorDetailsBean>>> waitUnloadingunsual(@Body Map<String, Object> params);

    /**
     *全部异常列表
     *
     * @return
     */
    @POST("order/listTbOrderAbnormalApp.do")
    Observable<RequestResults<ListALLResults<MotorDetailsBean>>> allunsual(@Body Map<String, Object> params);

    /**
     * 新建运单-货场列表信息
     *
     * @return
     */
    @POST("order/listFreYardAppOfAdd.do")
    Observable<RequestResultsList<LocationPopBean>> getNewOrderListGoodsyard(@Body Map<String, Object> params);

    /**
     * 到货确认-货场列表
     *
     * @return
     */
    @POST(HttpURL.GOODS_YARD_LIST)
    Observable<RequestResultsList<LocationPopBean>> getlistGoodsyard(@Body Map<String, Object> params);

    /**
     * 到货确认-到货货场分配列表信息
     *
     * @return
     */
    @POST(HttpURL.ARRIVAL_GOODS_YARD_LIST)
    Observable<RequestResultsList<LocationPopBean>> getArrivalListGoodsyard(@Body Map<String, Object> params);

    /**
     * 到货确认-根据货场获得对应货位列表
     *
     * @return
     */
    @POST(HttpURL.GOODS_YARD_CHILD_LIST)
    Observable<RequestResultsList<LocationPopBean>> getlistGoodsyardChlid(@Body Map<String, Object> params);

    /**
     * 到货确认-提交货场货位信息
     *
     * @return
     */
    @POST(HttpURL.GOODS_YARD_SUBMIT)
    Observable<RequestResults> submitGoodsyard(@Body Map<String, Object> params);

    /**
     * 调度审核-提交货场货位信息
     *
     * @return
     */
    @POST("order/saveDisptachTbOrderApp.do")
    Observable<RequestResults> submitDispatchGoodsyard(@Body Map<String, Object> params);

    /**
     * 火运-装车-装车货场列表
     *
     * @return
     */
    @POST("trainOrder/listTbFreightYardByTrainOrderIdApp.do")
    Observable<RequestResultsList<LocationPopBean>> getTrainYardList(@Body Map<String, Object> params);

    /**
     * 火运-装车-装车货位列表
     *
     * @return
     */
    @POST("trainOrder/listTbCargoLocationByYardIdApp.do")
    Observable<RequestResultsList<LocationPopBean>> getTrainLocationList(@Body Map<String, Object> params);

    /**
     * 火运-装车-车型列表
     *
     * @return
     */
    @POST("trainOrder/listTbTrainTypeApp.do")
    Observable<RequestResultsList<LocationPopBean>> getTrainTypeList(@Body Map<String, Object> params);


    /**
     * 火运-装车/到货-卸货运单列表
     *
     * @return
     */
    @POST("trainOrder/listTbTrainOrderCargoPalceByTrainOrderIdApp.do")
    Observable<RequestResultsList<EntruckOrderDetailBean>> getOrderDetailList(@Body Map<String, Object> params);


    /**
     * 火运-卸货-车号列表
     *
     * @return
     */
    @POST("trainOrder/listTbTrainOrderCargoPalceByTrainOrderIdCarTypeId.do")
    Observable<RequestResultsList<LocationPopBean>> getTrainNumList(@Body Map<String, Object> params);


    /**
     * 火运-装车-装车信息提交
     *
     * @return
     */
    @POST("trainOrder/saveTbTraninOrderTruckLoadApp.do")
    Observable<RequestResults> submitEntruckinfo(@Body Map<String, Object> params);

    /**
     * 火运-卸车-卸车信息提交
     *
     * @return
     */
    @POST("trainOrder/saveTbTrainOrderArrivalApp.do")
    Observable<RequestResults> submitUnloadinfo(@Body Map<String, Object> params);


    /**
     * 火运-等待回单-等待回单信息提交
     *
     * @return
     */
    @POST("trainOrder/saveWaybillReceiptTrainOrderApp.do")
    Observable<RequestResults> submitWaitCall(@Body Map<String, Object> params);


//    /**
//     * 发送验证码
//     *
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(HttpURL.SMS_SEND)
//    Observable<CommonResult> sendsms(@Field("data") String data);
//
//    /**
//     * 验证验证码
//     *
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(HttpURL.CHECK_SMS_CODE)
//    Observable<CommonResult> checkSms(@Field("data") String data);
//

}

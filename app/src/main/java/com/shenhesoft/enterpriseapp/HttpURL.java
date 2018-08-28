package com.shenhesoft.enterpriseapp;

/**
 * 作者：Tornado
 * 创作日期：2017/7/13.
 * 描述：APP网络请求 接口
 */

public class HttpURL {

//    外网IP
//    public static final String IP = "http://139.196.100.149:8081/logistics-manage-web";

    public static final String IP = "http://192.168.1.117:7012/logistics-manage-web";

    //测试IP
//    public static final String IP = "http://192.168.2.104:8088/logistics-manage-web";

    //服务器
    private static final String SERVER = "/app/";

    //请求的base URL
    public static final String POST_URL = IP + SERVER;

    //登陆
    public static final String LOGIN = "login/systemUserDoLogin.do";

    //等待调度列表
    public static final String WAITDISPATCH = "order/listAllTbOrderDifferentStatus.do";

    //核准(确认调度)
    public static final String CHECK_ORDER = "order/approvalTbOrderApp.do";

    //等待调度-驳回列表
    public static final String REJECT_DISPATCH = "order/listTbOrderTurndownApp.do";

    //等待调度-驳回提交
    public static final String REJECT_SUBMIT = "order/turndownTbOrderApp.do";

    //等待调度-驳回列表-还原
    public static final String RESTORE_ORDER = "order/restoreTbOrderTurndownApp.do";

    //到货确认-等待卸货列表
    public static final String WAIT_UNLOADING = "order/listAllTbOrderDifferentStatus.do";

    //到货确认-计费确认-异常列表
    public static final String WAIT_UNLOADING_UNSUAL = "order/listTbOrderExceptionApp.do";

    //到货确认-获得货场列表信息
    public static final String ARRIVAL_GOODS_YARD_LIST = "order/listGoodsYardApp.do";
    //调度审核-获得货场列表信息
    public static final String GOODS_YARD_LIST = "order/listFreYardAppOfDispatch.do";

    //到货确认-获得货场列表信息
    public static final String GOODS_YARD_CHILD_LIST = "order/listTbCargoLocationByFreightIdApp.do";

    //到货确认-保存货场货位信息
    public static final String GOODS_YARD_SUBMIT = "order/saveGuideTbOrderApp.do";
}

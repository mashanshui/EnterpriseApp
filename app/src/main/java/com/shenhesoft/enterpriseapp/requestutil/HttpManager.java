package com.shenhesoft.enterpriseapp.requestutil;

import android.util.Log;

import com.shenhesoft.enterpriseapp.HttpURL;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 作者：Tornado
 * 创作日期：2017/8/8.
 * 描述：基于Retrofit进行封装的 网络请求类
 * <p>
 * （ 纠结半天还是决定不搭配RxJava了，虽然现在Retorfit+RxJava很火，好像必须这样搭配才是主流，
 * 但是我觉得不能为了跟随主流而使用RxJava，RxJava在性能和效率上好像并没有什么优势，使用它反而代码量会变多，
 * 但它通过大量操作符进行的流式处理使逻辑与结构十分清晰，不过说真的上手度实在不敢恭维，不在项目中使用的
 * 很大原因也是自己还不是能非常熟练使用吧，还有若是后来维护者没接触过RxJava，那也给别人带去了困扰！ ）
 * <p>
 * 三天后.... 原谅我又改变主意了！
 *
 */
public class HttpManager {

    private Retrofit retrofit;
    private OrderService orderservice;
    private UserService userService;


    private HttpManager() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i("logging", "==log==" + message);
                    }
                }
        );
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        Interceptor mTokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
//                if (Your.sToken == null || alreadyHasAuthorizationHeader(originalRequest)) {
//                    return chain.proceed(originalRequest);
//                }
                Request authorised = originalRequest.newBuilder()
//                        .addHeader("time", String.valueOf(System.currentTimeMillis()))
                        .build();
                return chain.proceed(authorised);
            }
        };

        //创建OkHttpClient 设置相关参数
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new LoggingIntercepotr())
//                .addInterceptor(new TokenInterceptor())
//                .addInterceptor(mTokenInterceptor)
//                .addInterceptor(new SaveCookiesInterceptor())
                .build();


        //创建Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(HttpURL.POST_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        orderservice = retrofit.create(OrderService.class);
        userService = retrofit.create(UserService.class);
    }


    //在访问HttpManager时创建单例
    private static class SingletonHolder {
        private static final HttpManager httpManager = new HttpManager();
    }

    /**
     * 开放一个获取实例的方法
     *
     * @return HttpManager实例
     */
    public static synchronized HttpManager getInstance() {
        return SingletonHolder.httpManager;
    }


    public UserService getUserService() {
        return userService;
    }

    public OrderService getOrderService() {
        return orderservice;
    }


    /**
     * 开始一个请求任务
     *
     * @param observable 被观察对象（即一个网络请求任务）
     * @param observer   观察者（对任务执行的全方位监听、线程控制、数据处理、异常处理）
     */
    public void statrPostTask(Observable observable, HttpObserver observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    //随机数
    private static long Getrand() {
        return System.currentTimeMillis();
    }

    public void statrPostTaskFunc(Observable observable, HttpObserver observer) {
        observable.map(observer.getFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}

package com.shenhesoft.enterpriseapp.requestutil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.shenhesoft.enterpriseapp.utils.IToast;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 作者：Tornado
 * 创作日期：2017/8/9.
 * 描述：网络请求专用的Observer
 * （封装了LoaidngDiaog）
 */

public class HttpObserver<T> implements Observer<T> {
    private static final String TAG = "HttpObserver";
    public static final int REQUEST_COMPLETE = 100;
    public static final int REQUEST_ERROR = 200;

    //rxjava2新增接口，简单理解为观察者与观察对象的连接相关操作
    private Disposable disposable;

    private Context context;

    //处理数据的回调
    private DisposeData<T> disposeData;

    //失败的回掉
    private ErrorDisposeData errorDisposeData;

    private boolean isShowErrorMsg = true;


    /**
     * 当你看这个，发现我还在构造中传递了一个Dialog时，先别嗤之以鼻。
     * 我也知道可以写个LoadingDialogHandler来优雅实现，奈何谷歌留下了坑，没错就是谷歌的锅。
     * 不知道你有没有遇到过这个异常：android.view.WindowManager$BadTokenException: Unable to add window
     * -- is your activity running?
     * 当我们做一些异步任务/后台操作时，想要显示一个带有活动上下文Context的对话框，在这个过程中，
     * 我们的活动(Activity)正在因为某种原因而自我毁灭，Android操作系统应该处理这种情况，但目前还没有。
     * 我们可以用
     * if(!activity.isFinishing()){dialog.show()}或者if(activity.hasWindowFocus()){dialog.show()}
     * 两种方法来避免这个异常，但却无法解决Dialog不显示的问题。所以只好回到最初的写法，在Activity中创建Dialog，
     * 再交给Observer来控制...
     * 如果你找到了更优的解决方案欢迎讨论
     */
    private Dialog loadingDialog;
    private BGARefreshLayout refreshLayout;
    private Handler handler;

    /**
     * 数据处理接口
     *
     * @param <T>
     */
    public interface DisposeData<T> {
        /**
         * 在UI更新数据处实现
         *
         * @param data
         */
        void onDispose(T data);
    }

    public interface ErrorDisposeData {
        void errorDispose(Throwable throwable);
    }

    public HttpObserver(Context context, DisposeData<T> disposeData) {
        this.context = context;
        this.disposeData = disposeData;
    }

    public HttpObserver(Context context, DisposeData<T> disposeData, ErrorDisposeData errorDisposeData) {
        this.context = context;
        this.disposeData = disposeData;
        this.errorDisposeData = errorDisposeData;
    }

    public HttpObserver(Context context, Dialog loadingDialog, DisposeData<T> disposeData) {
        this.context = context;
        this.disposeData = disposeData;
        this.loadingDialog = loadingDialog;

        if (loadingDialog == null) {
            return;

//            throw new NullPointerException("loadingDialog是空的，" +
//                    "请在要使用的Activity的onCreate()中调用initLoadingDialog()方法实例化");
        }

        //当用户点击返回键 关闭dialog时，即认为用户想取消本次请求
        loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (disposable != null) {
                    //取消关联，即取消本次请求
                    disposable.dispose();
                }
            }
        });
    }

    public HttpRequestFunc getFunc() {
        return new HttpRequestFunc<T>();
    }

    //private HttpRequestFuncimplements<

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    @Override
    public void onNext(T value) {
        disposeData.onDispose(value);
    }

    @Override
    public void onError(Throwable e) {
        dismissDialog();
        refreshOrLoadmoreFinsh();
        if (errorDisposeData != null) {
            errorDisposeData.errorDispose(e);
        }
        Log.e("request_error", "==error==" + e);
        if (handler != null) {
            handler.sendEmptyMessage(REQUEST_ERROR);
        }
        if (!isShowErrorMsg) {
            return;
        }
        //将错误统一处理
        if (e instanceof ConnectException
                || e instanceof SocketException
                || e instanceof UnknownHostException
                || e instanceof SocketTimeoutException) {
            IToast.showShort("未连接到服务器！");
            return;
        }
        if (e instanceof HttpException) {
            HttpException he = (HttpException) e;
            IToast.showShort("服务器发生错误（HTTP_CODE:" + he.code() + ")");
            return;
        }
        if (e instanceof JsonParseException ||
                e instanceof JSONException ||
                e instanceof ParseException) {
            IToast.showShort("解析数据发生错误");
            return;
        }
        if (e instanceof RequestErrorException) {
            IToast.showShort(e.getMessage());
            return;
        }
        IToast.showShort("抱歉，发生一个未知错误！");

    }

    @Override
    public void onComplete() {
//        Log.i("aaaa", "==加载完成==");
        dismissDialog();
        refreshOrLoadmoreFinsh();
        if (handler != null) {
            handler.sendEmptyMessage(REQUEST_COMPLETE);
        }
    }


    /**
     * 设置Handler
     *
     * @param handler
     * @return
     */
    public HttpObserver setHandler(Handler handler) {
        this.handler = handler;
        return this;
    }

    /**
     * 设置LoadingDialog
     *
     * @param dialog
     * @return
     */
    public HttpObserver setLoadingDialog(Dialog dialog) {
        this.loadingDialog = dialog;
        if (loadingDialog == null) {
            throw new NullPointerException("loadingDialog是空的，" +
                    "请在要使用的Activity的onCreate()中调用initLoadingDialog()方法实例化");
        }

        //当用户点击返回键 关闭dialog时，即认为用户想取消本次请求
        loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (disposable != null) {
                    //取消关联，即取消本次请求
                    disposable.dispose();
                }
            }
        });
        return this;
    }


    /**
     * 设置RefreshLayout,用来控制刷新完成，及加载完成
     *
     * @param refreshLayout 上拉刷新和加载更多
     * @return
     */
    public HttpObserver setRefreshLayout(BGARefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
        return this;
    }


    public HttpObserver setShowErrorMsg(boolean showErrorMsg) {
        isShowErrorMsg = showErrorMsg;
        return this;
    }

    /**
     * 关闭LoadingDialog
     */
    private void dismissDialog() {
        if (loadingDialog == null) {
            return;
        }
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 刷新或加载完成
     */
    private void refreshOrLoadmoreFinsh() {
        if (refreshLayout == null) {
            return;
        }
        if (refreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
            refreshLayout.endRefreshing();
        }
        if (refreshLayout.isLoadingMore()) {
            refreshLayout.endLoadingMore();
        }
    }

}

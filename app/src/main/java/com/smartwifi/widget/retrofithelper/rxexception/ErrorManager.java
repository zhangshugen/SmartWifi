package com.smartwifi.widget.retrofithelper.rxexception;

import com.smartwifi.bean.ServerErrorMessageBean;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.widget.retrofithelper.utils.NetUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;


/**
 * Created by wificityios on 2018/7/12
 */
public class ErrorManager {
    public ErrorManager() {
    }

    /**
     * 对错误数据进行处理，返回对应字符串提示信息
     *
     * @param e 错误数据接口
     * @return 返回对应错误提示信息
     */
    public static ServerErrorMessageBean handleError(ErrorBundle e) {
        e.getException().printStackTrace();
        ServerErrorMessageBean bean = new ServerErrorMessageBean();
        String message;
        if (!NetUtils.isNetworkConnected()) {
            message = "网络中断，请检查您的网络状态";
            ToastUtils.showShort(message);
        } else if (e.getException() instanceof SocketTimeoutException) {
            message = "网络连接超时，请检查您的网络状态";
            ToastUtils.showShort(message);
        } else if (e.getException() instanceof ConnectException) {
            message = "连接异常，请检查您的网络状态ConnectException";
            ToastUtils.showShort(message);
        } else if (e.getException() instanceof NetworkConnectionException) {
            message = "网络中断，请检查您的网络状态";
            ToastUtils.showShort(message);
        } else if (e.getException() instanceof ServerException) {
            int code = ((ServerException) e.getException()).getCode();
            //在这里你可以获取code来判断是什么类型，进行相应处理，比如token失效了可以实现跳转到登录页面
            message = e.getMessage();
            bean.code=code;
        } else {
            message = "连接服务器失败,请稍后再试";
            //ToastUtils.showShort(message);
        }
        bean.message=message;

        return bean;
    }
}

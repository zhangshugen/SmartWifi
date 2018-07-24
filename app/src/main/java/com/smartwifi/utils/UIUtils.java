package com.smartwifi.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import com.smartwifi.base.BaseApplication;

/**
 * Created on 2018/7/9. UI相关的工具类
 */

public class UIUtils {
    /**
     * 得到Resouce对象
     */
    public static Resources getResource() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    /**
     * 得到String.xml中的字符串,带占位符
     */
    public static String getString(int id, Object... formatArgs) {
        return getResource().getString(id, formatArgs);
    }

    /**
     * 得到String.xml中的字符串数组
     */
    public static String[] getStringArr(int resId) {
        return getResource().getStringArray(resId);
    }

    /**
     * 得到colors.xml中的颜色
     */
    public static int getColor(int colorId) {
        return getResource().getColor(colorId);
    }
    public static Context  getContext(){
        return BaseApplication.getContext();
    }
    public static void postTaskSafely(Runnable task) {
        int curThreadId = android.os.Process.myTid();

        if (curThreadId == getMainThreadId()) {// 如果当前线程是主线程
            task.run();
        } else {// 如果当前线程不是主线程
            getMainThreadHandler().post(task);
        }
    }
    /**
     * 得到主线程id
     */
    public static long getMainThreadId() {
        return BaseApplication.getMainTreadId();
    }
    /**
     * 得到主线程Handler
     */
    public static Handler getMainThreadHandler() {
        return BaseApplication.getMainHandler();
    }

    public static void postDelayed(Runnable runnable,long delayMillis) {
        getMainThreadHandler().postDelayed(runnable,delayMillis);
    }
}

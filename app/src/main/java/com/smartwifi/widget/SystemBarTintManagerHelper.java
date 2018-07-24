package com.smartwifi.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/28
 * @Describe
 */

public class SystemBarTintManagerHelper {

    private static SystemBarTintManager tintManager;

    private static class SystemBarTintManagerHelperSingleton {
        private static final SystemBarTintManagerHelper instance = new SystemBarTintManagerHelper();
    }

    private SystemBarTintManagerHelper() {
    }

    public static final SystemBarTintManagerHelper getInsatance() {
        return SystemBarTintManagerHelperSingleton.instance;
    }

    public   void initStateBar(AppCompatActivity appCompatActivity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true, appCompatActivity);
        }
        tintManager = new SystemBarTintManager(appCompatActivity);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        // 激活导航栏设置
        tintManager.setNavigationBarTintEnabled(true);
        // 设置一个状态栏颜色
        tintManager.setStatusBarTintResource(color);
    }

    public void setStatusBarTintResource(int color){
        tintManager.setStatusBarTintResource(color);
    }

    @TargetApi(19)
    private   void setTranslucentStatus(boolean on, AppCompatActivity appCompatActivity) {
        Window win = appCompatActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}

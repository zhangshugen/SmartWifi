package com.smartwifi.widget.mvvm;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 67337 on 2018/3/8.
 * 界面初始化一些操作
 */

public interface ViewInitializeLifeCycle {
    void initView(Context context, View rootView, AttributeSet attrs);
    void initData(Context context);
    void initEvent(Context context);

}

package com.smartwifi.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.smartwifi.BR;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/28
 * @Describe
 */

public class TestBean extends BaseObservable {
    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    @Bindable
    public String text;


}

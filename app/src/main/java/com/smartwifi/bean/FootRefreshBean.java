package com.smartwifi.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.smartwifi.BR;


/**
 * Created by Administrator on 2018/7/9
 */

public class FootRefreshBean extends BaseObservable {
    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    @Bindable
    public int status;
    @Bindable
    public String text;
}

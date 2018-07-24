package com.smartwifi.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.smartwifi.BR;


/**
 * Created by Administrator on 2018/7/19.
 */

public class FootLoadMoreBean extends BaseObservable {
    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }
    public void setShowLine(boolean showLine) {
        isShowLine = showLine;
        notifyPropertyChanged(BR.isShowLine);
    }
    @Bindable
    public int status;
    @Bindable
    public String text;
    @Bindable
    public boolean isShowLine;
}

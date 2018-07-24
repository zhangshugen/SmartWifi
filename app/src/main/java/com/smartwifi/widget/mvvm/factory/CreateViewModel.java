package com.smartwifi.widget.mvvm.factory;

import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2018/2/7 0007.c
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateViewModel {
    Class<? extends BaseViewModel> value();
}

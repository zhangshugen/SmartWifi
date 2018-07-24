package com.smartwifi.widget.mvvm.proxy;

import com.smartwifi.widget.mvvm.factory.ViewModelFactory;
import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;

/**
 * ViewModel 的代理类
 */

public interface BaseViewModelProxy<VM extends BaseViewModel> {
    /**
     * 由于不是所有的ViewModel 都适用于注解模式生成ViewModel对象,所以传入一个基类工厂,获取到ViewModel
     */
    void setViewModelFactory(ViewModelFactory factory);

    ViewModelFactory getViewModelFactory();

    /***/
    VM getViewModel();

}

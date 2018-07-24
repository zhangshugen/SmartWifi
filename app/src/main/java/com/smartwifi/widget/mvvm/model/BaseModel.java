package com.smartwifi.widget.mvvm.model;

import android.databinding.ViewDataBinding;

import com.smartwifi.widget.mvvm.DisposableLifeCycleListener;
import com.smartwifi.widget.mvvm.view.BaseMVVMView;
import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class BaseModel {
    protected DisposableLifeCycleListener mLifeCycleListener;

    public <E extends BaseModel, D extends ViewDataBinding> void cancleNet() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
    }

    public <V extends BaseMVVMView> void setDisposableLifeCycleListener(DisposableLifeCycleListener lifeCycleListener) {
        this.mLifeCycleListener = lifeCycleListener;
    }
}

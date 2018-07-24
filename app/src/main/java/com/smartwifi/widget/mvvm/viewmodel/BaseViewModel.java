package com.smartwifi.widget.mvvm.viewmodel;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.smartwifi.utils.TUtil;
import com.smartwifi.widget.mvvm.DisposableLifeCycleListener;
import com.smartwifi.widget.mvvm.UILifeCycle;
import com.smartwifi.widget.mvvm.model.BaseModel;
import com.smartwifi.widget.mvvm.proxy.BaseViewModelProxyMethod;
import com.smartwifi.widget.mvvm.proxy.ViewModelDisposableProxy;
import com.smartwifi.widget.mvvm.view.BaseMVVMView;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/2/7 0007.
 */

public class BaseViewModel<V extends BaseMVVMView, D extends ViewDataBinding, E extends BaseModel> implements BaseViewModelProxyMethod<V>, UILifeCycle<V, D>, DisposableLifeCycleListener {


    protected V mView;
    protected D mBinding;
    protected E mModel;
    private ViewModelDisposableProxy mProxy;
    protected FragmentActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mModel = TUtil.getT(this, 2);
     //   mModel.setDisposableLifeCycleListener(this);
        if (isUseDisposableProxy())
            mProxy = new ViewModelDisposableProxy();
    }

    @Override
    public void onStart() {
        if (isUseDisposableProxy())
            mProxy.onStart();
    }

    @Override
    public void onAttachView(V view) {
        this.mView = view;
    }

    @Override
    public void onResume(V view) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {
        if (isUseDisposableProxy())
            mProxy.onStop(isStopCancelRxJava());
    }

    @Override
    public void onDetachView() {
        this.mView = null;
        this.mBinding = null;
        this.mActivity = null;
    }

    @Override
    public void onDestroyViewModel() {

    }

    @Override
    public void onDestroy() {
        if (isUseDisposableProxy())
            mProxy.onDestroy();
    }

    @Override
    public Boolean onKeyDown(int keyCode, KeyEvent event) {
        return null;
    }


    @Override
    public Boolean onBackPressed() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public boolean isStopRxJava() {
        return isStopCancelRxJava();
    }

    private boolean isStopCancelRxJava() {
        return false;
    }


    private boolean isUseDisposableProxy() {
        return true;
    }

    @Override
    public void onCreateViewModel(@Nullable Bundle savedState) {

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }

    @Override
    public void setViewBinding(D mBinding) {
        this.mBinding = mBinding;
    }

    @Override
    public boolean addRxStop(Disposable disposable) {
        if (isUseDisposableProxy())
            return mProxy.addRxStop(disposable);
        return false;
    }

    @Override
    public boolean addRxDestroy(Disposable disposable) {
        if (isUseDisposableProxy())
            return mProxy.addRxDestroy(disposable);
        return false;
    }

    @Override
    public void remove(Disposable disposable) {
        if (isUseDisposableProxy())
            mProxy.remove(disposable);
    }


    public void setActivity(FragmentActivity activity) {
        this.mActivity = activity;
    }
}

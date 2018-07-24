package com.smartwifi.widget.mvvm.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartwifi.widget.mvvm.ActivityLiftCycle;
import com.smartwifi.widget.mvvm.factory.ViewModelFactory;
import com.smartwifi.widget.mvvm.factory.ViewModelFactoryImpl;
import com.smartwifi.widget.mvvm.proxy.BaseViewModelProxy;
import com.smartwifi.widget.mvvm.proxy.ViewModelProxyImpl;
import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;

import butterknife.ButterKnife;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/13
 * @Describe MVVM的Fragment基类, Proxy管理 View与ViewModel的生命周期
 */

public abstract class BaseMVVMFragment<VM extends BaseViewModel, D extends ViewDataBinding> extends Fragment implements BaseViewModelProxy<VM>, ActivityLiftCycle, BaseMVVMView {
    private static final String VIEW_MODEL_SAVE_KEY = "view_model_save_key";

    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    protected ViewModelProxyImpl<VM> mProxy;
    protected View mRootView;
    protected D mBinding;
    private VM mViewModel;
    protected FragmentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (FragmentActivity) context;
    }

    @Override
    public void setViewModelFactory(ViewModelFactory factory) {
        mProxy.setViewModelFactory(factory);
    }

    @Override
    public ViewModelFactory getViewModelFactory() {
        return mProxy.getViewModelFactory();
    }

    @Override
    public VM getViewModel() {
        return mProxy.getViewModel();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isUseProxy()) {
            mProxy = new ViewModelProxyImpl<VM>(ViewModelFactoryImpl.<VM>createViewModelFactory(getClass()));
            mViewModel = getViewModel();
            mProxy.onCreate(savedInstanceState);
            mProxy.onAttachViewModelView(this);
            mProxy.setActivity(mActivity);
        }


    }

    public boolean isUseProxy() {
        return true;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() <= 0) throw new NullPointerException("layout is null ");
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            if (isUseDataBinding()) {
                mBinding = DataBindingUtil.bind(mRootView);
                if (isUseProxy())
                    mProxy.setViewBinding(mBinding);
            } else {
                ButterKnife.bind(this, mRootView);
            }
            initView();
            initData();
            initEvent();
        }
        return mRootView;
    }

    protected boolean isUseDataBinding() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isUseProxy())
            mProxy.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isUseProxy())
            mProxy.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isUseProxy())
            mProxy.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isUseProxy())
            mProxy.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isUseProxy())
            outState.putBundle(VIEW_MODEL_SAVE_KEY, mProxy.onSaveInstanceState());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isUseProxy())
            mProxy.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isUseProxy())
            mProxy.onActivityResult(requestCode, resultCode, data);
    }

}
